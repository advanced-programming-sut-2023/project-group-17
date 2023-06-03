package View;

import Controller.ProfileMenuController;
import Utils.CheckValidation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;

public class ProfileMenu extends Application {

    private final ProfileMenuController controller;
    private static Pane mainPane;
    public TextField usernameText;
    public TextField nicknameText;
    public TextField emailText;
    public ImageView avatar;
    public Button changeUsername;
    public Button changePassword;
    public Button changeNickname;
    public Button changeEmail;
    public TextField sloganText;
    public Button changeSlogan;
    public Button deleteSlogan;
    public static Stage stage;
    public Button mainMenu;
    public Text usernameError;
    public Text nicknameError;
    public Text emailError;
    public ChoiceBox randomSlogans;
    public Button randomSlogan;
    public Button scoreboard;
    public Button avatarMenu;

    public static void main(String[] args) {
        launch(args);
    }

    public ProfileMenu() {
        controller = new ProfileMenuController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        Pane pane = FXMLLoader.load(ProfileMenu.class.getResource("/fxml/ProfileMenu.fxml"));
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/Backgrounds/profileMenu.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
        ImageView imageView = new ImageView(new Image(controller.getAvatarPath() , 160 ,160, false, false));
        imageView.setX(0); imageView.setY(0);
        pane.getChildren().add(imageView);
        mainPane = pane;
        stage.getScene().setRoot(pane);
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    public void initialize() throws IOException {
//        usernameText.setText("shamim");
        usernameText.setText(controller.getUsername());
        usernameText.setDisable(true);
        usernameError.setFill(Color.RED);
        makeListenerForUsernameTextField();
//        nicknameText.setText("ashkan");
        nicknameError.setFill(Color.RED);
        nicknameText.setText(controller.getNickname());
        makeListenerForNicknameTextField();
        nicknameText.setDisable(true);
//        emailText.setText("a.kasrahmi@gmail.com");
        emailError.setFill(Color.RED);
        emailText.setText(controller.getEmail());
        makeListenerForEmailTextField();
        emailText.setDisable(true);
//        sloganText.setText("Slogan is empty");
        if (controller.getSlogan().equals("")) sloganText.setText("Slogan is empty");
        else sloganText.setText(controller.getSlogan());
        sloganText.setDisable(true);
        makeListenerForDisablingRemoveButton();
        if (sloganText.getText().equals("Slogan is empty")) deleteSlogan.setDisable(true);
        avatar = new ImageView(new Image(controller.getAvatarPath(), 160 ,160, false, false));

        makeListenerForRandomSlogans();
//        changeDataPane = FXMLLoader.load(ProfileMenu.class.getResource("/fxml/profileMenu/dataPane.fxml"));
//        headerDataPane.setText(""); textDataPane.setText(""); errorDataPane.setText("");
    }

    private void makeListenerForRandomSlogans() {
        randomSlogans.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                sloganText.setText(controller.getAvailableSlogans(new_value.intValue()));
            }
        });
    }

    private void makeListenerForEmailTextField() {
        emailText.textProperty().addListener((observable, oldValue, newValue) -> {
            switch (controller.changeEmailError(newValue)) {
                case SUCCESS:
                    emailError.setText("");
                    changeEmail.setDisable(false);
                    break;
                case INVALID_EMAIL:
                    emailError.setText("email change failed : invalid email format");
                    changeEmail.setDisable(true);
                    break;
                case EMAIL_EXISTS:
                    emailError.setText("email change failed : email already exists");
                    changeEmail.setDisable(true);
                    break;
                case SAME_EMAIL:
                    emailError.setText("email change failed : your new email cannot be the same as your current email");
                    changeEmail.setDisable(true);
                    break;
            }
        });
    }

    private void makeListenerForNicknameTextField() {
        nicknameError.textProperty().addListener((observableValue, oldValue, newValue) -> {
            switch (controller.changeNicknameErrors(newValue)) {
                case SUCCESS:
                    nicknameError.setText("");
                    changeNickname.setDisable(false);
                    break;
                case SAME_NICKNAME:
                    nicknameError.setText("nickname change failed : your new nickname cannot be the same as your current nickname");
                    changeNickname.setDisable(true);
                    break;
            }
        });
    }

    private void makeListenerForUsernameTextField() {
        usernameText.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            switch (controller.changeUsernameErrors(newValue)) {
                case SUCCESS:
                    usernameError.setText("");
                    changeUsername.setDisable(false);
                    break;
                case SAME_USERNAME:
                    usernameError.setText("username change failed : your new username cannot be the same as your current username");
                    changeUsername.setDisable(true);
                    break;
                case USERNAME_EXISTS:
                    usernameError.setText("username change failed : username " + newValue + " already exists");
                    changeUsername.setDisable(true);
                    break;
                case INVALID_USERNAME:
                    usernameError.setText("username change failed : invalid username format");
                    changeUsername.setDisable(true);
                    break;

            }
        }));
    }

//    @Override
//    public void run() {
//        Matcher matcher;
//        String command;
//
//        while(true) {
//            command = scanner.nextLine();
//            if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_USERNAME)) != null)
//                changeUsername(matcher);
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_NICKNAME)) != null)
//                changeNickname(matcher);
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_PASSWORD)) != null)
//                changePassword(matcher);
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_EMAIL)) != null)
//                changeEmail(matcher);
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_SLOGAN)) != null)
//                changeSlogan(matcher);
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.REMOVE_SLOGAN)) != null)
//                removeSlogan();
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_HIGH_SCORE)) != null)
//                displayHighScore();
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_PROFILE)) != null)
//                displayProfile();
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_SLOGAN)) != null)
//                displaySlogan();
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_RANK)) != null)
//                displayRank();
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.BACK)) != null)
//                return;
//            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.SHOW_CURRENT_MENU)) != null)
//                System.out.println("Profile menu");
//            else System.out.println("Invalid Command");
//
//        }
//
//    }
//
//    private void changeUsername(Matcher matcher) {
//        if(checkBlankField(matcher.group("username")))
//            System.out.println("username change failed : blank field");
//
//        String username = handleDoubleQuote(matcher.group("username"));
//
//        switch (controller.changeUsername(username)) {
//            case SUCCESS:
//                System.out.println("username changed successfully");
//                break;
//            case INVALID_USERNAME:
//                System.out.println("username change failed : invalid username format");
//                break;
//            case USERNAME_EXISTS:
//                System.out.println("username change failed : username " + username + " already exists");
//                break;
//            case SAME_USERNAME:
//                System.out.println("username change failed : your new username cannot be the same as your current username");
//                break;
//        }
//    }
//
//    private void changeNickname(Matcher matcher) {
//        if(checkBlankField(matcher.group("nickname")))
//            System.out.println("profile change failed : blank field");
//
//        String nickname = handleDoubleQuote(matcher.group("nickname"));
//
//        switch (controller.changeNickname(nickname)) {
//            case SUCCESS:
//                System.out.println("nickname changed successfully");
//                break;
//            case SAME_NICKNAME:
//                System.out.println("nickname change failed : your new nickname cannot be the same as your current nickname");
//                break;
//        }
//    }
//
//    private void changeEmail(Matcher matcher) {
//        if(checkBlankField(matcher.group("email")))
//            System.out.println("email change failed : blank field");
//
//        String email = handleDoubleQuote(matcher.group("email"));
//
//        switch (controller.changeEmail(email)) {
//            case SUCCESS:
//                System.out.println("email changed successfully");
//                break;
//            case INVALID_EMAIL:
//                System.out.println("email change failed : invalid email format");
//                break;
//            case EMAIL_EXISTS:
//                System.out.println("email change failed : email already exists");
//                break;
//            case SAME_EMAIL:
//                System.out.println("email change failed : your new email cannot be the same as your current email");
//                break;
//        }
//
//    }
//
//    private void changeSlogan(Matcher matcher) {
//        if(checkBlankField(matcher.group("slogan")))
//            System.out.println("slogan change failed : blank field");
//
//        String slogan = handleDoubleQuote(matcher.group("slogan"));
//        changeSloganErrors(slogan);
////
////        switch (controller.changeSlogan(slogan)) {
////            case SUCCESS:
////                System.out.println("slogan changed successfully");
////                break;
////            case RANDOM_SLOGAN:
////                System.out.println("your slogan is \"" + (slogan = controller.getRandomSlogan()) + "\"");
////                controller.changeSlogan(slogan);
//////            case SAME_SLOGAN:
//////                System.out.println("slogan change failed : your new slogan cannot be the same as your current slogan");
//////                break;
////        }
//    }
//
//    private void changeSloganErrors(String slogan) {
//        switch (controller.changeSlogan(slogan)) {
//            case SUCCESS:
//                System.out.println("slogan changed successfully");
//                break;
//            case RANDOM_SLOGAN:
//                System.out.println("your slogan is \"" + (slogan = controller.getRandomSlogan()) + "\"");
//                changeSloganErrors(slogan);
//                break;
//        }
//    }
//
//    private void removeSlogan() {
//        switch (controller.removeSlogan()) {
//            case SUCCESS:
//                System.out.println("slogan removed successfully");
//                break;
//            case EMPTY_SLOGAN:
//                System.out.println("slogan remove failed : there is no slogan to remove");
//                break;
//        }
//    }
//
//    private void changePassword(Matcher matcher) {
//        if (checkBlankField(matcher.group("oldPass")) || checkBlankField(matcher.group("newPass")))
//            System.out.println("profile change failed : blank field");
//
//        String oldPassword = handleDoubleQuote(matcher.group("oldPass"));
//        String newPassword = handleDoubleQuote(matcher.group("newPass"));
//        String confirmationPassword;
//
//        switch (controller.changePassword(oldPassword, newPassword)) {
//            case SUCCESS:
//                System.out.println("password changed successfully");
//                break;
//            case INCORRECT_PASSWORD:
//                System.out.println("password change failed : current password is incorrect");
//                break;
//            case SAME_PASSWORD:
//                System.out.println("password change failed : your new password cannot be the same as your current password");
//                break;
//            case RANDOM_PASSWORD:
//                System.out.print("your random password is : " + (newPassword = controller.getRandomPassword()) + '\n' +
//                                 "please re-enter your password here : \n");
//                confirmationPassword = scanner.nextLine();
//                changePasswordError(newPassword, confirmationPassword);
//                break;
//            case ENTER_PASSWORD_AGAIN:
//                System.out.println("please enter your new password again : ");
//                confirmationPassword = scanner.nextLine();
//                changePasswordError(newPassword, confirmationPassword);
//                break;
//        }
//    }
//
//        private void changePasswordError(String newPassword, String confirmationPassword) {
//            switch (controller.setNewPassword(newPassword, confirmationPassword)) {
//                case SUCCESS:
//                    System.out.println("password changed successfully");
//                    break;
//                case SHORT_PASSWORD:
//                    System.out.println("password change failed : password must have at least 6 characters");
//                    break;
//                case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
//                    System.out.println("password change failed : password must have at least 1 lowercase character");
//                    break;
//                case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
//                    System.out.println("password change failed : password must have at least 1 uppercase character");
//                    break;
//                case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
//                    System.out.println("password change failed : password must have at least 1 integer");
//                    break;
//                case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
//                    System.out.println("password change failed : password must have at least 1 special character");
//                    break;
//                case PASSWORDS_DO_NOT_MATCH:
//                    System.out.println("password change failed : passwords do not match");
//                    break;
//            }
//        }
//
//    private void displaySlogan() {
//        System.out.println(controller.displaySlogan());
//    }
//
//    private void displayHighScore() {
//        System.out.println(controller.displayHighScore());
//    }
//
//    private void displayRank() {
//        System.out.println(controller.displayRank());
//    }
//
//    private void displayProfile() {
//        System.out.print(controller.displayProfile());
//    }

    public void changeUsername(ActionEvent actionEvent) {
        if (usernameText.isDisable()) {
            changeUsername.setText("Confirm");
            usernameText.setDisable(false);
        } else {
            changeUsername.setText("Change Username");
            controller.changeUsername(emailText.getText());
            usernameText.setDisable(true);
        }
    }

    public void changePassword(ActionEvent actionEvent) {
        stage.setFullScreen(false);
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create the labels and text fields
        Label oldPasswordLabel = new Label("Old Password:");
        TextField oldPasswordTextField = new TextField();

        Label newPasswordLabel = new Label("New Password:");
        TextField newPasswordTextField = new TextField();

        // Add the labels and text fields to the grid pane
        gridPane.add(oldPasswordLabel, 0, 0);
        gridPane.add(oldPasswordTextField, 1, 0);

        gridPane.add(newPasswordLabel, 0, 1);
        gridPane.add(newPasswordTextField, 1, 1);

        Text errorText = new Text("");
        errorText.setFill(Color.RED);

        Text controllerError = new Text("");
        controllerError.setFill(Color.RED);

        makeListenerForNewPassword(newPasswordTextField, errorText, controllerError);

        // Create the submit and cancel buttons
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Back");

        // Add the buttons to an HBox
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.getChildren().addAll(submitButton, cancelButton);

        // Add the grid pane and button box to a VBox
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(gridPane, errorText, controllerError, buttonBox);
//        vbox.setPrefWidth(600); vbox.setPrefHeight(400);


        // Create a new scene with the VBox as the root node
        Scene scene = new Scene(vbox, 450, 300);

        // Create a new stage for the dialog
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(scene);
        dialogStage.setTitle("Change Password");
        dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                dialogStage.close();
                stage.setFullScreen(true);
            }
        });

        // Show the dialog
        dialogStage.show();

        // Set the action for the submit button
        submitButton.setOnAction(event -> {
            // Get the values from the text fields
            if (!newPasswordTextField.getText().equals( "" ) && errorText.getText().equals("")) {
                switch (controller.changePassword(oldPasswordTextField.getText(), newPasswordTextField.getText())) {
                    case INCORRECT_PASSWORD:
                        controllerError.setText("password change failed : current password is incorrect");
                        new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                controllerError.setText("");
                            }
                        })).play();
                        break;
                    case SAME_PASSWORD:
                        controllerError.setText("password change failed : your new password cannot be the same as your current password");
                        new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                controllerError.setText("");
                            }
                        })).play();
                        break;
                    case SUCCESS:
                        Popup popup = new Popup();
                        popup.setOpacity(0.5);
                        popup.setAnchorX(580); popup.setAnchorY(300);
                        Label label = new Label("You have successfully changed your password");
                        label.setTextFill(Color.WHITE);
                        label.setMinWidth(200);
                        label.setMinHeight(100);
                        label.setStyle("-fx-background-color: black;");
                        popup.getContent().add(label);
                        popup.show(dialogStage);
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                popup.hide();
                            }
                        }));
                        timeline.play();
                        break;
                }
            }
//
//             Close the dialog
//            dialogStage.close();
        });

        // Set the action for the cancel button
        cancelButton.setOnAction(event -> {
            // Close the dialog
            dialogStage.close();
            stage.setFullScreen(true);
        });
    }

    private void makeListenerForNewPassword(TextField newPasswordTextField, Text errorText, Text controllerError) {
        controllerError.setText("");
        newPasswordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            switch (CheckValidation.isPasswordStrong(newValue)) {
                case PASSWORD_IS_STRONG:
                    errorText.setText("");
                    break;
                case SHORT_PASSWORD:
                    errorText.setText("password change failed : password must have at least 6 characters");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
                    errorText.setText("password change failed : password must have at least 1 lowercase character");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
                    errorText.setText("password change failed : password must have at least 1 uppercase character");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
                    errorText.setText("password change failed : password must have at least 1 integer");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
                    errorText.setText("password change failed : password must have at least 1 special character");
                    break;
            }
        });
    }

    public void changeSlogan(ActionEvent actionEvent) {
        if (sloganText.isDisable()) {
            if (sloganText.getText().equals("Slogan is empty")) sloganText.setText("");
            sloganText.setDisable(false);
            changeSlogan.setText("Confirm");
            randomSlogans.setVisible(true);
            randomSlogan.setVisible(true);
        } else {
            changeSlogan.setText("Change");
            controller.changeSlogan(sloganText.getText());
            if (sloganText.getText().equals("")) sloganText.setText("Slogan is empty");
            sloganText.setDisable(true);
            randomSlogans.setVisible(false);
            randomSlogan.setVisible(false);
        }
    }

    public void deleteSlogan(ActionEvent actionEvent) {
        sloganText.setText("");
    }

    public void backToMainMenu(ActionEvent actionEvent) throws Exception {
        new MainMenu().start(Main.stage);
    }

    private void makeListenerForDisablingRemoveButton() {
        sloganText.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            deleteSlogan.setDisable(sloganText.getText().equals("Slogan is empty"));
        }));
    }

    public void changeNickname(ActionEvent actionEvent) {
        if (nicknameText.isDisable()) {
            changeNickname.setText("Confirm");
            nicknameText.setDisable(false);
        } else {
            changeNickname.setText("Change Nickname");
            controller.changeNickname(nicknameText.getText());
            nicknameText.setDisable(true);
        }
    }

    public void changeEmail(ActionEvent actionEvent) {
        if (emailText.isDisable()) {
            changeEmail.setText("Confirm");
            emailText.setDisable(false);
        } else {
            changeEmail.setText("Change Email");
            controller.changeEmail(emailText.getText());
            emailText.setDisable(true);
        }
    }

    public void chooseSlogan(MouseEvent mouseEvent) {

    }

    public void randomSlogan(ActionEvent actionEvent) {
        sloganText.setText(controller.getRandomSlogan());
    }

    public void openScoreboard(ActionEvent actionEvent) throws Exception {
        new ScoreBoardMenu().start(new Stage());
    }

    public void openAvatarMenu(ActionEvent actionEvent) throws Exception {
        new AvatarMenu().start(Main.stage);
    }
}
