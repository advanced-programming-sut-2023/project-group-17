package Model.MapCellItems;

import Model.Database;
import Model.MapCell;
import Model.User;

public class Wall extends MapCellItems {
    private Wall.heights height;
    private Wall.thicknesses thickness;
    private int x;
    private int y;
    private int hp;
    public enum heights {
        TALL("tall", 5),
        MEDIUM("medium", 4),
        SHORT("short", 3)
        ;
        public String height;
        public int hpRateHeight;
        private heights(String height, int hpRateHeight) {
            this.height = height;
            this.hpRateHeight = hpRateHeight;
        }
    }
    public enum thicknesses {
        THICK("thick", 2),
        THIN("thin", 1)
        ;
        public String thickness;
        public int hpRateThickness;
        private thicknesses(String thickness, int hpRateThickness) {
            this.thickness = thickness;
            this.hpRateThickness = hpRateThickness;
        }
    }
    public Wall(User owner, heights height, thicknesses thickness, int x, int y) {
        super(owner);
        this.height = height;
        this.thickness = thickness;
        this.hp = hpCalculator(height, thickness);
        this.x = x;
        this.y = y;
    }
    public int hpCalculator(heights height, thicknesses thickness) {
        return 20 * height.hpRateHeight + 10 * thickness.hpRateThickness;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void changeHp(int damage) {
        this.hp -= damage;
    }

    public static thicknesses getThickness(String name) {
        for (thicknesses value : thicknesses.values()) {
            if (value.thickness.equals(name)) return value;
        }
        return null;
    }

    public static heights getHeight(String name) {
        for (heights value : heights.values()) {
            if (value.height.equals(name)) return value;
        }
        return null;
    }

    public boolean haveStairs() {
        MapCell mapCell;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (Utils.CheckMapCell.validationOfY(y + j) && Utils.CheckMapCell.validationOfX(x + i)) {
                    mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x + i, y + j);
                    if ((mapCell.getStair() != null) && mapCell.getStair().getOwner().equals(getOwner()))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "wall owner: " + getOwner().getUsername() + "hp : " + getHp() + "\n";
    }
}
