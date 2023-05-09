package Model.People;

import Model.MapCell;
import Model.User;

public class Person {
    private User owner;
    private int hp;
    private MapCell destination;

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
    public void changeHp(int hp) {
        this.hp += hp;
    }

    public MapCell getDestination() {
        return destination;
    }

    public void setDestination(MapCell destination) {
        this.destination = destination;
    }
}
