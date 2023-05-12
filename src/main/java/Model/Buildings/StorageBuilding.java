package Model.Buildings;

import Model.*;
import Model.Items.Animal;
import Model.Items.Item;

import java.util.ArrayList;
import java.util.Objects;

public class StorageBuilding extends Building{
    private ArrayList<Item> storedItems;
    private ArrayList<Item.ItemType> storedItemTypes;
    public StorageBuilding(User owner, int x, int y, StorageBuilding storageBuilding) {
        super(owner, storageBuilding, x, y);
        this.storedItems = new ArrayList<>();
        this.storedItemTypes = storageBuilding.getStoredItemTypes();
        addStoredItems();
        if(storageBuilding.getBuildingName().equals("stable")) {
            Objects.requireNonNull(Item.getAvailableItems("horse")).changeNumber(4);
            Database.getCurrentMapGame().getMapCellByCoordinates(this.getX(), this.getY()).
                    addAnimal((Animal) Item.getAvailableItems("horse"));
        }
        if (storageBuilding.getBuildingName().equals("caged war dogs")) {
            Objects.requireNonNull(Item.getAvailableItems("dog")).changeNumber(3);
            Database.getCurrentMapGame().getMapCellByCoordinates(this.getX(), this.getY()).
                    addAnimal((Animal) Item.getAvailableItems("dog"));
        }
    }

    public ArrayList<Item> getStoredItems() {
        return storedItems;
    }

    public void addItem(Item item) {
        this.storedItems.add(item);
    }

    public Item getItemByName(String name) {
        for (Item item1 : storedItems) {
            if(item1.getItemName().equals(name)) return item1;
        }
        return null;
    }

    public ArrayList<Item.ItemType> getStoredItemTypes() {
        return storedItemTypes;
    }

    public void addStoredItems() {
        for (Item.ItemType itemType : storedItemTypes)
            this.storedItems.add(Item.getAvailableItems(itemType.getName()));
    }
}
