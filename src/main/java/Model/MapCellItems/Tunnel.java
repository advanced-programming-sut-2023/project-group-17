package Model.MapCellItems;

import Model.MapCell;
import Model.User;

public class Tunnel extends MapCellItems{
    private MapCell startCell;
    private MapCell endCell;
    public Tunnel(User owner, MapCell startCell, MapCell endCell) {
        super(owner);
        this.startCell = startCell;
        this.endCell = endCell;
    }
}
