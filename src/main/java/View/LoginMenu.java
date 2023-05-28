package View;

import Controller.LoginMenuController;
import Utils.CheckValidation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginMenu extends Application {

    public TextField username;
    public PasswordField password;
    public CheckBox forgotPassword;
    public Text securityQuestion;
    public TextField securityQuestionAnswer;
    public PasswordField newPassword;
    public Text newPasswordError;
    public Text usernameError;
    public Text passwordError;
    public Button submitButton;
    public TextField forgotPasswordUsername;
    public Text forgotPasswordUsernameError;
    private LoginMenuController controller;
    public LoginMenu() {
        controller = new LoginMenuController();
    }
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane pane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/LoginMenu.fxml"));
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/Backgrounds/LoginBackground.PNG").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    public void initialize() {
        newPassword.textProperty().addListener((observable, oldText, newText) -> {
            newPasswordError.setFill(Color.DARKRED);
            switch (CheckValidation.isPasswordStrong(newPassword.getText())) {
                case SHORT_PASSWORD:
                    newPasswordError.setText("password must have at least 6 characters");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
                    newPasswordError.setText("password must have at least 1 lowercase character");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
                    newPasswordError.setText("password must have at least 1 uppercase character");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
                    newPasswordError.setText("password must have at least 1 integer");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
                    newPasswordError.setText("password must have at least 1 special character");
                    break;
                default:
                    newPasswordError.setText("");
                    break;
            }
        });

        username.textProperty().addListener((observable, oldText, newText) -> {
            usernameError.setFill(Color.DARKRED);
            if (!username.getText().equals("")) usernameError.setText("");
        } );

        password.textProperty().addListener((observable, oldText, newText) -> {
            passwordError.setFill(Color.DARKRED);
            if (!password.getText().equals("")) passwordError.setText("");
        });

        forgotPasswordUsername.textProperty().addListener((observableValue, oldText, newText) -> {
            forgotPasswordUsernameError.setFill(Color.DARKRED);
            if (!forgotPasswordUsername.getText().equals("")) forgotPasswordUsernameError.setText("");

            System.out.println(forgotPasswordUsername.getText());
            System.out.println(controller.getUserByUsername(forgotPasswordUsername.getText()));
            System.out.println(controller.getUserRecoveryQuestion(forgotPasswordUsername.getText()));
            if (controller.getUserByUsername(forgotPasswordUsername.getText()) != null) {
                System.out.println("goh");
                securityQuestion.setText(controller.getUserRecoveryQuestion(forgotPasswordUsername.getText()));
            }

        });


    }

    public void forgotPassword() {
        if (forgotPassword.isSelected()) {
            forgotPassword.setVisible(false);
            securityQuestion.setVisible(true);
            securityQuestionAnswer.setVisible(true);
            forgotPasswordUsername.setVisible(true);
            newPassword.setVisible(true);
            newPasswordError.setVisible(true);
            submitButton.setVisible(true);
        }
    }


    public void enterSignupMenu(MouseEvent mouseEvent) throws Exception{
        new SignupMenu().start(Main.stage);
    }

    public void submit(MouseEvent mouseEvent) {
        if (forgotPasswordUsername.getText().equals("")) {
            forgotPasswordUsernameError.setFill(Color.DARKRED);
            forgotPasswordUsernameError.setText("Username doesn't exist");
        }
    }

    public void login(MouseEvent mouseEvent) throws Exception{
        if (username.getText().equals("")) {
            usernameError.setFill(Color.DARKRED);
            usernameError.setText("Username Error");
        }

        if (password.getText().equals("")) {
            passwordError.setFill(Color.DARKRED);
            passwordError.setText("Password Error");
        }

        Popup popup = new Popup();
        popup.setAnchorX(580); popup.setAnchorY(300);
        popup.centerOnScreen();
        popup.setOpacity(0.5);
        Label label = new Label();
        label.setTextFill(Color.WHITE);
        label.setMinWidth(200); label.setMinHeight(60);
        label.setStyle("-fx-background-color: black;");
        label.setAlignment(Pos.CENTER);
        popup.getContent().add(label);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popup.hide();
            }
        }));

        Timeline mainMenuTimeLine = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new MainMenu().start(Main.stage);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));


        switch (controller.loginUser(username.getText(), password.getText(), true)) {
            case SUCCESS:
                label.setText("You have entered successfully!");
                popup.show(Main.stage);
                timeline.play();
                mainMenuTimeLine.play();
                break;
            case USERNAME_DOES_NOT_EXISTS:
                label.setText("Username doesn't exist!");
                popup.show(Main.stage);
                timeline.play();
                break;
            case WRONG_PASSWORD:
                label.setText("Your password is wrong!");
                popup.show(Main.stage);
                timeline.play();
                break;
        }
    }


//    private final LoginMenuController controller;
//
//    public LoginMenu(){
//         this.controller = new LoginMenuController();
//    }
//
//    public void run() {
//        String command;
//        Matcher matcher = null;
//
//        if (controller.checkStayLoggedIn()) enterMainMenu();
//
//        while (true){
//            command = scanner.nextLine();
//            if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.USER_LOGIN)) != null)
//                loginUser(matcher);
//            else if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGET_PASSWORD)) != null)
//                forgetPassword(matcher);
////            else if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.ENTER_SIGNUP_MENU)) != null)
////                enterSignupMenu();
//            else if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.SHOW_CURRENT_MENU)) != null)
//                System.out.println("Login menu");
//            else System.out.println("Invalid Command");
//        }
//    }
//
//    public void mustEnterCaptcha(){
//        System.out.println("please enter the captcha to login");
//        Matcher matcher;
//        String input;
//        while(true) {
//            controller.GenerateCaptcha();
//            input = scanner.nextLine();
//            if((matcher = LoginMenuCommands.getMatcher(input, LoginMenuCommands.PICK_CAPTCHA)) != null) {
//                if(controller.validateCaptcha(Integer.parseInt(input))) {
//                    System.out.println("user logged in successfully");
//                    break;
//                }
//            }
//            else System.out.println("login failed : please enter the captcha again");
//        }
//    }
//
//    private void loginUser(Matcher matcher) {
//        if(checkBlankField(matcher.group("username")) || checkBlankField(matcher.group("password"))) {
//            System.out.println("login failed : blank field");
//            return;
//        }
//
//        String username = handleDoubleQuote(matcher.group("username"));
//        String password = handleDoubleQuote(matcher.group("password"));
//        boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;
//
//        switch (controller.loginUser(username , password , stayLoggedIn)){
//            case SUCCESS:
//                mustEnterCaptcha();
//                enterMainMenu();
//                break;
//            case WRONG_PASSWORD:
//                System.out.println("login failed : password is not correct");
//                wrongPassword(password, username);
//                break;
//            case USERNAME_DOES_NOT_EXISTS:
//                System.out.println("login failed : username does not exist");
//                break;
//        }
//    }
//
//    private void wrongPassword(String password, String username) {
//        int attempts = 1;
//        while (true) {
//            if(!controller.checkPassword(username, password)) {
//                password = scanner.nextLine();
//                if(LoginMenuCommands.getMatcher(password, LoginMenuCommands.FORGET_PASSWORD) != null) {
//                    forgetPassword(LoginMenuCommands.getMatcher(password, LoginMenuCommands.FORGET_PASSWORD));
//                    return;
//                }
//                long time = currentTimeMillis();
//
//                while (currentTimeMillis() - time < attempts * 5000L) {
//                    long timeLeft = attempts * 5000L - currentTimeMillis() + time;
//                    System.out.println("please wait " + timeLeft/1000 + " more seconds.");
//                    password = scanner.nextLine();
//                    if(LoginMenuCommands.getMatcher(password, LoginMenuCommands.FORGET_PASSWORD) != null) {
//                        forgetPassword(LoginMenuCommands.getMatcher(password, LoginMenuCommands.FORGET_PASSWORD));
//                        return;
//                    }
//                }
//                attempts++;
//            }
//            else {
//                mustEnterCaptcha();
//                controller.setLoggedInUserInController(username);
//                enterMainMenu();
//                break;
//            }
//        }
//    }
//
//    private void forgetPassword(Matcher matcher) {
//        if(checkBlankField(matcher.group("username"))) {
//            System.out.println("password change failed : blank field");
//            return;
//        }
//
//        String username = handleDoubleQuote(matcher.group("username"));
//        String answer;
//        String newPassword;
//
//        switch (controller.forgetPassword(username)) {
//                case USERNAME_DOES_NOT_EXISTS:
//                    System.out.println("password change failed : username does not exist");
//                    break;
//                case ANSWER_RECOVERY_QUESTION:
//                    System.out.println("please answer this question : " + controller.getUserRecoveryQuestion(username));
//                    answer = scanner.nextLine();
//                    if(controller.isRecoveryQuestionAnswerCorrect(username, answer)) {
//                        System.out.println("please enter your new password:");
//                        newPassword = scanner.nextLine();
//                        forgetPasswordError(username, newPassword);
//                    } else System.out.println("password change failed : your answer is not correct");
//                    break;
//        }
//    }
//
//    private void forgetPasswordError(String username, String newPassword) {
//        switch (controller.setNewPassword(username, newPassword)) {
//            case WRONG_PASSWORD_RECOVERY_ANSWER:
//                System.out.println("password change failed : your answer is not correct");
//                break;
//            case SHORT_PASSWORD:
//                System.out.println("password change failed : your password must contain mare than five characters");
//                break;
//            case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
//                System.out.println("password change failed : your password must contain at least one integer");
//                break;
//            case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
//                System.out.println("password change failed : your password must contain at least one uppercase character");
//                break;
//            case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
//                System.out.println("password change failed : your password must contain at least one lowercase character");
//                break;
//            case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
//                System.out.println("password change failed : your password must contain at least" +
//                                   " one character other than letters and numbers");
//                break;
//            case SUCCESS:
//                System.out.println("password changed successfully");
//                break;
//        }
//    }
//
////    private void enterSignupMenu() {
////        System.out.println("entered signup menu successfully");
////        new SignupMenu().start();
////    }
//
//    private void enterMainMenu() {
////        System.out.println("entered main menu successfully");
//        new MainMenu().run();
//    }

}
