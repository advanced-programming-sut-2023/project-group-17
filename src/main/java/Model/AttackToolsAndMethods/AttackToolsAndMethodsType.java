package Model.AttackToolsAndMethods;

public enum AttackToolsAndMethodsType {
    //TODO
    TREBUCHETS("trebuchets", 3, false, 0, 0, 0, 0, 0, 0),
    PORTABLE_SHIELDS("portable shield", 1, true, 0, 0, 0, 0, 0, 0),
    BATTERING_RAM("battering ram", 4, true, 0, 0, 0, 0, 0, 0),
    FIRE_BALLISTA("fire ballista", 1, true, 0, 0, 0, 0, 0, 0),
    SIEGE_TOWER("siege tower", 4, true, 0, 0, 0, 0, 0, 0),
    CATAPULT("catapult", 2, true, 0, 0, 0, 0, 0, 0);
    private final int numberOfEngineers;
    private final String name;
    private final boolean isPortable;
    private int hp;
    private final int range;
    private final int damage;
    private final int speed;
    private final int accuracy;
    private final int power;
    private AttackToolsAndMethodsType(String name, int numberOfEngineers, boolean isPortable, int hp, int range,
                                      int damage, int speed, int accuracy, int power) {
        this.name = name;
        this.numberOfEngineers = numberOfEngineers;
        this.isPortable = isPortable;
        this.hp = hp;
        this.range = range;
        this.damage = damage;
        this.speed = speed;
        this.accuracy = accuracy;
        this.power = power;
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

    public int getAccuracy() {
        return accuracy;
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }
}
