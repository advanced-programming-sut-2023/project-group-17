package Model.Buildings;

import Model.User;

public class Inn extends Building{
    private int wineUsage;
    private int popularityRate;

    public Inn(User owner, int number, Building building) {
        super(owner, building);
    }
}
