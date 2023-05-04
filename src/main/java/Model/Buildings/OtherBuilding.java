package Model.Buildings;

import Model.User;

public class OtherBuilding extends Building{
    boolean religiousBuilding;
    int capacity;
    public OtherBuilding(User owner, int number, Building building, int x, int y) {
        super(owner, building, x, y);
    }

    public boolean isReligiousBuilding() {
        return religiousBuilding;
    }

    public void setReligiousBuilding(boolean religiousBuilding) {
        this.religiousBuilding = religiousBuilding;
    }

    public int getCapacity() {
        return capacity;
    }

}
