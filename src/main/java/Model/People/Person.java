package Model.People;

import Model.MapCell;
import Model.User;

public class Person {
    private User owner;
    private int hp;
    private MapCell destination;
    private MapCell secondDestination;
    private int x;
    private int y;

    public Person(User owner, int hp) {
        this.owner = owner;
        this.hp = hp;
    }

    public User getOwner() {
        return owner;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

    public MapCell getSecondDestination() {
        return secondDestination;
    }

    public void setSecondDestination(MapCell secondDestination) {
        this.secondDestination = secondDestination;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
