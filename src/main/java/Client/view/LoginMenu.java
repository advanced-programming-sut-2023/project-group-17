package Client.view;

import Client.ClientMain;
import Server.controller.LoginMenuController;
import Utils.CheckValidation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginMenu extends Application {
    private static Pane pane;

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
    public Button loginButton;
    public ImageView CaptchaImageView;
    public TextField CaptchaTextField;
    public Button ResetCaptchaButton;
    public Button ConfirmCaptchaButton;
    private LoginMenuController controller;
    private ImageView imageView;
    private Captcha captcha;
    public LoginMenu() {
        controller = new LoginMenuController();
//        this.captcha = new Captcha();
//        this.CaptchaImageView = new ImageView(new Image(getClass().getResource
//                ("/Captcha/" + captcha.getAnswer() + ".png").toExternalForm(), 120, 80, false, false));
    }
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane pane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/LoginMenu.fxml"));
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/Backgrounds/LoginBackground.PNG").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));

        LoginMenu.pane = pane;
//        setCaptcha();
        stage.getScene().setRoot(pane);
        stage.setFullScreen(true);
        stage.show();
    }

//    private void setCaptcha() {
//        imageView = new ImageView(new Image(getClass().getResource
//                ("/Captcha/" + captcha.getAnswer() + ".png").toExternalForm(), 120, 80, false, false));
//        imageView.setX(755); imageView.setY(559);
//        pane.getChildren().add(imageView);
//    }

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
            if (forgotPasswordUsername.getText().equals("")) {
                forgotPasswordUsernameError.setText("");
                securityQuestion.setText("");
            }

            if (controller.getUserByUsername(forgotPasswordUsername.getText()) != null) {
                securityQuestion.setText(controller.getUserRecoveryQuestion(forgotPasswordUsername.getText()));
                forgotPasswordUsernameError.setText("");
            }else forgotPasswordUsernameError.setText("Username doesn't exist");

        });

        securityQuestionAnswer.textProperty().addListener((observable, oldText, newText) -> {
            if (controller.getUserByUsername(forgotPasswordUsername.getText()) != null) {
                newPassword.setDisable(!controller.getUserByUsername(
                        forgotPasswordUsername.getText()).getPasswordRecoveryAnswer().equals(securityQuestionAnswer.getText()));
            }
        });
        loginButton.setDisable(true);
    }

    public void forgotPassword() {
        if (forgotPassword.isSelected()) toggleForgotPasswordFields();
    }

    public void toggleForgotPasswordFields() {
        forgotPassword.setVisible(!forgotPassword.isVisible());
        securityQuestion.setVisible(!securityQuestion.isVisible());
        securityQuestionAnswer.setVisible(!securityQuestionAnswer.isVisible());
        forgotPasswordUsername.setVisible(!forgotPasswordUsername.isVisible());
        newPassword.setVisible(!newPassword.isVisible());
        newPasswordError.setVisible(!newPasswordError.isVisible());
        submitButton.setVisible(!submitButton.isVisible());
    }

    private void resetForgetPasswordFields() {
        securityQuestion.setText("");
        securityQuestionAnswer.setText("");
        forgotPasswordUsername.setText("");
        forgotPasswordUsernameError.setText("");
        newPassword.setText("");
        newPasswordError.setText("");
    }

    public void enterSignupMenu(MouseEvent mouseEvent) throws Exception{
        new SignupMenu().start(ClientMain.stage);
    }

    public void submit(MouseEvent mouseEvent) {

        Popup popup = getPopup();
        Label label = getLabel();
        popup.getContent().add(label);

        Timeline timeline = hidePopup(popup);

        switch (controller.setNewPassword(forgotPasswordUsername.getText(), newPassword.getText(), securityQuestionAnswer.getText())) {
            case SUCCESS:
                label.setText("Password Changed Successfully");
                popup.show(ClientMain.stage);
                timeline.play();
                break;
            case WRONG_PASSWORD_RECOVERY_ANSWER:
                label.setText("Recovery Answer is Wrong");
                popup.show(ClientMain.stage);
                timeline.play();
                break;
            case WEAK_PASSWORD:
                label.setText("Your password is Weak");
                popup.show(ClientMain.stage);
                timeline.play();
                break;
            case BLANK_FIELD:
                label.setText("Blank Field");
                popup.show(ClientMain.stage);
                timeline.play();
                break;
        }

        toggleForgotPasswordFields();
        resetForgetPasswordFields();
        forgotPassword.setSelected(false);
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

        Popup popup = getPopup();
        Label label = getLabel();
        popup.getContent().add(label);

        Timeline timeline = hidePopup(popup);

        Timeline mainMenuTimeLine = new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new MainMenu().start(ClientMain.stage);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));


        switch (controller.loginUser(username.getText(), password.getText(), true)) {
            case SUCCESS:
                label.setText("You have entered successfully!");
                popup.show(ClientMain.stage);
                timeline.play();
                mainMenuTimeLine.play();
                break;
            case BLANK_FIELD:
                label.setText("Blank Field!");
                popup.show(ClientMain.stage);
                timeline.play();
                break;
            case USERNAME_DOES_NOT_EXISTS:
                label.setText("Username doesn't exist!");
                popup.show(ClientMain.stage);
                timeline.play();
                break;
            case WRONG_PASSWORD:
                label.setText("Your password is wrong!");
                popup.show(ClientMain.stage);
                timeline.play();
                break;
        }
    }

    public Popup getPopup() {
        Popup popup = new Popup();
        popup.setAnchorX(580); popup.setAnchorY(300);
        popup.centerOnScreen();
        popup.setOpacity(0.5);
        return popup;
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

    public Timeline hidePopup(Popup popup) {
        return new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popup.hide();
            }
        }));
    }

//    public void resetCaptcha(MouseEvent mouseEvent) {
//        captcha.generateNewCaptcha();
//        setCaptcha();
//    }
//
//    public void checkCaptcha(MouseEvent mouseEvent) {
//        loginButton.setDisable(!CaptchaTextField.getText().equals(String.valueOf(captcha.getAnswer())));
//    }


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
