package Model.Buildings;

public class GateHouseType {
    enum Type {
        SMALL_STONE_GATE_HOUSE(8, false),
        BIG_STONE_GATE_HOUSE(10, false),
        HOVEL(8, true)
        ;
        int maximumCapacity;
        boolean isHouse;
        Type(int maximumCapacity, boolean isHouse) {
            this.maximumCapacity = maximumCapacity;
            this.isHouse = isHouse;
        }
    }
}
