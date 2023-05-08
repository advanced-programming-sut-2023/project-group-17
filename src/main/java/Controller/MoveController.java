package Controller;

import Model.Database;
import Model.Map;
import Model.MapCell;

import java.util.*;

import static java.lang.Math.abs;

public class MoveController {
    static boolean isValid(Map map, int x, int y) {
        return x >= 1 && x < map.getWidth() && y >= 1 && y < map.getLength();
    }

    static boolean isUnBlocked(Map map, int x, int y) {
        return Utils.CheckMapCell.mapCellEmptyByCoordinates(x, y);
    }

    static boolean isDestination(int x, int y, int destinationX, int destinationY) {
        return x == destinationX && y == destinationY;
    }

    static double calculateHValue(int currentX, int currentY, int goalX, int goalY) {
        return (Math.sqrt((currentX - goalX) * (currentX - goalX) + (currentY - goalY) * (currentY - goalY)));
    }

    static void tracePath(Map map, int goalX, int goalY) {
        System.out.println("the path is ");
        //TODO: ja be ja
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
        MapCell p;
        while (path.size() > 0) {
            p = path.get(0);
            path.remove(0);

            if(p.getX() == 2 || p.getX() == 1){
                System.out.println("-> (" + p.getX() + ", " + (p.getY() - 1) + ")");
            }
            else System.out.println("-> (" + p.getX() + ", " + p.getY() + ")");
        }
        return;
    }

    public static void aStarSearch(Map map, int currentX, int currentY, int goalX, int goalY) {
        if (!isValid(map, currentX, currentY)) {
            System.out.println("start cell is invalid\n");
            return;
        }

        if (!isValid(map, goalX, goalY)) {
            System.out.println("destination cell is invalid\n");
            return;
        }

        if (!isUnBlocked(map, currentX, currentY) || !isUnBlocked(map, goalX, goalY)) {
            System.out.println("the start cell or the destination cell is blocked\n");
            return;
        }

        if (isDestination(currentX, currentY, goalX, goalY)) {
            System.out.println("We are already at the destination\n");
            return;
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
            closedList[x][y] = true;

            int gNew, fNew;
            double hNew;
            MapCell newCell;

            int[] xMove = {-1, 1, 0, 0, -1, -1, 1, 1};
            int[] yMove = {0, 0, 1, -1, 1, -1, 1, -1};

            for(int i = 0 ; i < 7; i++) {
                int newX = x+xMove[i];
                int newY = y+yMove[i];
                newCell = map.getMapCellByCoordinates(newX, newY);
                if(isValid(map, newX, newY)){
                    if(isDestination(newX, newY, goalX, goalY)) {
                        newCell.setParentX(x);
                        newCell.setParentY(y);
                        System.out.println("The destination cell is found\n");
                        tracePath(map, goalX, goalY);
                        foundDest = true;
                        return;
                    }
                    else if(!closedList[newX][newY] && isUnBlocked(map, newX, newY)) {
                        gNew = map.getMapCellByCoordinates(x, y).getgValue() + 1;
                        hNew = calculateHValue(newX, newY, goalX, goalY);
                        fNew = (int) (gNew + hNew);
                        if(newCell.getfValue() == 2147483647 || newCell.getfValue() > fNew) {
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
    }
    public static void main(String[] args) {
        Map map = new Map(10, 10);
        Database.setCurrentMapGame(map);
        aStarSearch(map, 1, 1, 4, 3);
    }

}
