package Controller;

import Model.Buildings.Building;
import Model.Database;
import Model.Items.Item;
import Model.Map;
import Model.MapCell;
import Model.MapCellItems.MapCellItems;
import Model.User;

import javax.xml.crypto.Data;
import java.util.*;

public class MoveController {
    static boolean isValid(Map map, int x, int y) {
        return x >= 1 && x <= map.getWidth() && y >= 1 && y <= map.getLength();
    }

    static boolean isUnBlocked(Map map, int x, int y) {
        return Utils.CheckMapCell.mapCellTraversableByCoordinates(x, y);
    }

    static boolean isDestination(int x, int y, int destinationX, int destinationY) {
        return x == destinationX && y == destinationY;
    }

    static double calculateHValue(int currentX, int currentY, int goalX, int goalY) {
        return (Math.sqrt((currentX - goalX) * (currentX - goalX) + (currentY - goalY) * (currentY - goalY)));
    }

    static ArrayList<MapCell> tracePath(Map map, int goalX, int goalY) {
        int row = goalX;
        int col = goalY;
        MapCell mapCell = map.getMapCellByCoordinates(row, col);;
        ArrayList<MapCell> path = new ArrayList<>();

        while (!(mapCell.getParentX() == row && mapCell.getParentY() == col)) {
            path.add(mapCell);
            int tmpRow = mapCell.getParentX();
            int tmpCol = mapCell.getParentY();
            row = tmpRow;
            col = tmpCol;
            mapCell = map.getMapCellByCoordinates(row, col);
        }

        mapCell = map.getMapCellByCoordinates(row, col);
        path.add(mapCell);
//        for (MapCell p : path) {
//            System.out.println("-> (" + p.getX() + ", " + p.getY() + ")");
//        }
        return path;
    }

    public static ArrayList<MapCell> aStarSearch(Map map, int currentX, int currentY, int goalX, int goalY) {
        if (!isUnBlocked(map, goalX, goalY)) {
            return null;
        }

        if (isDestination(currentX, currentY, goalX, goalY)) {
            return null;
        }

        boolean[][] closedList = new boolean[map.getWidth()][map.getLength()];
        for(boolean[] arr : closedList) {
            Arrays.fill(arr, false);
        }

        MapCell startCell = map.getMapCellByCoordinates(currentX, currentY);
        MapCell destinationCell = map.getMapCellByCoordinates(goalX, goalY);

        startCell.setfValue(0);
        startCell.setgValue(0);
        startCell.sethValue(0);
        startCell.setParentX(currentX);
        startCell.setParentY(currentY);

        LinkedHashMap<MapCell, Integer> openList = new LinkedHashMap<>();
        openList.put(startCell, 0);

        boolean foundDest = false;

        while (openList.size() > 0) {
            MapCell tmpCell = openList.entrySet().iterator().next().getKey();
            openList.remove(tmpCell);
            int x = tmpCell.getX();
            int y = tmpCell.getY();
            closedList[x-1][y-1] = true;

            int gNew, fNew;
            double hNew;
            MapCell newCell;

            int[] xMove = {-1, 1, 0, 0, -1, -1, 1, 1};
            int[] yMove = {0, 0, 1, -1, 1, -1, 1, -1};

            for(int i = 0; i < 8; i++) {
                int newX = x+xMove[i];
                int newY = y+yMove[i];
                if(isValid(map, newX, newY)){
                    newCell = map.getMapCellByCoordinates(newX, newY);
                    if(isDestination(newX, newY, goalX, goalY)) {
                        newCell.setParentX(x);
                        newCell.setParentY(y);
                        foundDest = true;
                        return tracePath(map, goalX, goalY);
                    }
                    else if(!closedList[newX-1][newY-1] && isUnBlocked(map, newX, newY)) {
                        gNew = map.getMapCellByCoordinates(x, y).getgValue() + 1;
                        hNew = calculateHValue(newX, newY, goalX, goalY);
                        fNew = (int) (gNew + hNew);
                        if(newCell.getfValue() == 2147483647 || (newCell.getfValue() > fNew)) {
                            openList.put(newCell, fNew);
                            newCell.setfValue(fNew);
                            newCell.setgValue(gNew);
                            newCell.sethValue(hNew);
                            newCell.setParentX(x);
                            newCell.setParentY(y);
                        }
                    }
                }
            }
        }
        return null;
    }
}

//https://www.geeksforgeeks.org/a-search-algorithm/