package Controller;

import Model.Database;
import Model.User;
import Utils.Captcha;
import Utils.CheckValidation;
import View.Enums.Messages.LoginMenuMessages;
import View.Enums.Messages.UtilsMessages;

import static Model.Database.setCurrentUser;

public class LoginMenuController {
    int verifyingNumber;

    public LoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        Database.loadUsers();

        if (username.equals("") || password.equals("")) return LoginMenuMessages.BLANK_FIELD;

        if(getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        User user = getUserByUsername(username);
        if(!user.getPassword().equals(User.SHA256Code(password)))
            return LoginMenuMessages.WRONG_PASSWORD;
        if (stayLoggedIn) Database.setStayLoggedInUser(user);
        setCurrentUser(user);
        Database.loadUnits();
        Database.loadBuildings();
        Database.loadAttackToolsAndMethods();
        Database.setLoggedInUser(Database.getCurrentUser());
        return LoginMenuMessages.SUCCESS;
    }

    public LoginMenuMessages forgetPassword(String username) {
        if(getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        return LoginMenuMessages.ANSWER_RECOVERY_QUESTION;
    }

    public LoginMenuMessages setNewPassword(String username, String newPassword, String recoveryQuestionAnswer) {
        User user = getUserByUsername(username);

        if (username.equals("") || newPassword.equals("") || recoveryQuestionAnswer.equals(""))
            return LoginMenuMessages.BLANK_FIELD;

        if (!recoveryQuestionAnswer.equals(user.getPasswordRecoveryAnswer()))
            return LoginMenuMessages.WRONG_PASSWORD_RECOVERY_ANSWER;

        if(!CheckValidation.isPasswordStrong(newPassword).equals(UtilsMessages.PASSWORD_IS_STRONG)) {
            switch (CheckValidation.isPasswordStrong(newPassword)) {
                case PASSWORD_IS_STRONG:
                    break;
                default:
                    return LoginMenuMessages.WEAK_PASSWORD;
            }
        }

        user.setPassword(User.SHA256Code(newPassword));
        Database.saveUsers();
        return LoginMenuMessages.SUCCESS;
    }

    public String getUserRecoveryQuestion(String username) {
        return Database.getUserByUsername(username).getPasswordRecoveryQuestion();
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
            Database.setCurrentUser(Database.getUserByUsername(Database.getStayLoggedInUser()));
            Database.setLoggedInUser(Database.getCurrentUser());
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
        setCurrentUser(user);
        Database.loadUnits();
        Database.loadBuildings();
    }

    public User getUserByUsername(String username) {
        return Database.getUserByUsername(username);
    }
}
