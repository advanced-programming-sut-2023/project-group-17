package Controller;

import View.Enums.Messages.GameMenuMessages;

public class GameMenuController {

    public GameMenuMessages chooseMapGame(int id) {
        return GameMenuMessages.SUCCESS;
    }

    public GameMenuMessages showMap(int x, int y) {
        return GameMenuMessages.SUCCESS;
    }

    public GameMenuMessages defineMapSize(int width, int length) {
        return GameMenuMessages.SUCCESS;
    }
}
