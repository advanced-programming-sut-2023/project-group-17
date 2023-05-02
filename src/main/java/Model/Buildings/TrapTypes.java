package Model.Buildings;

public class TrapTypes {
    enum Type {
        DITCH(true, false, "ditch"),
        KILLING_PIT(false, true, "killing")
        ;
        private boolean flammable;
        private boolean visible;
        private String name;
        Type(boolean flammable, boolean visible, String name) {
            this.flammable = flammable;
            this.visible = visible;
            this.name = name;
        }

        public boolean isFlammable() {
            return flammable;
        }

        public boolean isVisible() {
            return visible;
        }
    }
}
