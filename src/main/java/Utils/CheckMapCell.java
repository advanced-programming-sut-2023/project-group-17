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
        return mapCell.getBuildings().size() == 0 && mapCell.getMapCellItems().size() == 0;
    }
}
