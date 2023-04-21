package Controller;

import Model.User;
import Utils.CheckValidation;
import Utils.Randoms;
import View.Enums.Messages.SignupAndLoginMenuMessages;
import static View.Menu.print;
import static View.Menu.scan;
import static Model.Database.*;

public class SignupMenuController {
    User tempUser;
    public SignupAndLoginMenuMessages createUser(String username, String password, String confirmationPassword, String email, String nickname, String slogan) {
        if(!username.matches("[A-Za-z0-9_]+"))
            return SignupAndLoginMenuMessages.INVALID_USERNAME;

        if(getUserByUsername(username) != null)
            return SignupAndLoginMenuMessages.USERNAME_EXISTS;

        if(password.equals("random") && confirmationPassword == null) {
            password = Randoms.generateRandomPassword();
            print("your random password is " + password);
            print("please re-enter your password here:");
            confirmationPassword = scan();
        }

        if(!CheckValidation.isPasswordStrong(password).equals(SignupAndLoginMenuMessages.PASSWORD_IS_STRONG))
            return CheckValidation.isPasswordStrong(password);

        if(!password.equals(confirmationPassword))
            return SignupAndLoginMenuMessages.PASSWORD_DOES_NOT_MATCH;

        if(emailExists(email))
            return SignupAndLoginMenuMessages.EMAIL_EXISTS;

        if(!email.matches("([A-Za-z0-9_.]+@[A-Za-z0-9_.]+\\.[A-Za-z0-9_.]+)"))
            return SignupAndLoginMenuMessages.INVALID_EMAIL;

        if(slogan.equals("random")) {
            slogan = Randoms.generateRandomSlogan();
            print("your slogan is \"" + slogan + "\"");
        }

        tempUser = new User(username, password, nickname, email, slogan);
        addUser(tempUser);
        return SignupAndLoginMenuMessages.SUCCESS;
    }

    public SignupAndLoginMenuMessages pickQuestion(Integer questionNumber, String answer, String confirmationAnswer) {
        if(tempUser == null)
            return SignupAndLoginMenuMessages.PICK_QUESTION_TWICE;

        if(questionNumber != 1 && questionNumber != 2 && questionNumber != 3)
            return SignupAndLoginMenuMessages.WRONG_NUMBER;

        if(!answer.equals(confirmationAnswer))
            return SignupAndLoginMenuMessages.ANSWER_DOES_NOT_MATCH;

        tempUser.setPasswordRecoveryQuestion(getQuestionByNumber(questionNumber));
        tempUser.setPasswordRecoveryAnswer(answer);
        tempUser = null;
        return SignupAndLoginMenuMessages.SUCCESS;
    }
}
