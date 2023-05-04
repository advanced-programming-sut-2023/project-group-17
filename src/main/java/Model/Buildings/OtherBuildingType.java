package Model.Buildings;

public class OtherBuildingType {
    enum OtherType {
        CHURCH("church", true, 0),
        CATHEDRAL("cathedral", true, 0),
        DRAWBRIDGE("drawbridge", false, 0),
        MARKET("market", false, 0),
        OX_THEATER("ox theater", false, 12)
        ;
        String name;
        boolean religiousBuilding;
        int capacity;
        OtherType(String name, boolean religiousBuilding, int capacity) {
            this.name = name;
            this.religiousBuilding = religiousBuilding;
            this.capacity = capacity;
        }
    }
}
