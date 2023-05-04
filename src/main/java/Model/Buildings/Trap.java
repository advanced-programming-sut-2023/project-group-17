package Model.Buildings;

import Model.User;

public class Trap extends Building{
    private boolean flammable;
    private boolean visible;
    public Trap(User owner, Building building, int x, int y) {
        super(owner, building, x, y);
    }

    public boolean isFlammable() {
        return flammable;
    }

    public boolean isVisible() {
        return visible;
    }
}
