package Server.model.People;

import Server.model.User;

public class King extends Person {
    private final int damage;
    private final int range;
    public King(User owner) {
        super(owner, 500);
        this.damage = 200;
        this.range = 1;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }
}
