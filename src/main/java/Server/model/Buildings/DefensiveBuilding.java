package Server.model.Buildings;

import Server.model.Direction;
import Server.model.User;

public class DefensiveBuilding extends Building{
    int fireRange;
    int defenceRange;
    Direction.directions direction;
    public DefensiveBuilding(User owner, Direction.directions direction, int x, int y, DefensiveBuilding defensiveBuilding) {
        super(owner, defensiveBuilding, x, y);
        this.direction = direction;
        this.fireRange = defensiveBuilding.fireRange;
        this.defenceRange = defensiveBuilding.defenceRange;
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
