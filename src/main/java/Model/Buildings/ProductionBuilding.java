package Model.Buildings;

import Model.Items.Item;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionBuilding extends Building{
    private HashMap<Item.ItemType, Item.ItemType> productionItem;
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

    public HashMap<Item.ItemType, Item.ItemType> getProductionItem() {
        return productionItem;
    }

    public int getRate() {
        return rate;
    }

    public Item getItemByName(String name) {
        for (Item item1 : storage) {
            if(item1.getItemName().equals(name)) return item1;
        }
        return null;
    }

}
