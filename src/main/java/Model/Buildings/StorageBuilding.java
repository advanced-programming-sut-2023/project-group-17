package Model.Buildings;

import Model.Database;
import Model.Items.Animal;
import Model.Items.Item;
import Model.Map;
import Model.MapCell;
import Model.People.Soldier;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class StorageBuilding extends Building{
    private ArrayList<Item> storedItems;
    public StorageBuilding(User owner, int number, Building building, int x, int y) {
        super(owner, building, x, y);
        this.storedItems = new ArrayList<>();
        if(building.getBuildingName().equals("stable")) createHorses(x, y);
    }

    private void createHorses(int x, int y) {
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        mapCell.addItems(new Animal(Animal.animalNames.HORSE, Database.getLoggedInUser(), 4));
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
