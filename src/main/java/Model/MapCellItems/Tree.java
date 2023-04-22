package Model.MapCellItems;

import Model.Items.Resource;
import Model.User;

public class Tree extends MapCellItems{
    private Tree.treeType typeOfTree;
    public enum treeType {
        DESSERT_SHRUB("desert shrub"),
        CHERRY_PALM("cherry palm"),
        OLIVE_TREE("olive tree"),
        COCONUT_PALM("coconut palm"),
        DATE_TREE("date tree");
        private final String type;
        private treeType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
    public Tree(User owner, treeType type) {
        super(owner);
        this.typeOfTree = type;
    }

    public treeType getTypeOfTree() {
        return typeOfTree;
    }

    public static Tree.treeType getTreeType(String treeName) {
        for (treeType treeTypes : treeType.values()) {
            if(treeTypes.type.equals(treeName)) return treeTypes;
        }
        return null;
    }
}
