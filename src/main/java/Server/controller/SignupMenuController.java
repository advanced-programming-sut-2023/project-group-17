package Server.controller;

import Model.Database;
import Model.User;
import Server.enums.Messages.SignupMenuMessages;
import Utils.Randoms;
import static Model.Database.*;

public class SignupMenuController {
    User tmpUser;
    public int verifyingNumber;
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

        //TODO: solve this for validation?
//        if(!CheckValidation.isPasswordStrong(password).equals(UtilsMessages.PASSWORD_IS_STRONG)) {
//            switch (CheckValidation.isPasswordStrong(password)) {
//                case SHORT_PASSWORD:
//                    return SignupMenuMessages.SHORT_PASSWORD;
//                case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
//                    return SignupMenuMessages.PASSWORD_DOES_NOT_CONTAIN_LOWERCASE;
//                case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
//                    return SignupMenuMessages.PASSWORD_DOES_NOT_CONTAIN_INTEGER;
//                case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
//                    return SignupMenuMessages.PASSWORD_DOES_NOT_CONTAIN_UPPERCASE;
//                case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
//                    return SignupMenuMessages.PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER;
//            }
//        }

        if(!password.equals(confirmationPassword))
            return SignupMenuMessages.PASSWORD_DOES_NOT_MATCH;

        if(slogan != null && slogan.equals("random"))
            return SignupMenuMessages.RANDOM_SLOGAN;

        tmpUser = new User(username, User.SHA256Code(password), nickname, email, slogan, null, null);
        addUser(tmpUser);
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

        tmpUser.setPasswordRecoveryQuestion(getQuestionByNumber(questionNumber));
        tmpUser.setPasswordRecoveryAnswer(answer);
        tmpUser = null;
        saveUsers();
        return SignupMenuMessages.SUCCESS;
    }

    public String getSecurityQuestions() {
        String questions = "pick your question : \n";
        for (int i = 0; i < recoveryQuestions.length; i++)
            questions += (i + 1) + ". " + recoveryQuestions[i] + "\n";
        return questions;
    }

//    public void GenerateCaptcha() {
//        verifyingNumber = Captcha.generateRandomNumber();
//        Captcha.printTextArt(verifyingNumber);
//    }

    public boolean validateCaptcha(int input) {
        return verifyingNumber == input;
    }

    public String getAvailableSlogans (int i) {
        if (i != -1) return Randoms.Slogans[i];
        return null;
    }

    public String getSecurityQuestions (int i) {
        if (i != -1) return recoveryQuestions[i];
        return null;
    }
}