package View;

import Controller.UnitMenuController;
import View.Enums.Commands.UnitMenuCommands;

import java.util.regex.Matcher;

public class UnitMenu extends Menu {
    private final UnitMenuController controller;

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
            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.MOVE_UNIT_TO)) != null)
                moveUnitTo(matcher);
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
                disbandUnit();
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
        if (checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))) {
            System.out.println("select unit failed : blank field");
            return;
        }

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        switch (controller.selectUnit(x, y)) {
            case SUCCESS:
                System.out.println("unit selected successfully");
                break;
            case X_OUT_OF_BOUNDS:
            System.out.println("select unit failed : x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("select unit failed : y out of bounds");
                break;
            case DOES_NOT_INCLUDE_UNIT:
                System.out.println("select unit failed : there is no unit here");
                break;
        }

    }

    private void moveUnitTo(Matcher matcher) {
        if (checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))) {
            System.out.println("move unit failed : blank field");
            return;
        }

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        switch (controller.moveUnitTo(x, y)) {
            case SUCCESS:
                System.out.println("unit moved successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("move unit failed : x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("move unit failed : y out of bounds");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("move unit failed : you have not selected any unit");
                break;
            case DISTANCE_OUT_OF_BOUNDS:
                System.out.println("move unit failed : distance out of bounds");
                break;
            case NOT_TRAVERSABLE:
                System.out.println("move unit failed : you cannot move to that spot");
                break;
        }
    }

    private void patrolUnit(Matcher matcher) {
        if (checkBlankField(matcher.group("x1")) || checkBlankField(matcher.group("y1")) ||
            checkBlankField(matcher.group("x2")) || checkBlankField(matcher.group("y2"))) {
            System.out.println("patrol unit failed : blank field");
            return;
        }

        int x1 = Integer.parseInt(matcher.group("x1"));
        int y1 = Integer.parseInt(matcher.group("y1"));
        int x2 = Integer.parseInt(matcher.group("x2"));
        int y2 = Integer.parseInt(matcher.group("y2"));

        switch (controller.patrolUnit(x1, y1, x2, y2)) {
            case SUCCESS:
                System.out.println("unit patrolled successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("patrol unit failed : x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("patrol unit failed : y out of bounds");
                break;
            case NOT_TRAVERSABLE:
                System.out.println("patrol unit failed : start point or end point is not reachable");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("patrol unit failed : you have not selected any unit");
                break;
            case DISTANCE_OUT_OF_BOUNDS:
                System.out.println("patrol unit failed : distance out of bounds");
                break;
        }
    }

    private void setUnitMood(Matcher matcher) {
        if (checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))
            || checkBlankField(matcher.group("status"))) {
            System.out.println("set unit mood failed : blank field");
            return;
        }

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String status = matcher.group("status");

        switch (controller.setUnitMood(x, y, status)) {
            case SUCCESS:
                System.out.println("unit mood set successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("set unit mood failed : x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("set unit mood failed : y out of bounds");
                break;
            case DOES_NOT_INCLUDE_UNIT:
                System.out.println("set unit mood failed : there is no unit here");
                break;
            case INVALID_MOOD:
                System.out.println("set unit mood failed : invalid unit mood");
                break;

        }

    }

    private void attackEnemy(Matcher matcher) {
        if (checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))) {
            System.out.println("attack enemy failed : blank field");
            return;
        }

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        switch (controller.attackEnemy(x, y)) {
            case SUCCESS:
                System.out.println("attacked enemy successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("attack enemy failed : x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("attack enemy failed : y out of bounds");
                break;
            case DOES_NOT_INCLUDE_UNIT:
                System.out.println("attack enemy failed : there is no enemy in that position");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("attack enemy failed : you have not selected any unit");
                break;
        }
    }

    private void airAttack(Matcher matcher) {
        if (checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))) {
            System.out.println("air attack failed : blank field");
            return;
        }

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        switch (controller.airAttack(x, y)) {
            case SUCCESS:
                System.out.println("air attacked successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("air attack failed : x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("air attack failed : y out of bounds");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("air attack failed : you have not selected any unit");
                break;
            case DOES_NOT_INCLUDE_UNIT:
                System.out.println("air attack failed : there is no enemy in that position");
                break;
            case INVALID_TYPE_OF_SELECTED_UNIT:
                System.out.println("air attack failed : you have to select air attackers unit");
                break;
        }
    }

    private void pourOil(Matcher matcher) {
        if (checkBlankField(matcher.group("direction"))) {
            System.out.println("pour oil failed : blank field");
            return;
        }

        String direction = matcher.group("direction");

        switch (controller.pourOil(direction)) {
            case SUCCESS:
                System.out.println("oil poured successfully");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("pour oil failed : you have not selected any unit");
                break;
            case INVALID_DIRECTION:
                System.out.println("pour oil failed : invalid direction");
                break;
            case INVALID_TYPE_OF_SELECTED_UNIT:
                System.out.println("pour oil failed : you have to select an engineer");
                break;
            case OIL_SMELTER_DOES_NOT_EXIST:
                System.out.println("pour oil failed : you don't have oil smelter");
                break;
            case OIL_SMELTER_EMPTY:
                System.out.println("pour oil failed : oil smelter is empty");
                break;
        }
    }

    private void digTunnel(Matcher matcher) {
        if (checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))) {
            System.out.println("dig tunnel failed : blank field");
            return;
        }

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        switch (controller.digTunnel(x, y)) {
            case SUCCESS:
                System.out.println("tunnel dug successfully");
                return;
            case X_OUT_OF_BOUNDS:
                System.out.println("dig tunnel failed : x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("dig tunnel failed : y out of bounds");
                break;
            case INAPPROPRIATE_TEXTURE:
                System.out.println("dig tunnel failed : inappropriate texture");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("dig tunnel failed : you have not selected any unit");
                break;
            case INVALID_TYPE_OF_SELECTED_UNIT:
                System.out.println("dig tunnel failed : you have to select tunneler");
                break;
        }

    }

    private void buildSurroundingEquipment(Matcher matcher) {
        if (checkBlankField(matcher.group("equipment"))) {
            System.out.println("build equipment failed : blank field");
            return;
        }

        String equipment = handleDoubleQuote(matcher.group("equipment"));

        switch (controller.buildSurroundingEquipment(equipment)) {
            case SUCCESS:
                System.out.println("equipment built successfully");
                break;
            case INVALID_EQUIPMENT_NAME:
                System.out.println("build equipment failed : invalid equipment name");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("build equipment failed : you have not selected any unit");
                break;
            case INVALID_TYPE_OF_SELECTED_UNIT:
                System.out.println("build equipment failed : you have to select an engineer");
                break;
            case INSUFFICIENT_SOURCE:
                System.out.println("build equipment failed : insufficient resources");
                break;
        }
    }

    private void disbandUnit() {
        switch (controller.disbandUnit()) {
            case SUCCESS:
                System.out.println("unit disbanded successfully");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("disband unit failed : you have not selected any unit");
                break;
        }
    }

    private void digMoat(Matcher matcher) {
        if (checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))) {
            System.out.println("dig moat failed : blank field");
            return;
        }

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        switch (controller.digMoat(x, y)) {
            case SUCCESS:
                System.out.println("moat dug successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("dig moat failed : x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("dig moat failed : y out of bounds");
                break;
            case INAPPROPRIATE_TEXTURE:
                System.out.println("dig moat failed : inappropriate texture");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("dig moat failed : you have not selected any unit");
                break;
            case INVALID_TYPE_OF_SELECTED_UNIT:
                System.out.println("dig moat failed : this unit cannot dig moat");
                break;
        }
    }

    private void fillMoat(Matcher matcher) {
        if (checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))) {
            System.out.println("fill moat failed : blank field");
            return;
        }

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        switch (controller.fillMoat(x, y)) {
            case SUCCESS:
                System.out.println("moat filled successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("fill moat failed : x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("fill moat failed : y out of bounds");
                break;
            case NO_MOAT:
                System.out.println("fill moat failed : there is no moat in that position");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("fill moat failed : you have not selected any unit");
                break;
            case INVALID_TYPE_OF_SELECTED_UNIT:
                System.out.println("fill moat failed : this unit cannot fill moat");
                break;
            case FILL_OPPONENT_MOAT:
                System.out.println("fill moat failed : fill opponent moat");
                break;
        }
    }

    private void burnOil() {
        switch (controller.burnOil()) {
            case SUCCESS:
                System.out.println("oil burned successfully");
                break;
            case NO_UNIT_SELECTED:
                System.out.println("burn oil failed : you have not selected any unit");
                break;
            case INVALID_TYPE_OF_SELECTED_UNIT:
                System.out.println("burn oil failed : you have to select an engineer");
                break;
            case OIL_SMELTER_DOES_NOT_EXIST:
                System.out.println("burn oil failed : there is no oil smelter");
                break;
        }
    }

}
