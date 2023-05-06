package Controller;

import Model.Database;
import Model.User;
import Utils.Captcha;
import Utils.CheckValidation;
import View.Enums.Messages.LoginMenuMessages;
import View.Enums.Messages.UtilsMessages;

import static Model.Database.getUserByUsername;
import static Model.Database.setLoggedInUser;

public class LoginMenuController {
    int verifyingNumber;

    public LoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        Database.loadUsers();
        if(getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        User user = getUserByUsername(username);

        if(!user.getPassword().equals(User.SHA256Code(password)))
            return LoginMenuMessages.WRONG_PASSWORD;

        if (stayLoggedIn) Database.setStayLoggedInUser(user);
        setLoggedInUser(user);
        Database.loadUnits();
        Database.loadBuildings();
        Database.loadAttackToolsAndMethods();
        return LoginMenuMessages.SUCCESS;
    }

    public LoginMenuMessages forgetPassword(String username) {
        if(getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        return LoginMenuMessages.ANSWER_RECOVERY_QUESTION;
    }

    public LoginMenuMessages setNewPassword(String username, String newPassword) {
        User user = getUserByUsername(username);

        if(!CheckValidation.isPasswordStrong(newPassword).equals(UtilsMessages.PASSWORD_IS_STRONG)) {
            switch (CheckValidation.isPasswordStrong(newPassword)) {
                case SHORT_PASSWORD:
                    return LoginMenuMessages.SHORT_PASSWORD;
                case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
                    return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_LOWERCASE;
                case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
                    return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_INTEGER;
                case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
                    return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_UPPERCASE;
                case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
                    return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER;
                case PASSWORD_IS_STRONG:
                    return LoginMenuMessages.PASSWORD_IS_STRONG;
            }
        }

        user.setPassword(User.SHA256Code(newPassword));
        Database.saveUsers();
        return LoginMenuMessages.SUCCESS;
    }

    public String getUserRecoveryQuestion(String username) {
        return getUserByUsername(username).getPasswordRecoveryQuestion();
    }

    public boolean isRecoveryQuestionAnswerCorrect(String username, String answer) {
        return getUserByUsername(username).getPasswordRecoveryAnswer().equals(answer);
    }

    public boolean checkStayLoggedIn() {
        if (Database.getStayLoggedInUser() != null) {
            Database.loadUsers();
            Database.loadUnits();
            Database.loadBuildings();
            Database.loadAttackToolsAndMethods();
            Database.setLoggedInUser(Database.getUserByUsername(Database.getStayLoggedInUser()));
            return true;
        }
        return false;
    }

    public void GenerateCaptcha() {
        verifyingNumber = Captcha.generateRandomNumber();
        Captcha.printTextArt(verifyingNumber);
    }

    public boolean validateCaptcha(int input) {
        return verifyingNumber == input;
    }

    public boolean checkPassword(String username, String password) {
        return Database.getUserByUsername(username).getPassword().equals(User.SHA256Code(password));
    }

    public void setLoggedInUserInController(String username) {
        User user = Database.getUserByUsername(username);
        setLoggedInUser(user);
        Database.loadUnits();
        Database.loadBuildings();
    }
}
