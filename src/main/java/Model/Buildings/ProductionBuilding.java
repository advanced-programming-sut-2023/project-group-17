package Model.Buildings;

import Model.Items.Item;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionBuilding extends Building{
    private HashMap<Item.ItemType, Item.ItemType> productionItem;
    private String relatedStorageBuildingName;
    int rate;
    public ProductionBuilding(User owner, int x, int y, ProductionBuilding productionBuilding) {
        super(owner, productionBuilding, x, y);
        this.productionItem = productionBuilding.getProductionItem();
        this.relatedStorageBuildingName = productionBuilding.getRelatedStorageBuildingName();
    }

    public HashMap<Item.ItemType, Item.ItemType> getProductionItem() {
        return productionItem;
    }

    public int getRate() {
        return rate;
    }

    public String getRelatedStorageBuildingName() {
        return relatedStorageBuildingName;
    }
}
