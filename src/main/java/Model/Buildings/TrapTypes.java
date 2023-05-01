package Model.Buildings;

public class TrapTypes {
    enum Type {
        DITCH(true, false),
        KILLING_PIT(false, true)
        ;
        private boolean flammable;
        private boolean visible;
        Type(boolean flammable, boolean visible) {
            this.flammable = flammable;
            this.visible = visible;
        }

        public boolean isFlammable() {
            return flammable;
        }

        public boolean isVisible() {
            return visible;
        }
    }
}
