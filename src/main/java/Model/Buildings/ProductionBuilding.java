package Model.Buildings;

import Model.Items.Item;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionBuilding extends Building{
    private HashMap<Item.ItemType, Item.ItemType> productionItem;
    private Building relatedStorageBuilding;
    int rate;
    public ProductionBuilding(User owner, int number, Building building, int x, int y) {
        //TODO: constructor hameye buildinga
        super(owner, building, x, y);
    }

    public HashMap<Item.ItemType, Item.ItemType> getProductionItem() {
        return productionItem;
    }

    public int getRate() {
        return rate;
    }
}
