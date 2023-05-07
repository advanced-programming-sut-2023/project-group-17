package View;

import Controller.LoginMenuController;
import View.Enums.Commands.LoginMenuCommands;

import java.util.regex.Matcher;

import static java.lang.System.currentTimeMillis;

public class LoginMenu extends Menu {
    private final LoginMenuController controller;

    public LoginMenu(){
         this.controller = new LoginMenuController();
    }

    public void run() {
        String command;
        Matcher matcher = null;

        if (controller.checkStayLoggedIn()) enterMainMenu();

        while (true){
            command = scanner.nextLine();
            if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.USER_LOGIN)) != null)
                loginUser(matcher);
            else if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGET_PASSWORD)) != null)
                forgetPassword(matcher);
            else if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.ENTER_SIGNUP_MENU)) != null)
                enterSignupMenu();
            else System.out.println("Invalid Command");
        }
    }

    public void mustEnterCaptcha(){
        System.out.println("please enter the captcha to login");
        Matcher matcher;
        String input;
        while(true) {
            controller.GenerateCaptcha();
            input = scanner.nextLine();
            if((matcher = LoginMenuCommands.getMatcher(input, LoginMenuCommands.PICK_CAPTCHA)) != null) {
                if(controller.validateCaptcha(Integer.parseInt(input))) {
                    System.out.println("user logged in successfully");
                    break;
                }
            }
            else System.out.println("login failed : please enter the captcha again");
        }
    }

    private void loginUser(Matcher matcher) {
        if(checkBlankField(matcher.group("username")) || checkBlankField(matcher.group("password"))) {
            System.out.println("login failed : blank field");
            return;
        }

        String username = handleDoubleQuote(matcher.group("username"));
        String password = handleDoubleQuote(matcher.group("password"));
        boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;

        switch (controller.loginUser(username , password , stayLoggedIn)){
            case SUCCESS:
                mustEnterCaptcha();
                enterMainMenu();
                break;
            case WRONG_PASSWORD:
                System.out.println("login failed : password is not correct");
                wrongPassword(password, username);
                break;
            case USERNAME_DOES_NOT_EXISTS:
                System.out.println("login failed : username does not exist");
                break;
        }
    }

    private void wrongPassword(String password, String username) {
        int attempts = 1;
        while (true) {
            if(!controller.checkPassword(username, password)) {
                System.out.println("try again in " + attempts * 5 + " seconds");
                password = scanner.nextLine();
                if(LoginMenuCommands.getMatcher(password, LoginMenuCommands.FORGET_PASSWORD) != null)
                    forgetPassword(LoginMenuCommands.getMatcher(password, LoginMenuCommands.FORGET_PASSWORD));
                long time = currentTimeMillis();

                while (currentTimeMillis() - time < attempts * 5000L) {
                    long timeLeft = attempts * 5000L - currentTimeMillis() + time;
                    System.out.println("please wait " + timeLeft/1000 + " more seconds.");
                    password = scanner.nextLine();
                }
                attempts++;
            }
            else {
                mustEnterCaptcha();
                controller.setLoggedInUserInController(username);
                enterMainMenu();
                break;
            }
        }
    }

    private void forgetPassword(Matcher matcher) {
        if(checkBlankField(matcher.group("username"))) {
            System.out.println("password change failed : blank field");
            return;
        }

        String username = handleDoubleQuote(matcher.group("username"));
        String answer;
        String newPassword;

        switch (controller.forgetPassword(username)) {
                case USERNAME_DOES_NOT_EXISTS:
                System.out.println("password change failed : username does not exist");
                break;
                case ANSWER_RECOVERY_QUESTION:
                System.out.println("please answer this question : " + controller.getUserRecoveryQuestion(username));
                answer = scanner.nextLine();
                if(controller.isRecoveryQuestionAnswerCorrect(username, answer)) {
                    System.out.println("please enter your new password:");
                    newPassword = scanner.nextLine();
                    forgetPasswordError(username, newPassword);
                }else System.out.println("password change failed : your answer is not correct");
        }
    }

    private void forgetPasswordError(String username, String newPassword) {
        switch (controller.setNewPassword(username, newPassword)) {
            case WRONG_PASSWORD_RECOVERY_ANSWER:
                System.out.println("password change failed : your answer is not correct");
                break;
            case SHORT_PASSWORD:
                System.out.println("password change failed : your password must contain mare than five characters");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
                System.out.println("password change failed : your password must contain at least one integer");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
                System.out.println("password change failed : your password must contain at least one uppercase character");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
                System.out.println("password change failed : your password must contain at least one lowercase character");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
                System.out.println("password change failed : your password must contain at least" +
                                   " one character other than letters and numbers");
            case SUCCESS:
                System.out.println("password changed successfully");
                break;
        }
    }

    private void enterSignupMenu() {
        System.out.println("entered signup menu successfully");
        new SignupMenu().run();
    }

    private void enterMainMenu() {
//        System.out.println("entered main menu successfully");
        new MainMenu().run();
    }

}
