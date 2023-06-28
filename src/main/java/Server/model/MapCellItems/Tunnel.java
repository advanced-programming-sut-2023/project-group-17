package Server.model.MapCellItems;

import Server.model.MapCell;
import Server.model.User;

public class Tunnel extends MapCellItems{
    private MapCell startCell;
    private MapCell endCell;
    public Tunnel(User owner, MapCell startCell, MapCell endCell) {
        super(owner);
        this.startCell = startCell;
        this.endCell = endCell;
    }
}
