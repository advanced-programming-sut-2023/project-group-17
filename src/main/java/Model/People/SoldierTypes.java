package Model.People;

public enum SoldierTypes {
    ARCHER("archer", UnitAttributes.Speed.FAST, UnitAttributes.AttackRating.LOW, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.LOW, UnitAttributes.Weapon.BOW, UnitAttributes.Nationality.EUROPEAN,
            UnitAttributes.Health.LOW, true , true),
    CROSSBOWMEN("crossbowmen", UnitAttributes.Speed.SLOW, UnitAttributes.AttackRating.LOW, UnitAttributes.Armor.LEATHER,
            UnitAttributes.DefenceRating.MEDIUM, UnitAttributes.Weapon.CROSSBOW, UnitAttributes.Nationality.EUROPEAN,
            UnitAttributes.Health.MEDIUM, false, false),
    SPEARMAN("spearman", UnitAttributes.Speed.AVERAGE, UnitAttributes.AttackRating.MEDIUM, UnitAttributes.Armor.NA,
            UnitAttributes.DefenceRating.VERY_LOW, UnitAttributes.Weapon.SPEAR, UnitAttributes.Nationality.EUROPEAN,
            UnitAttributes.Health.LOW, true, true),
//    PIKEMEN,
//    MACEMEN,
//    SWORDSMEN,
//    KNIGHT,
//    TUNNELER,
//    LADDERMAN,
//    ENGINEER,
//    BLACK_MONK,
//    ARCHER_BOW,
//    SLAVE,
//    SLINGER,
//    ASSASIN,
//    HORSE_ARCHERS,
//    ARABIAN_SWORDSMAN,
//    FIRE_THROWER
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

    SoldierTypes(String name, UnitAttributes.Speed speed, UnitAttributes.AttackRating attackRating,
                 UnitAttributes.Armor armor, UnitAttributes.DefenceRating defenceRating,
                 UnitAttributes.Weapon weapon, UnitAttributes.Nationality nationality, UnitAttributes.Health health,
                 boolean canClimbLadder, boolean canDigMoat) {
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
    }
}
