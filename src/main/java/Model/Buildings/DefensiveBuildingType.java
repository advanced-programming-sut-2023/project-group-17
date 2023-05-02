package Model.Buildings;

public class DefensiveBuildingType {
    public enum DefensiveType {
        LOOKOUT_TOWER("lookout tower", 20, 5),
        PERIMETER_TOWER("perimeter tower", 15, 10),
        DEFENSE_TURRET("defence turret", 15, 10),
        SQUARE_TOWER("square tower", 15, 10),
        ROUND_TOWER("round tower", 15, 10)
        ;
        String name;
        int fireRange;
        int defenceRange;
        DefensiveType(String name, int fireRange, int defenceRange) {
            this.name = name;
        }
    }
}
