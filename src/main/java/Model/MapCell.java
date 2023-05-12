package Model;

import Model.Buildings.Building;
import Model.Buildings.Trap;
import Model.Items.Animal;
import Model.Items.Item;
import Model.MapCellItems.*;
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
    private AttackToolsAndMethods attackToolsAndMethods;
    private String color;
    private int fValue;
    private int gValue;
    private double hValue;
    private int parentX;
    private int parentY;
    public MapCell(int x, int y, MaterialMap.textureMap materialMap) {
        this.x = x;
        this.y = y;
        this.materialMap = materialMap;
        people = new ArrayList<Person>();
        items = new ArrayList<Item>();
        mapCellItems = new ArrayList<MapCellItems>();
        this.color = materialMap.getColor();
        this.fValue = 2147483647;
        this.gValue = 2147483647;
        this.hValue = 2147483647;
        this.parentX = -1;
        this.parentY = -1;
    }
    public void clear() {
        setBuilding(null);
        getMapCellItems().clear();
        getItems().clear();
        setAttackToolsAndMethods(null);
        getPeople().clear();
        setMaterialMap(MaterialMap.getTextureMap("land"));
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

    public AttackToolsAndMethods getAttackToolsAndMethods() {
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

    public void setAttackToolsAndMethods(AttackToolsAndMethods attackToolsAndMethods) {
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
        this.attackToolsAndMethods = (attackToolsAndMethod);
    }
    public boolean haveBuilding() {
        return building != null;
    }
    public boolean haveMapCellItem() {
        return mapCellItems.size() != 0;
    }
    public boolean haveAttackTools() {
        return attackToolsAndMethods != null;
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
    public Tunnel getTunnel() {
        for (MapCellItems mapCellItem : mapCellItems) {
            if (mapCellItem instanceof Tunnel) return (Tunnel) mapCellItem;
        }
        return null;
    }
    public Stair getStair() {
        for (MapCellItems mapCellItem : mapCellItems) {
            if (mapCellItem instanceof Stair) return (Stair) mapCellItem;
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

    public void removePerson(Person normalPerson) {
        for (Person person : people) {
            if(person.equals(normalPerson)) {
                people.remove(person);
                break;
            }
        }
    }

    public void removeAnimal(Animal animal) {
        for (Item item : items) {
            if(item.equals(animal)) {
                items.remove(animal);
                break;
            }
        }
    }

    public boolean canDropItems() {
        return !haveMapCellItem() && !haveBuilding() && !haveAttackTools();
    }

    public boolean isTraversable() {
        if (haveBuilding() && !(getBuilding() instanceof Trap)) return false;

        if (getTunnel() != null) return false;
        if (getWall() != null && dfsForStair(getX(), getY())) {
            checkForDfs.clear();
            return true;
        }
        if (checkForDfs != null) checkForDfs.clear();
        if (getWall() != null) return false;

        return getMaterialMap().isTraversable();
    }

    private ArrayList<MapCell> checkForDfs = new ArrayList<>();
    public boolean dfsForStair(int firstX, int firstY) {
        int x , y;
        MapCell mapCell;
        int[] xMove = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] yMove = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
        for (int i = 0; i < 9; i++) {
            x = firstX + xMove[i];
            y = firstY + yMove[i];
            if (Utils.CheckMapCell.validationOfX(x) && Utils.CheckMapCell.validationOfY(y) &&
            !checkForDfs.contains(Database.getCurrentMapGame().getMapCellByCoordinates(x, y)) &&
            Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getWall() != null) {
                mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
                checkForDfs.add(mapCell);
                if (mapCell.getWall().haveStairs() || dfsForStair(x, y)) {
                    return true;
                }
            }
        }
        return false;
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
        if (getSoldier().size() != 0) return "S";
        if (haveBuilding()) return "B";
        if (haveAttackTools()) return "A";
        if (getRock() != null) return "R";
        if (getTree() != null) return "T";
        if (getWall() != null) return "W";
        if (getStair() != null) return "P";
        return "#";
    }

    public int getfValue() {
        return fValue;
    }

    public void setfValue(int fValue) {
        this.fValue = fValue;
    }

    public int getgValue() {
        return gValue;
    }

    public void setgValue(int gValue) {
        this.gValue = gValue;
    }

    public double gethValue() {
        return hValue;
    }

    public void sethValue(double hValue) {
        this.hValue = hValue;
    }

    public int getParentX() {
        return parentX;
    }

    public void setParentX(int parentX) {
        this.parentX = parentX;
    }

    public int getParentY() {
        return parentY;
    }

    public void setParentY(int parentY) {
        this.parentY = parentY;
    }

    public void removeBuilding(Building building) {
        if(this.building.equals(building)) this.building = null;
    }

    public void addAnimal(Animal animal) {
        items.add(animal);
    }
}
