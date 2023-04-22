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
    private MaterialMap.textureMap materialMap;
    private Building building;
    private ArrayList<Person> people;
    private ArrayList<Item> items;
    private ArrayList<MapCellItems> mapCellItems;
    private ArrayList<AttackToolsAndMethods> attackToolsAndMethods;
    public MapCell(int x, int y, MaterialMap.textureMap materialMap) {
        this.x = x;
        this.y = y;
        this.materialMap = materialMap;
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

    public MaterialMap.textureMap getMaterialMap() {
        return materialMap;
    }

    public Building getBuilding() {
        return building;
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

    public void setMaterialMap(MaterialMap.textureMap textureMap) {
        this.materialMap = materialMap;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setMapCellItems(ArrayList<MapCellItems> mapCellItems) {
        this.mapCellItems = mapCellItems;
    }

    public void setAttackToolsAndMethods(ArrayList<AttackToolsAndMethods> attackToolsAndMethods) {
        this.attackToolsAndMethods = attackToolsAndMethods;
    }

    public void addBuilding(Building building) {
        this.building = building;
    }

    public void addPeople(Person person) {
        this.people.add(person);
    }

    public void addItems(Item item) {
        this.items.add(item);
    }

    public void addMapCellItems(MapCellItems mapCellItem) {
        this.mapCellItems.add(mapCellItem);
    }

    public void addAttackToolsAndMethods(AttackToolsAndMethods attackToolsAndMethod) {
        this.attackToolsAndMethods.add(attackToolsAndMethod);
    }
    public boolean haveBuilding() {
        return building != null;
    }
    public boolean haveMapCellItem() {
        return mapCellItems.size() != 0;
    }
}
