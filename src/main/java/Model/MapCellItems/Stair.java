package Model.MapCellItems;

import Model.User;

public class Stair extends MapCellItems{
    private int x;
    private int y;
    public Stair(User owner, int x, int y) {
        super(owner);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Stair in x : " + x + " in y : " + y + " and the owner is : " + getOwner().getUsername() + "\n";
    }
}