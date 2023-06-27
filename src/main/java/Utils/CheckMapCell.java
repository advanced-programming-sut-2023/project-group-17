package Utils;

import Model.Database;
import Model.MapCell;

public class CheckMapCell {
    public static boolean validationOfX(int x) {
        return x > 0 && x <= Database.getCurrentMapGame().getWidth();
    }
    public static boolean validationOfY(int y) {
        return y > 0 && y <= Database.getCurrentMapGame().getLength();
    }

    public static boolean mapCellEmptyByCoordinates(int x, int y) {
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        assert mapCell != null;
        return mapCell.canDropItems();
    }

    public static boolean mapCellTraversableByCoordinates(int x, int y) {
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        assert mapCell != null;
        return mapCell.isTraversable();
    }

//    public static UtilsMessages mapCellHaveBuildingByCoordinates(int x, int y, User owner) {
//        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
//        assert mapCell != null;
//        if (!mapCell.haveBuilding()) return UtilsMessages.NO_BUILDING_IN_THIS_CELL;
//        if (!mapCell.getBuilding().getOwner().equals(owner)) return UtilsMessages.OPPONENT_BUILDING;
//        return UtilsMessages.SUCCESS;
//    }

}
