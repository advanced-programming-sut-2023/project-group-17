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
    SMALL_STONE_GATEHOUSE("", "small stone gatehouse", 0, new HashMap<>(), new HashMap<>()),
    LARGE_STONE_GATEHOUSE("", "large stone gatehouse", 0,
            new HashMap<>(){{put(Resource.resourceType.STONE, 20);}}, new HashMap<>()),
    DRAWBRIDGE("", "drawbridge", 0,
            new HashMap<>(){{put( Resource.resourceType.WOOD, 10);}}, new HashMap<>()),
    LOOKOUT_TOWER("", "lookout tower", 0,
            new HashMap<>(){{put(Resource.resourceType.STONE, 10);}}, new HashMap<>()),
    PERIMETER_TOWER("", "perimeter tower", 0,
            new HashMap<>(){{put(Resource.resourceType.STONE, 10);}}, new HashMap<>()),
    DEFENCE_TURRET("", "defence turret", 0,
            new HashMap<>(){{put(Resource.resourceType.STONE, 15);}}, new HashMap<>()),
    SQUARE_TOWER("", "square tower", 0,
            new HashMap<>(){{put(Resource.resourceType.STONE, 35);}}, new HashMap<>()),
    ROUND_TOWER("", "round tower", 0,
            new HashMap<>(){{put(Resource.resourceType.STONE, 40);}}, new HashMap<>()),
    ARMOURY("", "armory", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}}, new HashMap<>()),
    BARRACKS("", "barracks", 0,
            new HashMap<>(){{put(Resource.resourceType.STONE, 15);}}, new HashMap<>()),
    MERCENARY_POST("", "mercenary post", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);}}, new HashMap<>()),
    ENGINEER_GUILD("", "engineer guild", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);put(Resource.resourceType.GOLD, 100);}},
            new HashMap<>()),
    KILLING_PIT("", "killing pit",0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 6);}}, new HashMap<>() ),
    OIL_SMELTER("", "oil smelter", 0,
            new HashMap<>(){{put(Resource.resourceType.IRON, 10);put(Resource.resourceType.GOLD, 100);}},
            new HashMap<>(){{put(PeopleType.ENGINEER, 3);}}),
    CAGED_WAR_DOGS("", "caged war dogs", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);put(Resource.resourceType.GOLD, 100);}},
            new HashMap<>()),
    PITCH_DITCH("", "pitch ditch", 0,
            new HashMap<>(){{put(Resource.resourceType.PITCH, 2);/*har 5 moraba*/}}, new HashMap<>()),
    SIEGE_TENT("", "siege tent", 0, new HashMap<>(),
            new HashMap<>(){{put(PeopleType.ENGINEER, 1);/*hadaghal*/}}),
    STABLE("", "stable", 0,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 400);put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>()),
    APPLE_ORCHARD("", "apple orchard", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    DIARY_FARMER("", "diary farmer", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    HOPS_FARMER("", "hops farmer", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 15);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    HUNTER_POST("", "hunter post", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    WHEAT_FARMER("", "wheat farmer", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 15);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    BAKERY("", "bakery", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    BREWER("", "brewer", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 10);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    GRANARY("", "granary", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}}, new HashMap<>()),
    INN("", "inn", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 20);put(Resource.resourceType.GOLD, 100);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    MILL("", "mill", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 3);}}),
    IRON_MINE("", "iron mine", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 2);}}),
    MARKET("", "market", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    OX_TETHER("", "ox tether", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 5);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    PITCH_RIG("", "pitch rig", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    QUARRY("", "quarry", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 3);}}),
    STOCKPILE("", "stockpile", 0,
            new HashMap<>(), new HashMap<>()),
    WOODCUTTER("", "woodcutter", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 3);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    HOVEL("", "hovel", 0,
            new HashMap<>(){{put(Resource.resourceType.WOOD, 6);}}, new HashMap<>()),
    CHURCH("", "church", 0,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 250);}}, new HashMap<>()),
    CATHEDRAL("", "cathedral", 0,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 1000);}}, new HashMap<>()),
    ARMOURER("", "armourer", 0,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 100);put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    BLACKSMITH("", "blacksmith", 0,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 100);put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    FLETCHER("", "fletcher", 0,
            new HashMap<>(){{put(Resource.resourceType.GOLD, 100);put(Resource.resourceType.WOOD, 20);}},
            new HashMap<>(){{put(PeopleType.WORKER, 1);}}),
    POLETURNER("", "poleturner", 0,
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
