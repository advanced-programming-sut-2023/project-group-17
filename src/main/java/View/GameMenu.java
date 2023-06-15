package View;

import Controller.*;
import Model.Items.Item;
import Utils.Pair;
import View.Enums.Messages.BuildingMenuMessages;
import View.Enums.Messages.MapMenuMessages;
import View.Enums.Messages.UnitMenuMessages;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

import static java.lang.Math.pow;

public class GameMenu extends Application {

    private GameMenuController gameMenuController;
    private DataAnalysisController dataController;
    private BuildingMenuController buildingMenuController;
    private UnitMenuController unitMenuController;
    private MapMenuController mapMenuController;
    private ShopMenuController shopMenuController;
    private EmpireMenuController empireMenuController;
    private MapMenu mapMenu;
    private GridPane gridPane;
    private ToolBar toolBar;
    private HBox toolBarHBox;
    private BorderPane mainBorderPane;
    private ScrollPane scrollPane;
    private Pane tradeMenuPane;
    private Pane newTradePane;
    private Pane tradeHistoryPane;
    private Rectangle selectedArea;
    private double startX, startY;
    private double startCol, startRow;
    private double endCol, endRow;
    private double initialX, initialY;
    private double currentX, currentY;
    private List<Node> selectedNodes;
    private boolean cheatMode;
    private HashMap<String, Integer> soldiers;
    private static int goalX, goalY;

    public GameMenu() {
        this.gameMenuController = new GameMenuController();
        this.mapMenu = new MapMenu();
        this.dataController = new DataAnalysisController();
        this.buildingMenuController = new BuildingMenuController();
        this.mapMenuController = new MapMenuController();
        this.shopMenuController = new ShopMenuController();
        this.empireMenuController = new EmpireMenuController();
        this.unitMenuController = new UnitMenuController();
        cheatMode = false;
        this.cheatMode = false;
        this.selectedNodes = new ArrayList<>();
        this.soldiers = new HashMap<>();
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

        Image image = new Image(getClass().getResource("/assets/Texture/graveland.jpg").toExternalForm());
        for (int i = 0; i < numCols; i += 10) {
            for (int j = 0; j < numRows; j += 10) {
                ImageView imageView = new ImageView(image);
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
        scrollPane.requestFocus();
        handleZoom(scrollPane);
        handleHover(gridPane);
        handleCheatMode(borderPane);
        handleClick(gridPane);
        this.scrollPane = scrollPane;
        borderPane.setCenter(scrollPane);
        toolBar = createToolbar();
        this.toolBar = toolBar;
        this.mainBorderPane = borderPane;

        selectFunction();
        setHeadQuarters(gridPane);
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
    private void selectFunction() {
        gridPane.setOnMousePressed(this::handleMousePressed);
        gridPane.setOnMouseDragged(this::handleMouseDragged);
        gridPane.setOnMouseReleased(this::handleMouseReleased);
    }

    private void handleMousePressed(MouseEvent event) {
        if (!event.isShiftDown()) return;
        scrollPane.setPannable(false);
        ObservableList<Node> children = gridPane.getChildren();
        for (Node child : children) {
            if (child instanceof Rectangle) {
                gridPane.getChildren().remove(child);
                break;
            }
        }
        startX = event.getX();
        startY = event.getY();

        selectedArea = new Rectangle(startX, startY, 0, 0);
        selectedArea.setOpacity(0);
        mainBorderPane.getChildren().add(selectedArea);
    }

    private void handleMouseDragged(MouseEvent event) {
        if (selectedArea == null) return;
        double currentX = event.getX();
        double currentY = event.getY();

        double minX = Math.min(startX, currentX);
        double minY = Math.min(startY, currentY);
        double maxX = Math.max(startX, currentX);
        double maxY = Math.max(startY, currentY);

        selectedArea.setX(minX);
        selectedArea.setY(minY);
        selectedArea.setWidth(maxX - minX);
        selectedArea.setHeight(maxY - minY);
    }

    private void handleMouseReleased(MouseEvent event) {
        if (selectedArea == null) return;
        Bounds selectionBounds = selectedArea.getBoundsInParent();

        for (Node node : selectedNodes) {
            if (node instanceof Region) {
                ((Region) node).setBorder(null);
            }
        }
        selectedNodes.clear();
        startCol = 1000; startRow = 1000;
        endCol = 0; endRow = 0;
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            Node cellGrid = gridPane.getChildren().get(i);
            Bounds bounds = cellGrid.getBoundsInParent();
            if (bounds.intersects(selectionBounds)) {
                if (cellGrid instanceof Region) {
                    selectedNodes.add(cellGrid);
                    startCol = Math.min(startCol, GridPane.getColumnIndex(cellGrid));
                    startRow = Math.min(startRow, GridPane.getRowIndex(cellGrid));
                    endCol = Math.max(endCol, GridPane.getColumnIndex(cellGrid));
                    endRow = Math.max(endRow, GridPane.getRowIndex(cellGrid));
                    ((Region) cellGrid).setBorder(new Border(new
                            BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                            BorderWidths.DEFAULT)));
                }
            }
        }
        showSelectedCells();

        scrollPane.setPannable(true);
        mainBorderPane.getChildren().remove(selectedArea);
        selectedArea = null;
    }

    private void showSelectedCells() {
        toolBarHBox.getChildren().clear();
        Button button = new Button();
        HashMap<String, Integer> soldiers = mapMenuController.
                getSoldiers(startCol + 1, startRow + 1, endCol + 1, endRow + 1);
        for (String soldier : soldiers.keySet()) {
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            hBox.setSpacing(5);
            Slider slider = new Slider(0, soldiers.get(soldier), soldiers.get(soldier) + 1);
            slider.setPrefWidth(90);
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMajorTickUnit(1); // Set major tick unit to 1
            slider.setMinorTickCount(0); // Set minor tick count to 0
            slider.setBlockIncrement(1);

            Text text = new Text("\t" + soldiers.get(soldier) + "x");
//            text.setStrokeWidth(30);
            text.setFont(Font.font(15));
            String path = getClass().getResource("/assets/Soldiers/" +
                    soldier + ".png").toExternalForm();
            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                int roundedValue = (int) Math.round(newValue.doubleValue());
                slider.setValue(roundedValue);
                soldiers.put(soldier, roundedValue);
                this.soldiers = soldiers;
                unitMenuController.setSelectedUnit(startCol + 1, startRow + 1,
                        endCol + 1, endRow + 1, soldiers);
                text.setText("\t" + roundedValue + "x");
            });
            ImageView imageView = new ImageView(new Image(path, 60, 60, false, false));
            imageView.setId(soldier);
            hBox.getChildren().addAll(text, imageView);
            vBox.setSpacing(5);
            vBox.getChildren().addAll(hBox, slider);
            toolBarHBox.getChildren().add(vBox);
        }
        button.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/unitMenu.png").toExternalForm(), 20, 20, false, false)));
        toolBarHBox.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (soldiers.size() > 0) handleMove();
            }
        });
        this.soldiers = soldiers;
        System.out.println(this.soldiers);
        unitMenuController.setSelectedUnit(startCol + 1, startRow + 1,
                endCol + 1, endRow + 1, soldiers);
//        if (soldiers.size() > 0) handleMove(startCol, startRow, endCol, endRow);
    }

    private void handleMove() {
        ArrayList<Button> buttons = new ArrayList<>();
        Button move = new Button("move unit");
        move.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                moveUnit();
            }
        });
        Button patrolUnit = new Button("patrol unit");
        Button attackEnemy = new Button("attack enemy");
        Button setMode = new Button("set mode");
        Button airAttack = new Button("airAttack");
        Button pourOil = new Button("pour oil");
        Button digTunnel = new Button("dig tunnel");
        Button buildEquipment = new Button("build equipment");
        Button disbandUnit = new Button("disband unit");
        Button digMoat = new Button("dig moat");
        Button fillMoat = new Button("fill moat");
        Collections.addAll(buttons, move, patrolUnit, attackEnemy, setMode, airAttack, pourOil, digTunnel,
                buildEquipment, disbandUnit, digMoat, fillMoat);
        for (Button button : buttons) {
            button.setPrefWidth(130);
        }
        toolBarHBox.getChildren().clear();
        for (int i = 0; i < 9; i+=3) {
            VBox vBox = new VBox(buttons.get(i), buttons.get(i+1), buttons.get(i+2));
            vBox.setSpacing(10);
            toolBarHBox.getChildren().add(vBox);
        }
        VBox vBox = new VBox(digMoat, fillMoat);
        vBox.setSpacing(10);
        toolBarHBox.getChildren().add(vBox);
    }

    private void moveUnit() {
        toolBarHBox.getChildren().clear();
        TextField x = new TextField();
        x.setPromptText("destination x");
        TextField y = new TextField();
        y.setPromptText("destination y");
        Button done = new Button("move");
        HBox hBox = new HBox(x, y, done);
        toolBarHBox.getChildren().add(hBox);
        x.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!y.getText().equals("") && !y.getText().equals("-") && !newValue.equals("-") && !newValue.equals("")) {
                    UnitMenuMessages unitMenuMessages = unitMenuController.
                            moveUnitTo(Integer.parseInt(newValue), Integer.parseInt(y.getText()));
                    if (unitMenuMessages.equals(UnitMenuMessages.X_OUT_OF_BOUNDS) ||
                            unitMenuMessages.equals(UnitMenuMessages.NOT_TRAVERSABLE))
                        done.setDisable(true);
                    else if (unitMenuMessages.equals(UnitMenuMessages.SUCCESS)) done.setDisable(false);
                }
            }
        });
        y.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!x.getText().equals("") && !x.getText().equals("-") && !newValue.equals("-") && !newValue.equals("")) {
                    UnitMenuMessages unitMenuMessages = unitMenuController.
                            moveUnitTo(Integer.parseInt(x.getText()), Integer.parseInt(newValue));
                    if (unitMenuMessages.equals(UnitMenuMessages.Y_OUT_OF_BOUNDS) ||
                            unitMenuMessages.equals(UnitMenuMessages.NOT_TRAVERSABLE))
                        done.setDisable(true);
                    else if (unitMenuMessages.equals(UnitMenuMessages.SUCCESS)) {
                        done.setDisable(false);
                    }
                }
            }
        });
        Popup popup = getPopup();
        Label label = getLabel();
        popup.getContent().add(label);

        Timeline timeline = hidePopup(popup);
        done.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                label.setText("Destination set");
                popup.show(Main.stage);
                timeline.play();
                soldiers.clear();
                openGatehouseBuildings();
            }
        });
    }



    private void handleClick(GridPane gridPane) {
        gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.isShiftDown()) return;
            for (Node node : selectedNodes) {
                if (node instanceof Region) {
                    ((Region) node).setBorder(null);
                }
            }
            selectedNodes.clear();
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
                startCol = columnIndex;
                startRow = rowIndex;
                endCol = columnIndex;
                endRow = rowIndex;
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
        Popup popup = getPopup();
        Label label = getLabel();
        Timeline timeline = hidePopup(popup);
        popup.getContent().add(label);

        borderPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (cheatCode.match(keyEvent)) {
                    if (cheatMode) label.setText("Cheat Mode OFF");
                    else label.setText("Cheat Mode ON");
                    popup.show(Main.stage);
                    timeline.play();
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
                    System.out.println(soldiers.size());
                    Popup popup = getPopup();
                    Label label = getLabel();
                    popup.getContent().add(label);
                    Timeline timeline = hidePopup(popup);
                    if (dataController.isSoldierName(db.getString()) && soldiers.size() > 0) {
                        UnitMenuMessages unitMenuMessages = unitMenuController.
                                moveUnitTo(columnIndex + 1, rowIndex + 1);
                        if (unitMenuMessages.equals(UnitMenuMessages.SUCCESS)) {
                            label.setText("Destination set");
                            popup.show(Main.stage);
                            timeline.play();
                            soldiers.clear();
                            openGatehouseBuildings();
                        } else {
                            label.setText("Destination set failed");
                            popup.show(Main.stage);
                            timeline.play();
                        }
                    }
                    else if (dataController.isBuildingName(db.getString())) {
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
        //TODO: delete one of these comments
//        toolBarHBox.setTranslateX(-70);
        toolBarHBox.setTranslateX(-140);

        Button buy = new Button("Buy"); buy.setDisable(true); buy.setPrefWidth(70);
        Button sell = new Button("Sell"); sell.setDisable(true); sell.setPrefWidth(70);
        Text text = getText();
        VBox vBox1 = new VBox(); vBox1.setSpacing(10); vBox1.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(buy, text, sell);

        ArrayList<Item.ItemType> item = dataController.getWeaponsName();
        VBox vBox = new VBox();
        ArrayList<HBox> hBoxes = getShopMenuHbox();

        for (int i = 0; i < item.size(); i++) {
            String path = getClass().getResource("/assets/Item/" +
                    item.get(i).getName() + ".png").toExternalForm();
            ImageView imageView = new ImageView(new Image(path, 50, 50, false, false));
            imageView.setId(item.get(i).getName());
            int finalI = i;
            imageView.setOnMouseClicked(e -> {
                text.setText(item.get(finalI).getName() + "\nbuy: " + (int)item.get(finalI).getCost() + "\nsell: " + (int)(item.get(finalI).getCost() * 0.8));
                buy.setDisable(false); sell.setDisable(false);
                buy.setOnMouseClicked(event -> buyResource(item.get(finalI)));
                sell.setOnMouseClicked(mouseEvent -> sellResource(item.get(finalI)));
            });

            if (i < 8) hBoxes.get(1).getChildren().add(imageView);
            else if (i < 16) hBoxes.get(3).getChildren().add(imageView);
            else hBoxes.get(5).getChildren().add(imageView);
        }
        hBoxes.get(5).setAlignment(Pos.CENTER); hBoxes.get(5).setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBoxes.get(0), hBoxes.get(1), hBoxes.get(2), hBoxes.get(3), hBoxes.get(4), hBoxes.get(5));
        vBox.setAlignment(Pos.CENTER);
        toolBarHBox.getChildren().addAll(vBox1, vBox);

        //TODO: trade o khoshgel konim:D
        Button button = new Button("Trade");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                handleTradeMenu();
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        toolBarHBox.getChildren().add(button);
        //
    }

    private void handleTradeMenu() {
        toolBarHBox.getChildren().clear();

        //TODO: delete one of these comments
        toolBarHBox.setTranslateX(90);
//        toolBarHBox.setTranslateX(150);

        Text text = new Text("Trade Menu");
        Font font = Font.font("Showcard Gothic", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16);
        text.setFont(font);

        Button backButton = new Button("Back"); backButton.setPrefWidth(70);
        Button newButton = new Button("New"); newButton.setPrefWidth(70);
        Button historyButton = new Button("History"); historyButton.setPrefWidth(70);
        HBox hBox = new HBox(newButton, historyButton); hBox.setSpacing(20); hBox.setAlignment(Pos.CENTER);
        HBox hBox1 = new HBox(backButton); hBox1.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(text, hBox, hBox1); vBox.setSpacing(20); vBox.setAlignment(Pos.CENTER);
        toolBarHBox.setAlignment(Pos.CENTER);
        toolBarHBox.getChildren().addAll(vBox);

        backButton.setOnMouseClicked(mouseEvent -> createShopMenu());
        newButton.setOnMouseClicked(mouseEvent -> openNewTrade());
        historyButton.setOnMouseClicked(mouseEvent -> openTradeHistory());
    }

    private void openNewTrade() {
        //TODO
    }

    private void openTradeHistory() {
        //TODO
    }

    public ArrayList<HBox> getShopMenuHbox() {
        ArrayList<HBox> hBoxes = new ArrayList<>();
        HBox hBox1 = new HBox(); hBox1.setSpacing(40);
        HBox hBox2 = new HBox(); hBox2.setSpacing(10);
        HBox hBox3 = new HBox(); hBox3.setSpacing(40);
        HBox hBox4 = new HBox(); hBox4.setSpacing(10);
        HBox hBox5 = new HBox(); hBox5.setSpacing(40);
        HBox hBox6 = new HBox(); hBox6.setSpacing(10);
        Collections.addAll(hBoxes, hBox1, hBox2, hBox3, hBox4, hBox5, hBox6);
        return hBoxes;
    }

    public Popup getPopup() {
        Popup popup = new Popup();
        popup.setAnchorX(580); popup.setAnchorY(300);
        popup.centerOnScreen();
        popup.setOpacity(0.7);
        return popup;
    }

    public Timeline hidePopup(Popup popup) {
        return new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popup.hide();
            }
        }));
    }

    public Label getLabel() {
        Label label = new Label();
        label.setTextFill(Color.WHITE);
        label.setMinWidth(200);
        label.setMinHeight(60);
        label.setStyle("-fx-background-color: black;");
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public Text getText() {
        Text text = new Text();
        text.setFill(Color.BLACK);
        Font font = Font.font("serif", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        text.setFont(font);
        return text;
    }

    private void sellResource(Item.ItemType itemType) {
//        System.out.println("sell : " + itemType.getName());
        Popup popup = getPopup();
        Label label = getLabel();
        popup.getContent().add(label);
        Timeline timeline = hidePopup(popup);

        switch (shopMenuController.sellItem(itemType.getName(), 1)) {
            case ITEM_DOES_NOT_EXISTS:
                label.setText("Not Enough " + itemType.getName());
                popup.show(Main.stage);
                timeline.play();
                break;
            case SUCCESS:
                label.setText(itemType.getName() + " Sold Successfully");
                popup.show(Main.stage);
                timeline.play();
                break;
        }
    }

    private void buyResource(Item.ItemType itemType) {
//        System.out.println("buy : " + itemType.getName());
        Popup popup = getPopup();
        Label label = getLabel();
        popup.getContent().add(label);
        Timeline timeline = hidePopup(popup);

        switch (shopMenuController.buyItem(itemType.getName(), 1)) {
            case NOT_ENOUGH_COIN:
                label.setText("Not Enough Coin");
                popup.show(Main.stage);
                timeline.play();
                break;
            case SUCCESS:
                label.setText(itemType.getName() + " Bought Successfully");
                popup.show(Main.stage);
                timeline.play();
                break;
        }
    }



    private void createUnitMenu(String name, int columnIndex, int rowIndex) {
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
        removeFocus();
        ImageView imageView = new ImageView(new Image(path, 70, 70, false, false));
        imageView.setOnDragDetected((MouseEvent event) -> {
            if (soldiers.size() > 0) {
                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(soldierName);
                db.setContent(content);
                removeFocus();
            }
        });
        imageView.setOnMouseDragged((MouseEvent event) -> {
            event.setDragDetect(true);
        });
        if (cheatMode) {
            if (mapMenuController.dropUnit(dataController.getXHeadquarter(), dataController.getYHeadquarter() + 1,
                    soldierName, 0, imageView).equals(MapMenuMessages.SUCCESS)) {
                gridPane.add(imageView, dataController.getXHeadquarter() - 1, dataController.getYHeadquarter());
            }
        }
        else {
            if (buildingMenuController.createUnit(dataController.getXHeadquarter(),
                    dataController.getYHeadquarter() + 1, soldierName, 1, imageView)
                    .equals(BuildingMenuMessages.SUCCESS)) {
                gridPane.add(imageView, dataController.getXHeadquarter() - 1, dataController.getYHeadquarter());
            }
        }
//        System.out.println(UnitMenuController.soldierImageViewHashMap);
//        for (Soldier soldier : UnitMenuController.soldierImageViewHashMap.keySet()) {
//            new Timeline(new KeyFrame(Duration.seconds(2),
//                    e -> gridPane.getChildren().remove(UnitMenuController.soldierImageViewHashMap.get(soldier)))).play();
//        }
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
        toolBar = new ToolBar();
        toolBar.setPrefHeight(150);
        toolBar.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/ToolBar/menu.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
        Button button = new Button();
        button.setGraphic(new ImageView(new Image(GameMenu.class.getResource(
                "/assets/ToolBar/Buttons/Empire.png").toExternalForm(), 20, 20, false, false)));
        toolBar.getItems().add(button);
        setOnActionEmpireButton(button);
        HBox hBoxButtons = new HBox();
        //TODO: delete one of these
//        hBoxButtons.setTranslateX(1050);
        hBoxButtons.setTranslateX(920);

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

//        hBox.setTranslateY(20); hBox.setTranslateX(-20);
        hBox.setTranslateY(20); hBox.setTranslateX(-140);

        this.toolBarHBox = hBox;
        toolBar.getItems().add(hBoxButtons);
        Button button8 = new Button("next turn");
        toolBar.getItems().add(button8);
        button8.setTranslateY(-55);
//        button8.setTranslateX(1000);
        button8.setTranslateX(850);
        button8.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                nextTurn();
            }
        });
        toolBar.getItems().add(hBox);
        openGatehouseBuildings();

        return toolBar;
    }

    private void nextTurn() {
        //TODO
//        gameMenuController.nextTurn();
        HashMap<ImageView, ArrayList<ArrayList<Pair>>> hashMap = gameMenuController.applyMoves();
        for (Map.Entry<ImageView, ArrayList<ArrayList<Pair>>> imageViewArrayListEntry : hashMap.entrySet()) {
            new MoveAnimation(imageViewArrayListEntry.getKey(), gridPane, imageViewArrayListEntry.getValue().get(0)).play();
        }
        refreshToolBar();
    }

    private void setOnActionEmpireButton(Button button) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Popup popup = getPopup(); popup.setAnchorX(550); popup.setAnchorY(200);
                VBox vBox = new VBox(); vBox.setSpacing(5); vBox.setStyle("-fx-background-color: black;");
                Label fearLabel = getLabel();
                HBox fear = new HBox(); fear.setSpacing(10);
                double fearRate = empireMenuController.getFearRate();
                fearLabel.setText("Fear :\t" + fearRate);
                String faceImagePath = getPathFaceImage(fearRate);
                ImageView imageView = new ImageView(new Image(faceImagePath, 20, 20, false, false));
                fear.setAlignment(Pos.CENTER);
                fear.getChildren().addAll(fearLabel, imageView);
                HBox tax = new HBox(); tax.setSpacing(10);
                Label taxLabel = getLabel();
                double taxRate = empireMenuController.getTaxRate();
                taxLabel.setText("Tax :\t" + taxRate);
                faceImagePath = getPathFaceImage(taxRate);
                ImageView taxImage = new ImageView(new Image(faceImagePath, 20, 20, false, false));
                tax.getChildren().addAll(taxLabel, taxImage);
                tax.setAlignment(Pos.CENTER);
                HBox food = new HBox(); food.setSpacing(10);
                Label foodLabel = getLabel();
                double foodRate = empireMenuController.getFoodRate(); foodLabel.setText("Food :\t" + foodRate);
                faceImagePath = getPathFaceImage(foodRate);
                ImageView foodImage = new ImageView(new Image(faceImagePath, 20, 20, false, false));
                food.getChildren().addAll(foodLabel, foodImage); food.setAlignment(Pos.CENTER);
                HBox religious = new HBox(); religious.setSpacing(10);
                Label religiousLabel = getLabel(); double religiousRate = empireMenuController.getReligiousRate();
                religiousLabel.setText("Religious :\t" + religiousRate);
                faceImagePath = getPathFaceImage(religiousRate);
                ImageView religiousImage = new ImageView(new Image(faceImagePath, 20, 20, false, false));
                religious.getChildren().addAll(religiousLabel, religiousImage); religious.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(fear, tax, food, religious);
                popup.getContent().add(vBox);
                popup.show(Main.stage);
                Timeline timeline = hidePopup(popup);
                timeline.play();
            }
        });
    }

    private String getPathFaceImage(double rate) {
        if (rate > 0) return GameMenu.class.getResource(
                "/assets/EmpireFaces/Happy.PNG").toExternalForm();
        else if (rate == 0) return GameMenu.class.getResource(
                "/assets/EmpireFaces/Poker.PNG").toExternalForm();
        else return GameMenu.class.getResource(
                "/assets/EmpireFaces/Sad.PNG").toExternalForm();
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
        removeFocus();
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
                removeFocus();
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openOtherBuildings() {
        removeFocus();
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
                removeFocus();
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openDefensiveBuildings() {
        removeFocus();
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
                removeFocus();
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openStorageBuildings() {
        removeFocus();
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
                removeFocus();
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openMiningBuildings() {
        removeFocus();
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
                removeFocus();
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openProductionBuildings() {
        removeFocus();
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
                removeFocus();
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    private void openGatehouseBuildings() {
        removeFocus();
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
                removeFocus();
            });
            imageView.setOnMouseDragged((MouseEvent event) -> {
                event.setDragDetect(true);
            });
            toolBarHBox.getChildren().add(imageView);
        }
    }

    public void removeFocus() {
        ObservableList<Node> children = gridPane.getChildren();
        for (Node child : children) {
            if (child instanceof Rectangle) {
                gridPane.getChildren().remove(child);
                break;
            }
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
        gridPane.add(imageView, xBuildingForHeadquarter - 1, yBuildingForHeadquarter - 1);
        if (nameBuildingForHeadquarter.equals("smallStoneGatehouse")) {
            imageView.setOnMouseClicked(e -> openEmpireMenu());
        }
    }

    private void openEmpireMenu() {
        toolBarHBox.getChildren().clear();
        HBox fear = createSliderHBox("Fear", -5, 5);
        HBox food = createSliderHBox("Food", -2, 2);
        HBox tax = createSliderHBox("Tax", -3, 8);
        toolBarHBox.getChildren().addAll(fear, food, tax);
    }

    private HBox createSliderHBox(String name, int i, int j) {
        HBox hBox = new HBox();

        Text text = new Text(name); text.setFont(Font.font(15));

        Slider slider = new Slider(i, j, Math.abs(i) + Math.abs(j) + 1);
        slider.setPrefWidth(175);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setBlockIncrement(1);
        switch (name) {
            case "Fear":
                slider.setValue(empireMenuController.getFearRate());
                break;
            case "Food":
                slider.setValue(empireMenuController.getFoodRate());
                break;
            case "Tax":
                slider.setValue(empireMenuController.getTaxRate());
                break;
        }
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int roundedValue = (int) Math.round(newValue.doubleValue());
            slider.setValue(roundedValue);
            switch (name) {
                case "Fear":
                    empireMenuController.setFearRate(roundedValue);
                    break;
                case "Food":
                    empireMenuController.setFoodRate(roundedValue);
                    break;
                case "Tax":
                    empireMenuController.setTaxRate(roundedValue);
                    break;
            }
        });

        hBox.getChildren().addAll(text, slider);

        return hBox;
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
