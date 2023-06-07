package Model.People;

import Model.User;

public class Soldier extends Person {
    private String name;
    private String status;
    private int speed;
    private int attackRating;
    private String armor;
    private int defenceRating;
    private String weapon;
    private UnitAttributes.Nationality nationality;
//    private int health;
    private boolean climbLadder;
    private boolean digMoat;
    private int attackRange;
    private double cost;
    public Soldier(User owner, Soldier soldier) {
        super(owner, soldier.getHp());
        this.name = soldier.name;
        this.speed = soldier.speed;
        this.attackRating = soldier.attackRating;
        this.armor = soldier.armor;
        this.defenceRating = soldier.defenceRating;
        this.weapon = soldier.weapon;
        this.nationality = soldier.nationality;
        this.climbLadder = soldier.climbLadder;
        this.digMoat = soldier.digMoat;
        this.status = UnitAttributes.Status.STANDING.getStatus();
        this.attackRange = soldier.attackRange;
        this.cost = soldier.cost;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAttackRating() {
        return attackRating;
    }

    public String getArmor() {
        return armor;
    }

    public int getDefenceRating() {
        return defenceRating;
    }

    public String getWeapon() {
        return weapon;
    }

    public UnitAttributes.Nationality getNationality() {
        return nationality;
    }

//    public int getHealth() {
//        return health;
//    }

    public boolean canClimbLadder() {
        return climbLadder;
    }

    public boolean canDigMoat() {
        return digMoat;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public double getCost() {
        return cost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void changeDamage(double amount) {
        this.attackRating *= amount;
    }

    public void changeSpeed(int amount) {
        this.speed += amount;
    }

    @Override
    public String toString() {
        return "soldier name: " + this.name + " ,speed: " + this.speed + " ,owner: " + getOwner().getUsername()
                + " ,hp: " + getHp() + " ,range:  " + this.attackRange + " ,damage: " + this.attackRating +"\n";
    }
}
