package Model;

import Model.People.Engineer;
import Model.People.Soldier;

import java.util.ArrayList;

public class AttackToolsAndMethods {
    private final User owner;
    private final String name;
    private final int numberOfEngineers;
    private ArrayList<Engineer> engineers;
    private ArrayList<Soldier> soldiers;
    private final boolean isPortable;
    private int hp;
    private final int range;
    private final int damage;
    private final int speed;
    private final int cost;
    private int x;
    private int y;
    private MapCell destination;
    public AttackToolsAndMethods(User owner, AttackToolsAndMethods type, int x, int y) {
        this.owner = owner;
        this.name = type.getName();
        this.numberOfEngineers = type.getNumberOfEngineers();
        this.isPortable = type.isPortable();
        this.hp = type.getHp();
        this.range = type.getRange();
        this.damage = type.getDamage();
        this.cost = type.getCost();
        this.speed = type.getSpeed();
        this.x = x;
        this.y = y;
        this.engineers = new ArrayList<>();
        this.soldiers = new ArrayList<>();
        if (name.equals("siege tower")) addSoldiers();
    }

    private void addSoldiers() {
        int counter = 0;
        MapCell mapCell;
        for (Soldier soldier : owner.getEmpire().getSoldiers()) {
            if (soldier.canClimbLadder()) {
                counter++;
                mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(soldier.getX(), soldier.getY());
                mapCell.removePerson(soldier);
                this.soldiers.add(soldier);
            }
            if (counter == 4) return;
        }
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public void setHp(int hp) {
        this.hp = hp;
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

    public void changeHp(int hp) {
        this.hp += hp;
    }

    public MapCell getDestination() {
        return destination;
    }

    public void setDestination(MapCell destination) {
        this.destination = destination;
    }

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }
}
