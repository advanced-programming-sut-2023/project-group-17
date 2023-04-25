package Controller;

import Model.Database;
import Model.User;
import Utils.CheckValidation;
import Utils.Randoms;
import View.Enums.Messages.SignupMenuMessages;
import View.Enums.Messages.UtilsMessages;

import static View.Menu.print;
import static View.Menu.scan;
import static Model.Database.*;

public class SignupMenuController {
    User tempUser;
    public SignupMenuMessages createUser(String username, String password, String confirmationPassword,
                                         String email, String nickname, String slogan) {
        Database.loadUsers();
        if(!username.matches("[A-Za-z0-9_]+"))
            return SignupMenuMessages.INVALID_USERNAME;

        if(getUserByUsername(username) != null)
            return SignupMenuMessages.USERNAME_EXISTS;

        if(slogan != null && slogan.equals("random")) {
            slogan = Randoms.generateRandomSlogan();
            print("your slogan is \"" + slogan + "\"");
        }

        if(password.equals("random") && confirmationPassword == null) {
            password = Randoms.generateRandomPassword();
            print("your random password is " + password);
            print("please re-enter your password here:");
            confirmationPassword = scan();
        }

        if(!CheckValidation.isPasswordStrong(password).equals(UtilsMessages.PASSWORD_IS_STRONG)) {
            switch (CheckValidation.isPasswordStrong(password)) {
                case SHORT_PASSWORD:
                    return SignupMenuMessages.SHORT_PASSWORD;
                case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
                    return SignupMenuMessages.PASSWORD_DOES_NOT_CONTAIN_LOWERCASE;
                case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
                    return SignupMenuMessages.PASSWORD_DOES_NOT_CONTAIN_INTEGER;
                case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
                    return SignupMenuMessages.PASSWORD_DOES_NOT_CONTAIN_UPPERCASE;
                case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
                    return SignupMenuMessages.PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER;
                case PASSWORD_IS_STRONG:
                    return SignupMenuMessages.PASSWORD_IS_STRONG;
            }
        }

        if(!password.equals(User.SHA256Code(confirmationPassword)))
            return SignupMenuMessages.PASSWORD_DOES_NOT_MATCH;

        if(emailExists(email))
            return SignupMenuMessages.EMAIL_EXISTS;

        if(!email.matches("([A-Za-z0-9_.]+@[A-Za-z0-9_.]+\\.[A-Za-z0-9_.]+)"))
            return SignupMenuMessages.INVALID_EMAIL;

        tempUser = new User(username, User.SHA256Code(password), nickname, email, slogan);
        addUser(tempUser);
        return SignupMenuMessages.SUCCESS;
    }

    public SignupMenuMessages pickQuestion(Integer questionNumber, String answer, String confirmationAnswer) {
        if(tempUser == null)
            return SignupMenuMessages.PICK_QUESTION_TWICE;

        if(questionNumber != 1 && questionNumber != 2 && questionNumber != 3)
            return SignupMenuMessages.WRONG_NUMBER;

        if(!answer.equals(confirmationAnswer))
            return SignupMenuMessages.ANSWER_DOES_NOT_MATCH;

        tempUser.setPasswordRecoveryQuestion(getQuestionByNumber(questionNumber));
        tempUser.setPasswordRecoveryAnswer(answer);
        tempUser = null;
        return SignupMenuMessages.SUCCESS;
    }
    public String getSecurityQuestions() {
        String questions = "";
        for (int i = 0; i < recoveryQuestions.length; i++)
            questions += (i + 1) + ". " + recoveryQuestions[i] + "\n";
        return questions;
    }
}
