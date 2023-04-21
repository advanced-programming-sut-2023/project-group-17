package Model;

import Model.AttackToolsAndMethods.AttackToolsAndMethods;
import Model.Buildings.Building;
import Model.Items.Item;
import Model.MapCellItems.MapCellItems;
import Model.People.Person;

import java.util.ArrayList;

public class MapCell {
    private final int x;
    private final int y;
    private MaterialMap materialMap;
    private ArrayList<Building> buildings;
    private ArrayList<Person> people;
    private ArrayList<Item> items;
    private ArrayList<MapCellItems> mapCellItems;
    private ArrayList<AttackToolsAndMethods> attackToolsAndMethods;
    public MapCell(int x, int y, MaterialMap materialMap) {
        this.x = x;
        this.y = y;
        this.materialMap = materialMap;
        buildings = new ArrayList<Building>();
        people = new ArrayList<Person>();
        items = new ArrayList<Item>();
        mapCellItems = new ArrayList<MapCellItems>();
        attackToolsAndMethods = new ArrayList<AttackToolsAndMethods>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MaterialMap getMaterialMap() {
        return materialMap;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<MapCellItems> getMapCellItems() {
        return mapCellItems;
    }

    public ArrayList<AttackToolsAndMethods> getAttackToolsAndMethods() {
        return attackToolsAndMethods;
    }

    public void setMaterialMap(MaterialMap materialMap) {
        this.materialMap = materialMap;
    }

    public void addBuildings(Building building) {
        this.buildings.add(building);
    }

    public void setPeople(Person person) {
        this.people.add(person);
    }

    public void setItems(Item item) {
        this.items.add(item);
    }

    public void setMapCellItems(MapCellItems mapCellItem) {
        this.mapCellItems.add(mapCellItem);
    }

    public void setAttackToolsAndMethods(AttackToolsAndMethods attackToolsAndMethod) {
        this.attackToolsAndMethods.add(attackToolsAndMethod);
    }
}
