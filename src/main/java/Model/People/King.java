package Model.People;

import Model.User;

public class King extends Person {
//    private final int hp;
    private final int damage;
    private final int range;
    public King(User owner) {
        super(owner, 500);
//        this.hp = 500;
        this.damage = 200;
        this.range = 1;
    }

//    @Override
//    public int getHp() {
//        return hp;
//    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }
}
