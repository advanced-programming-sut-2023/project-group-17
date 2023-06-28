package Server.model;

public class MaterialMap {
    public enum textureMap {
        LAND("land", false, Color.ANSI_YELLOW_BACKGROUND, true),
        GRAVEL_LAND("grave land", false, Color.ANSI_PURPLE_BACKGROUND, true),
        ROCK("rock", false, Color.ANSI_RED_BACKGROUND, true),
        STONE("stone", false, Color.ANSI_BLACK_BACKGROUND, true),
        IRON("iron", false, Color.ANSI_WHITE_BACKGROUND, true),
        GRASS("grass", false, Color.ANSI_GREEN_BACKGROUND, true),
        MEADOW("meadow", false, Color.ANSI_CYAN_BACKGROUND, true),
        DENSE_MEADOW("dense meadow", false, Color.ANSI_CYAN_BACKGROUND, true),
        OIL("oil", true, Color.ANSI_BLUE_BACKGROUND, false),
        PLAIN("plain", true, Color.ANSI_BLUE_BACKGROUND, false),
        SHALLOW_LAKE("shallow lake", true, Color.ANSI_BLUE_BACKGROUND, false),
        RIVER("river", true, Color.ANSI_BLUE_BACKGROUND, false),
        SHALLOW_POND("shallow pond", true, Color.ANSI_BLUE_BACKGROUND, false),
        DEEP_POND("deep pond", true, Color.ANSI_BLUE_BACKGROUND, false),
        BEACH("beach", true, Color.ANSI_BLUE_BACKGROUND, false),
        SEA("sea", true, Color.ANSI_BLUE_BACKGROUND, false),
        MOAT("moat", true, Color.ANSI_BLACK_BACKGROUND, false);
        final private String material;
        final private boolean waterZone;
        final private String color;
        final private boolean traversable;

        private textureMap(String material, boolean waterZone, String color, boolean traversable) {
            this.material = material;
            this.waterZone = waterZone;
            this.color = color;
            this.traversable = traversable;
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

        public boolean isTraversable() {
            return traversable;
        }
    }

    public static MaterialMap.textureMap getTextureMap(String material) {
        for (textureMap materialsMap : MaterialMap.textureMap.values()) {
            if(materialsMap.material.equals(material)) return materialsMap;
        }
        return null;
    }
}
