package Model.Buildings;

import Model.Direction;
import Model.User;

public class DefensiveBuilding extends Building{
    int fireRange;
    int defenceRange;
    Direction.directions direction;
    public DefensiveBuilding(User owner, Direction.directions direction, Building building, int x, int y, DefensiveBuildingType.DefensiveType defensiveType) {
        super(owner, building, x, y);
        this.direction = direction;
        this.fireRange = defensiveType.fireRange;
        this.defenceRange = defensiveType.defenceRange;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefenceRange() {
        return defenceRange;
    }

    public Direction.directions getDirection() {
        return direction;
    }
}
