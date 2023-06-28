package Server.model.MapCellItems;

import Server.model.User;

public class MapCellItems {
    private User owner;
    public MapCellItems(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }
}
