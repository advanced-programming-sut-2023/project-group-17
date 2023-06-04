package View;

import Controller.GameMenuController;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class GameMenu extends Application {

    private GameMenuController controller;
    private MapMenu mapMenu;
    private GridPane gridPane;

    public GameMenu() {
        this.controller = new GameMenuController();
        this.mapMenu = new MapMenu();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane gridPane = new GridPane();
        this.gridPane = gridPane;
        gridPane.setGridLinesVisible(true);
        final int numCols = controller.getWidthMap();
        final int numRows = controller.getLengthMap();
//        System.out.println("col " + numCols);
//        System.out.println("row " + numRows);
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(80);
            colConst.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(80);
            rowConst.setValignment(VPos.CENTER);
            gridPane.getRowConstraints().add(rowConst);
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);

        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        for (int i = 0; i < numCols; i += 10) {
            for (int j = 0; j < numRows; j += 10) {
                ImageView imageView = new ImageView(new Image(getClass().getResource(
                        "/assets/Texture/graveland.jpg").toExternalForm()));
                imageView.setFitWidth(80 * Math.min(10, numCols - i));
                imageView.setFitHeight(80 * Math.min(10, numRows - j));
                gridPane.add(imageView, i, j, Math.min(10, numCols - i), Math.min(10, numRows - j));
                imageView.setOpacity(0.8);
            }
        }

//        for (int i = 0; i < numCols; i++) {
//            for (int j = 0; j < numRows; j++) {
//                ImageView imageView = new ImageView(new Image(getClass().getResource(
//                        "/assets/Backgrounds/collection27.png").toExternalForm()));
//                imageView.setRotate(45);
//                gridPane.add(imageView, i, j);
//                imageView.setFitHeight(114);
//                imageView.setFitWidth(114);
//                imageView.setOpacity(0.8);
//            }
//        }
        setHeadQuarters(gridPane);

        primaryStage.getScene().setRoot(scrollPane);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void setHeadQuarters(GridPane gridPane) {
        for (int i = 0; i < controller.getNumberOfPlayers() * 3; i++) {
            createBuilding(controller.getNameBuildingForHeadquarter(i), controller.getXBuildingForHeadquarter(i),
                    controller.getYBuildingForHeadquarter(i), gridPane);
        }
    }

    private void createBuilding(String nameBuildingForHeadquarter, int xBuildingForHeadquarter, int yBuildingForHeadquarter, GridPane gridPane) {
        String url = getClass().getResource("/assets/Buildings/" + nameBuildingForHeadquarter +".png").toExternalForm();
        gridPane.add(new ImageView(new Image(url, 80, 80, false, false)),
                xBuildingForHeadquarter - 1, yBuildingForHeadquarter - 1);
    }

//    @Override
//    public void run() {
////        System.out.println("entered game menu successfully");
//        chooseMapGame();
//        String command = null;
//        Matcher matcher;
//
//        while (true) {
//            command = scanner.nextLine();
//            if((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_MAP)) != null)
//                showMap(matcher);
//
//            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.NEXT_TURN) != null) {
//                if (nextTurn()) {
//                    System.out.println(controller.showWinner());
//                    return;
//                }
//
//                System.out.println(controller.getCurrentUserName() + " is now playing");
//            }
//
//            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_EMPIRE_MENU) != null)
//                new EmpireMenu().run();
//
//            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_BUILDING_MENU) != null)
//                new BuildingMenu().run();
//
//            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_UNIT_MENU) != null)
//                new UnitMenu().run();
//
//            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_TRADE_MENU) != null)
//                new TradeMenu().run();
//
//            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_SHOP_MENU) != null)
//                new ShopMenu().run();
//
//            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_MAP_MENU) != null)
//                mapMenu.run();
//
//            else if((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_CURRENT_MENU)) != null)
//                System.out.println("Game menu");
//
//            else System.out.println("Invalid Command");
//        }
//    }
//
//    private void showMap(Matcher matcher) {
//        if(checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))) {
//            System.out.println("show map failed : blank field");
//            return;
//        }
//
//        int x = Integer.parseInt(matcher.group("x"));
//        int y = Integer.parseInt(matcher.group("y"));
//
//        switch (controller.showMap(x , y)) {
//            case X_OUT_OF_BOUNDS:
//                System.out.println("show map failed : x out of bounds");
//                break;
//            case Y_OUT_OF_BOUNDS:
//                System.out.println("show map failed : y out of bounds");
//                break;
//            case SUCCESS:
//                mapMenu.showMap(x, y);
//                mapMenu.run();
//                break;
//        }
//    }
//
//    private void chooseMapGame() {
//        System.out.println(controller.chooseMap());
//        String command = scanner.nextLine();
//        Matcher matcher;
//        if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.GET_MAP_ID)) == null) {
//            System.out.println("Invalid command");
//            chooseMapGame();
//            return;
//        }
//
//        if(checkBlankField(matcher.group("id"))) {
//            System.out.println("show map failed : blank field");
//            chooseMapGame();
//            return;
//        }
//        int mapId = Integer.parseInt(matcher.group("id"));
//        if (mapId == 0) {
//            System.out.println("enter width and length");
//            command = scanner.nextLine();
//            if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.GET_WIDTH_AND_LENGTH)) == null) {
//                System.out.println("Invalid command");
//                chooseMapGame();
//                return;
//            }
//            if (checkBlankField(matcher.group("width")) && checkBlankField(matcher.group("length"))) {
//                System.out.println("show map failed : blank field");
//                chooseMapGame();
//                return;
//            }
//            int width = Integer.parseInt(matcher.group("width"));
//            int length = Integer.parseInt(matcher.group("length"));
//            switch (controller.createNewMap(width, length)) {
//                case INVALID_LENGTH:
//                    System.out.println("create map failed : invalid length");
//                    chooseMapGame();
//                    break;
//                case INVALID_WIDTH:
//                    System.out.println("create map failed : invalid width");
//                    chooseMapGame();
//                    break;
//                case SUCCESS:
//                    System.out.println("map created successfully");
//                    break;
//            }
//            setHeadquarters();
//            return;
//        }
//
//        switch (controller.chooseMapGame(mapId)) {
//            case INVALID_MAP_NUMBER:
//                System.out.println("choose map failed : id does not exist");
//                chooseMapGame();
//                break;
//            case SUCCESS:
//                System.out.println("map chose successfully");
//                setHeadquarters();
//                break;
//        }
//    }
//    private void setHeadquarters() {
//
//        for (int i = 0; i < controller.getNumberOfPlayers(); i++) {
//            outer : while (true) {
//                System.out.println(controller.getPlayerName(i) + " enter coordinate x and y for your headquarter");
//                String input = scanner.nextLine();
//                Matcher matcher;
//
//                if ((matcher = MainMenuCommands.getMatcher(input, MainMenuCommands.SELECT_COORDINATES_HEADQUARTERS)) != null) {
//
//                    if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y"))) {
//                        System.out.println("Show details failed : Blanked field");
//                        return;
//                    }
//
//                    int x = Integer.parseInt(matcher.group("x"));
//                    int y = Integer.parseInt(matcher.group("y"));
//
//                    switch (controller.setHeadquarters(i, x, y)) {
//                        case X_OUT_OF_BOUNDS:
//                            System.out.println("Set coordinate failed : Coordinate of x is out of bounds");
//                            break;
//                        case Y_OUT_OF_BOUNDS:
//                            System.out.println("Set coordinate failed : Coordinate of y is out of bounds");
//                            break;
//                        case INAPPROPRIATE_TEXTURE:
//                            System.out.println("Set coordinate failed : Inappropriate texture");
//                            break;
//                        case SUCCESS:
//                            System.out.println("Successfully set");
//                            break outer;
//                    }
//                } else System.out.println("Invalid Command");
//            }
//        }
//    }
//
//    public boolean nextTurn() {
//        return controller.nextTurn();
//    }
}
