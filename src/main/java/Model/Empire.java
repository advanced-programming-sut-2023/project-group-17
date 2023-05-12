package Model;

import Model.Buildings.Building;
import Model.Buildings.GateHouse;
import Model.Buildings.StorageBuilding;
import Model.Items.Animal;
import Model.Items.ArmorAndWeapon;
import Model.Items.Food;
import Model.Items.Resource;
import Model.People.*;

import java.util.ArrayList;

public class Empire {
    private final User owner;
    private ArrayList<Food> foods;
    private ArrayList<Resource> resources;
    private ArrayList<Person> people;
    private King king;
    private ArrayList<ArmorAndWeapon> weapons;
    private ArrayList<Animal> animals;
    private ArrayList<TradeRequest> receivedTradeRequests;
    private ArrayList<TradeRequest> sentTradeRequests;
    private ArrayList<Building> buildings;
    private ArrayList<AttackToolsAndMethods> attackToolsAndMethods;
    private int numberOfKingsKilled;
    private int fearRate;
    private int taxRate;
    private int foodRate;
    private double religionRate;
    private int popularityRate;
    private double coins;
    private empireColors empireColor;
    private double efficiency;
    private MapCell headquarter;
    private double score;
    public Empire(User owner, empireColors empireColor) {
        this.owner = owner;
        this.foods = new ArrayList<Food>();
        this.resources = new ArrayList<Resource>();
        this.people = new ArrayList<Person>();
        this.weapons = new ArrayList<ArmorAndWeapon>();
        this.animals = new ArrayList<Animal>();
        this.attackToolsAndMethods = new ArrayList<>();
        this.receivedTradeRequests = new ArrayList<>();
        this.sentTradeRequests = new ArrayList<>();
        this.buildings = new ArrayList<Building>();
        this.king = new King(owner);
        this.fearRate = 0;
        this.taxRate = 0;
        this.foodRate = 0;
        this.religionRate = 0;
        this.popularityRate = 0;
        this.coins = 0;
        this.efficiency = 1;
        this.score = 0;
        addFoods();
        addResources();
        addWeapons();
        addAnimals();
        this.empireColor = empireColor;
        this.numberOfKingsKilled = 0;
    }

    public empireColors getEmpireColor() {
        return empireColor;
    }

    public double getEfficiency() {
        return efficiency;
    }

    private void addAnimals() {
        animals.add(new Animal(Animal.animalNames.COW, owner, 0));
        animals.add(new Animal(Animal.animalNames.DOG, owner, 0));
        animals.add(new Animal(Animal.animalNames.HORSE, owner, 0));

    }

    private void addWeapons() {
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.METAL_ARMOR, owner, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.LEATHER_ARMOR, owner, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.BOW, owner, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.MACE, owner, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.PIKE, owner, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.CROSSBOW, owner, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.SPEAR, owner, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.SWORDS, owner, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.OIL, owner, 0));
    }

    private void addResources() {
        resources.add(new Resource(Resource.resourceType.WOOD, owner, 100));
        resources.add(new Resource(Resource.resourceType.IRON, owner, 0));
        resources.add(new Resource(Resource.resourceType.STONE, owner, 48));
        resources.add(new Resource(Resource.resourceType.ALE, owner, 0));
        resources.add(new Resource(Resource.resourceType.FLOUR, owner, 0));
        resources.add(new Resource(Resource.resourceType.HOPS, owner, 0));
        resources.add(new Resource(Resource.resourceType.PITCH, owner, 0));
        resources.add(new Resource(Resource.resourceType.WHEAT, owner, 0));
        resources.add(new Resource(Resource.resourceType.GOLD, owner, 2000));
    }

    private void addFoods() {
        foods.add(new Food(Food.foodType.CHEESE, owner, 20));
        foods.add(new Food(Food.foodType.MEAT, owner, 10));
        foods.add(new Food(Food.foodType.APPLE, owner, 30));
        foods.add(new Food(Food.foodType.BREAD, owner, 40));
    }

    public MapCell getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(MapCell headquarter, User user) {
        GateHouse gateHouse = new GateHouse(user, headquarter.getX(), headquarter.getY(),
            Database.getGatehouseBuildingDataByName("small stone gatehouse"));
        addBuilding(gateHouse);
        headquarter.addBuilding(gateHouse);
        gateHouse.addPerson(king);
        addPopulation(king);
        this.headquarter = headquarter;
    }
    public void makeHeadquarter(int x, int y, User user) {
        setHeadquarter(Database.getCurrentMapGame().getMapCellByCoordinates(x, y), user);
        makeStockpileAndGranary(x, y, user, "stockpile");
        makeStockpileAndGranary(x, y, user, "granary");
    }

    public void removeBuilding(Building currentBuilding) {
        for (Building building : buildings) {
            if(building.equals(currentBuilding)) {
                buildings.remove(building);
                break;
            }
        }
    }

    public void removeAttackToolsAndMethods(AttackToolsAndMethods currentAttackToolsAndMethods) {
        for (AttackToolsAndMethods attackToolsAndMethod : attackToolsAndMethods) {
            if(attackToolsAndMethod.equals(currentAttackToolsAndMethods)) {
                attackToolsAndMethods.remove(attackToolsAndMethod);
                break;
            }
        }
    }

    private void makeStockpileAndGranary(int x, int y, User user, String name) {

        for (int z = -1; z < 2; z++) {
            for (int j = -1; j < 2; j++) {
                if (Utils.CheckMapCell.validationOfY(y + j) && Utils.CheckMapCell.validationOfX(x + z) &&
                        !Database.getCurrentMapGame().getMapCellByCoordinates(x + z, y + j).haveBuilding()
                        && !(z == 0 && j == 1)) {

                    MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x + z, y + j);
                    if (mapCell.canDropItems()) {
                        StorageBuilding stockpile = new StorageBuilding(user, x + z, y + j,
                                Database.getStorageBuildingDataByName(name));
                        mapCell.addBuilding(stockpile);
                        user.getEmpire().addBuilding(stockpile);
                        return;
                    }
                }
            }
        }

        for (int z = -1; z < 2; z++) {
            for (int j = -1; j < 2; j++) {
                if (Utils.CheckMapCell.validationOfY(y + j) && Utils.CheckMapCell.validationOfX(x + z) &&
                        !Database.getCurrentMapGame().getMapCellByCoordinates(x + z, y + j).haveBuilding()
                        && !(z == 0 && j == 1)) {
                    MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x + z, y + j);
                    mapCell.clear();
                    StorageBuilding stockpile = new StorageBuilding(user, x + z, y + j,
                            Database.getStorageBuildingDataByName(name));
                    mapCell.addBuilding(stockpile);
                    user.getEmpire().addBuilding(stockpile);
                    return;
                }
            }
        }
    }

    public User getOwner() {
        return owner;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public ArrayList<ArmorAndWeapon> getWeapons() {
        return weapons;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<TradeRequest> getReceivedTradeRequests() {
        return receivedTradeRequests;
    }
    public void addReceivedTradeRequests(TradeRequest tradeRequest) {
        this.receivedTradeRequests.add(tradeRequest);
    }
    public ArrayList<TradeRequest> getSentTradeRequests() {
        return sentTradeRequests;
    }
    public void addSentTradeRequests(TradeRequest tradeRequest) {
        this.sentTradeRequests.add(tradeRequest);
    }

    public TradeRequest getReceivedRequestById(int id) {
        for (TradeRequest request : receivedTradeRequests) {
            if(request.getId() == id) return request;
        }
        return null;
    }

    public int getFearRate() {
        return fearRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public double getReligionRate() {
        return religionRate;
    }

    public int getPopularityRate() {
        return popularityRate;
    }

    public double getCoins() {
        return coins;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public void setReligionRate(double religionRate) {
        this.religionRate = religionRate;
    }

    public void setPopularityRate(int popularityRate) {
        this.popularityRate = popularityRate;
    }

    public void changePopularityRate(int popularityRate) {
        this.popularityRate += popularityRate;
    }

    public void changeReligionRate (int amount) {
        this.religionRate += amount;
    }

    public void addFood(Food food) {
        this.foods.add(food);
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }


    public void addPopulation(Person person) {
        this.people.add(person);
    }

    public void addWeapon(ArmorAndWeapon weapon) {
        this.weapons.add(weapon);
    }

    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }
    public void changeCoins(double coins) {
        this.coins += coins;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    public Building getBuildingByName(String name) {
        for (Building building : buildings) {
            if (building.getBuildingName().equals(name)) return building;
        }
        return null;
    }

    public ArrayList<Soldier> getSoldiers() {
        ArrayList<Soldier> soldiers = new ArrayList<>();
        for (Person person : people) {
            if (person instanceof Soldier) soldiers.add((Soldier) person);
        }
        return soldiers;
    }

    public ArrayList<NormalPeople> getNormalPeople() {
        ArrayList<NormalPeople> normalPeople = new ArrayList<>();
        for (Person person : people) {
            if (person instanceof NormalPeople) normalPeople.add((NormalPeople) person);
        }
        return normalPeople;
    }

    public int getFoodDiversity() {
        int diversity = 0;
        for (Food food : this.foods) {
            if(food.getNumber() != 0) diversity++;
        }
        return diversity;
    }

    public double getFoodNumbers() {
        int number = 0;
        for (Food food : this.foods) {
            number += food.getNumber();
        }
        return number;
    }

    public int getPopulation() {
        return people.size();
    }

    public void changeFoodNumber(double amount) {
        while (amount != 0) {
            for (Food food : foods) {
                if (amount == 0) break;
                if (food.getNumber() > 0) food.changeNumber(-0.5);
                amount -= 0.5;
            }
        }
    }

    public Food getFoodByName(String name) {
        for (Food food : foods)
            if (food.getItemName().equals(name)) return food;
        return null;
    }
    public Resource getResourceByName(String name) {
        for (Resource resource : resources) {
            if(resource.getItemName().equals(name)) return resource;
        }
        return null;
    }

    public ArmorAndWeapon getWeaponByName(String name) {
        for (ArmorAndWeapon weapon : weapons)
            if (weapon.getItemName().equals(name)) return weapon;
        return null;
    }

    public Animal getAnimalByName(String name) {
        for (Animal animal : animals)
            if (animal.getItemName().equals(name)) return animal;
        return null;
    }

    @Override
    public String toString() {
        return "1.Food : " + getFoodRate() + "\n" +
                "2.Tax : " + getTaxRate() + "\n" +
                "3.Religion : " + getReligionRate() + "\n" +
                "4.Fear : " + getFearRate() ;
    }

    public void changeEfficiency(double amount) {
        this.efficiency += amount;
    }

    public ArrayList<Engineer> getEngineers() {
        ArrayList<Engineer> engineers = new ArrayList<>();
        for (Person person : people) {
            if(person instanceof Engineer) engineers.add((Engineer) person);
        }
        return engineers;
    }

    public void addAttackToolsAndMethods(AttackToolsAndMethods attackToolsAndMethods) {
        this.attackToolsAndMethods.add(attackToolsAndMethods);
    }

    public ArrayList<AttackToolsAndMethods> getAttackToolsAndMethods() {
        return attackToolsAndMethods;
    }

    public void removePerson(Person person) {
        people.remove(person);
    }

    public double getScore() {
        return this.score;
    }

    public void changeScore(double amount) {
        this.score += amount;
    }

    public King getKing() {
        return king;
    }

    public int getNumberOfKingsKilled() {
        return numberOfKingsKilled;
    }

    public void increaseNumberOfKingsKilled() {
        this.numberOfKingsKilled++;
    }

    public void removeAnimal(Animal animal) {
        for (Animal animal1 : animals) {
            if (animal1.equals(animal)) animal1.changeNumber(-animal1.getNumber());
        }
    }
}
