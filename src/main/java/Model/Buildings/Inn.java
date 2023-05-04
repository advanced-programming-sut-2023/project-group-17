package Model.Buildings;

import Model.User;

public class Inn extends Building{
    private int wineUsage;
    private int popularityRate;

    public Inn(User owner, Building building, int x, int y) {
        super(owner, building, x, y);
    }

    public int getWineUsage() {
        return wineUsage;
    }

    public int getPopularityRate() {
        return popularityRate;
    }
}
