package Controller;

import Model.Buildings.Building;
import Model.Database;
import Model.People.Soldier;
import View.EmpireMenu;
import View.Menu;
import jdk.jshell.execution.Util;

class BuildingsHandler {
    BuildingsHandler next;

//    BuildingsHandler() {
//        HandlerLargeStoneGateHouse largeStoneGateHouse = new HandlerLargeStoneGateHouse();
//        HandlerSmallStoneGateHouse smallStoneGateHouse = new HandlerSmallStoneGateHouse();
//    }
    public void setNext(BuildingsHandler h) {
        this.next = h;
    }
    public void handle(Building building) {
        if(next != null) {
            next.handle(building);
        }
    }
}

class HandlerStoneGateHouse extends BuildingsHandler {
    public void handle(Building building) {
        if(building.getBuildingName().equals("small stone gate house") ||
                building.getBuildingName().equals("large stone gate house")) {
            new EmpireMenu().run();
        }
        else {
            super.setNext(new HandlerDrawBridge());
            super.handle(building);
        }
    }
}

class HandlerDrawBridge extends BuildingsHandler {
    public void handle(Building building) {
        if(building.getBuildingName().equals("drawbridge")) {
            for(int i = building.getX()-1; i <= building.getX()+1; i++) {
                for(int j = building.getY()-1; j <= building.getY()+1; j++) {
                    if(Utils.CheckMapCell.validationOfX(i) && Utils.CheckMapCell.validationOfY(j)) {
                        for (Soldier soldier : Database.getCurrentMapGame().
                                getMapCellByCoordinates(i, j).getSoldier()) {
                            if (!soldier.getOwner().equals(Database.getLoggedInUser())) {
                                soldier.changeSpeed(-1);
                            }
                        }
                    }
                }
            }
        }
        else {
            super.handle(building);
        }
    }
}
