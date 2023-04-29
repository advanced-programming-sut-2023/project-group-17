package Model;

import Model.AttackToolsAndMethods.AttackToolsAndMethods;
import Model.Buildings.Building;
import Model.Items.Item;
import Model.MapCellItems.MapCellItems;
import Model.MapCellItems.Rock;
import Model.MapCellItems.Tree;
import Model.MapCellItems.Wall;
import Model.People.Person;
import Model.People.Soldier;

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
    private String color;
    public MapCell(int x, int y, MaterialMap.textureMap materialMap) {
        this.x = x;
        this.y = y;
        this.materialMap = materialMap;
        people = new ArrayList<Person>();
        items = new ArrayList<Item>();
        mapCellItems = new ArrayList<MapCellItems>();
        attackToolsAndMethods = new ArrayList<AttackToolsAndMethods>();
        this.color = materialMap.getColor();
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
        this.materialMap = textureMap;
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
    public boolean haveAttackTools() {
        return attackToolsAndMethods.size() != 0;
    }
    public Wall getWall() {
        for (MapCellItems mapCellItem : mapCellItems) {
            if (mapCellItem instanceof Wall) return (Wall) mapCellItem;
        }
        return null;
    }
    public Tree getTree() {
        for (MapCellItems mapCellItem : mapCellItems) {
            if (mapCellItem instanceof Tree) return (Tree) mapCellItem;
        }
        return null;
    }
    public Rock getRock() {
        for (MapCellItems mapCellItem : mapCellItems) {
            if (mapCellItem instanceof Rock) return (Rock) mapCellItem;
        }
        return null;
    }
    public ArrayList<Soldier> getSoldier() {
        ArrayList<Soldier> soldiers = new ArrayList<>();
        for (Person person : people) {
            if (person instanceof Soldier) soldiers.add((Soldier) person);
        }
        return soldiers;
    }
    public boolean canDropItems() {
        return !haveMapCellItem() && !haveBuilding() && !haveAttackTools();
    }

    @Override
    public String toString() {
        String mapCellString = "";
        mapCellString += materialMap.getColor() +"|";
        for (int i = 0; i < 3; i++) mapCellString += "#";
        mapCellString += "|" + Color.ANSI_RESET + "\n";
        mapCellString += materialMap.getColor() + "|#";
        mapCellString += objectInCell();
        mapCellString += "#|" + Color.ANSI_RESET + "\n" + materialMap.getColor() + "|";
        for (int i = 0; i < 3; i++) mapCellString += "#";
        mapCellString += "|" + Color.ANSI_RESET + "\n";
        return mapCellString;
    }

    public String objectInCell() {
        if (haveBuilding()) return  "B";
        if (getRock() != null) return "R";
        if (getTree() != null) return "T";
        if (getWall() != null) return "W";
        if (getSoldier().size() != 0) return "S";
        return "#";
    }
}
