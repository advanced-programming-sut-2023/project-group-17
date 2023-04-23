package Model;

import Model.Items.Resource;

public class MaterialMap {
    public enum textureMap {
        LAND("land", false, Color.ANSI_YELLOW_BACKGROUND),
        GRAVEL_LAND("grave land", false, Color.ANSI_PURPLE_BACKGROUND),
        ROCK("rock", false, Color.ANSI_RED_BACKGROUND),
        STONE("stone", false, Color.ANSI_BLACK_BACKGROUND),
        IRON("iron", false, Color.ANSI_WHITE_BACKGROUND),
        GRASS("grass", false, Color.ANSI_GREEN_BACKGROUND),
        MEADOW("meadow", false, Color.ANSI_CYAN_BACKGROUND),
        DENSE_MEADOW("dense meadow", false, Color.ANSI_CYAN_BACKGROUND),
        OIL("oil", true, Color.ANSI_BLUE_BACKGROUND),
        PLAIN("plain", true, Color.ANSI_BLUE_BACKGROUND),
        SHALLOW_LAKE("shallow lake", true, Color.ANSI_BLUE_BACKGROUND),
        RIVER("river", true, Color.ANSI_BLUE_BACKGROUND),
        SHALLOW_POND("shallow pond", true, Color.ANSI_BLUE_BACKGROUND),
        DEEP_POND("deep pond", true, Color.ANSI_BLUE_BACKGROUND),
        BEACH("beach", true, Color.ANSI_BLUE_BACKGROUND),
        SEA("sea", true, Color.ANSI_BLUE_BACKGROUND);
        final private String material;
        final private boolean waterZone;
        final private String color;

        private textureMap(String material, boolean waterZone, String color) {
            this.material = material;
            this.waterZone = waterZone;
            this.color = color;
        }

        public String getMaterial() {
            return material;
        }

        public boolean isWaterZone() {
            return waterZone;
        }

        public String getColor() {
            return color;
        }
    }

    public static MaterialMap.textureMap getTextureMap(String material) {
        for (textureMap materialsMap : MaterialMap.textureMap.values()) {
            if(materialsMap.material.equals(material)) return materialsMap;
        }
        return null;
    }
}
