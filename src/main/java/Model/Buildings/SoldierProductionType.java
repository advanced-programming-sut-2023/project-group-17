package Model.Buildings;

import Model.Database;
import Model.People.Soldier;

import java.util.ArrayList;
import java.util.HashMap;

public class SoldierProductionType {
    public enum SoldierType {
        BARRACKS("barracks", new HashMap<>(){{
        put(new Soldier(null, Database.getSoldierDataByName("archer")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("crossbowmen")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("spearman")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("pikemen")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("macemen")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("swordsmen")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("knight")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("tunneler")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("ladderman")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("black monk")), 0);
        }}),
        ENGINEER_GUILD("engineer guild", new HashMap<>(){{
            put(new Soldier(null, Database.getSoldierDataByName("engineer")), 0);
        }}),
        MERCENARY_POST("mercenary post", new HashMap<>(){{
        put(new Soldier(null, Database.getSoldierDataByName("archer bow")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("slave")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("slinger")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("assasin")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("horse archer")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("arabian swordsman")), 0);
        put(new Soldier(null, Database.getSoldierDataByName("fire thrower")), 0);
        }})
        ;
        String name;
        private ArrayList<Soldier> storedSoldiers;
        private HashMap<Soldier, Integer> soldiersTrained;
        SoldierType(String name, HashMap<Soldier, Integer> soldiersTrained) {
            this.name = name;
            this.soldiersTrained = soldiersTrained;
        }

        public String getName() {
            return name;
        }

        public ArrayList<Soldier> getStoredSoldiers() {
            return storedSoldiers;
        }

        public HashMap<Soldier, Integer> getSoldiersTrained() {
            return soldiersTrained;
        }
    }
    public static SoldierType getSoldierProductionType(String name) {
        for (SoldierType soldierProductionType : SoldierType.values()) {
            if(soldierProductionType.name.equals(name)) return soldierProductionType;
        }
        return null;
    }
}
