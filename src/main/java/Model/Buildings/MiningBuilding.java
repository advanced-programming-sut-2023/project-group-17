package Model.Buildings;

import Model.Items.Item;
import Model.User;

import java.util.ArrayList;

public class MiningBuilding extends Building{
    private Item.ItemType production;
    private int rate;
    ArrayList<Item> storage;
    public MiningBuilding(User owner, int number, Building building, int x, int y, MiningBuildingType.MiningType miningType) {
        super(owner, building, x, y);
        this.storage = new ArrayList<>();
        this.production = miningType.getProduction();
    }

    public Item.ItemType getProduction() {
        return production;
    }

    public int getRate() {
        return rate;
    }

    public ArrayList<Item> getStorage() {
        return storage;
    }

    public void addItemToStorage(Item item) {
        this.storage.add(item);
    }
}
