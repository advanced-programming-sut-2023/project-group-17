package Controller;

import Model.Database;
import Model.People.Person;
import Model.People.Soldier;
import View.Enums.Messages.UnitMenuMessages;

import java.util.ArrayList;

public class UnitMenuController {
    private ArrayList<Person> selectedUnit;
    public UnitMenuMessages selectUnit(int x, int y) {
        if(!Utils.CheckMapCell.validationOfX(x)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if(!Utils.CheckMapCell.validationOfY(y)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;

        for (Person person : selectedUnit = Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getPeople()) {
            if((person instanceof Soldier) && person.getOwner().equals(Database.getLoggedInUser())) {
                selectedUnit.add(person);
            }
        }

        if(selectedUnit.size() == 0) return UnitMenuMessages.DOES_NOT_INCLUDE_UNIT;
        return UnitMenuMessages.SUCCESS;
    }

    public UnitMenuMessages moveUnitTo(int x, int y) {
        return null;
    }

    public UnitMenuMessages patrolUnit(int x1, int y1, int x2, int y2) {
        return null;
    }

    public UnitMenuMessages setUnitMood(int x, int y, String status) {
        return null;
    }

    public UnitMenuMessages attackEnemy(int x, int y) {
        return null;
    }

    public UnitMenuMessages airAttack(int x, int y) {
        return null;
    }

    public UnitMenuMessages pourOil(String direction) {
        return null;
    }

    public UnitMenuMessages digTunnel(int x, int y) {
        return null;
    }

    public UnitMenuMessages buildSurroundingEquipment(String buildingName) {
        return null;
    }

    public UnitMenuMessages disbandUnit() {
        return null;
    }

    public UnitMenuMessages digMoat(int x, int y) {
        return null;
    }

    public UnitMenuMessages fillMoat(int x, int y) {
        return null;
    }

    public UnitMenuMessages burnOil() {
        return null;
    }

    private void ifDefensiveBuildingExists() {
        //TODO
    }
}
