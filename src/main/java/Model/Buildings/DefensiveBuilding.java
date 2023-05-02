package Model.Buildings;

import Model.Direction;
import Model.User;

public class DefensiveBuilding extends Building{
    int fireRange;
    int defenceRange;
    Direction direction;
    public DefensiveBuilding(User owner, int number, Building building, int x, int y) {
        super(owner, building, x, y);
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefenceRange() {
        return defenceRange;
    }

    public Direction getDirection() {
        return direction;
    }
}
