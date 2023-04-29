package Controller;

import Model.*;
import Model.Buildings.Building;
import Model.MapCellItems.Rock;
import Model.MapCellItems.Tree;
import Model.People.Soldier;
import Utils.CheckMapCell;
import View.Enums.Messages.MapMenuMessages;
import View.MapMenu;

public class MapMenuController {
    private int xMap;
    private int yMap;
    public MapMenuMessages showMap(int x, int y) {
        String map ;
        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        xMap = getAppropriateX(x);
        yMap = getAppropriateY(y);
        map = showMapCells(x, y);
        //TODO complete how to print map
        MapMenu.print(map);
        return MapMenuMessages.SUCCESS;
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
        String data = "";
        for (int i = 0; i < 5 * 4 + 1; i++) {
            for (int j = 0; j < 5 * 4 + 1; j++) {
                if (j % 4 == 0) data += "|";
                else if (i % 4 == 0) data += "-";
                else if (i % 2 == 0 && j % 2 == 0) {
                    MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x + i % 5, y + j % 5);
                    data += mapCell.getMaterialMap().getColor() + mapCell.objectInCell() + Color.ANSI_RESET;
                } else {
                    MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x + i % 5, y + j % 5);
                    data += mapCell.getMaterialMap().getColor() + "#" + Color.ANSI_RESET;
                }
            }
            data += "\n";
        }
        return data;
    }

    public String moveMap(int[] directions) {
        int transversalMove = directions[1] - directions[3];
        int longitudinalMove = directions[0] - directions[2];
        MapMenu.print(isSafeToMove(transversalMove, longitudinalMove));
        showMap(xMap, yMap);
        return "";
    }
    public String isSafeToMove(int transversalMove, int longitudinalMove) {
        int width = xMap + transversalMove;
        int length = yMap + longitudinalMove;
        boolean widthOutOfMap = width < 0 || width > Database.getCurrentMapGame().getWidth();
        boolean lengthOutOfMap = length < 0 || length > Database.getCurrentMapGame().getLength();
        if (widthOutOfMap && lengthOutOfMap) return "Both coordinates out of map";
        if (widthOutOfMap) {
            yMap = length;
            return "Width out of map";
        }
        if (lengthOutOfMap) {
            xMap = width;
            return "Length out of map";
        }
        xMap = width;
        yMap = length;
        return "Move successfully\n" +
                "New map coordinates are : \n" +
                "x : " + xMap + "\n" +
                "y : " + yMap;
    }

    public MapMenuMessages showDetails(int x, int y) {
        String details = "";

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;

        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        details += "MapCell with coordinates of x : " + x + " and y : " + y + "\n"
                + "Texture : " + mapCell.getMaterialMap().getMaterial() + "\n";

        if(mapCell.haveBuilding()) details += "Building : " + mapCell.getBuilding().getBuildingName() + "\n";
        else if (mapCell.haveAttackTools()) details += "AttackTool : "
                + mapCell.getAttackToolsAndMethods().get(0).getName() + "\n";
        else if (mapCell.getWall() != null) details += "have wall\n" ;
        else if (mapCell.getTree() != null) details += "Tree : " + mapCell.getTree().getTypeOfTree().getType() + "\n";
        else if (mapCell.getRock() != null) details += "have rock\n" ;
        details += "Number of people in this cell : " + mapCell.getSoldier().size();
        //TODO complete soldiers
        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages setTextureOfOneBlock(int x, int y, String type) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (MaterialMap.getTextureMap(type) == null) return MapMenuMessages.INVALID_TYPE;

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

    public MapMenuMessages clearBlock(int x, int y) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        mapCell.setBuilding(null);
        mapCell.setMapCellItems(null);
        mapCell.setItems(null);
        mapCell.setAttackToolsAndMethods(null);
        mapCell.setPeople(null);
        mapCell.setMaterialMap(MaterialMap.getTextureMap("land"));

        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages dropRock(int x, int y, String direction) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (Direction.getDirection(direction) == null) return MapMenuMessages.INVALID_DIRECTION;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return MapMenuMessages.CELL_IS_FULL;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        if (mapCell.getMaterialMap().isWaterZone()) return MapMenuMessages.INAPPROPRIATE_TEXTURE;

        Rock rock = new Rock(Database.getLoggedInUser(), Direction.getDirection(direction));
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

        Tree tree = new Tree(Database.getLoggedInUser(), Tree.getTreeType(type));

        mapCell.addMapCellItems(tree);

        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages dropBuilding(int x, int y, String type) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (Database.getBuildingDataByName(type) == null) return MapMenuMessages.INVALID_TYPE;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return MapMenuMessages.CELL_IS_FULL;

        if (Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getMaterialMap().isWaterZone())
            return MapMenuMessages.INAPPROPRIATE_TEXTURE;

        Building building = new Building(Database.getLoggedInUser(), Database.getBuildingDataByName(type));
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        mapCell.addBuilding(building);

        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages dropUnit(int x, int y, String type, int count) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (Database.getSoldierDataByName(type) == null) return MapMenuMessages.INVALID_TYPE;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return MapMenuMessages.CELL_IS_FULL;

        if (Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getMaterialMap().isWaterZone())
            return MapMenuMessages.INAPPROPRIATE_TEXTURE;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        for (int i = 0; i < count; i++) {
            Soldier soldier = new Soldier(Database.getLoggedInUser(), Database.getSoldierDataByName(type));
            Database.getLoggedInUser().getEmpire().addPopulation(soldier);
            mapCell.addPeople(soldier);
        }

        return MapMenuMessages.SUCCESS;
    }
}
