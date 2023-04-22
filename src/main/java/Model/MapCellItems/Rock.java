package Model.MapCellItems;

import Model.Direction;
import Model.User;

public class Rock extends MapCellItems{
    private Direction.directions direction;
    public Rock(User owner, Direction.directions direction) {
        super(owner);
        this.direction = direction;
    }

    public Direction.directions getDirection() {
        return direction;
    }
}
