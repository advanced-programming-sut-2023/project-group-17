package Model.Buildings;

import Model.Items.Item;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionBuilding extends Building{
    private HashMap<Item.ItemType, Item.ItemType> productionItem;
    private String relatedStorageBuildingName;
    int rate;
    public ProductionBuilding(User owner, Building building, int x, int y, ProductionBuildingType.ProductionType productionType) {
        //TODO: constructor hameye buildinga
        super(owner, building, x, y);
        this.productionItem = productionType.getProductionItem();
        this.relatedStorageBuildingName = productionType.getRelatedStorageBuildingName();
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
