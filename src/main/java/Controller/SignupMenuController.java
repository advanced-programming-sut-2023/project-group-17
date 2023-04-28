package Controller;

import Model.Database;
import Model.User;
import Utils.CheckValidation;
import Utils.Randoms;
import View.Enums.Messages.SignupMenuMessages;
import View.Enums.Messages.UtilsMessages;

import static Model.Database.*;

public class SignupMenuController {
//    User tmpUser;
    String[] tmpUserInfo;
    public SignupMenuMessages createUser(String username, String password, String confirmationPassword,
                                         String email, String nickname, String slogan) {
        Database.loadUsers();
        if(!username.matches("[A-Za-z0-9_]+"))
            return SignupMenuMessages.INVALID_USERNAME;

        if(getUserByUsername(username) != null)
            return SignupMenuMessages.USERNAME_EXISTS;

        if(emailExists(email))
            return SignupMenuMessages.EMAIL_EXISTS;

        if(!email.matches("([A-Za-z0-9_.]+@[A-Za-z0-9_.]+\\.[A-Za-z0-9_.]+)"))
            return SignupMenuMessages.INVALID_EMAIL;

        if(password.equals("random") && confirmationPassword == null)
            return SignupMenuMessages.RANDOM_PASSWORD;

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

        if(!password.equals(confirmationPassword))
            return SignupMenuMessages.PASSWORD_DOES_NOT_MATCH;

        if(slogan != null && slogan.equals("random"))
            return SignupMenuMessages.RANDOM_SLOGAN;


//        tempUser = new User(username, User.SHA256Code(password), nickname, email, slogan, "null", "null");
        tmpUserInfo = new String[]{username, User.SHA256Code(password), nickname, email, slogan};
        return SignupMenuMessages.SUCCESS;
    }

    public String getRandomPassword() {
        return Randoms.generateRandomPassword();
    }

    public String getRandomSlogan() {
        return Randoms.generateRandomSlogan();
    }

    public boolean isRandomPasswordsMatches(String password, String confirmationPassword) {
        return password.equals(confirmationPassword);
    }

    public SignupMenuMessages pickQuestion(Integer questionNumber, String answer, String confirmationAnswer) {

        if(questionNumber != 1 && questionNumber != 2 && questionNumber != 3)
            return SignupMenuMessages.WRONG_NUMBER;

        if(!answer.equals(confirmationAnswer))
            return SignupMenuMessages.ANSWER_DOES_NOT_MATCH;

//        tempUser.setPasswordRecoveryQuestion(getQuestionByNumber(questionNumber));
//        tempUser.setPasswordRecoveryAnswer(answer);
//        tmpUser = new User(tmpUserInfo[0], tmpUserInfo[1], tmpUserInfo[2], tmpUserInfo[3], tmpUserInfo[4], getQuestionByNumber(questionNumber), answer);
        addUser(new User(tmpUserInfo[0], tmpUserInfo[1], tmpUserInfo[2], tmpUserInfo[3], tmpUserInfo[4], getQuestionByNumber(questionNumber), answer));
//        tmpUser = null;
        return SignupMenuMessages.SUCCESS;
    }
    public String getSecurityQuestions() {
        String questions = "pick your question : \n";
        for (int i = 0; i < recoveryQuestions.length; i++)
            questions += (i + 1) + ". " + recoveryQuestions[i] + "\n";
        return questions;
    }
}
