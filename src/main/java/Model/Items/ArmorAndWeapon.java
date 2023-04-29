package Model.Items;

import Model.Buildings.Building;
import Model.Database;
import Model.User;
import Model.Database;
import static Model.Database.getBuildingDataByName;

public class ArmorAndWeapon extends Item{

    public enum WeaponAndArmor {
        BOW("bow", getBuildingDataByName("fletcher"), new Resource(Resource.resourceType.WOOD, null), 2),
        CROSSBOW("crossbow", getBuildingDataByName("fletcher"), new Resource(Resource.resourceType.WOOD, null), 3),
        SPEAR("spear", getBuildingDataByName("poleturner"), new Resource(Resource.resourceType.WOOD, null), 1),
        PIKE("pike", getBuildingDataByName("poleturner"), new Resource(Resource.resourceType.WOOD, null), 2),
        MACE("mace", getBuildingDataByName("blacksmith"), new Resource(Resource.resourceType.IRON, null), 1),
        SWORDS("swords", getBuildingDataByName("blacksmith"), new Resource(Resource.resourceType.IRON, null), 1),
        LEATHER_ARMOR("leather armor", getBuildingDataByName("dairy farmer"), new Animal(Animal.animalNames.COW, null), 1),
        METAL_ARMOR("metal armor", getBuildingDataByName("armorer"), new Resource(Resource.resourceType.IRON, null), 1);
        private String name;
        private Building producedIn;
        private Item itemType;
        private double cost;
        private WeaponAndArmor(String name, Building producedIn, Item itemType, double cost) {
            this.name = name;
            this.producedIn = producedIn;
            this.itemType = itemType;
            this.cost = cost;
        }
    }
    private String name;
    private Building producedIn;
    private Item itemType;
    private double cost;
    public ArmorAndWeapon(WeaponAndArmor weaponAndArmor, User owner) {
        super(weaponAndArmor.name, weaponAndArmor.cost, owner);
        this.name = weaponAndArmor.name;
        this.cost = weaponAndArmor.cost;
        this.itemType = weaponAndArmor.itemType;
        this.producedIn = weaponAndArmor.producedIn;
    }

    public String getName() {
        return name;
    }

    public Building getProducedIn() {
        return producedIn;
    }

    public Item getItemType() {
        return itemType;
    }

    @Override
    public double getCost() {
        return cost;
    }
}
