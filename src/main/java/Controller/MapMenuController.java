package Controller;

import Model.Database;
import Model.Direction;
import Model.MapCell;
import Model.MapCellItems.Rock;
import Model.MapCellItems.Tree;
import Model.MaterialMap;
import Utils.CheckMapCell;
import View.Enums.Messages.MapMenuMessages;
import View.MapMenu;
import View.Menu;

public class MapMenuController {
    public MapMenuMessages showMap(int x, int y) {
        String map = "";
        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        int xFromCadre = Math.min((x - Database.getCurrentMapGame().getWidth()), x);
        int yFromCadre = Math.min((y - Database.getCurrentMapGame().getLength()), y);
        //TODO complete how to print map
//        MapMenu.print();
        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages moveMap(String directions) {
        return null;
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
        return null;
    }

    public MapMenuMessages dropUnit(int x, int y, String type, int count) {
        return null;
    }
}
