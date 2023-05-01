package Model.Buildings;

import Model.User;

public class DefensiveBuilding extends Building{
    int fireRange;
    int defenceRange;
    String direction;
    public DefensiveBuilding(User owner, int number, Building building, int x, int y) {
        super(owner, building, x, y);
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefenceRange() {
        return defenceRange;
    }

    public String getDirection() {
        return direction;
    }
}
