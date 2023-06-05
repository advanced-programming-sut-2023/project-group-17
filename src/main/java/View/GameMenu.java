package View;

import Controller.BuildingMenuController;
import Controller.DataAnalysisController;
import Controller.GameMenuController;
import Controller.MapMenuController;
import View.Enums.Messages.BuildingMenuMessages;
import View.Enums.Messages.MapMenuMessages;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Math.pow;

public class GameMenu extends Application {

    private GameMenuController controller;
    private DataAnalysisController dataController;
    private BuildingMenuController buildingMenuController;
    private MapMenuController mapMenuController;
    private MapMenu mapMenu;
    private GridPane gridPane;
    private ToolBar toolBar;
    private HBox toolBarHBox;
    private boolean cheatMode;

    public GameMenu() {
        this.controller = new GameMenuController();
        this.mapMenu = new MapMenu();
        this.dataController = new DataAnalysisController();
        this.buildingMenuController = new BuildingMenuController();
        this.mapMenuController = new MapMenuController();
        cheatMode = false;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        controller.setFirstUser();

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
        handleCheatMode(borderPane);
        handleClick(gridPane);
        borderPane.setCenter(scrollPane);
        ToolBar toolBar = createToolbar();
        this.toolBar = toolBar;
        setDropActionForGridPane();

        borderPane.setBottom(toolBar);
        primaryStage.getScene().setRoot(borderPane);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void handleClick(GridPane gridPane) {
        gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
            if (clickedNode != gridPane) {
                ObservableList<Node> children = gridPane.getChildren();
                for (Node child : children) {
                    if (child instanceof Rectangle) {
                        gridPane.getChildren().remove(child);
                    }
                }

                int columnIndex = GridPane.getColumnIndex(clickedNode);
                int rowIndex = GridPane.getRowIndex(clickedNode);
                Rectangle cellBorder = new Rectangle(80, 80);
                cellBorder.setFill(Color.TRANSPARENT);
                cellBorder.setStroke(Color.DEEPSKYBLUE);
                cellBorder.setOpacity(0.5);
                cellBorder.setStrokeWidth(4);
                gridPane.add(cellBorder, columnIndex, rowIndex);
            }
        });
    }

    private void handleCheatMode(BorderPane borderPane) {
        KeyCombination cheatCode = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
        borderPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (cheatCode.match(keyEvent)) {
                    cheatMode = !cheatMode;
                    System.out.println(cheatMode);
                }
            }
        });
    }

    private void setDropActionForGridPane() {
        for (Node node : gridPane.getChildren()) {
            node.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getGestureSource() != node && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }

                    event.consume();
                }
            });

            node.setOnDragDropped((DragEvent event) -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    System.out.println("Dropped: " + db.getString());
                    int columnIndex = GridPane.getColumnIndex(node);
                    int rowIndex = GridPane.getRowIndex(node);
                    if (cheatMode) {
                        if (mapMenuController.dropBuilding(columnIndex + 1, rowIndex + 1, db.getString()).equals(MapMenuMessages.SUCCESS)) {
                            String path = getClass().getResource("/assets/Buildings/" +
                                    db.getString() + ".png").toExternalForm();
                            ImageView imageView = new ImageView(new Image(path, 80, 80, false, false));
                            gridPane.add(imageView, columnIndex, rowIndex);
                        }
                    }
                    else {
                        if (buildingMenuController.dropBuilding(columnIndex + 1, rowIndex + 1, db.getString()).equals(BuildingMenuMessages.SUCCESS)) {
                            String path = getClass().getResource("/assets/Buildings/" +
                                    db.getString() + ".png").toExternalForm();
                            ImageView imageView = new ImageView(new Image(path, 80, 80, false, false));
                            gridPane.add(imageView, columnIndex, rowIndex);
                        }
                    }
                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }
                event.consume();
            });
        }
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
        toolBar.setPrefHeight(150);
        toolBar.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/ToolBar/menu.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
        HBox hBoxButtons = new HBox();
        hBoxButtons.setTranslateX(1050);
        hBoxButtons.setSpacing(10);
        hBoxButtons.setTranslateY(10);
        hBoxButtons.setPrefHeight(30);

        Button button1 = new Button();
        button1.setTranslateY(110);
        button1.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Gatehouse.png").toExternalForm(), 20, 20, false, false)));
        Button button2 = new Button("");
        button2.setTranslateY(110);
        button2.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Production.png").toExternalForm(), 20, 20, false, false)));
        Button button3 = new Button("");
        button3.setTranslateY(110);
        button3.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Farm.png").toExternalForm(), 20, 20, false, false)));
        Button button4 = new Button("");
        button4.setTranslateY(110);
        button4.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Town.png").toExternalForm(), 20, 20, false, false)));
        Button button5 = new Button("");
        button5.setTranslateY(110);
        button5.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Weapons.png").toExternalForm(), 20, 20, false, false)));
        Button button6 = new Button("");
        button6.setTranslateY(110);
        button6.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Food.png").toExternalForm(), 20, 20, false, false)));
        setOnActionButtons(button1, button2, button3, button4, button5, button6);
        hBoxButtons.getChildren().addAll(button1, button2, button3, button4, button5, button6);

//        toolBar.getItems().addAll(button1, button2, button3, button4, button5, button6);
        HBox hBox = new HBox();
        hBox.setTranslateY(20); hBox.setTranslateX(40);
        this.toolBarHBox = hBox;
        toolBar.getItems().add(hBoxButtons);
        toolBar.getItems().add(hBox);
        openGatehouseBuildings();

        return toolBar;
    }

    private void setOnActionButtons(Button button1, Button button2, Button button3, Button button4, Button button5, Button button6) {
        button1.setOnAction(e -> openGatehouseBuildings());
        button2.setOnAction(e -> openProductionBuildings());
    }

    private void openProductionBuildings() {
        String path = "";
        toolBarHBox.getChildren().clear();
        toolBarHBox.setSpacing(10);
        for (int i = 0; i < dataController.getProductionBuildingsSize(); i++) {
            path = getClass().getResource("/assets/Buildings/" +
                    dataController.getProductionBuildingNameByNumber(i) + ".png").toExternalForm();
            ImageView imageView = new ImageView(new Image(path, 80, 80, false, false));
            imageView.setId(dataController.getProductionBuildingNameByNumber(i));
            int finalI = i;
            imageView.setOnDragDetected((MouseEvent event) -> {

                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(dataController.getProductionBuildingNameByNumber(finalI));
                db.setContent(content);
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openGatehouseBuildings() {
        String path = "";
        toolBarHBox.getChildren().clear();
        toolBarHBox.setSpacing(10);
        for (int i = 0; i < dataController.getGatehouseBuildingsSize(); i++) {
            path = getClass().getResource("/assets/Buildings/" +
                    dataController.getGatehouseBuildingNameByNumber(i) + ".png").toExternalForm();
            ImageView imageView = new ImageView(new Image(path, 80, 80, false, false));
            imageView.setId(dataController.getGatehouseBuildingNameByNumber(i));
            int finalI = i;
            imageView.setOnDragDetected((MouseEvent event) -> {

                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(dataController.getGatehouseBuildingNameByNumber(finalI));
                db.setContent(content);
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });


//            imageView.setOnDragDetected(event -> {
//                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
//                ClipboardContent content = new ClipboardContent();
//                content.putImage(imageView.getImage());
//                db.setContent(content);
//                event.consume();
//            });
//            imageView.setOnDragOver(new EventHandler<DragEvent>() {
//                public void handle(DragEvent event) {
//                    if (event.getGestureSource() != imageView && event.getDragboard().hasFiles()) {
//                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                    }
//                    event.consume();
//                }
//            });
//            imageView.setOnDragDropped(new EventHandler<DragEvent>() {
//                public void handle(DragEvent event) {
//                    Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
//                    ClipboardContent content = new ClipboardContent();
//                    content.putImage(imageView.getImage());
//                    db.setContent(content);
//                    event.consume();
//                }
//            });
//
//            // Change the appearance of the node when the drag enters
//            imageView.setOnDragEntered(event -> {
//                if (event.getGestureSource() != imageView && event.getDragboard().hasFiles()) {
//                    // You can change the appearance of the node here, e.g., change the background color
//                }
//            });
//
//            // Reset the appearance of the node when the drag exits
//            imageView.setOnDragExited(event -> {
//                // Reset the appearance of the node here, e.g., change the background color back to the original
//            });

//            imageView.setOnDragDropped(new EventHandler<DragEvent>() {
//                @Override
//                public void handle(DragEvent event) {
//                    Dragboard db = event.getDragboard();
//                    boolean success = false;
//                    if (db.hasFiles()) {
//                        // Handle the file drop here
//                        success = true;
//                    }
//                    event.setDropCompleted(success);
//                    event.consume();
//                }
//            });
            toolBarHBox.getChildren().add(imageView);
        }
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
        ImageView imageView = new ImageView(new Image(url, 80, 80, false, false));
        imageView.setOnDragDetected(event -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            db.setContent(content);
            event.consume();
        });
        gridPane.add(imageView,
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
