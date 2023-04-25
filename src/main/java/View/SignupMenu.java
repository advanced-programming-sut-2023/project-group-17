package View;

import Controller.SignupMenuController;
import View.Enums.Commands.SignupMenuCommands;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu extends Menu{
    private Random random = new Random();
    private SignupMenuController controller;

    public SignupMenu() {
        controller = new SignupMenuController();
    }

    @Override
    public void run(Scanner scanner) {
        Matcher matcher;
        String command;

        while(true) {
            command = scanner.nextLine();
            if((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.CREATE_USER)) != null)
                createUser(matcher);
            else if((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.ENTER_LOGIN_MENU)) != null) {
                enterLoginMenu();
                return;
            }
            else System.out.println("Invalid Command");
        }
    }

    public void mustPickQuestion() {
        Matcher matcher;
        String pickQuestionCommand;

        System.out.println(controller.getSecurityQuestions());
        while(true) {
            pickQuestionCommand = scanner.nextLine();
            if((matcher = SignupMenuCommands.getMatcher(pickQuestionCommand, SignupMenuCommands.PICK_QUESTION)) != null) {
                pickQuestion(matcher);
                return;
            }
            else System.out.println("signup failed : please pick question to complete your registration");
        }
    }

    private void enterLoginMenu() {
        System.out.println("enter login menu successfully");
    }

    private void createUser(Matcher matcher) {
        if(checkBlankField(matcher.group("username")) || checkBlankField(matcher.group("password")) ||
            (!checkBlankField(matcher.group("confirmationDash")) && checkBlankField(matcher.group("confirmation"))) ||
                checkBlankField(matcher.group("email")) ||
           checkBlankField(matcher.group("nickname")) ||
                (!checkBlankField(matcher.group("sloganDash")) && checkBlankField(matcher.group("slogan")))) {
            System.out.println("signup failed : blank field");
            return;
        }

        String username = handleDoubleQuote(matcher.group("username"));
        String password = handleDoubleQuote(matcher.group("password"));
        String confirmationPassword = handleDoubleQuote(matcher.group("confirmation"));
        String email = handleDoubleQuote(matcher.group("email"));
        String nickname = handleDoubleQuote(matcher.group("nickname"));
        String slogan = handleDoubleQuote(matcher.group("slogan"));

        switch (controller.createUser(username, password, confirmationPassword, email, nickname, slogan)) {
            case SUCCESS:
                mustPickQuestion();
                break;
            case INVALID_USERNAME:
                System.out.println("signup failed : invalid username format");
                break;
            case USERNAME_EXISTS:
                System.out.println("signup failed : user " + username + " already exists");
                System.out.println("suggested username: " + username + random.nextInt(10) + random.nextInt(10));
                break;
            case SHORT_PASSWORD:
                System.out.println("signup failed : password must have at least 6 characters");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
                System.out.println("signup failed : password must have at least 1 lowercase character");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
                System.out.println("signup failed : password must have at least 1 uppercase character");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
                System.out.println("signup failed : password must have at least 1 integer");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
                System.out.println("signup failed : password must have at least 1 special character");
                break;
            case PASSWORD_DOES_NOT_MATCH:
                System.out.println("signup failed : passwords do not match");
                break;
            case EMAIL_EXISTS:
                System.out.println("signup failed : email " + email + " already exists");
                break;
            case INVALID_EMAIL:
                System.out.println("signup failed : invalid email format");
        }

    }

    private void pickQuestion(Matcher matcher) {
        if(checkBlankField(matcher.group("questionNumber")) ||
           checkBlankField(matcher.group("answer")) ||
           checkBlankField(matcher.group("confirmation"))) {
            System.out.println("pick question failed : blank field");
            return;
        }

        Integer questionNumber = Integer.parseInt(matcher.group("questionNumber"));
        String answer = handleDoubleQuote(matcher.group("answer"));
        String confirmationAnswer = handleDoubleQuote(matcher.group("confirmation"));

        switch (controller.pickQuestion(questionNumber, answer, confirmationAnswer)) {
            case SUCCESS:
                System.out.print("question number " + questionNumber + " picked successfully" + '\n' + "user created successfully" + '\n');
                break;
            case WRONG_NUMBER:
                System.out.println("pick question failed : invalid question number");
                break;
            case ANSWER_DOES_NOT_MATCH:
                System.out.println("pick question failed : answers do not match");
                break;
            case PICK_QUESTION_TWICE:
                System.out.println("pick question failed : you have already picked one question");
                break;
        }

    }

}
