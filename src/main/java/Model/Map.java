package Model;

import java.util.ArrayList;

public class Map {
    private ArrayList<MapCell> mapCells;
    private final int length;
    private final int width;
    private int id;
    private static int idCount = 1;

    public Map(int length, int width) {
        this.length = length;
        this.width = width;
        this.id = idCount;
        idCount++;
        for(int i=1; i<=length; i++) {
            for(int j=1; j<=width; j++) {
                mapCells.add(new MapCell(i, j, ));
                //todo: add default material to map cell constructor
            }
        }
    }

    public ArrayList<MapCell> getMapCells() {
        return mapCells;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getId() {
        return id;
    }
}
