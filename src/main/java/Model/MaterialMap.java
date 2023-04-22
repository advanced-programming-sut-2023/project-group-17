package Model;

import Model.Items.Resource;

public class MaterialMap {
    public enum textureMap {
        LAND("land", false),
        GRAVEL_LAND("grave land", false),
        ROCK("rock", false),
        STONE("stone", false),
        IRON("iron", false),
        GRASS("grass", false),
        MEADOW("meadow", false),
        DENSE_MEADOW("dense meadow", false),
        OIL("oil", true),
        PLAIN("plain", true),
        SHALLOW_LAKE("shallow lake", true),
        RIVER("river", true),
        SHALLOW_POND("shallow pond", true),
        DEEP_POND("deep pond", true),
        BEACH("beach", true),
        SEA("sea", true);
        final private String material;
        final private boolean waterZone;

        private textureMap(String material, boolean waterZone) {
            this.material = material;
            this.waterZone = waterZone;
        }

        public String getMaterial() {
            return material;
        }

        public boolean isWaterZone() {
            return waterZone;
        }
    }

    public static MaterialMap.textureMap getTextureMap(String material) {
        for (textureMap materialsMap : MaterialMap.textureMap.values()) {
            if(materialsMap.material.equals(material)) return materialsMap;
        }
        return null;
    }
}
