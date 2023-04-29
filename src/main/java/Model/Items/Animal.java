package Model.Items;

import Model.User;

public class Animal extends Item{
    public enum animalNames {
        COW("cow", 10, 0),
        HORSE("horse", 10, 0),
        DOG("dog", 10, 20);
        private String name;
        private int health;
        private int damage;
        private animalNames(String name, int health, int damage) {
            this.name = name;
            this.health = health;
            this.damage = damage;
        }
    }

    private String name;
    private int health;
    private int damage;
    public Animal(animalNames animalName, User owner) {
        super(animalName.name, 0, owner);
        this.name = animalName.name;
        this.health = animalName.health;
        this.damage = animalName.damage;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }
}
