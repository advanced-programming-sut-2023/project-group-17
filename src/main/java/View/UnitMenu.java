package View;

import Controller.UnitMenuController;
import View.Enums.Commands.UnitMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu extends Menu {
    private UnitMenuController controller;

    public UnitMenu() {
        controller = new UnitMenuController();
    }

    @Override
    public void run() {
        System.out.println("entered unit menu successfully");
        Matcher matcher;
        String command;

        while(true) {
            command = scanner.nextLine();
            if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.SELECT_UNIT)) != null)
                selectUnit(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.MOVE_UNIT)) != null)
                moveUnit(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.PATROL_UNIT)) != null)
                patrolUnit(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.SET_UNIT_MODE)) != null)
                setUnitMood(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.ATTACK_ENEMY)) != null)
                attackEnemy(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.AIR_ATTACK)) != null)
                airAttack(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.POUR_OIL)) != null)
                pourOil(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.DIG_TUNNEL)) != null)
                digTunnel(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.BUILD_SURROUNDING_EQUIPMENT)) != null)
                buildSurroundingEquipment(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.DISBAND_UNIT)) != null)
                disbandUnit(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.DIG_MOAT)) != null)
                digMoat(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.BURN_OIL)) != null)
                burnOil();
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.FILL_MOAT)) != null)
                fillMoat(matcher);
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.BACK)) != null) {
                System.out.println("entered game menu successfully");
                return;
            }
            else System.out.println("Invalid Command");
        }
    }

    private void selectUnit(Matcher matcher) {
//        if (checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))) {
//            System.out.println("select unit failed : blank field");
//            return;
//        }
//
//        Integer x = Integer.parseInt(matcher.group("x"));
//        Integer y = Integer.parseInt(matcher.group("y"));
//
//        switch (controller.selectUnit(x, y)) {
//            case SUCCESS:
//                System.out.println("unit selected successfully");
//                break;
//            case X_OUT_OF_BOUNDS:
//                System.out.println("select unit failed : x out of bounds");
//                break;
//            case Y_OUT_OF_BOUNDS:
//                System.out.println("select unit failed : y out of bounds");
//                break;
//        }

    }

    private void moveUnit(Matcher matcher) {

    }

    private void patrolUnit(Matcher matcher) {

    }

    private void setUnitMood(Matcher matcher) {

    }

    private void attackEnemy(Matcher matcher) {

    }

    private void airAttack(Matcher matcher) {

    }

    private void pourOil(Matcher matcher) {

    }

    private void digTunnel(Matcher matcher) {

    }

    private void buildSurroundingEquipment(Matcher matcher) {

    }

    private void disbandUnit(Matcher matcher) {

    }

    private void digMoat(Matcher matcher) {

    }

    private void fillMoat(Matcher matcher) {

    }

    private void burnOil() {

    }

}
