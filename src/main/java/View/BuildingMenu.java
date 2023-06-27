//package View;
//
//import Server.controller.BuildingMenuController;
//import View.Enums.Commands.BuildingMenuCommands;
//
//import java.util.regex.Matcher;
//
//public class BuildingMenu extends Menu{
//
//    BuildingMenuController controller;
//    public BuildingMenu() {
//        controller = new BuildingMenuController();
//    }
//    void run() {
//        System.out.println("entered building menu successfully");
//        Matcher matcher;
//        String command;
////        while (true) {
////            command = scanner.nextLine();
////
////            if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.DROP_BUILDING)) != null)
////                dropBuilding(matcher);
////
////            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.SELECT_BUILDING)) != null)
////                selectBuilding(matcher);
////
////            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.CREATE_UNIT)) != null);
//////                createUnit(matcher);
////
////            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.REPAIR)) != null)
////                repair();
////
////            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.CREATE_ATTACK_TOOL)) != null)
////                createAttackTool(matcher);
////
////            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.BACK)) != null) {
////                System.out.println("entered game menu successfully");
////                return;
////            }
////
////            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.DROP_WALL)) != null)
////                dropWall(matcher);
////
////            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.DROP_STAIR)) != null)
////                dropStair(matcher);
////
////            else if((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.SHOW_CURRENT_MENU)) != null)
////                System.out.println("Building menu");
////
////            else System.out.println("Invalid Command");
////        }
//    }
//
////    private void dropStair(Matcher matcher) {
////        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y"))) {
////            System.out.println("create attack tool : blank field");
////            return;
////        }
////        int x = Integer.parseInt(matcher.group("x"));
////        int y = Integer.parseInt(matcher.group("y"));
////        switch (controller.dropStair(x, y)) {
////            case SUCCESS:
////                System.out.println("The stair dropped successfully");
////                break;
////            case X_OUT_OF_BOUNDS:
////                System.out.println("Drop stair failed : Coordinate of x is out of bounds");
////                break;
////            case Y_OUT_OF_BOUNDS:
////                System.out.println("Drop stair failed : Coordinate of y is out of bounds");
////                break;
////            case CELL_IS_FULL:
////                System.out.println("Drop stair failed : The cell is full");
////                break;
////            case THERE_IS_NOT_ANY_WALLS_NEAR:
////                System.out.println("Drop stair failed : There isn't any of your walls near");
////                break;
////        }
////    }
////
////    private void dropWall(Matcher matcher) {
////        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y")) ||
////            Menu.checkBlankField(matcher.group("thickness")) || Menu.checkBlankField(matcher.group("height"))) {
////            System.out.println("create attack tool : blank field");
////            return;
////        }
////        int x = Integer.parseInt(matcher.group("x"));
////        int y = Integer.parseInt(matcher.group("y"));
////        String thickness = Menu.handleDoubleQuote(matcher.group("thickness"));
////        String height = Menu.handleDoubleQuote(matcher.group("height"));
////        switch (controller.dropWall(thickness, height, x, y)) {
////            case SUCCESS:
////                System.out.println("The wall dropped successfully");
////                break;
////            case X_OUT_OF_BOUNDS:
////                System.out.println("Drop wall failed : Coordinate of x is out of bounds");
////                break;
////            case Y_OUT_OF_BOUNDS:
////                System.out.println("Drop wall failed : Coordinate of y is out of bounds");
////                break;
////            case CELL_IS_FULL:
////                System.out.println("Drop wall failed : The cell is full");
////                break;
////            case INVALID_TYPE:
////                System.out.println("Drop wall failed : Invalid type of wall");
////                break;
////        }
////    }
////
////    private void createAttackTool(Matcher matcher) {
////        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y")) ||
////                Menu.checkBlankField(matcher.group("type"))) {
////            System.out.println("create attack tool : blank field");
////            return;
////        }
////        int x = Integer.parseInt(matcher.group("x"));
////        int y = Integer.parseInt(matcher.group("y"));
////        String type = Menu.handleDoubleQuote(matcher.group("type"));
////        switch (controller.createAttackTool(x, y, type)) {
////            case SUCCESS:
////                System.out.println("The tool built successfully");
////                break;
////            case X_OUT_OF_BOUNDS:
////                System.out.println("build tool failed : Coordinate of x is out of bounds");
////                break;
////            case Y_OUT_OF_BOUNDS:
////                System.out.println("build tool failed : Coordinate of y is out of bounds");
////                break;
////            case INVALID_TYPE:
////                System.out.println("build tool failed : Type of tool is invalid");
////                break;
////            case INVALID_TYPE_BUILDING:
////                System.out.println("build tool failed : Invalid building type for creating this tool");
////                break;
////            case BUILDING_IS_NOT_SELECTED:
////                System.out.println("build tool failed : Siege tent is not selected");
////                break;
////            case CELL_IS_FULL:
////                System.out.println("build tool failed : cell is full");
////                break;
////            case INSUFFICIENT_ENGINEER:
////                System.out.println("build tool failed : not enough engineers available");
////                break;
////            case INSUFFICIENT_GOLD:
////                System.out.println("build tool failed : not enough gold");
////                break;
////        }
////    }
////
////    private void dropBuilding(Matcher matcher) {
////        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y")) ||
////        Menu.checkBlankField(matcher.group("type"))) {
////            System.out.println("Drop building failed : blank field");
////            return;
////        }
////        int x = Integer.parseInt(matcher.group("x"));
////        int y = Integer.parseInt(matcher.group("y"));
////        String type = Menu.handleDoubleQuote(matcher.group("type"));
//////        switch (controller.dropBuilding(x, y, type)) {
//////            case SUCCESS:
//////                System.out.println("The building successfully dropped");
//////                break;
//////            case X_OUT_OF_BOUNDS:
//////                System.out.println("Drop building failed : Coordinate of x is out of bounds");
//////                break;
//////            case Y_OUT_OF_BOUNDS:
//////                System.out.println("Drop building failed : Coordinate of y is out of bounds");
//////                break;
//////            case INVALID_TYPE:
//////                System.out.println("Drop building failed : Type of building is invalid");
//////                break;
//////            case INAPPROPRIATE_TEXTURE:
//////                System.out.println("Drop building failed : Texture of this cell is inappropriate for placing this building");
//////                break;
//////            case CELL_IS_FULL:
//////                System.out.println("Drop building failed : Cell is full");
//////                break;
//////            case NOT_ENOUGH_CROWD:
//////                System.out.println("Drop building failed : Not enough crowd to make workers");
//////                break;
//////            case NOT_ENOUGH_ENGINEERS:
//////                System.out.println("Drop building failed : Not enough engineers to build the building");
//////                break;
//////        }
////    }
////
////    private void selectBuilding(Matcher matcher) {
////        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y"))) {
////            System.out.println("Select building failed : blank field");
////            return;
////        }
////        int x = Integer.parseInt(matcher.group("x"));
////        int y = Integer.parseInt(matcher.group("y"));
////        switch (controller.selectBuilding(x, y)) {
////            case SUCCESS:
////                System.out.println("The building successfully selected");
////                break;
////            case CELL_IS_EMPTY:
////                System.out.println("Select building failed : There is not any building in this cell");
////                break;
////            case OPPONENT_BUILDING:
////                System.out.println("Select building failed : The building belongs to your opponents");
////                break;
////        }
////    }
////
//////    private void createUnit(Matcher matcher) {
//////        if (Menu.checkBlankField(matcher.group("type")) || Menu.checkBlankField(matcher.group("count")) ||
//////                Menu.checkBlankField(matcher.group("type"))) {
//////            System.out.println("Create unit failed : blank field");
//////            return;
//////        }
//////        String type = Menu.handleDoubleQuote(matcher.group("type"));
//////        int count = Integer.parseInt(matcher.group("count"));
//////        switch (controller.createUnit(type, count)) {
//////            case SUCCESS:
//////                System.out.println("Units successfully crated");
//////                break;
//////            case INVALID_NUMBER:
//////                System.out.println("Create unit failed : The number is invalid");
//////                break;
//////            case INSUFFICIENT_STORAGE:
//////                System.out.println("Create unit failed : Lack of resource for creating units");
//////                break;
//////            case NOT_ENOUGH_CROWD:
//////                System.out.println("Create unit failed : Lack of crowd for creating units");
//////                break;
//////            case INVALID_TYPE:
//////                System.out.println("Crate unit failed : Invalid soldier type");
//////                break;
//////            case INVALID_TYPE_BUILDING:
//////                System.out.println("Crate unit failed : Invalid building type for creating this unit");
//////                break;
//////            case BUILDING_IS_NOT_SELECTED:
//////                System.out.println("Create unit failed : Building is not selected");
//////                break;
//////        }
//////    }
////
////    private void repair() {
////        switch (controller.repair()) {
////            case SUCCESS:
////                System.out.println("Units successfully crated");
////                break;
////            case INSUFFICIENT_STONE:
////                System.out.println("Repair failed : Lack of stones for repairing this building");
////                break;
////            case OPPONENT_SOLDIER_AROUND:
////                System.out.println("Repair failed : Opponent soldier is near this building");
////                break;
////            case BUILDING_IS_NOT_SELECTED:
////                System.out.println("Repair failed : Building is not selected");
////                break;
////        }
////    }
////
////    private void back() {
////        controller.back();
////    }
//
//}
