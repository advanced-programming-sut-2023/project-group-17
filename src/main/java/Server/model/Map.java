package Server.model;

import Server.model.MapGeneration.MapOrganizer;

import java.util.ArrayList;

public class Map {
    private final ArrayList<MapCell> mapCells;
    private final int length;
    private final int width;
    private final int id;
    private static int idCount = 1;

    public Map(int length, int width) {
        this.length = length;
        this.width = width;
        MapOrganizer.getMapId();
        this.id = Database.getMapId().size() + 1;
        idCount++;
        this.mapCells = new ArrayList<>();
        for(int i=1; i<=width; i++) {
            for(int j=1; j<=length; j++) {
                mapCells.add(new MapCell(i, j, MaterialMap.textureMap.LAND));
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

    public MapCell getMapCellByCoordinates(int x, int y) {
        for (MapCell mapCell : mapCells) {
            if(mapCell.getX() == x && mapCell.getY() == y) {
                return mapCell;
            }
        }
        return null;
    }
}
