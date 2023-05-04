package Model.AttackToolsAndMethods;

public enum AttackToolsAndMethodsType {
    TREBUCHETS("trebuchets", 3, false, 150, 15, 140, 0, 0),
    PORTABLE_SHIELDS("portable shield", 1, true, 100, 0, 0, 10, 10),
    BATTERING_RAM("battering ram", 4, true, 200, -1, 140, 3, 200),
    FIRE_BALLISTA("fire ballista", 1, true, 150, 15, 140, 3, 150),
    SIEGE_TOWER("siege tower", 4, true, 200, 0, 0, 1, 250),
    CATAPULT("catapult", 2, true, 100, 15, 90, 3, 150);
    private final int numberOfEngineers;
    private final String name;
    private final boolean isPortable;
    private int hp;
    private final int range;
    private final int damage;
    private final int speed;
    private final int cost;
    private AttackToolsAndMethodsType(String name, int numberOfEngineers, boolean isPortable, int hp, int range,
                                      int damage, int speed, int cost) {
        this.name = name;
        this.numberOfEngineers = numberOfEngineers;
        this.isPortable = isPortable;
        this.hp = hp;
        this.range = range;
        this.damage = damage;
        this.speed = speed;
        this.cost = cost;
    }

    public int getNumberOfEngineers() {
        return numberOfEngineers;
    }

    public boolean isPortable() {
        return isPortable;
    }

    public int getHp() {
        return hp;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}
