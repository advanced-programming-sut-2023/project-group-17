package Model;

import Model.Items.Animal;
import Model.Items.ArmorAndWeapon;
import Model.Items.Food;
import Model.Items.Resource;
import Model.People.Person;
import Model.UnitAttributes.Weapon;

import java.util.ArrayList;

public class Empire {
    private ArrayList<Food> foods;
    private ArrayList<Resource> resources;
    private ArrayList<Person> people;
    private ArrayList<ArmorAndWeapon> weapons;
    private ArrayList<Animal> animals;
    private int fearRate;
    private int taxRate;
    private int foodRate;
    private double religionRate;
    private int popularityRate;
    private double coins;
    public Empire() {
        this.foods = new ArrayList<Food>();
        this.resources = new ArrayList<Resource>();
        this.people = new ArrayList<Person>();
        this.weapons = new ArrayList<ArmorAndWeapon>();
        this.animals = new ArrayList<Animal>();
        this.fearRate = 0;
        this.taxRate = 0;
        this.foodRate = 0;
        this.religionRate = 0;
        this.popularityRate = 0;
        this.coins = 0;
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
}
