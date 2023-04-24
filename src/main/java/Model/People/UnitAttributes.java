package Model.People;

public class UnitAttributes {

    //TODO: addada
    public enum AttackRating {
        VERY_HIGH(5),
        HIGH(4),
        MEDIUM(3),
        LOW(2),
        VERY_LOW(1),
        NA(0);
        private final int attackRate;
        private AttackRating(int attackRate) {
            this.attackRate = attackRate;
        }

        public int getAttackRate() {
            return attackRate;
        }
    }

    public enum DefenceRating {
        VERY_HIGH(5),
        HIGH(4),
        MEDIUM(3),
        LOW(2),
        VERY_LOW(1),
        NA(0);
        private final int defenceRate;
        private DefenceRating(int defenceRate) {
            this.defenceRate = defenceRate;
        }

        public int getDefenceRate() {
            return defenceRate;
        }
    }

    public enum Health {
        VERY_HIGH(5),
        HIGH(4),
        MEDIUM(3),
        LOW(2),
        VERY_LOW(1),
        NA(0);
        private final int health;
        private Health(int health) {
            this.health = health;
        }

        public int getHealth() {
            return health;
        }
    }

    public enum Nationality {
        EUROPEAN,
        ARABIAN;
    }

    public enum AttackRange {
        FAR(3),
        MIDDLE(2),
        CLOSE(1);
        private final int attackRange;
        private AttackRange (int attackRange) {
            this.attackRange = attackRange;
        }

        public int getAttackRange() {
            return attackRange;
        }
    }

    public enum Weapon {
        BOW("bow"),
        CROSSBOW("crossbow"),
        SPEAR("spear"),
        PIKE("pike"),
        MACE("mace"),
        SWORD("sword"),
        PICK_AXE("pick axe"),
        TORCH("torch"),
        SLING("sling"),
        SCIMITAR("scimitar"),
        GREEK_FIRE("greek fire"),
        LADDER("ladder"),
        NA("n/a");
        private final String name;

        private Weapon(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Speed {
        VERY_FAST(5),
        FAST(4),
        AVERAGE(3),
        SLOW(2),
        EXTREMELY_SLOW(1);
        private final int speed;

        private Speed (int speed) {
            this.speed = speed;
        }

        public int getSpeed() {
            return speed;
        }
    }

    public enum Armor {
        LEATHER("leather"),
        METAL("metal"),
        METAL_AND_HORSE("metal and horse"),
        HORSE("horse"),
        NA("n/a");
        private final String name;

        private Armor (String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Status {
        STANDING("standing"),
        DEFENSIVE("defensive"),
        OFFENSIVE("offensive");
        private final String status;

        private Status (String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
