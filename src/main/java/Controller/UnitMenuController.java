package Controller;

import Model.Database;
import Model.MapCell;
import Model.MaterialMap;
import Model.People.Person;
import Model.People.Soldier;
import Model.People.Tunneler;
import Utils.CheckMapCell;
import View.Enums.Messages.UnitMenuMessages;

import java.util.ArrayList;

public class UnitMenuController {
    private ArrayList<Person> selectedUnit;
    public UnitMenuMessages selectUnit(int x, int y) {
        if(!Utils.CheckMapCell.validationOfX(x)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if(!Utils.CheckMapCell.validationOfY(y)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;

        for (Person person : selectedUnit = Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getPeople()) {
            if((person instanceof Soldier) && person.getOwner().equals(Database.getCurrentUser())) {
                selectedUnit.add(person);
                person.setCoordinates(x, y);
            }
        }

        if(selectedUnit.size() == 0) return UnitMenuMessages.DOES_NOT_INCLUDE_UNIT;
        return UnitMenuMessages.SUCCESS;
    }

    public UnitMenuMessages moveUnitTo(int x, int y) {
        if(!Utils.CheckMapCell.validationOfX(x)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if(!Utils.CheckMapCell.validationOfY(y)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;
        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        for (Person person : selectedUnit) {
            if(((Soldier)person).getSpeed() < ((int)Math.sqrt(Math.pow(person.getX() - x, 2) + Math.pow(person.getY() - y, 2))))
                return UnitMenuMessages.DISTANCE_OUT_OF_BOUNDS;
        }

        //TODO: traversable???

        for (Person person : selectedUnit) {
            person.setDestination(mapCell);
        }

        if (selectedUnit.size() > 0) {
            selectedUnit.subList(0, selectedUnit.size()).clear();
        }
        return UnitMenuMessages.SUCCESS;
    }

    public UnitMenuMessages patrolUnit(int x1, int y1, int x2, int y2) {
        if(!Utils.CheckMapCell.validationOfX(x1) || !Utils.CheckMapCell.validationOfX(x2))
            return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if(!Utils.CheckMapCell.validationOfY(y1) || !Utils.CheckMapCell.validationOfY(y2))
            return UnitMenuMessages.Y_OUT_OF_BOUNDS;

        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;

        //TODO: traversable???

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x1, y1);
        MapCell secondMapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x2, y2);

        for (Person person : selectedUnit) {
            if(((Soldier)person).getSpeed() < ((int)Math.sqrt(Math.pow(person.getX() - x1, 2) + Math.pow(person.getY() - y1, 2))))
                return UnitMenuMessages.DISTANCE_OUT_OF_BOUNDS;
        }

        for (Person person : selectedUnit) {
            if(((Soldier)person).getSpeed() < ((int)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))))
                return UnitMenuMessages.DISTANCE_OUT_OF_BOUNDS;
        }

        for (Person person : selectedUnit) {
            person.setDestination(mapCell);
            person.setSecondDestination(secondMapCell);
        }

        if (selectedUnit.size() > 0) {
            selectedUnit.subList(0, selectedUnit.size()).clear();
        }
        return UnitMenuMessages.SUCCESS;
    }

    public UnitMenuMessages setUnitMood(int x, int y, String status) {
        boolean isMySoldier = false;
        if (!CheckMapCell.validationOfX(x)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;
        if (Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getSoldier() ==  null) return UnitMenuMessages.DOES_NOT_INCLUDE_UNIT;

        for (Soldier soldier : Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getSoldier()) {
            if(soldier.getOwner().equals(Database.getCurrentUser())) {
                soldier.setStatus(status);
                isMySoldier = true;
                //TODO: need for save soldier??? bcz of json
            }
        }
        if(!isMySoldier)
            return UnitMenuMessages.OPPONENT_UNIT;
        return UnitMenuMessages.SUCCESS;
    }

    public UnitMenuMessages attackEnemy(int x, int y) {
        if(!Utils.CheckMapCell.validationOfX(x)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if(!Utils.CheckMapCell.validationOfY(y)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;
        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;
        if(Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getSoldier() == null) return UnitMenuMessages.DOES_NOT_INCLUDE_UNIT;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        boolean isCorrectSoldier = false;

        for (Person person : selectedUnit) {
            int distance = ((int)Math.sqrt(Math.pow(person.getX() - x, 2) + Math.pow(person.getY() - y, 2)));
            if(((Soldier)person).getAttackRange() == 1) {
                if(((Soldier)person).getSpeed() < distance) return UnitMenuMessages.DISTANCE_OUT_OF_BOUNDS;
                else person.setDestination(mapCell);
                isCorrectSoldier = true;
            }
        }

        if(!isCorrectSoldier)
            return UnitMenuMessages.INVALID_TYPE_OF_SELECTED_UNIT;

        if (selectedUnit.size() > 0) {
            selectedUnit.subList(0, selectedUnit.size()).clear();
        }
        return UnitMenuMessages.SUCCESS;
    }

    public UnitMenuMessages airAttack(int x, int y) {
        if(!Utils.CheckMapCell.validationOfX(x)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if(!Utils.CheckMapCell.validationOfY(y)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;
        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;
        if(Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getSoldier() == null) return UnitMenuMessages.DOES_NOT_INCLUDE_UNIT;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        boolean isCorrectSoldier = false;
        boolean isReachable = false;

        for (Person person : selectedUnit) {
            int distance = ((int)Math.sqrt(Math.pow(person.getX() - x, 2) + Math.pow(person.getY() - y, 2)));
            if(((Soldier)person).getAttackRange() > 1) {
                isCorrectSoldier = true;
                if (((Soldier) person).getAttackRange() >= distance) {
                    isReachable = true;
                }
            }
        }

        if(!isReachable)
            return UnitMenuMessages.DISTANCE_OUT_OF_BOUNDS;

        if(!isCorrectSoldier)
            return UnitMenuMessages.INVALID_TYPE_OF_SELECTED_UNIT;

        if (selectedUnit.size() > 0) {
            selectedUnit.subList(0, selectedUnit.size()).clear();
        }
        return UnitMenuMessages.SUCCESS;
    }

    public UnitMenuMessages pourOil(String direction) {
        return null;
    }

    public UnitMenuMessages digTunnel(int x, int y) {
        if(!Utils.CheckMapCell.validationOfX(x)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if(!Utils.CheckMapCell.validationOfY(y)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;
        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        if(!mapCell.getMaterialMap().equals(MaterialMap.textureMap.LAND) &&
           !mapCell.getMaterialMap().equals(MaterialMap.textureMap.GRASS) &&
           !mapCell.getMaterialMap().equals(MaterialMap.textureMap.MEADOW))
            return UnitMenuMessages.INAPPROPRIATE_TEXTURE;

        for (Person person : selectedUnit) {
            if (person instanceof Tunneler) {
                //TODO: dig tunnel/ make it invisible/ find the nearest wall


                selectedUnit.subList(0, selectedUnit.size()).clear();
                return UnitMenuMessages.SUCCESS;
            }
        }

        return UnitMenuMessages.INVALID_TYPE_OF_SELECTED_UNIT;
    }

    public UnitMenuMessages buildSurroundingEquipment(String buildingName) {
        return null;
    }

    public UnitMenuMessages disbandUnit() {
        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;

        int x = Database.getCurrentUser().getEmpire().getHeadquarter().getX() - 1;
        int y = Database.getCurrentUser().getEmpire().getHeadquarter().getY() - 1;

        for (Person person : selectedUnit) {
            person.setDestination(Database.getCurrentMapGame().getMapCellByCoordinates(x, y));
        }

        if (selectedUnit.size() > 0) {
            selectedUnit.subList(0, selectedUnit.size()).clear();
        }
        return UnitMenuMessages.SUCCESS;
    }

    public UnitMenuMessages digMoat(int x, int y) {
        if(!Utils.CheckMapCell.validationOfX(x)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if(!Utils.CheckMapCell.validationOfY(y)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;
        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        if(!mapCell.getMaterialMap().equals(MaterialMap.textureMap.LAND) &&
           !mapCell.getMaterialMap().equals(MaterialMap.textureMap.MEADOW) &&
           !mapCell.getMaterialMap().equals(MaterialMap.textureMap.GRASS))
            return UnitMenuMessages.INAPPROPRIATE_TEXTURE;

        for (Person person : selectedUnit) {
            if(((Soldier)person).canDigMoat()) {
                mapCell.setMaterialMap(MaterialMap.textureMap.MOAT);
                //TODO:  save map when texture is changed??
                selectedUnit.subList(0, selectedUnit.size()).clear();
                return UnitMenuMessages.SUCCESS;
            }
        }
        return UnitMenuMessages.INVALID_TYPE_OF_SELECTED_UNIT;
    }

    public UnitMenuMessages fillMoat(int x, int y) {
        if(!Utils.CheckMapCell.validationOfX(x)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if(!Utils.CheckMapCell.validationOfY(y)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;
        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        if(!mapCell.getMaterialMap().equals(MaterialMap.textureMap.MOAT)) return UnitMenuMessages.NO_MOAT;

        for (Person person : selectedUnit) {
            if(((Soldier)person).canDigMoat()) {
                mapCell.setMaterialMap(MaterialMap.textureMap.LAND);
                //TODO: save map when texture is change??
                selectedUnit.subList(0, selectedUnit.size()).clear();
                return UnitMenuMessages.SUCCESS;
            }
        }

        return UnitMenuMessages.INVALID_TYPE_OF_SELECTED_UNIT;
    }

    public UnitMenuMessages burnOil() {
        return null;
    }

    private void ifDefensiveBuildingExists() {
        //TODO
    }
}
