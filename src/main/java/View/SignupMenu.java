package View;

import Controller.SignupMenuController;
import Utils.CheckValidation;
import View.Enums.Commands.SignupMenuCommands;
import View.Enums.Messages.UtilsMessages;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;

public class SignupMenu extends Application {
    public static String securityQuestionSelected;
    public TextField slogan;
    public ChoiceBox randomSlogans;
    public TextField username;
    public PasswordField password;
    public TextField email;
    public ChoiceBox securityQuestionChoiceBox;
    public TextField answer;
    public Text usernameError;
    public Text passwordError;
    public TextField nickname;
    public CheckBox randomSlogan;
    public CheckBox showPassword;
    public CheckBox randomPassword;
    public CheckBox sloganYes;
    public TextField passwordVisible;
    public Text emailError;
    public Text nicknameError;
    public Text answerError;
    private Random random = new Random();
    private SignupMenuController controller;

    public SignupMenu() {
        controller = new SignupMenuController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/SignupMenu.fxml"));
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/Backgrounds/LoginBackground.PNG").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
        stage.setScene(new Scene(pane));
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    public void initialize () {

        password.textProperty().addListener((observable, oldText, newText)-> {
            passwordVisible.setText(password.getText());
            passwordError.setFill(Color.DARKRED);
            switch (Utils.CheckValidation.isPasswordStrong(password.getText())) {
                case SHORT_PASSWORD:
                    passwordError.setText("password must have at least 6 characters");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
                    passwordError.setText("password must have at least 1 lowercase character");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
                    passwordError.setText("password must have at least 1 uppercase character");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
                    passwordError.setText("password must have at least 1 integer");
                    break;
                case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
                    passwordError.setText("password must have at least 1 special character");
                    break;
                default:
                    passwordError.setText("");
                    break;
            }
        });

        username.textProperty().addListener((observable, oldText, newText)-> {
            usernameError.setFill(Color.DARKRED);
            switch (Utils.CheckValidation.isUsernameOk(username.getText())) {
                case INVALID_USERNAME:
                    usernameError.setText("Invalid username format");
                    break;
                case USERNAME_EXISTS:
                    usernameError.setText("Username exists");
                    break;
                default:
                    usernameError.setText("");
                    break;
            }
        });

        nicknameError.textProperty().addListener((observable, oldText, newText)-> {
            if (!nickname.getText().equals("")) nicknameError.setText("");
        });

        answer.textProperty().addListener((observable, oldText, newText)-> {
            if (!answer.getText().equals("")) answerError.setText("");
        });

        email.textProperty().addListener((observable, oldText, newText)-> {
            emailError.setFill(Color.DARKRED);
            if (Objects.requireNonNull(CheckValidation.isEmailOk(email.getText())) == UtilsMessages.INVALID_EMAIL) {
                emailError.setText("Invalid email format");
            } else {
                emailError.setText("");
            }
        });

    }

    public void showPassword(ActionEvent actionEvent) {
        if (showPassword.isSelected()) {
            passwordVisible.setVisible(true);
            password.setVisible(false);
        }
        else {
            passwordVisible.setVisible(false);
            password.setVisible(true);
        }
    }

    public void randonPassword(ActionEvent actionEvent) {
        if (randomPassword.isSelected()) {
            password.setText(controller.getRandomPassword());
        }
    }

    public void addSlogan(ActionEvent actionEvent) {
        if (sloganYes.isSelected()) {
            sloganYes.setVisible(false);
            slogan.setVisible(true);
            randomSlogans.setVisible(true);
            randomSlogan.setVisible(true);
        }
    }

    public void randomSlogan(ActionEvent actionEvent) {
        if (randomSlogan.isSelected()) slogan.setText(controller.getRandomSlogan());
    }

    public void signup(MouseEvent mouseEvent) {
        if (username.getText().equals("")) {
            usernameError.setFill(Color.DARKRED);
            usernameError.setText("enter username");
        }
        if (password.getText().equals("")) {
            passwordError.setFill(Color.DARKRED);
            passwordError.setText("enter password");
        }
        if (email.getText().equals("")) {
            emailError.setFill(Color.DARKRED);
            emailError.setText("enter email");
        }
        if (nickname.getText().equals("")) {
            nicknameError.setFill(Color.DARKRED);
            nicknameError.setText("enter nickname");
        }
        if (answer.getText().equals("")) {
            answerError.setFill(Color.DARKRED);
            answerError.setText("enter answer");
            return;
        }

        controller.createUser(username.getText(), password.getText(), password.getText(),
                email.getText(), nickname.getText(), slogan.getText());
    }

    public void chooseSlogan(MouseEvent mouseEvent) {
        randomSlogans.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                slogan.setText(controller.getAvailableSlogans(new_value.intValue()));
            }
        });
    }

    public void chooseSecurityQuestion(MouseEvent mouseEvent) {
        securityQuestionChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                securityQuestionSelected = controller.getSecurityQuestions(new_value.intValue());
            }
        });
    }


//    @Override
//    public void run() {
//        Matcher matcher;
//        String command;
//
//        while(true) {
//            command = scanner.nextLine();
//            if((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.CREATE_USER)) != null)
//                createUser(matcher);
//            else if((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.ENTER_LOGIN_MENU)) != null) {
//                enterLoginMenu();
//                return;
//            }
//            else if((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.SHOW_CURRENT_MENU)) != null)
//                System.out.println("Signup menu");
//            else System.out.println("Invalid Command");
//        }
//    }
//
//    public void mustPickQuestion() {
//        Matcher matcher;
//        String pickQuestionCommand;
//
//        while(true) {
//            pickQuestionCommand = scanner.nextLine();
//            if((matcher = SignupMenuCommands.getMatcher(pickQuestionCommand, SignupMenuCommands.PICK_QUESTION)) != null) {
//                pickQuestion(matcher);
//                break;
//            }
//            else System.out.println("signup failed : please pick question to complete your registration");
//        }
//    }
//
//    public void mustEnterCaptcha(){
//        Matcher matcher;
//        String input;
//        while(true) {
//            controller.GenerateCaptcha();
//            input = scanner.nextLine();
//            if((matcher = SignupMenuCommands.getMatcher(input, SignupMenuCommands.PICK_CAPTCHA)) != null) {
//                if(controller.validateCaptcha(Integer.parseInt(input))) {
//                    System.out.println("user created successfully");
//                    break;
//                }
//            }
//            else System.out.println("signup failed : please enter the captcha again");
//        }
//    }
//
//    private void enterLoginMenu() {
//        System.out.println("entered login menu successfully");
//    }
//
//    private void createUser(Matcher matcher) {
//        if(checkBlankField(matcher.group("username")) || checkBlankField(matcher.group("password")) ||
//            (!checkBlankField(matcher.group("confirmationDash")) && checkBlankField(matcher.group("confirmation"))) ||
//                checkBlankField(matcher.group("email")) ||
//                checkBlankField(matcher.group("nickname")) ||
//                (!checkBlankField(matcher.group("sloganDash")) && checkBlankField(matcher.group("slogan")))) {
//            System.out.println("signup failed : blank field");
//            return;
//        }
//
//        String username = handleDoubleQuote(matcher.group("username"));
//        String password = handleDoubleQuote(matcher.group("password"));
//        String confirmationPassword = handleDoubleQuote(matcher.group("confirmation"));
//        String email = handleDoubleQuote(matcher.group("email"));
//        String nickname = handleDoubleQuote(matcher.group("nickname"));
//        String slogan = handleDoubleQuote(matcher.group("slogan"));
//
//        createUserErrors(username, password, confirmationPassword, email, nickname, slogan);
//
//    }
//
//    public void createUserErrors(String username, String password, String confirmationPassword, String email, String nickname, String slogan) {
//        switch (controller.createUser(username, password, confirmationPassword, email, nickname, slogan)) {
//            case SUCCESS:
//                System.out.print(controller.getSecurityQuestions());
//                mustPickQuestion();
//                break;
//            case INVALID_USERNAME:
//                System.out.println("signup failed : invalid username format");
//                break;
//            case USERNAME_EXISTS:
//                System.out.print("signup failed : user " + username + " already exists" + '\n' +
//                            "suggested username: " + username + random.nextInt(10) + random.nextInt(10) + '\n');
//                break;
//            case RANDOM_SLOGAN:
//                System.out.println(("your slogan is \"" + (slogan = controller.getRandomSlogan()) + "\""));
//                createUserErrors(username, password, confirmationPassword, email, nickname, slogan);
//                break;
//            case RANDOM_PASSWORD:
//                System.out.print("your random password is : " + (password = controller.getRandomPassword()) + '\n' +
//                                 "please re-enter your password here:" + '\n');
//                confirmationPassword = scanner.nextLine();
//                if(controller.isRandomPasswordsMatches(password, confirmationPassword))
//                    createUserErrors(username, password, confirmationPassword, email, nickname, slogan);
//                else System.out.println("signup failed : passwords do not match");
//                break;
//            case SHORT_PASSWORD:
//                System.out.println("signup failed : password must have at least 6 characters");
//                break;
//            case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
//                System.out.println("signup failed : password must have at least 1 lowercase character");
//                break;
//            case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
//                System.out.println("signup failed : password must have at least 1 uppercase character");
//                break;
//            case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
//                System.out.println("signup failed : password must have at least 1 integer");
//                break;
//            case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
//                System.out.println("signup failed : password must have at least 1 special character");
//                break;
//            case PASSWORD_DOES_NOT_MATCH:
//                System.out.println("signup failed : passwords do not match");
//                break;
//            case EMAIL_EXISTS:
//                System.out.println("signup failed : email " + email + " already exists");
//                break;
//            case INVALID_EMAIL:
//                System.out.println("signup failed : invalid email format");
//                break;
//        }
//    }
//
//    private void pickQuestion(Matcher matcher) {
//        if(checkBlankField(matcher.group("questionNumber")) ||
//           checkBlankField(matcher.group("answer")) ||
//           checkBlankField(matcher.group("confirmation"))) {
//            System.out.println("pick question failed : blank field");
//            return;
//        }
//
//        Integer questionNumber = Integer.parseInt(matcher.group("questionNumber"));
//        String answer = handleDoubleQuote(matcher.group("answer"));
//        String confirmationAnswer = handleDoubleQuote(matcher.group("confirmation"));
//
//        switch (controller.pickQuestion(questionNumber, answer, confirmationAnswer)) {
//            case SUCCESS:
//                System.out.print("question number " + questionNumber + " picked successfully" + '\n' +
//                                "please enter the captcha to complete your registration\n");
//                mustEnterCaptcha();
//                break;
//            case WRONG_NUMBER:
//                System.out.println("pick question failed : invalid question number");
//                mustPickQuestion();
//                break;
//            case ANSWER_DOES_NOT_MATCH:
//                System.out.println("pick question failed : answers do not match");
//                mustPickQuestion();
//                break;
//        }
//
//    }

}
