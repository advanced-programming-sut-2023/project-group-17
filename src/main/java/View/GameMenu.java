package View;

import Controller.*;
import Model.Items.Item;
import View.Enums.Messages.BuildingMenuMessages;
import View.Enums.Messages.MapMenuMessages;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

public class GameMenu extends Application {

    private GameMenuController gameMenuController;
    private DataAnalysisController dataController;
    private BuildingMenuController buildingMenuController;
    private MapMenuController mapMenuController;
    private ShopMenuController shopMenuController;
    private MapMenu mapMenu;
    private GridPane gridPane;
    private ToolBar toolBar = new ToolBar();
    private HBox toolBarHBox;
    private BorderPane mainBorderPane;
    private Rectangle selectedArea;
    private double initialX, initialY;
    private double currentX, currentY;
    private List<Node> selectedNodes;
    private boolean cheatMode;

    public GameMenu() {
        this.gameMenuController = new GameMenuController();
        this.mapMenu = new MapMenu();
        this.dataController = new DataAnalysisController();
        this.buildingMenuController = new BuildingMenuController();
        this.mapMenuController = new MapMenuController();
        this.shopMenuController = new ShopMenuController();
        cheatMode = false;
        this.cheatMode = false;
        this.selectedNodes = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        gameMenuController.setFirstUser();

        GridPane gridPane = new GridPane();
        this.gridPane = gridPane;
        gridPane.setGridLinesVisible(true);
        final int numCols = gameMenuController.getWidthMap();
        final int numRows = gameMenuController.getLengthMap();
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

        //
//
//        // Set up the mini-map GridPane
//        GridPane miniMap = new GridPane();
//        final int miniCols = numCols / 10;
//        final int miniRows = numRows / 10;
//        for (int i = 0; i < miniCols; i++) {
//            ColumnConstraints colConst = new ColumnConstraints();
//            colConst.setPrefWidth(8);
//            colConst.setHalignment(HPos.CENTER);
//            miniMap.getColumnConstraints().add(colConst);
//        }
//        for (int i = 0; i < miniRows; i++) {
//            RowConstraints rowConst = new RowConstraints();
//            rowConst.setPrefHeight(8);
//            rowConst.setValignment(VPos.CENTER);
//            miniMap.getRowConstraints().add(rowConst);
//        }
//
//
//
        //



        ScrollPane scrollPane = new ScrollPane(gridPane);

//        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
//        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);

        for (int i = 0; i < numCols; i += 10) {
            for (int j = 0; j < numRows; j += 10) {
                ImageView imageView = new ImageView(new Image(getClass().getResource(
                        "/assets/Texture/graveland.jpg").toExternalForm()));
                imageView.setFitWidth(80 * Math.min(10, numCols - i));
                imageView.setFitHeight(80 * Math.min(10, numRows - j));
                gridPane.add(imageView, i, j, Math.min(10, numCols - i), Math.min(10, numRows - j));
                imageView.setOpacity(0.8);

                //

//                if (i % 100 == 0 && j % 100 == 0) {
//                    ImageView miniImageView = new ImageView(new Image(getClass().getResource(
//                            "/assets/Texture/graveland.jpg").toExternalForm()));
//                    miniImageView.setFitWidth(8 * Math.min(10, miniCols - i / 10));
//                    miniImageView.setFitHeight(8 * Math.min(10, miniRows - j / 10));
//                    miniMap.add(miniImageView, i / 10, j / 10);
//                    miniImageView.setOnMouseClicked(event -> {
//                        double x = event.getX() / miniImageView.getBoundsInLocal().getWidth() * numCols;
//                        double y = event.getY() / miniImageView.getBoundsInLocal().getHeight() * numRows;
////                        gridPane.scrollTo(x, y, ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.NEVER);
//                    });
//                }


                //

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
        this.mainBorderPane = borderPane;
        gridPane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            // Store the initial mouse press position
//            if (!event.isShiftDown()) {
//                for (Node node : gridPane.getChildren()) {
//                    if (node instanceof Region) {
//                        ((Region) node).setBorder(null);
//                    }
//                }
//                return;
//            }
            initialX = event.getX();
            initialY = event.getY();
            event.consume();
        });

        gridPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            // Determine the current mouse position during the drag
            currentX = event.getX();
            currentY = event.getY();
            event.consume();
        });

        gridPane.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            // Determine which nodes are within the selection area
            Bounds selectionBounds = new BoundingBox(
                    Math.min(initialX, currentX), // X coordinate of the top-left corner of the selection area
                    Math.min(initialY, currentY), // Y coordinate of the top-left corner of the selection area
                    Math.abs(currentX - initialX), // Width of the selection area
                    Math.abs(currentY - initialY) // Height of the selection area
            );

            // Remove the border from all previously selected nodes
            for (Node node : selectedNodes) {
                if (node instanceof Region) {
                    ((Region) node).setBorder(null);
                }
            }
            selectedNodes.clear();

            // Add a border to the selected nodes
            for (Node node : gridPane.getChildren()) {
                if (node.getBoundsInParent().intersects(selectionBounds)) {
                    if (node instanceof Region) {
                        ((Region) node).setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                        selectedNodes.add(node);
                    }
                }
            }
            event.consume();
        });

//        selectFunction();
        setDropActionForGridPane();

        borderPane.setBottom(toolBar);
        //
//        miniMap.setCenterShape(true);
//        borderPane.setBottom(miniMap);
//        setHeadQuarters(miniMap);
        //

        primaryStage.getScene().setRoot(borderPane);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
//
//    private void selectFunction() {
//        gridPane.setOnMousePressed(this::handleMousePressed);
//        gridPane.setOnMouseDragged(this::handleMouseDragged);
//        gridPane.setOnMouseReleased(this::handleMouseReleased);
//    }
//
//    private void handleMousePressed(MouseEvent event) {
//        if (!event.isShiftDown()) return;
//        startX = event.getX();
//        startY = event.getY();
//
//        selectedArea = new Rectangle(startX, startY, 0, 0);
//        selectedArea.setOpacity(0);
//        mainBorderPane.getChildren().add(selectedArea);
//    }
//
//    private void handleMouseDragged(MouseEvent event) {
//        if (selectedArea == null) return;
//        double currentX = event.getX();
//        double currentY = event.getY();
//
//        double minX = Math.min(startX, currentX);
//        double minY = Math.min(startY, currentY);
//        double maxX = Math.max(startX, currentX);
//        double maxY = Math.max(startY, currentY);
//
//        selectedArea.setX(minX);
//        selectedArea.setY(minY);
//        selectedArea.setWidth(maxX - minX);
//        selectedArea.setHeight(maxY - minY);
//    }
//
//    private void handleMouseReleased(MouseEvent event) {
//        if (selectedArea == null) return;
//        Bounds selectionBounds = selectedArea.getBoundsInParent();
//
//        for (int i = 0; i < gridPane.getChildren().size(); i++) {
//            System.out.println("hata inja");
//            Node cellGrid = gridPane.getChildren().get(i);
//            Bounds bounds = cellGrid.getBoundsInParent();
//            if (bounds.intersects(selectionBounds)) {
//                int columnIndex = GridPane.getColumnIndex(cellGrid);
//                int rowIndex = GridPane.getRowIndex(cellGrid);
//                Rectangle cellBorder = new Rectangle(80, 80, Color.TRANSPARENT);
//                cellBorder.setStroke(Color.DEEPSKYBLUE);
//                cellBorder.setOpacity(0.5);
//                cellBorder.setStrokeWidth(4);
//                gridPane.add(cellBorder, columnIndex, rowIndex);
//            }
//        }
//        mainBorderPane.getChildren().remove(selectedArea);
//        selectedArea = null;
//    }

    private void handleClick(GridPane gridPane) {
        gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
            if (clickedNode != gridPane) {
                ObservableList<Node> children = gridPane.getChildren();
                for (Node child : children) {
                    if (child instanceof Rectangle) {
                        gridPane.getChildren().remove(child);
                        break;
                    }
                }

                int columnIndex = GridPane.getColumnIndex(clickedNode);
                int rowIndex = GridPane.getRowIndex(clickedNode);
                Rectangle cellBorder = new Rectangle(80, 80, Color.TRANSPARENT);
                cellBorder.setStroke(Color.DEEPSKYBLUE);
                cellBorder.setOpacity(0.5);
                cellBorder.setStrokeWidth(4);
                gridPane.add(cellBorder, columnIndex, rowIndex);
            }
        });
    }

    private void handleCheatMode(BorderPane borderPane) {
        KeyCombination cheatCode = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_ANY);
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
                    ImageView imageView;
                    if (cheatMode) {
                        if (mapMenuController.dropBuilding(columnIndex + 1, rowIndex + 1, db.getString()).equals(MapMenuMessages.SUCCESS)) {
                            String path = getClass().getResource("/assets/Buildings/" +
                                    db.getString() + ".png").toExternalForm();
                            if (db.getString().equals("oxTether"))
                                imageView = new ImageView(new Image(path, 50, 50, false, false));
                            else
                                imageView = new ImageView(new Image(path, 80, 80, false, false));
                            gridPane.add(imageView, columnIndex, rowIndex);
                        }
                    }
                    else {
                        if (buildingMenuController.dropBuilding(columnIndex + 1, rowIndex + 1, db.getString()).equals(BuildingMenuMessages.SUCCESS)) {
                            String path = getClass().getResource("/assets/Buildings/" +
                                    db.getString() + ".png").toExternalForm();
                            if (db.getString().equals("oxTether"))
                                imageView = new ImageView(new Image(path, 50, 50, false, false));
                            else
                                imageView = new ImageView(new Image(path, 80, 80, false, false));
                            handleSelectBuilding(imageView, db.getString(), columnIndex, rowIndex);
                            gridPane.add(imageView, columnIndex, rowIndex);
                        }
                    }
                    refreshToolBar();
                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }
                event.consume();
            });
        }
    }

    private void handleSelectBuilding(ImageView imageView, String name, int columnIndex, int rowIndex) {
        System.out.println(name);
        switch (dataController.getTypeBuilding(name)) {
            case "production":
                //TODO
                break;
            case "soldier production":
                imageView.setOnMouseClicked(e -> createUnitMenu(name, columnIndex, rowIndex));
                break;
            case "other buildings":
                if (name.equals("market"))
                    imageView.setOnMouseClicked(e -> createShopMenu());
                break;
        }
    }

    private void createShopMenu() {
        toolBarHBox.getChildren().clear();
        Button buy = new Button("Buy"); buy.setDisable(true); buy.setPrefWidth(70);
        Button sell = new Button("Sell"); sell.setDisable(true); sell.setPrefWidth(70);
        VBox vBox1 = new VBox(); vBox1.setSpacing(120);
        vBox1.getChildren().addAll(buy, sell);

        ArrayList<Item.ItemType> item = dataController.getWeaponsName();
        VBox vBox = new VBox();
        HBox hBox1 = new HBox(); hBox1.setSpacing(40);
        HBox hBox2 = new HBox(); hBox2.setSpacing(10);
        HBox hBox3 = new HBox(); hBox3.setSpacing(40);
        HBox hBox4 = new HBox(); hBox4.setSpacing(10);
        HBox hBox5 = new HBox(); hBox5.setSpacing(40);
        HBox hBox6 = new HBox(); hBox6.setSpacing(10);

        toolBar.getItems().get(0).setTranslateY(68);

        for (int i = 0; i < item.size(); i++) {
            String path = getClass().getResource("/assets/Item/" +
                    item.get(i).getName() + ".png").toExternalForm();
            ImageView imageView = new ImageView(new Image(path, 50, 50, false, false));
            imageView.setId(item.get(i).getName());
            int finalI = i;
            imageView.setOnMouseClicked(e -> {
                buy.setDisable(false); sell.setDisable(false);
                buy.setOnMouseClicked(event -> buyResource(item.get(finalI)));
                sell.setOnMouseClicked(mouseEvent -> sellResource(item.get(finalI)));
            });
            Text text = new Text("" + item.get(i).getCost());

            if (i < 8) {
                hBox1.getChildren().add(text);
                hBox2.getChildren().add(imageView);
            }
            else if (i < 16) {
                hBox3.getChildren().add(text);
                hBox4.getChildren().add(imageView);
            }
            else {
                hBox5.getChildren().add(text);
                hBox6.getChildren().add(imageView);
            }


        }
        hBox6.setAlignment(Pos.CENTER); hBox5.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox1, hBox2, hBox3, hBox4, hBox5, hBox6);
        vBox.setAlignment(Pos.CENTER);
        toolBarHBox.getChildren().addAll(vBox1, vBox);
    }

    private void sellResource(Item.ItemType itemType) {
//        System.out.println("sell : " + itemType.getName());
        shopMenuController.sellItem(itemType.getName(), 1);
    }

    private void buyResource(Item.ItemType itemType) {
//        System.out.println("buy : " + itemType.getName());
        shopMenuController.buyItem(itemType.getName(), 1);
    }

    private void setSelectedItem(Item.ItemType selectedItem, Item.ItemType itemType) {
        selectedItem = itemType;
    }


    private void createUnitMenu(String name, int columnIndex, int rowIndex) {
        toolBar.getItems().get(0).setTranslateY(10);

        toolBarHBox.getChildren().clear();
        ArrayList<String> soldiers = dataController.getSoldierNames(name);
        for (int i = 0; i < soldiers.size(); i++) {
            String path = getClass().getResource("/assets/Soldiers/" +
                    soldiers.get(i) + ".png").toExternalForm();
            ImageView imageView = new ImageView(new Image(path, 60, 60, false, false));
            imageView.setId(soldiers.get(i));
            int finalI = i;
            imageView.setOnMouseClicked(e -> createSoldiers(soldiers.get(finalI), path, columnIndex, rowIndex));
            toolBarHBox.getChildren().add(imageView);
        }
//        System.out.println(dataController.getSoldierNames(name));
    }

    private void createSoldiers(String soldierName, String path, int columnIndex, int rowIndex) {
        if (cheatMode) {
            if (mapMenuController.dropUnit(dataController.getXHeadquarter(), dataController.getYHeadquarter() + 1,
                    soldierName, 0).equals(MapMenuMessages.SUCCESS)) {
                ImageView imageView = new ImageView(new Image(path, 70, 70, false, false));
                gridPane.add(imageView, dataController.getXHeadquarter() - 1, dataController.getYHeadquarter());
            }
        }
        else {
            if (buildingMenuController.createUnit(dataController.getXHeadquarter(),
                    dataController.getYHeadquarter() + 1, soldierName, 1)
                    .equals(BuildingMenuMessages.SUCCESS)) {
                ImageView imageView = new ImageView(new Image(path, 60, 60, false, false));
                gridPane.add(imageView, dataController.getXHeadquarter() - 1, dataController.getYHeadquarter());
            }
        }
        refreshToolBar();
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
        toolBar.setPrefHeight(150);
        toolBar.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/ToolBar/menu.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
        HBox hBoxButtons = new HBox();
        //TODO: delete one of these
//        hBoxButtons.setTranslateX(1050);
        hBoxButtons.setTranslateX(950);

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
                "/assets/ToolBar/Buttons/Mining.png").toExternalForm(), 20, 20, false, false)));
        Button button4 = new Button("");
        button4.setTranslateY(110);
        button4.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Storage.png").toExternalForm(), 20, 20, false, false)));
        Button button5 = new Button("");
        button5.setTranslateY(110);
        button5.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Defensive.png").toExternalForm(), 20, 20, false, false)));
        Button button6 = new Button("");
        button6.setTranslateY(110);
        button6.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Other.png").toExternalForm(), 20, 20, false, false)));
        Button button7 = new Button("");
        button7.setTranslateY(110);
        button7.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Soldier.png").toExternalForm(), 20, 20, false, false)));
        setOnActionButtons(button1, button2, button3, button4, button5, button6, button7);
        hBoxButtons.getChildren().addAll(button1, button2, button3, button4, button5, button6, button7);

//        toolBar.getItems().addAll(button1, button2, button3, button4, button5, button6);
        HBox hBox = new HBox();
        hBox.setTranslateY(20); hBox.setTranslateX(-20);
        this.toolBarHBox = hBox;
        toolBar.getItems().add(hBoxButtons);
        toolBar.getItems().add(hBox);
        openGatehouseBuildings();

        return toolBar;
    }

    private void setOnActionButtons(Button button1, Button button2, Button button3,
                                    Button button4, Button button5, Button button6, Button button7) {
        button1.setOnAction(e -> openGatehouseBuildings());
        button2.setOnAction(e -> openProductionBuildings());
        button3.setOnAction(e -> openMiningBuildings());
        button4.setOnAction(e -> openStorageBuildings());
        button5.setOnAction(e -> openDefensiveBuildings());
        button6.setOnAction(e -> openOtherBuildings());
        button7.setOnAction(e -> openSoldierBuildings());
    }

    private void openSoldierBuildings() {
        toolBar.getItems().get(0).setTranslateY(10);

        String path = "";
        toolBarHBox.getChildren().clear();
        toolBarHBox.setSpacing(10);
        for (int i = 0; i < dataController.getSoldierBuildingsSize(); i++) {
            path = getClass().getResource("/assets/Buildings/" +
                    dataController.getSoldierBuildingNameByNumber(i) + ".png").toExternalForm();
            ImageView imageView = new ImageView(new Image(path, 80, 80, false, false));
            imageView.setId(dataController.getSoldierBuildingNameByNumber(i));
            int finalI = i;
            imageView.setOnDragDetected((MouseEvent event) -> {

                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(dataController.getSoldierBuildingNameByNumber(finalI));
                db.setContent(content);
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openOtherBuildings() {
        toolBar.getItems().get(0).setTranslateY(10);

        String path = "";
        toolBarHBox.getChildren().clear();
        toolBarHBox.setSpacing(10);
        for (int i = 0; i < dataController.getOtherBuildingsSize(); i++) {
            path = getClass().getResource("/assets/Buildings/" +
                    dataController.getOtherBuildingNameByNumber(i) + ".png").toExternalForm();
            ImageView imageView = new ImageView(new Image(path, 80, 80, false, false));;
//            if (dataController.getOtherBuildingNameByNumber(i).equals("oxTether") || dataController.getOtherBuildingNameByNumber(i).equals("drawbridge"))
//                imageView = new ImageView(new Image(path, 60, 60, false, false));
//            else
//                imageView = new ImageView(new Image(path, 80, 80, false, false));
            imageView.setId(dataController.getOtherBuildingNameByNumber(i));
            int finalI = i;
            imageView.setOnDragDetected((MouseEvent event) -> {

                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(dataController.getOtherBuildingNameByNumber(finalI));
                db.setContent(content);
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openDefensiveBuildings() {
        toolBar.getItems().get(0).setTranslateY(10);

        String path = "";
        toolBarHBox.getChildren().clear();
        toolBarHBox.setSpacing(10);
        for (int i = 0; i < dataController.getDefensiveBuildingsSize(); i++) {
            path = getClass().getResource("/assets/Buildings/" +
                    dataController.getDefensiveBuildingNameByNumber(i) + ".png").toExternalForm();
            ImageView imageView = new ImageView(new Image(path, 80, 80, false, false));
            imageView.setId(dataController.getDefensiveBuildingNameByNumber(i));
            int finalI = i;
            imageView.setOnDragDetected((MouseEvent event) -> {

                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(dataController.getDefensiveBuildingNameByNumber(finalI));
                db.setContent(content);
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openStorageBuildings() {
        toolBar.getItems().get(0).setTranslateY(10);

        String path = "";
        toolBarHBox.getChildren().clear();
        toolBarHBox.setSpacing(10);
        for (int i = 0; i < dataController.getStorageBuildingsSize(); i++) {
            path = getClass().getResource("/assets/Buildings/" +
                    dataController.getStorageBuildingNameByNumber(i) + ".png").toExternalForm();
            ImageView imageView = new ImageView(new Image(path, 80, 80, false, false));
            imageView.setId(dataController.getStorageBuildingNameByNumber(i));
            int finalI = i;
            imageView.setOnDragDetected((MouseEvent event) -> {

                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(dataController.getStorageBuildingNameByNumber(finalI));
                db.setContent(content);
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openMiningBuildings() {
        toolBar.getItems().get(0).setTranslateY(10);

        String path = "";
        toolBarHBox.getChildren().clear();
        toolBarHBox.setSpacing(10);
        for (int i = 0; i < dataController.getMiningBuildingsSize(); i++) {
            path = getClass().getResource("/assets/Buildings/" +
                    dataController.getMiningBuildingNameByNumber(i) + ".png").toExternalForm();
            ImageView imageView = new ImageView(new Image(path, 80, 80, false, false));
            imageView.setId(dataController.getMiningBuildingNameByNumber(i));
            int finalI = i;
            imageView.setOnDragDetected((MouseEvent event) -> {

                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(dataController.getMiningBuildingNameByNumber(finalI));
                db.setContent(content);
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openProductionBuildings() {
        toolBar.getItems().get(0).setTranslateY(10);

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
        toolBar.getItems().get(0).setTranslateY(10);

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
                String details = gameMenuController.showDetails(i + 1, j + 1);
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
        for (int i = 0; i < gameMenuController.getNumberOfPlayers() * 3; i++) {
            createBuilding(gameMenuController.getNameBuildingForHeadquarter(i), gameMenuController.getXBuildingForHeadquarter(i),
                    gameMenuController.getYBuildingForHeadquarter(i), gridPane);
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

    private void refreshToolBar() {
        ArrayList<Label> labels = new ArrayList<>();
        for (Node child : gridPane.getChildren()) {
            if (child instanceof Label) labels.add((Label) child);
        }

        for (Label label : labels) {
            label.getTooltip().setText(gameMenuController.showDetails(GridPane.getColumnIndex(label)+1, GridPane.getRowIndex(label)+1));
        }
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
