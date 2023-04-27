package Model.People;

import java.nio.file.FileAlreadyExistsException;

public enum SoldierTypes {
    ARCHER("archer", UnitAttributes.Speed.FAST, UnitAttributes.AttackRating.LOW, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.LOW, UnitAttributes.Weapon.BOW, UnitAttributes.Nationality.EUROPEAN,
            UnitAttributes.Health.LOW, true , true, UnitAttributes.AttackRange.FAR, 12.0),
    CROSSBOWMEN("crossbowmen", UnitAttributes.Speed.SLOW, UnitAttributes.AttackRating.LOW, UnitAttributes.Armor.LEATHER,
            UnitAttributes.DefenceRating.MEDIUM, UnitAttributes.Weapon.CROSSBOW, UnitAttributes.Nationality.EUROPEAN,
            UnitAttributes.Health.MEDIUM, false, false, UnitAttributes.AttackRange.MIDDLE, 20.0),
    SPEARMEN("spearman", UnitAttributes.Speed.AVERAGE, UnitAttributes.AttackRating.MEDIUM, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.VERY_LOW, UnitAttributes.Weapon.SPEAR, UnitAttributes.Nationality.EUROPEAN,
            UnitAttributes.Health.LOW, true, true, UnitAttributes.AttackRange.CLOSE, 8.0),
    PIKEMEN("pikemen", UnitAttributes.Speed.SLOW, UnitAttributes.AttackRating.MEDIUM, UnitAttributes.Armor.METAL,
            UnitAttributes.DefenceRating.HIGH, UnitAttributes.Weapon.PIKE, UnitAttributes.Nationality.EUROPEAN, UnitAttributes.Health.HIGH
            , false, true, UnitAttributes.AttackRange.CLOSE, 20.0),
    MACEMEN("macemen", UnitAttributes.Speed.AVERAGE, UnitAttributes.AttackRating.HIGH, UnitAttributes.Armor.LEATHER,
            UnitAttributes.DefenceRating.MEDIUM, UnitAttributes.Weapon.MACE, UnitAttributes.Nationality.EUROPEAN, UnitAttributes.Health.MEDIUM
            , true, true, UnitAttributes.AttackRange.CLOSE, 20.0),
    SWORDSMEN("swordsmen", UnitAttributes.Speed.EXTREMELY_SLOW, UnitAttributes.AttackRating.VERY_HIGH,
        UnitAttributes.Armor.METAL, UnitAttributes.DefenceRating.VERY_LOW, UnitAttributes.Weapon.SWORD,
        UnitAttributes.Nationality.EUROPEAN, UnitAttributes.Health.HIGH, false,
            false, UnitAttributes.AttackRange.CLOSE, 40.0),
    KNIGHT("knight", UnitAttributes.Speed.VERY_FAST, UnitAttributes.AttackRating.VERY_HIGH, UnitAttributes.Armor.METAL_AND_HORSE,
        UnitAttributes.DefenceRating.HIGH, UnitAttributes.Weapon.SWORD, UnitAttributes.Nationality.EUROPEAN,
            UnitAttributes.Health.VERY_HIGH, false, false, UnitAttributes.AttackRange.CLOSE, 40.0),
    TUNNELER("tunneler", UnitAttributes.Speed.FAST, UnitAttributes.AttackRating.MEDIUM, UnitAttributes.Armor.NA,
        UnitAttributes.DefenceRating.VERY_LOW, UnitAttributes.Weapon.PICK_AXE, UnitAttributes.Nationality.EUROPEAN,
           UnitAttributes.Health.LOW ,false, false, UnitAttributes.AttackRange.CLOSE, 30.0),
    LADDERMAN("ladderman", UnitAttributes.Speed.FAST, UnitAttributes.AttackRating.NA, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.VERY_LOW, UnitAttributes.Weapon.NA, UnitAttributes.Nationality.EUROPEAN,
            UnitAttributes.Health.VERY_LOW ,false, false, UnitAttributes.AttackRange.CLOSE, 3.0),
    ENGINEER("engineer", UnitAttributes.Speed.AVERAGE, UnitAttributes.AttackRating.NA, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.VERY_LOW, UnitAttributes.Weapon.NA, UnitAttributes.Nationality.EUROPEAN,
             UnitAttributes.Health.VERY_LOW, false, true, UnitAttributes.AttackRange.CLOSE, 30.0),
    BLACK_MONK("black monk", UnitAttributes.Speed.SLOW, UnitAttributes.AttackRating.MEDIUM, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.MEDIUM, UnitAttributes.Weapon.STAFF, UnitAttributes.Nationality.EUROPEAN,
            UnitAttributes.Health.LOW, false, false, UnitAttributes.AttackRange.CLOSE, 10.0),
    ARCHER_BOW("archer bow", UnitAttributes.Speed.FAST, UnitAttributes.AttackRating.LOW, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.LOW, UnitAttributes.Weapon.BOW, UnitAttributes.Nationality.ARABIAN,
            UnitAttributes.Health.LOW,false, true, UnitAttributes.AttackRange.FAR, 75.0),
    SLAVE("slave", UnitAttributes.Speed.FAST, UnitAttributes.AttackRating.VERY_LOW, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.VERY_LOW, UnitAttributes.Weapon.TORCH, UnitAttributes.Nationality.ARABIAN,
            UnitAttributes.Health.VERY_LOW,false, true, UnitAttributes.AttackRange.CLOSE, 5.0),
    SLINGER("slinger", UnitAttributes.Speed.FAST, UnitAttributes.AttackRating.LOW, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.VERY_LOW, UnitAttributes.Weapon.SLING, UnitAttributes.Nationality.ARABIAN,
            UnitAttributes.Health.LOW,false, false, UnitAttributes.AttackRange.CLOSE, 12.0),
    ASSASIN("assasin", UnitAttributes.Speed.AVERAGE, UnitAttributes.AttackRating.MEDIUM, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.MEDIUM, UnitAttributes.Weapon.SCIMITAR, UnitAttributes.Nationality.ARABIAN,
            UnitAttributes.Health.MEDIUM,false, false, UnitAttributes.AttackRange.CLOSE, 60.0),
    HORSE_ARCHERS("horse archer", UnitAttributes.Speed.VERY_FAST, UnitAttributes.AttackRating.LOW, UnitAttributes.Armor.HORSE,
            UnitAttributes.DefenceRating.MEDIUM, UnitAttributes.Weapon.BOW, UnitAttributes.Nationality.ARABIAN,
            UnitAttributes.Health.LOW,false, false, UnitAttributes.AttackRange.MIDDLE, 60.0),
    ARABIAN_SWORDSMAN("arabian swordsman", UnitAttributes.Speed.VERY_FAST, UnitAttributes.AttackRating.HIGH, UnitAttributes.Armor.METAL,
            UnitAttributes.DefenceRating.HIGH, UnitAttributes.Weapon.SCIMITAR, UnitAttributes.Nationality.ARABIAN,
            UnitAttributes.Health.HIGH,false, false, UnitAttributes.AttackRange.CLOSE, 100.0),
    FIRE_THROWER("fire thrower", UnitAttributes.Speed.VERY_FAST, UnitAttributes.AttackRating.HIGH, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.LOW, UnitAttributes.Weapon.GREEK_FIRE, UnitAttributes.Nationality.ARABIAN,
            UnitAttributes.Health.LOW,false, false, UnitAttributes.AttackRange.MIDDLE, 80.0)
;

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

    SoldierTypes(String name, UnitAttributes.Speed speed, UnitAttributes.AttackRating attackRating,
                 UnitAttributes.Armor armor, UnitAttributes.DefenceRating defenceRating,
                 UnitAttributes.Weapon weapon, UnitAttributes.Nationality nationality, UnitAttributes.Health health,
                 boolean canClimbLadder, boolean canDigMoat, UnitAttributes.AttackRange attackRange,
                 Double cost) {
        this.name = name;
        this.speed = speed.getSpeed();
        this.attackRating = attackRating.getAttackRate();
        this.armor = armor.getName();
        this.defenceRating = defenceRating.getDefenceRate();
        this.weapon = weapon.getName();
        this.nationality = nationality;
        this.health = health.getHealth();
        this.canClimbLadder = canClimbLadder;
        this.canDigMoat = canDigMoat;
        this.status = UnitAttributes.Status.STANDING.getStatus();
        this.attackRange = attackRange.getAttackRange();
        this.cost = cost;
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

    public int getHealth() {
        return health;
    }

    public boolean isCanClimbLadder() {
        return canClimbLadder;
    }

    public boolean isCanDigMoat() {
        return canDigMoat;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public double getCost() {
        return cost;
    }
//    allSoldiers.add(new Soldier(null, SoldierTypes.ARCHER));
//        allSoldiers.add(new Soldier(null, SoldierTypes.CROSSBOWMEN));
//        allSoldiers.add(new Soldier(null, SoldierTypes.SPEARMEN));
//        allSoldiers.add(new Soldier(null, SoldierTypes.PIKEMEN));
//        allSoldiers.add(new Soldier(null, SoldierTypes.MACEMEN));
//        allSoldiers.add(new Soldier(null, SoldierTypes.SWORDSMEN));
//        allSoldiers.add(new Soldier(null, SoldierTypes.KNIGHT));
//        allSoldiers.add(new Soldier(null, SoldierTypes.TUNNELER));
//        allSoldiers.add(new Soldier(null, SoldierTypes.LADDERMAN));
//        allSoldiers.add(new Soldier(null, SoldierTypes.ENGINEER));
//        allSoldiers.add(new Soldier(null, SoldierTypes.BLACK_MONK));
//        allSoldiers.add(new Soldier(null, SoldierTypes.ARCHER_BOW));
//        allSoldiers.add(new Soldier(null, SoldierTypes.SLAVE));
//        allSoldiers.add(new Soldier(null, SoldierTypes.SLINGER));
//        allSoldiers.add(new Soldier(null, SoldierTypes.ASSASIN));
//        allSoldiers.add(new Soldier(null, SoldierTypes.HORSE_ARCHERS));
//        allSoldiers.add(new Soldier(null, SoldierTypes.ARABIAN_SWORDSMAN));
//        allSoldiers.add(new Soldier(null, SoldierTypes.FIRE_THROWER));
}
