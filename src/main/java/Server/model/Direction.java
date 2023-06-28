package Server.model;

public class Direction {
    public enum directions {
        NORTH("north"),
        WEST("west"),
        SOUTH("south"),
        EAST("east");
        private final String directionName;
        directions (String direction) {
            this.directionName = direction;
        }

        public String getDirectionName() {
            return directionName;
        }
    }
    public static Direction.directions getDirection(String direction) {
        for (Direction.directions directions : Direction.directions.values()) {
            if(directions.directionName.equals(direction)) return directions;
        }
        return null;
    }
}
