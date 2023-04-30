package Model.Buildings;
class BuildingsHandler {
    BuildingsHandler next;
    public void setNext(BuildingsHandler h) {
        this.next = h;
    }
    public void handle(Building building) {
        if(next != null) {
            next.handle(building);
        }
    }
}

class HandlerSmallStoneGateHouse extends BuildingsHandler {
    public void handle(Building building) {
        if(building.getBuildingName().equals("small stone gate house")) {

        }
        else {
            super.setNext(new HandlerLargeStoneGateHouse());
            super.handle(building);
        }
    }
}

class HandlerLargeStoneGateHouse extends BuildingsHandler {
    public void handle(Building building) {
        if(building.getBuildingName().equals("large stone gate house")) {

        }
        else {
            super.handle(building);
        }
    }
}
