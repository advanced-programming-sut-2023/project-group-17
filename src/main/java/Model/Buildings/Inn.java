package Model.Buildings;

import Model.User;

public class Inn extends Building{
    private int wineUsage;
    private int popularityRate;

    public Inn(User owner, int number) {
        super(owner, BuildingType.INN);
    }
}
