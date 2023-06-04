package View;

import Controller.GameMenuController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.crypto.dsig.keyinfo.KeyName;
import java.awt.*;

import static java.lang.Math.pow;

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

        BorderPane borderPane = new BorderPane();

        GridPane gridPane = new GridPane();
        this.gridPane = gridPane;
        gridPane.setGridLinesVisible(true);
        final int numCols = controller.getWidthMap();
        final int numRows = controller.getLengthMap();
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

//        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
//        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
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
        setHeadQuarters(gridPane);
        scrollPane.requestFocus();
        handleZoom(scrollPane);
        handleHover(gridPane);
        borderPane.setCenter(scrollPane);
        ToolBar toolBar = createToolbar();

        borderPane.setBottom(toolBar);
        primaryStage.getScene().setRoot(borderPane);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void handleZoom(ScrollPane scrollPane) {
        KeyCombination zoomInKeyCombination = new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.CONTROL_DOWN);
        KeyCombination zoomOutKeyCombination = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN);
        scrollPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                double zoomFactor = 1.05;
                if (zoomInKeyCombination.match(event)) {
                    if (gridPane.getScaleX() != pow(zoomFactor, 6)) {
                        gridPane.setScaleX(gridPane.getScaleX() * zoomFactor);
                        gridPane.setScaleY(gridPane.getScaleY() * zoomFactor);
                        event.consume();
                    }
                }
                else if (zoomOutKeyCombination.match(event)) {
                    if (gridPane.getScaleX() != 1.0) {
                        gridPane.setScaleX(gridPane.getScaleX() / zoomFactor);
                        gridPane.setScaleY(gridPane.getScaleY() / zoomFactor);
                        event.consume();
                    }
                }
            }
        });
    }

    private ToolBar createToolbar() {
        ToolBar toolBar = new ToolBar();
        toolBar.setPrefHeight(200);
        toolBar.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/ToolBar/menu.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));

        Button button1 = new Button();
        button1.setTranslateX(310); button1.setTranslateY(80);
        button1.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Castle.png").toExternalForm(), 25, 25, false, false)));
        Button button2 = new Button("");
        button2.setTranslateX(310); button2.setTranslateY(80);
        button2.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Industry.png").toExternalForm(), 25, 25, false, false)));
        Button button3 = new Button("");
        button3.setTranslateX(310); button3.setTranslateY(80);
        button3.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Farm.png").toExternalForm(), 25, 25, false, false)));
        Button button4 = new Button("");
        button4.setTranslateX(310); button4.setTranslateY(80);
        button4.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Town.png").toExternalForm(), 25, 25, false, false)));
        Button button5 = new Button("");
        button5.setTranslateX(310); button5.setTranslateY(80);
        button5.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Weapons.png").toExternalForm(), 25, 25, false, false)));
        Button button6 = new Button("");
        button6.setTranslateX(310); button6.setTranslateY(80);
        button6.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Food.png").toExternalForm(), 25, 25, false, false)));

        toolBar.getItems().addAll(button1, button2, button3, button4, button5, button6);

        return toolBar;
    }

    private void handleHover(GridPane gridPane) {
        for (int i = 0; i < gridPane.getColumnCount(); i++) {
            for (int j = 0; j < gridPane.getRowCount(); j++) {
                Label label = new Label();
                label.setMaxSize(80, 80);
                String details = controller.showDetails(i + 1, j + 1);
                Tooltip tooltip = new Tooltip(details);
                tooltip.setShowDelay(Duration.seconds(2));
                tooltip.setHideDelay(Duration.seconds(1));
                label.setTooltip(tooltip);
                Tooltip.install(label, tooltip);
                gridPane.add(label, i, j);
            }
        }
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
