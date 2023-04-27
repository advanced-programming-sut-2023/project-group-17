package Model.People;

import Model.People.Person;
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
    private int health;
    private boolean canClimbLadder;
    private boolean canDigMoat;
    private int attackRange;
    private double cost;
    public Soldier(User owner, SoldierTypes soldierTypes) {
        super(owner);
        this.name = soldierTypes.getName();
        this.speed = soldierTypes.getSpeed();
        this.attackRating = soldierTypes.getAttackRating();
        this.armor = soldierTypes.getArmor();
        this.defenceRating = soldierTypes.getDefenceRating();
        this.weapon = soldierTypes.getWeapon();
        this.nationality = soldierTypes.getNationality();
        this.health = soldierTypes.getHealth();
        this.canClimbLadder = soldierTypes.isCanClimbLadder();
        this.canDigMoat = soldierTypes.isCanDigMoat();
        this.status = UnitAttributes.Status.STANDING.getStatus();
        this.attackRange = soldierTypes.getAttackRange();
        this.cost = soldierTypes.getCost();
    }
}
