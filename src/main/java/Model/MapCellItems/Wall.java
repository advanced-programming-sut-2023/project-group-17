package Model.MapCellItems;

import Model.User;

public class Wall extends MapCellItems {
    private Wall.heights height;
    private Wall.thicknesses thickness;
    private int hp;
    public enum heights {
        TALL("tall", 0),
        MEDIUM("medium", 0),
        SHORT("short", 0)
        ;
        public String height;
        public int hpRateHeight;
        private heights(String height, int hpRateHeight) {
            this.height = height;
            this.hpRateHeight = hpRateHeight;
        }
    }
    public enum thicknesses {
        THICK("thick", 0),
        THIN("thin", 0)
        ;
        public String thickness;
        public int hpRateThickness;
        private thicknesses(String thickness, int hpRateThickness) {
            this.thickness = thickness;
            this.hpRateThickness = hpRateThickness;
        }
    }
    public Wall(User owner, heights height, thicknesses thickness) {
        super(owner);
        this.height = height;
        this.thickness = thickness;
        this.hp = hpCalculator(height, thickness);
    }
    public int hpCalculator(heights height, thicknesses thickness) {
        return 10 * height.hpRateHeight + 10 * thickness.hpRateThickness;
    }

    public int getHp() {
        return hp;
    }

    public void changeHp(int damage) {
        this.hp -= damage;
    }
}
