package Client.view;

import Server.controller.MainMenuController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import static Client.ClientMain.stage;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;


public class MainMenu extends Application {

    public TextField turnsCount;
    public TextField width;
    public TextField length;
    public Button startNewGameButton;
    private MainMenuController controller = new MainMenuController();
    public static String users;

//    public MainMenu() {
//        this.controller = new MainMenuController();
//    }

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

        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                startNewGameButton.setDisable(true);
            if (newValue != null) {
                comboBox.setValue(newValue); // Update the selected item in the ComboBox
                startNewGameButton.setDisable(false);
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
        startNewGameButton.setDisable(true);
    }

    public void startNewGame(MouseEvent mouseEvent) throws Exception {
        //TODO: open new lobby
//        if (!width.getText().equals("") && !length.getText().equals("") && !users.equals("") && !turnsCount.getText().equals("")) {
//            controller.createNewMap(Integer.parseInt(width.getText()), Integer.parseInt(length.getText()));
//            controller.startNewGame(users, Integer.parseInt(turnsCount.getText()));
//            new SetHeadquarters().start(stage);
//            //TODO set headquarters?
//        }
//        new GameMenu().start(stage);
    }

    public void enterProfileMenu(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(stage);
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        controller.logout();
        new LoginMenu().start(stage);
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
