package Server.model.MapCellItems;

import Server.model.User;

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
        return "Stair owner : " + getOwner().getUsername() + "\n";
    }
}