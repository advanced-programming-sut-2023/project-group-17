package Model.Buildings;

import Model.User;

public class ProductionBuilding extends Building{
    public ProductionBuilding(User owner, int number, Building building, int x, int y) {
        super(owner, building, x, y);
    }
}
