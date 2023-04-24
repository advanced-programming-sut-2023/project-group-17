package Model.AttackToolsAndMethods;

import Model.User;

public class AttackToolsAndMethods {
    private final User owner;
    private final String name;
    private final int numberOfEngineers;
    private final boolean isPortable;
    private int hp;
    private final int range;
    private final int damage;
    private final int speed;
    private final int accuracy;
    private final int power;
    public AttackToolsAndMethods(User owner, AttackToolsAndMethodsType type) {
        this.owner = owner;
        this.name = type.getName();
        this.numberOfEngineers = type.getNumberOfEngineers();
        this.isPortable = type.isPortable();
        this.hp = type.getHp();
        this.range = type.getRange();
        this.damage = type.getDamage();
        this.accuracy = type.getAccuracy();
        this.speed = type.getSpeed();
        this.power = type.getPower();
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
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

    public void changeHp(int hp) {
        this.hp += hp;
    }
}
