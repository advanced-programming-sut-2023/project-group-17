package Model.Buildings;

import Model.Items.Item;

public class MiningBuildingType {
    public enum MiningType {
        OIL_SMELTER("oil smelter", Item.ItemType.OIL),
        APPLE_ORCHARD("apple orchard", Item.ItemType.APPLE),
        HOPS_FARMER("hops farmer", Item.ItemType.HOPS),
        HUNTER_POST("hunter post", Item.ItemType.MEAT),
        WHEAT_FARMER("wheat farmer", Item.ItemType.WHEAT),
        PITCH_RIG("pitch rig", Item.ItemType.PITCH),
        QUARRY("quarry", Item.ItemType.STONE),
        WOODCUTTER("woodcutter", Item.ItemType.WOOD),
        IRON_MINE("iron mine", Item.ItemType.IRON)
        ;
        private String name;
        private Item.ItemType production;
        MiningType(String name, Item.ItemType production) {
            this.name = name;
            this.production = production;
        }

        public String getName() {
            return name;
        }

        public Item.ItemType getProduction() {
            return production;
        }
    }
    public static MiningType getMiningType(String name) {
        for (MiningType miningType : MiningType.values()) {
            if(miningType.name.equals(name)) return miningType;
        }
        return null;
    }
}
