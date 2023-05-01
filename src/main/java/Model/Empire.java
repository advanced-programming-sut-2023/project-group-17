package Model;

import Model.Items.*;
import Model.People.NormalPeople;
import Model.People.Person;
import Model.People.Soldier;

import java.util.ArrayList;

public class Empire {
    private ArrayList<Food> foods;
    private ArrayList<Resource> resources;
    private ArrayList<Person> people;
    private ArrayList<ArmorAndWeapon> weapons;
    private ArrayList<Animal> animals;
    private ArrayList<TradeRequest> receivedTradeRequests;
    private ArrayList<TradeRequest> sentTradeRequests;
    private int fearRate;
    private int taxRate;
    private int foodRate;
    private double religionRate;
    private int popularityRate;
    private double coins;
    private double efficiency;
    public Empire() {
        this.foods = new ArrayList<Food>();
        this.resources = new ArrayList<Resource>();
        this.people = new ArrayList<Person>();
        this.weapons = new ArrayList<ArmorAndWeapon>();
        this.animals = new ArrayList<Animal>();
        this.receivedTradeRequests = new ArrayList<>();
        this.sentTradeRequests = new ArrayList<>();
        this.fearRate = 0;
        this.taxRate = 0;
        this.foodRate = 0;
        this.religionRate = 0;
        this.popularityRate = 0;
        this.coins = 0;
        this.efficiency = 1;
        addFoods();
        addResources();
        addWeapons();
        addAnimals();
    }

    private void addAnimals() {
        User loggedInUser = Database.getLoggedInUser();
        animals.add(new Animal(Animal.animalNames.COW, loggedInUser, 0));
        animals.add(new Animal(Animal.animalNames.DOG, loggedInUser, 0));
        animals.add(new Animal(Animal.animalNames.HORSE, loggedInUser, 0));

    }

    private void addWeapons() {
        User loggedInUser = Database.getLoggedInUser();
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.METAL_ARMOR, loggedInUser, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.LEATHER_ARMOR, loggedInUser, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.BOW, loggedInUser, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.MACE, loggedInUser, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.PIKE, loggedInUser, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.CROSSBOW, loggedInUser, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.SPEAR, loggedInUser, 0));
        weapons.add(new ArmorAndWeapon(ArmorAndWeapon.WeaponAndArmor.SWORDS, loggedInUser, 0));
    }

    private void addResources() {
        User loggedInUser = Database.getLoggedInUser();
        resources.add(new Resource(Resource.resourceType.WOOD, loggedInUser, 100));
        resources.add(new Resource(Resource.resourceType.IRON, loggedInUser, 0));
        resources.add(new Resource(Resource.resourceType.STONE, loggedInUser, 48));
        resources.add(new Resource(Resource.resourceType.ALE, loggedInUser, 0));
        resources.add(new Resource(Resource.resourceType.FLOUR, loggedInUser, 0));
        resources.add(new Resource(Resource.resourceType.HOPS, loggedInUser, 0));
        resources.add(new Resource(Resource.resourceType.PITCH, loggedInUser, 0));
        resources.add(new Resource(Resource.resourceType.WHEAT, loggedInUser, 0));
        resources.add(new Resource(Resource.resourceType.GOLD, loggedInUser, 2000));
    }

    private void addFoods() {
        //TODO: add foods when new game starts
        User loggedInUser = Database.getLoggedInUser();
        foods.add(new Food(Food.foodType.CHEESE, loggedInUser, 0));
        foods.add(new Food(Food.foodType.MEAT, loggedInUser, 0));
        foods.add(new Food(Food.foodType.APPLE, loggedInUser, 0));
        foods.add(new Food(Food.foodType.BREAD, loggedInUser, 0));
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
}
