package Model.Buildings;

import Model.*;
import Model.Items.Animal;
import Model.Items.Item;
import Model.People.Soldier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class StorageBuilding extends Building{
    private ArrayList<Item> storedItems;
    private ArrayList<Item.ItemType> storedItemTypes;
    public StorageBuilding(User owner, Building building, int x, int y, StorageBuildingType.StorageType storageType) {
        super(owner, building, x, y);
        this.storedItems = new ArrayList<>();
        this.storedItemTypes = storageType.getStoredItemsTypes();
        if(building.getBuildingName().equals("stable")) createHorses(x, y);
    }

    private void createHorses(int x, int y) {
        Empire empire = Database.getLoggedInUser().getEmpire();
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        Animal animal = new Animal(Animal.animalNames.HORSE, Database.getLoggedInUser(), 4);
        mapCell.addItems(animal);
        empire.addAnimal(animal);
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
}
