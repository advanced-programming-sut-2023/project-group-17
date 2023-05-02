package Model.Buildings;

public class GateHouseType {
    enum Type {
        SMALL_STONE_GATE_HOUSE(8, false,"small stone gatehouse"),
        BIG_STONE_GATE_HOUSE(10, false,"large stone gatehouse"),
        HOVEL(8, true, "hovel")
        ;
        private int maximumCapacity;
        private boolean isHouse;
        private String name;
        Type(int maximumCapacity, boolean isHouse,String name) {
            this.maximumCapacity = maximumCapacity;
            this.isHouse = isHouse;
            this.name = name;
        }

        public int getMaximumCapacity() {
            return maximumCapacity;
        }

        public String getName() {
            return name;
        }

        public boolean isHouse() {
            return isHouse;
        }
    }
    public static Type geteGatehouseType(String name) {
        for (Type gatehouseType : Type.values()) {
            if(gatehouseType.name.equals(name)) return gatehouseType;
        }
        return null;
    }
}
