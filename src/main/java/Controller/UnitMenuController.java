package Controller;

import Model.*;
import Model.Buildings.Building;
import Model.Buildings.DefensiveBuilding;
import Model.MapCellItems.MapCellItems;
import Model.MapCellItems.Tunnel;
import Model.MapCellItems.Wall;
import Model.People.Engineer;
import Model.People.Person;
import Model.People.Soldier;
import Model.People.Tunneler;
import Utils.CheckMapCell;
import View.Enums.Messages.BuildingMenuMessages;
import View.Enums.Messages.UnitMenuMessages;

import javax.xml.crypto.Data;
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

        if(!mapCell.isTraversable()) return UnitMenuMessages.NOT_TRAVERSABLE;

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


        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x1, y1);
        MapCell secondMapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x2, y2);

        if(!mapCell.isTraversable()) return UnitMenuMessages.NOT_TRAVERSABLE;
        if(!secondMapCell.isTraversable()) return UnitMenuMessages.NOT_TRAVERSABLE;

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
        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;

        Soldier engineer = null;
        for (Person person : selectedUnit)
            if(person instanceof Engineer) engineer = (Soldier) person;

        if(engineer == null) return UnitMenuMessages.INVALID_TYPE_OF_SELECTED_UNIT;

        if(!direction.equals(Direction.directions.EAST.getDirectionName()) &&
        !direction.equals(Direction.directions.WEST.getDirectionName()) &&
        !direction.equals(Direction.directions.NORTH.getDirectionName()) &&
        !direction.equals(Direction.directions.SOUTH.getDirectionName()))
            return UnitMenuMessages.INVALID_DIRECTION;

        boolean haveOilSmelter = false;
        for (Building building : Database.getCurrentUser().getEmpire().getBuildings())
            if (building.getBuildingName().equals("oil smelter")) {
                haveOilSmelter = true;
                break;
            }

        if(!haveOilSmelter) return UnitMenuMessages.OIL_SMELTER_DOES_NOT_EXIST;
        if(Database.getCurrentUser().getEmpire().getWeaponByName("oil").getNumber() < 1) return UnitMenuMessages.OIL_SMELTER_EMPTY;

        return pourOilIteration(engineer.getX(), engineer.getY(), engineer);
    }

    public UnitMenuMessages pourOilIteration(int startX, int startY, Soldier engineer) {
        int soldierNumber = 0;
        outerLoop:
        for(int x = startX - 1; x < startX + 2; x++) {
            for(int y = startY - 1; y < startY + 2; y++) {
                soldierNumber = 0;
                if(!Utils.CheckMapCell.validationOfX(x) || !Utils.CheckMapCell.validationOfY(y)) continue;

                MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
                for (Soldier soldier : mapCell.getSoldier()) {
                    if(!soldier.getOwner().equals(Database.getCurrentUser()))
                        soldierNumber++;
                }

                if(engineer.getStatus().equals("offensive") && soldierNumber >= 3) {
                    for (Soldier soldier : mapCell.getSoldier()) {
                        if(!soldier.getOwner().equals(Database.getCurrentUser()))
                            soldier.changeHp(-engineer.getAttackRating());
                    }
                    Database.getCurrentUser().getEmpire().getWeaponByName("oil").changeNumber(-1);
                    break outerLoop;
                }else if(engineer.getStatus().equals("defensive") && soldierNumber >= 1) {
                    for (Soldier soldier : mapCell.getSoldier()) {
                        if(!soldier.getOwner().equals(Database.getCurrentUser()))
                            soldier.changeHp(-engineer.getAttackRating());
                    }
                    Database.getCurrentUser().getEmpire().getWeaponByName("oil").changeNumber(-1);
                    break outerLoop;
                }
            }
        }

        if(soldierNumber == 0) return UnitMenuMessages.DOES_NOT_INCLUDE_UNIT;

        if (selectedUnit.size() > 0) {
            selectedUnit.subList(0, selectedUnit.size()).clear();
        }
        return UnitMenuMessages.SUCCESS;
    }

    public UnitMenuMessages digTunnel(int startX, int startY) {
        if(!Utils.CheckMapCell.validationOfX(startX)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if(!Utils.CheckMapCell.validationOfY(startY)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;
        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(startX, startY);
        if(!mapCell.getMaterialMap().equals(MaterialMap.textureMap.LAND) &&
           !mapCell.getMaterialMap().equals(MaterialMap.textureMap.GRASS) &&
           !mapCell.getMaterialMap().equals(MaterialMap.textureMap.MEADOW))
            return UnitMenuMessages.INAPPROPRIATE_TEXTURE;

        for (Person person : selectedUnit)
            if (person instanceof Tunneler) return digTunnelIteration(startX, startY, mapCell);

        return UnitMenuMessages.INVALID_TYPE_OF_SELECTED_UNIT;
    }

    public UnitMenuMessages digTunnelIteration(int startX, int startY, MapCell mapCell) {
        int range = 20;
        Tunnel tunnel;
        for(int x = startX - range; x < startX + range + 1; x++) {
            for(int y = startY - range; y < startY + range + 1; y++) {
                if(!Utils.CheckMapCell.validationOfX(x) || !Utils.CheckMapCell.validationOfY(y)) continue;

                MapCell endMapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

                if(endMapCell.getBuilding() != null &&
                !endMapCell.getBuilding().getOwner().equals(Database.getCurrentUser()) &&
                endMapCell.getBuilding() instanceof DefensiveBuilding) {
                    endMapCell.getBuilding().changeBuildingHp(-1200);
                    tunnel = new Tunnel(Database.getCurrentUser(), mapCell, endMapCell);
                    mapCell.addMapCellItems(tunnel);
                    selectedUnit.subList(0, selectedUnit.size()).clear();
                    return UnitMenuMessages.SUCCESS;
                }
                else {
                    for (MapCellItems mapCellItem : endMapCell.getMapCellItems()) {
                        if(mapCellItem instanceof Wall && !mapCellItem.getOwner().equals(Database.getCurrentUser())) {
                            ((Wall) mapCellItem).setHp(0);
                            tunnel = new Tunnel(Database.getCurrentUser(), mapCell, endMapCell);
                            mapCell.addMapCellItems(tunnel);
                            selectedUnit.subList(0, selectedUnit.size()).clear();
                            return UnitMenuMessages.SUCCESS;
                        }
                    }
                }
            }
        }
        return UnitMenuMessages.NO_BUILDING_IN_RANGE;
    }

    public UnitMenuMessages buildSurroundingEquipment(int x, int y, String type) {
        if (!CheckMapCell.validationOfX(x)) return UnitMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return UnitMenuMessages.Y_OUT_OF_BOUNDS;

        AttackToolsAndMethods sampleAttackToolsAndMethods = Database.getAttackToolsDataByName(type);
        if (sampleAttackToolsAndMethods == null) return UnitMenuMessages.INVALID_TYPE;

        if(selectedUnit == null) return UnitMenuMessages.UNIT_IS_NOT_SELECTED;

        int numberOfEngineersSelected = 0;
        int numberOfEngineers = sampleAttackToolsAndMethods.getNumberOfEngineers();
        for (Person person : selectedUnit) {
            if(person instanceof Engineer) numberOfEngineersSelected++;
        }
        if(numberOfEngineersSelected < numberOfEngineers) return UnitMenuMessages.INSUFFICIENT_ENGINEER_SELECTED;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        if(!Utils.CheckMapCell.mapCellEmptyByCoordinates(x, y))
            return UnitMenuMessages.CELL_IS_FULL;

        Empire empire = Database.getCurrentUser().getEmpire();
        int golds = sampleAttackToolsAndMethods.getCost();
        if(empire.getCoins() < golds) return UnitMenuMessages.INSUFFICIENT_GOLD;

        AttackToolsAndMethods sample = Database.getAttackToolsDataByName(type);
        AttackToolsAndMethods attackToolsAndMethods = new AttackToolsAndMethods(Database.getCurrentUser(), sample);
        for (Person person : selectedUnit) {
            if(person instanceof Engineer) {
                attackToolsAndMethods.getEngineers().add((Engineer) person);
                Database.getCurrentMapGame().getMapCellByCoordinates(person.getX(), person.getY()).removePerson(person);
                Database.getCurrentMapGame().getMapCellByCoordinates(person.getX(), person.getY()).addPeople(person);
            }
            if(attackToolsAndMethods.getEngineers().size() == numberOfEngineers) break;
        }
        empire.changeCoins(-golds);
        empire.addAttackToolsAndMethods(attackToolsAndMethods);
        mapCell.addAttackToolsAndMethods(attackToolsAndMethods);
        return UnitMenuMessages.SUCCESS;
    }

    public UnitMenuMessages disbandUnit() {
        if(selectedUnit == null) return UnitMenuMessages.NO_UNIT_SELECTED;

        int x = Database.getCurrentUser().getEmpire().getHeadquarter().getX();
        int y = Database.getCurrentUser().getEmpire().getHeadquarter().getY() + 1;

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
}
