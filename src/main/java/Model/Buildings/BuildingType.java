package Model.Buildings;

import Model.Items.Food;
import Model.Items.Item;
import Model.Items.Resource;
import Model.People.PeopleType;
import Model.People.Person;
import Model.People.Worker;

import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

public enum BuildingType {
    SMALL_STONE_GATEHOUSE("gate house", "small stone gatehouse", 1000, new HashMap<>(), new HashMap<>()),
    LARGE_STONE_GATEHOUSE("gate house", "large stone gatehouse", 2000,
            new HashMap<>(){{put(Resource.resourceType.STONE, 20);}}, new HashMap<>()),
    DRAWBRIDGE("other buildings", "drawbridge", 0,
            new HashMap<>(){{put( Resource.resourceType.WOOD, 10);}}, new HashMap<>()),
    LOOKOUT_TOWER("defensive", "lookout tower", 250,
            new HashMap<>(){{put(Resource.resourceType.STONE, 10);}}, new HashMap<>()),
    PERIMETER_TOWER("defensive", "perimeter tower", 1000,
            new HashMap<>(){{put(Resource.resourceType.STONE, 10);}}, new HashMap<>()),
    DEFENCE_TURRET("defensive", "defence turret", 1200,
            new HashMap<>(){{put(Resource.resourceType.STONE, 15);}}, new HashMap<>()),
    SQUARE_TOWER("defensive", "square tower", 1600,
            new HashMap<>(){{put(Resource.resourceType.STONE, 35);}}, new HashMap<>()),
    ROUND_TOWER("defensive", "round tower", 2000,
            new HashMap<>(){{put(Resource.resourceType.STONE, 40);}}, new HashMap<>()),
    ARMOURY("storage", "armory", 500,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}}, new HashMap<>()),
    BARRACKS("soldier production", "barracks", 500,
            new HashMap<>(){{put(Resource.resourceType.STONE, 15);}}, new HashMap<>()),
    MERCENARY_POST("soldier production", "mercenary post", 500,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);}}, new HashMap<>()),
    ENGINEER_GUILD("soldier production", "engineer guild", 500,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);put(Resource.resourceType.GOLD, 100);}},
            new HashMap<>()),
    KILLING_PIT("trap", "killing pit",0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 6);}}, new HashMap<>() ),
    OIL_SMELTER("mining", "oil smelter", 300,
            new HashMap<>(){{put(Resource.resourceType.IRON, 10);put(Resource.resourceType.GOLD, 100);}},
            new HashMap<>(){{put(PeopleType.ENGINEER, 3);}}),
    CAGED_WAR_DOGS("storage", "caged war dogs", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);put(Resource.resourceType.GOLD, 100);}},
            new HashMap<>()),
//    PITCH_DITCH("trap", "pitch ditch", 0,
//            new HashMap<>(){{put(Resource.resourceType.PITCH, 2);/*har 5 moraba*/}}, new HashMap<>()),
    SIEGE_TENT("siege tent", "siege tent", 500, new HashMap<>(),
            new HashMap<>(){{put(PeopleType.ENGINEER, 1);/*hadaghal*/}}),
    STABLE("storage", "stable", 300,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 400);put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>()),
    APPLE_ORCHARD("mining", "apple orchard", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    DIARY_FARMER("mining", "diary farmer", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    HOPS_FARMER("mining", "hops farmer", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 15);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    HUNTER_POST("mining", "hunter post", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    WHEAT_FARMER("mining", "wheat farmer", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 15);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    BAKERY("production", "bakery", 300,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    BREWER("production", "brewer", 300,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    GRANARY("storage", "granary", 300,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}}, new HashMap<>()),
    INN("inn", "inn", 300,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 20);put(Resource.resourceType.GOLD, 100);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    MILL("production", "mill", 300,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 3);}}),
    IRON_MINE("mining", "iron mine", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 2);}}),
    MARKET("other buildings", "market", 300,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    OX_TETHER("other buildings", "ox tether", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    PITCH_RIG("mining", "pitch rig", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    QUARRY("mining", "quarry", 300,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 3);}}),
    STOCKPILE("storage", "stockpile", 500,
            new HashMap<>(), new HashMap<>()),
    WOODCUTTER("mining", "woodcutter", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 3);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    HOVEL("others", "hovel", 100,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 6);}}, new HashMap<>()),
    CHURCH("other buildings", "church", 800,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 250);}}, new HashMap<>()),
    CATHEDRAL("other buildings", "cathedral", 1200,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 1000);}}, new HashMap<>()),
    ARMOURER("production", "armourer", 300,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 100);put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    BLACKSMITH("production", "blacksmith", 300,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 100);put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    FLETCHER("production", "fletcher", 300,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 100);put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    POLETURNER("production", "poleturner", 300,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 100);put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}});

    private final String category;
    private final String name;
    private final int hp;
    private final HashMap<Resource.resourceType, Integer> cost;
    private final HashMap<PeopleType, Integer> workers;

    private BuildingType(String category, String name, int hp, HashMap<Resource.resourceType, Integer> cost,
                         HashMap<PeopleType, Integer> workers) {
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

    public HashMap<PeopleType, Integer> getWorkers() {
        return workers;
    }
}
