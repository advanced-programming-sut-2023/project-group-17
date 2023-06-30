package Client.view;

import Client.controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Client.ClientMain.stage;


public class MainMenu extends Application {
    public Timeline updateTimeLine;

    public TextField turnsCount;
    public TextField lobbyCode;
    public TextField length;
    public Button startNewGameButton;
//    private MainMenuController controller = new MainMenuController();
    public static String users;

//    public MainMenu() {
//        this.controller = new MainMenuController();
//    }
    public ListView<String> listView = new ListView<>();
    public static int capacity;
    public Button enterLobbyButton;
    public Button openLobbieButton;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/MainMenu.fxml"));
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/Backgrounds/mainBackground.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
        ComboBox<String> comboBox = new ComboBox<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("2", "3", "4", "5", "6", "7", "8");
        comboBox.setItems(items);

        PopupControl popup = new PopupControl();
        ListView<String> listView = new ListView<>();
        listView.setItems(items);
        popup.getScene().setRoot(listView);

        comboBox.setOnAction(event -> {
            if (comboBox.getSelectionModel().getSelectedIndex() != -1) {
                listView.setPrefHeight(200); // Set the preferred height of the ListView
                listView.setMaxHeight(200); // Set the maximum height of the ListView
                listView.setMinHeight(200); // Set the minimum height of the ListView
                listView.setVisible(true); // Make the ListView visible
            } else {
                listView.setVisible(false); // Hide the ListView if no item is selected
            }
        });

        MainMenu.capacity = 2;
        comboBox.setValue("2");
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBox.setValue(newValue); // Update the selected item in the ComboBox
                MainMenu.capacity = Integer.parseInt(newValue);
            }
        });
//        ObservableList<String> items = FXCollections.observableArrayList();
//        ArrayList<String> list = new ArrayList<>();
//        controller.addUsers(list);
//        items.addAll(list);
//        users = controller.getLoggedInUserUsername();
//        CheckComboBox<String> control = new CheckComboBox<String>(items);


        pane.getChildren().add(comboBox);
        comboBox.setLayoutX(657);
        comboBox.setLayoutY(110);
        stage.getScene().setRoot(pane);
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    public void initialize() {
        enterLobbyButton.setDisable(true);
        lobbyCode.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null && newValue.matches("\\d+")) {
                enterLobbyButton.setDisable(!((boolean) Controller.send("is lobby exist", Integer.parseInt(newValue))));
            }
        }));
        listView.setVisible(false);
    }
    public void startNewGame(MouseEvent mouseEvent) throws Exception {
        //TODO: open new lobby
        if (updateTimeLine != null) updateTimeLine.stop();
        if (!turnsCount.getText().equals("") && turnsCount.getText().matches("\\d+")) {
            int lobbyCode = ((Double) Controller.send("create new lobby", MainMenu.capacity,
                    Integer.parseInt(turnsCount.getText()))).intValue();
            String usernameAdmin = (String) Controller.send("get my user");
//            System.out.println("Username admin : " + usernameAdmin);
            new Lobby(usernameAdmin, capacity, Integer.parseInt(turnsCount.getText()), lobbyCode);
            new LobbyMenu().start(stage);
        }
//        if (!width.getText().equals("") && !length.getText().equals("") && !users.equals("") && !turnsCount.getText().equals("")) {
//            controller.createNewMap(Integer.parseInt(width.getText()), Integer.parseInt(length.getText()));
//            controller.startNewGame(users, Integer.parseInt(turnsCount.getText()));
//            new SetHeadquarters().start(stage);
//            //TODO set headquarters?
//        }
//        new GameMenu().start(stage);
    }

    public void enterProfileMenu(MouseEvent mouseEvent) throws Exception {
        if (updateTimeLine != null) updateTimeLine.stop();
        Controller.send("change menu profile");
        new ProfileMenu().start(stage);
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        if (updateTimeLine != null) updateTimeLine.stop();
        Controller.send("logout");
//        controller.logout();
        new LoginMenu().start(stage);
    }

    public void openFriendshipMenu(ActionEvent actionEvent) throws Exception {
        if (updateTimeLine != null) updateTimeLine.stop();
        Controller.send("change menu friendship");
        new FriendShipMenu().start(stage);
    }

    public void enterLobby() throws Exception {
        if (updateTimeLine != null) updateTimeLine.stop();
        String usernameAdmin = (String) Controller.send("get lobby admin by code", Integer.parseInt(lobbyCode.getText()));
        int capacity = ((Double) Controller.send("get capacity by code", Integer.parseInt(lobbyCode.getText()))).intValue();
        int gameTurns = ((Double) Controller.send("get turns by code", Integer.parseInt(lobbyCode.getText()))).intValue();
        Controller.send("join lobby", Integer.parseInt(lobbyCode.getText()));
        Lobby lobby = new Lobby(usernameAdmin, capacity, gameTurns, Integer.parseInt(lobbyCode.getText()));
        new LobbyMenu().start(stage);
    }

    public void openLobbiesList(ActionEvent actionEvent) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> refresh()));
        this.updateTimeLine = timeline;
        timeline.setCycleCount(-1);
        timeline.play();
        openLobbieButton.setVisible(false);
        ArrayList<String> friends = (ArrayList<String>) Controller.send("get lobbies");
        ObservableList<String> items = FXCollections.observableArrayList(friends);
        listView.setItems(items);
        listView.setPrefHeight(Math.min(items.size() * 40, 100));
        listView.setVisible(true);
        String[] code = {null};
        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();

            cell.setOnMouseClicked(event -> {
                if (! cell.isEmpty()) {
                    String item = cell.getItem();
                    System.out.println("Item clicked: " + item);
                    String regex = ".+code : (?<code>\\d+).+";
                    Matcher matcher = Pattern.compile(regex).matcher(item);
                    matcher.matches();
                    code[0] = matcher.group("code");
                    lobbyCode.setText(code[0]);
                    try {
                        enterLobby();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            cell.textProperty().bind(cell.itemProperty());

            return cell ;
        });
    }

    public void refresh() {
        ArrayList<String> friends = (ArrayList<String>) Controller.send("get lobbies");
        ObservableList<String> items = FXCollections.observableArrayList(friends);
        listView.setItems(items);
        listView.setPrefHeight(Math.min(items.size() * 40, 100));
        listView.setVisible(true);
        String[] code = {null};
        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();

            cell.setOnMouseClicked(event -> {
                if (! cell.isEmpty()) {
                    String item = cell.getItem();
                    System.out.println("Item clicked: " + item);
                    String regex = ".+code : (?<code>\\d+).+";
                    Matcher matcher = Pattern.compile(regex).matcher(item);
                    matcher.matches();
                    code[0] = matcher.group("code");
                    lobbyCode.setText(code[0]);
                    try {
                        enterLobby();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            cell.textProperty().bind(cell.itemProperty());

            return cell ;
        });
    }

//    public void run() {
//        Matcher matcher;
//        String command;
//
//        while (true) {
//            command = scanner.nextLine();
//            if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.ENTER_PROFILE_MENU)) != null)
//                enterProfileMenu();
//            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.LOGOUT)) != null) {
//                if (logout()) return;
//            }
//            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.START_NEW_GAME)) != null)
//                startNewGame(matcher);
//            else if((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.SHOW_CURRENT_MENU)) != null)
//                System.out.println("Main menu");
//            else System.out.println("Invalid Command");
//        }
//    }
//
//
//    private void enterProfileMenu() {
//        System.out.println("entered profile menu successfully");
//        new ProfileMenu().run();
//        System.out.println("entered main menu successfully");
//    }
//
//    private boolean logout() {
//        System.out.println("Are you sure you want to logout?");
//        String answer = scanner.nextLine().trim();
//        if (answer.matches("yes")) {
//            System.out.println("user logged out successfully");
//            controller.logout();
//            return true;
//        } else System.out.println("logout failed");
//        return false;
//    }
//    private void startNewGame(Matcher matcher) {
//        if (Menu.checkBlankField(matcher.group("turnsNumber")) || Menu.checkBlankField(matcher.group("users"))) {
//            System.out.println("Start new game failed : blank field");
//            return;
//        }
//        int turnsNumber = Integer.parseInt(matcher.group("turnsNumber"));
//        String users = matcher.group("users").trim();
//        switch (controller.startNewGame(users, turnsNumber)) {
//            case SUCCESS:
//                System.out.println("Entered new game");
//                new GameMenu().run();
//                break;
//            case USERNAME_DOES_NOT_EXIST:
//                System.out.println("Start new game failed : Username does not exist");
//                break;
//            case INVALID_NUMBER:
//                System.out.println("Start new game failed : Invalid number");
//                break;
//        }
//    }
}
