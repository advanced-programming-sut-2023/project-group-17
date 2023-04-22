package Model.Buildings;

import Model.Items.Food;
import Model.Items.Item;
import Model.Items.Resource;
import Model.People.Person;

import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;

public enum BuildingType {
    SMALL_STONE_GATEHOUSE("", "small stone gatehouse", 0, new HashMap<>(), new HashMap<>()),
    LARGE_STONE_GATEHOUSE("", "large stone gatehouse", 0, new HashMap<>(), new HashMap<>()),
    DRAWBRIDGE("", "drawbridge", 0, new HashMap<>(), new HashMap<>()),
    LOOKOUT_TOWER("", "lookout tower", 0, new HashMap<>(), new HashMap<>()),
    PERIMETER_TOWER("", "perimeter tower", 0, new HashMap<>(), new HashMap<>()),
    DEFENCE_TURRET("", "defence turret", 0, new HashMap<>(), new HashMap<>()),
    SQUARE_TOWER("", "square tower", 0, new HashMap<>(), new HashMap<>()),
    ROUND_TOWER("", "round tower", 0, new HashMap<>(), new HashMap<>()),
    ARMOURY("", "armory", 0, new HashMap<>(), new HashMap<>()),
    BARRACKS("", "barracks", 0, new HashMap<>(), new HashMap<>()),
    MERCENARY_POST("", "mercenary post", 0, new HashMap<>(), new HashMap<>()),
    ENGINEER_GUILD("", "engineer guild", 0, new HashMap<>(), new HashMap<>()),
    KILLING_PIT("", "killing pit",0, new HashMap<>(), new HashMap<>() ),
    OIL_SMELTER("", "oil smelter", 0, new HashMap<>(), new HashMap<>()),
    CAGED_WAR_DOGS("", "caged war dogs", 0, new HashMap<>(), new HashMap<>()),
    PITCH_DITCH("", "pitch ditch", 0, new HashMap<>(), new HashMap<>()),
    SIEGE_TENT("", "siege tent", 0, new HashMap<>(), new HashMap<>()),
    STABLE("", "stable", 0, new HashMap<>(), new HashMap<>()),
    APPLE_ORCHARD("", "apple orchard", 0, new HashMap<>(), new HashMap<>()),
    DIARY_FARMER("", "diary farmer", 0, new HashMap<>(), new HashMap<>()),
    HOPS_FARMER("", "hops farmer", 0, new HashMap<>(), new HashMap<>()),
    HUNTER_POST("", "hunter post", 0, new HashMap<>(), new HashMap<>()),
    WHEAT_FARMER("", "wheat farmer", 0, new HashMap<>(), new HashMap<>()),
    BAKERY("", "bakery", 0, new HashMap<>(), new HashMap<>()),
    BREWER("", "brewer", 0, new HashMap<>(), new HashMap<>()),
    GRANARY("", "granary", 0, new HashMap<>(), new HashMap<>()),
    INN("", "inn", 0, new HashMap<>(), new HashMap<>()),
    MILL("", "mill", 0, new HashMap<>(), new HashMap<>()),
    IRON_MINE("", "iron mine", 0, new HashMap<>(), new HashMap<>()),
    MARKET("", "market", 0, new HashMap<>(), new HashMap<>()),
    OX_TETHER("", "ox tether", 0, new HashMap<>(), new HashMap<>()),
    PITCH_RIG("", "pitch rig", 0, new HashMap<>(), new HashMap<>()),
    QUARRY("", "quarry", 0, new HashMap<>(), new HashMap<>()),
    STOCKPILE("", "stockpile", 0, new HashMap<>(), new HashMap<>()),
    WOODCUTTER("", "woodcutter", 0, new HashMap<>(), new HashMap<>()),
    HOVEL("", "hovel", 0, new HashMap<>(), new HashMap<>()),
    CHURCH("", "church", 0, new HashMap<>(), new HashMap<>()),
    CATHEDRAL("", "cathedral", 0, new HashMap<>(), new HashMap<>()),
    ARMOURER("", "armourer", 0, new HashMap<>(), new HashMap<>()),
    BLACKSMITH("", "blacksmith", 0, new HashMap<>(), new HashMap<>()),
    FLETCHER("", "fletcher", 0, new HashMap<>(), new HashMap<>()),
    POLETURNER("", "poleturner", 0, new HashMap<>(), new HashMap<>());

    static {
        LARGE_STONE_GATEHOUSE.cost.put(Resource.resourceType.STONE, 20);
        DRAWBRIDGE.cost.put(Resource.resourceType.WOOD, 10);
        LOOKOUT_TOWER.cost.put(Resource.resourceType.STONE, 10);
        PERIMETER_TOWER.cost.put(Resource.resourceType.STONE, 10);
        DEFENCE_TURRET.cost.put(Resource.resourceType.STONE, 15);
        SQUARE_TOWER.cost.put(Resource.resourceType.STONE, 35);
        ROUND_TOWER.cost.put(Resource.resourceType.STONE, 40);
        ARMOURY.cost.put(Resource.resourceType.WOOD, 5);
        BARRACKS.cost.put(Resource.resourceType.STONE, 15);
        MERCENARY_POST.cost.put(Resource.resourceType.WOOD, 10);
        ENGINEER_GUILD.cost.put(Resource.resourceType.WOOD, 10);
        ENGINEER_GUILD.cost.put(Resource.resourceType.GOLD, 100);
        KILLING_PIT.cost.put(Resource.resourceType.WOOD, 6);
        INN.cost.put(Resource.resourceType.WOOD, 20);
        INN.cost.put(Resource.resourceType.GOLD, 100);
        MILL.cost.put(Resource.resourceType.WOOD, 20);
        IRON_MINE.cost.put(Resource.resourceType.WOOD, 20);
        MARKET.cost.put(Resource.resourceType.WOOD, 5);
        OX_TETHER.cost.put(Resource.resourceType.WOOD, 5);
        PITCH_RIG.cost.put(Resource.resourceType.WOOD, 20);
        QUARRY.cost.put(Resource.resourceType.WOOD, 20);
        WOODCUTTER.cost.put(Resource.resourceType.WOOD, 3);
        HOVEL.cost.put(Resource.resourceType.WOOD, 6);
        CHURCH.cost.put(Resource.resourceType.GOLD, 250);
        CATHEDRAL.cost.put(Resource.resourceType.GOLD, 1000);
        ARMOURER.cost.put(Resource.resourceType.GOLD, 100);
        ARMOURER.cost.put(Resource.resourceType.WOOD, 20);
        BLACKSMITH.cost.put(Resource.resourceType.GOLD, 100);
        BLACKSMITH.cost.put(Resource.resourceType.WOOD, 20);
        FLETCHER.cost.put(Resource.resourceType.GOLD, 100);
        FLETCHER.cost.put(Resource.resourceType.WOOD, 20);
        OIL_SMELTER.cost.put(Resource.resourceType.IRON, 10);
        OIL_SMELTER.cost.put(Resource.resourceType.GOLD, 100);
        PITCH_DITCH.cost.put(Resource.resourceType.PITCH, 2); //har 5 moraba
        CAGED_WAR_DOGS.cost.put(Resource.resourceType.WOOD, 10);
        CAGED_WAR_DOGS.cost.put(Resource.resourceType.GOLD, 100);
        STABLE.cost.put(Resource.resourceType.WOOD, 20);
        STABLE.cost.put(Resource.resourceType.GOLD, 400);
        APPLE_ORCHARD.cost.put(Resource.resourceType.WOOD, 5);
        DIARY_FARMER.cost.put(Resource.resourceType.WOOD, 10);
        HOPS_FARMER.cost.put(Resource.resourceType.WOOD, 15);
        HUNTER_POST.cost.put(Resource.resourceType.WOOD, 5);
        WHEAT_FARMER.cost.put(Resource.resourceType.WOOD, 15);
        BAKERY.cost.put(Resource.resourceType.WOOD, 10);
        BREWER.cost.put(Resource.resourceType.WOOD, 10);
        GRANARY.cost.put(Resource.resourceType.WOOD, 5);
    }

    private final String category;
    private final String name;
    private final int hp;
    private final HashMap<Resource.resourceType, Integer> cost;
    private final HashMap<Person, Integer> workers;

    private BuildingType(String category, String name, int hp, HashMap<Resource.resourceType, Integer> cost,
                         HashMap<Person, Integer> workers) {
        this.category = category;
        this.name = name;
        this.hp = hp;
        this.cost = cost;
        this.workers = workers;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public HashMap<Resource.resourceType, Integer> getCost() {
        return cost;
    }

    public HashMap<Person, Integer> getWorkers() {
        return workers;
    }
}
