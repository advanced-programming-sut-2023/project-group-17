package Model.Items;

import Model.Buildings.Building;
import Model.User;

import static Model.Database.getBuildingDataByName;

public class ArmorAndWeapon extends Item{

    public enum WeaponAndArmor {
        BOW("bow", getBuildingDataByName("fletcher"), new Resource(Resource.resourceType.WOOD, null, 1), 31),
        CROSSBOW("crossbow", getBuildingDataByName("fletcher"), new Resource(Resource.resourceType.WOOD, null, 1), 58),
        SPEAR("spear", getBuildingDataByName("poleturner"), new Resource(Resource.resourceType.WOOD, null, 1), 20),
        PIKE("pike", getBuildingDataByName("poleturner"), new Resource(Resource.resourceType.WOOD, null, 1), 36),
        MACE("mace", getBuildingDataByName("blacksmith"), new Resource(Resource.resourceType.IRON, null, 1), 58),
        SWORDS("swords", getBuildingDataByName("blacksmith"), new Resource(Resource.resourceType.IRON, null, 1), 58),
        LEATHER_ARMOR("leatherArmor", getBuildingDataByName("dairy farmer"), new Animal(Animal.animalNames.COW, null, 1), 25),
        METAL_ARMOR("metalArmor", getBuildingDataByName("armorer"), new Resource(Resource.resourceType.IRON, null, 1), 58),
        OIL("oil", getBuildingDataByName("armorer"), null, 1)
        ;
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
    private Building producedIn;
    private Item itemType;
    public ArmorAndWeapon(WeaponAndArmor weaponAndArmor, User owner, double number) {
        super(weaponAndArmor.name, weaponAndArmor.cost, owner, number);
        this.itemType = weaponAndArmor.itemType;
        this.producedIn = weaponAndArmor.producedIn;
    }
    public Building getProducedIn() {
        return producedIn;
    }

    public Item getItemType() {
        return itemType;
    }
}
