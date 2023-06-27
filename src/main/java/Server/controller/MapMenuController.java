package Server.controller;

import Model.Buildings.Building;
import Model.*;
import Model.MapCellItems.Rock;
import Model.MapCellItems.Tree;
import Model.People.Soldier;
import Utils.CheckMapCell;
import View.Enums.Messages.MapMenuMessages;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MapMenuController {
    private int xCell;
    private int yCell;
    private int xMap;
    private int yMap;
    public String showMap(int x, int y) {
        String map ;
        if (!CheckMapCell.validationOfX(x)) return "Show details failed : Coordinate of x is out of bounds";
        if (!CheckMapCell.validationOfY(y)) return "Show details failed : Coordinate of y is out of bounds";
        xCell = x;
        yCell = y;
        xMap = getAppropriateX(x);
        yMap = getAppropriateY(y);
        map = showMapCells(x, y);
        return map;
    }

    private int getAppropriateX(int x) {
        if (x < 3) return 3;
        return Math.min(x, Database.getCurrentMapGame().getWidth() - 3);
    }
    private int getAppropriateY(int y) {
        if (y < 3) return 3;
        return Math.min(y, Database.getCurrentMapGame().getLength() - 3);
    }

    private String showMapCells(int x, int y) {
        if (!CheckMapCell.validationOfX(x)) return "";
        if (!CheckMapCell.validationOfY(y)) return "";
        String data = "";
        for (int i = 0; i < 5 * 3 + 6; i++) {
            for (int j = 0; j < 5 * 3 + 6; j++) {
                if (i % 4 == 0) {
                    data += "-";
                }
                else if (j % 4 == 0)
                    data += "|";
                else {
                    MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(xMap - 2 + j / 4, yMap - 2 + i / 4);
                    if (xMap - 2 + j / 4 == x && yMap - 2 + i / 4 == y)
                        data += mapCell.getMaterialMap().getColor() + Color.ANSI_RED;
                    else if (mapCell.getMaterialMap().getColor().equals(Color.ANSI_BLACK_BACKGROUND))
                        data += mapCell.getMaterialMap().getColor() + Color.ANSI_WHITE;
                    else data += mapCell.getMaterialMap().getColor() + Color.ANSI_BLACK;
                    data += mapCell.objectInCell();
                    data += Color.ANSI_RESET;
                }
            }
            data += "\n";
        }
        return data;
    }

    public String moveMap(int[] directions) {
        String output = "";
        int transversalMove = directions[1] - directions[3];
        int longitudinalMove = directions[0] - directions[2];
        output += isSafeToMove(transversalMove, longitudinalMove) + "\n";
        return output += showMap(xCell, yCell);
    }
    public String isSafeToMove(int transversalMove, int longitudinalMove) {
        int width = xCell + transversalMove;
        int length = yCell - longitudinalMove;
        boolean widthOutOfMap = width < 1 || width > Database.getCurrentMapGame().getWidth();
        boolean lengthOutOfMap = length < 1 || length > Database.getCurrentMapGame().getLength();
        if (widthOutOfMap && lengthOutOfMap) return "Both coordinates out of map";
        if (widthOutOfMap) {
            yCell = length;
            return "Length out of map";
        }
        if (lengthOutOfMap) {
            xCell = width;
            return "Width out of map";
        }
        xCell = width;
        yCell = length;
        return "Move successfully\n" +
                "New map coordinates are : \n" +
                "x : " + xCell + "\n" +
                "y : " + yCell;
    }

    public String showDetails(int x, int y) {
        String details = "";
        if (!CheckMapCell.validationOfX(x)) return "Show details failed : Coordinate of x is out of bounds";
        if (!CheckMapCell.validationOfY(y)) return "Show details failed : Coordinate of y is out of bounds";

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        details += "MapCell with coordinates of x : " + x + " and y : " + y + "\n"
                + "Texture : " + mapCell.getMaterialMap().getMaterial() + "\n";

        if(mapCell.haveBuilding()) details += "Building : " + mapCell.getBuilding().toString() + "\n";
        else if (mapCell.haveAttackTools()) details += "AttackTool : "
                + mapCell.getAttackToolsAndMethods().getName() + "\n";
        else if (mapCell.getWall() != null) details += mapCell.getWall().toString() ;
        else if (mapCell.getTree() != null) details += "Tree : " + mapCell.getTree().getTypeOfTree().getType() + "\n";
        else if (mapCell.getRock() != null) details += "Have rock\n" ;
        else if (mapCell.getStair() != null) details += mapCell.getStair().toString();
        details += "Number of soldiers in this cell : " + mapCell.getSoldier().size() + "\n";
        for (Soldier soldier : mapCell.getSoldier()) {
            details += soldier.toString();
        }
        return details;
    }

    public MapMenuMessages setTextureOfOneBlock(int x, int y, String type) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (MaterialMap.getTextureMap(type) == null) return MapMenuMessages.INVALID_TYPE;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return MapMenuMessages.CELL_IS_FULL;
        if (Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getSoldier().size() != 0)
            return MapMenuMessages.CELL_IS_FULL;
        for (User user : Database.getUsersInTheGame()) {
            Empire empire = user.getEmpire();
            int xSet = empire.getHeadquarter().getX();
            int ySet = empire.getHeadquarter().getY() + 1;
            if (x == xSet && y == ySet) return MapMenuMessages.CELL_IS_FULL;
        }

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        mapCell.setMaterialMap(MaterialMap.getTextureMap(type));

        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages setTextureMultipleBlocks(int x1, int x2, int y1, int y2, String type) {

        if (!CheckMapCell.validationOfX(x1)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfX(x2)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y1)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y2)) return MapMenuMessages.Y_OUT_OF_BOUNDS;

        if (MaterialMap.getTextureMap(type) == null) return MapMenuMessages.INVALID_TYPE;

        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++)
                setTextureOfOneBlock(i, j, type);

        return MapMenuMessages.SUCCESS;
    }

    public ArrayList<ImageView> clearBlock(int x, int y) {
        ArrayList<ImageView> imageViews = new ArrayList<>();

        if (!CheckMapCell.validationOfX(x)) return imageViews;
        if (!CheckMapCell.validationOfY(y)) return imageViews;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        imageViews.add(BuildingMenuController.buildingImageViewHashMap.get(mapCell.getBuilding()));
        for (Soldier soldier : mapCell.getSoldier()) {
            imageViews.add(UnitMenuController.soldierImageViewHashMap.get(soldier));
        }

        mapCell.setBuilding(null);
        mapCell.getMapCellItems().clear();
        mapCell.getItems().clear();
        mapCell.setAttackToolsAndMethods(null);
        mapCell.getPeople().clear();
//        mapCell.setMaterialMap(MaterialMap.getTextureMap("land"));

        return imageViews;
    }

    public MapMenuMessages dropRock(int x, int y, String direction) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (Direction.getDirection(direction) == null) return MapMenuMessages.INVALID_DIRECTION;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return MapMenuMessages.CELL_IS_FULL;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        if (mapCell.getMaterialMap().isWaterZone()) return MapMenuMessages.INAPPROPRIATE_TEXTURE;

        Rock rock = new Rock(Database.getCurrentUser(), Direction.getDirection(direction));
        mapCell.addMapCellItems(rock);

        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages dropTree(int x, int y, String type) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (Tree.getTreeType(type) == null) return MapMenuMessages.INVALID_TYPE;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return MapMenuMessages.CELL_IS_FULL;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        if (mapCell.getMaterialMap().isWaterZone()) return MapMenuMessages.INAPPROPRIATE_TEXTURE;

        Tree tree = new Tree(Database.getCurrentUser(), Tree.getTreeType(type));

        mapCell.addMapCellItems(tree);

        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages dropBuilding(int x, int y, String type, ImageView imageView) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (Database.getBuildingDataByName(type) == null) return MapMenuMessages.INVALID_TYPE;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return MapMenuMessages.CELL_IS_FULL;

        for (User user : Database.getUsersInTheGame()) {
            Empire empire = user.getEmpire();
            int xSet = empire.getHeadquarter().getX();
            int ySet = empire.getHeadquarter().getY() + 1;
            if (x == xSet && y == ySet) return MapMenuMessages.CELL_IS_FULL;
        }

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        if ((mapCell.getMaterialMap().isWaterZone() && !type.equals("drawbridge") )||
                (!mapCell.getMaterialMap().isWaterZone() && type.equals("drawbridge"))
                ||(type.equals("ironMine") && !mapCell.getMaterialMap().equals(MaterialMap.textureMap.STONE))
                || (type.equals("pitchRig")) && !mapCell.getMaterialMap().equals(MaterialMap.textureMap.PLAIN))
            return MapMenuMessages.INAPPROPRIATE_TEXTURE;


        Building building = new Building(Database.getCurrentUser(),
                Objects.requireNonNull(Database.getBuildingDataByName(type)), x, y);

        mapCell.addBuilding(building);
        if (building.getBuildingName().equals("oxTether")) nearEnemyGatehouse(x, y);
        BuildingMenuController.buildingImageViewHashMap.put(building, imageView);

        return MapMenuMessages.SUCCESS;
    }

    private void nearEnemyGatehouse(int x, int y) {
        for (User user : Database.getUsersInTheGame()) {
            Empire empire = user.getEmpire();
            int xHeadquarter = empire.getHeadquarter().getX();
            int yHeadquarter = empire.getHeadquarter().getY();
            if (Math.sqrt(Math.pow((xHeadquarter - x), 2) + Math.pow((yHeadquarter - y), 2)) <= 4 &&
                    !Database.getCurrentUser().getUsername().equals(user.getUsername())) {
                empire.poison();
            }
        }
    }

    public MapMenuMessages dropUnit(int x, int y, String type, int count, ImageView imageView) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (Database.getSoldierDataByName(type) == null) return MapMenuMessages.INVALID_TYPE;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return MapMenuMessages.CELL_IS_FULL;

        if (Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getMaterialMap().isWaterZone())
            return MapMenuMessages.INAPPROPRIATE_TEXTURE;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        for (int i = 0; i < count; i++) {
            Soldier soldier = new Soldier(Database.getCurrentUser(), Database.getSoldierDataByName(type));
            Database.getCurrentUser().getEmpire().addPopulation(soldier);
            mapCell.addPeople(soldier);
            soldier.setCoordinates(mapCell.getX(), mapCell.getY());
            UnitMenuController.soldierImageViewHashMap.put(soldier, imageView);
        }

        return MapMenuMessages.SUCCESS;
    }

    public HashMap<String, Integer> getSoldiers(double startCol, double startRow, double endCol, double endRow) {
        HashMap<String, Integer> soldiers = new HashMap<>();
        for (int i = (int) startCol; i < endCol + 1; i++) {
            for (int j = (int) startRow; j < endRow + 1; j++) {
                MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(i, j);
                for (Soldier soldier : mapCell.getSoldier()) {
                    if (!soldier.getOwner().equals(Database.getCurrentUser())) continue;
                    if (soldiers.containsKey(soldier.getName()))
                        soldiers.put(soldier.getName(), soldiers.get(soldier.getName()) + 1);
                    else soldiers.put(soldier.getName(), 1);
                }
            }
        }
        return soldiers;
    }

    public String getData(double startCol, double startRow, double endCol, double endRow) {
        String data = "";
        for (int i = (int) startCol; i < endCol + 1; i++) {
            for (int j = (int) startRow; j < endRow + 1; j++) {
                data += showDetails(i, j);
            }
        }
    return data;
    }


    public String dropViaPaste(int droppedX, int droppedY, int copyX, int copyY, ImageView imageView) {
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(copyX, copyY);
        if (mapCell.getBuilding() == null || !mapCell.getBuilding().getOwner().equals(Database.getCurrentUser()))
            return null;
        if (dropBuilding(droppedX, droppedY, mapCell.getBuilding().getBuildingName(), imageView).equals(MapMenuMessages.SUCCESS))
            return mapCell.getBuilding().getBuildingName();
        return null;
    }

    public String getBuildingName(int copyX, int copyY) {
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(copyX, copyY);
        if (mapCell.getBuilding() == null || !mapCell.getBuilding().getOwner().equals(Database.getCurrentUser()))
            return null;
        else return mapCell.getBuilding().getBuildingName();
    }
}

