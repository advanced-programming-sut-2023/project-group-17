package Model.Buildings;

import Model.Items.Item;
import Model.User;

import java.util.ArrayList;

public class ProductionBuilding extends Building{
    private Item consumption;
    private Item production;
    ArrayList<Item> storage;
    int rate;
    public ProductionBuilding(User owner, int number, Building building, int x, int y) {
        super(owner, building, x, y);
        this.storage = new ArrayList<>();
    }

    public ArrayList<Item> getStorage() {
        return storage;
    }

    public void addItemToStorage(Item item) {
        this.storage.add(item);
    }
}
