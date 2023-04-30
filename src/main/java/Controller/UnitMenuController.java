package Controller;

import Model.Database;
import Model.Direction;
import Model.People.Person;
import Model.People.Soldier;
import Model.People.UnitAttributes.Status;
import View.Enums.Messages.UnitMenuMessages;

import java.util.ArrayList;

public class UnitMenuController {
    private ArrayList<Person> selectedUnit;
    public UnitMenuMessages selectUnit(Integer x, Integer y) {
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

    public UnitMenuMessages moveUnit(Integer x, Integer y) {
        return null;
    }

    public UnitMenuMessages patrolUnit(Integer x1, Integer y1, Integer x2, Integer y2) {
        return null;
    }

    public UnitMenuMessages setUnitMood(Integer x, Integer y, Status status) {
        return null;
    }

    public UnitMenuMessages attackEnemy(Integer x, Integer y) {
        return null;
    }

    public UnitMenuMessages airAttack(Integer x, Integer y) {
        return null;
    }

    public UnitMenuMessages pourOil(Direction direction) {
        return null;
    }

    public UnitMenuMessages digTunnel(Integer x, Integer y) {
        return null;
    }

    public UnitMenuMessages buildSurroundingEquipment(String buildingName) {
        return null;
    }

    public UnitMenuMessages disbandUnit() {
        return null;
    }

    public UnitMenuMessages digMoat(Integer x, Integer y) {
        return null;
    }

    public UnitMenuMessages fillMoat(Integer x, Integer y) {
        return null;
    }

    public UnitMenuMessages burnOil() {
        return null;
    }
}
