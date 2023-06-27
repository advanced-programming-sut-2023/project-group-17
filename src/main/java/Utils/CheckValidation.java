package Utils;

import Model.Database;
import Server.enums.Messages.UtilsMessages;

import static Model.Database.getUserByUsername;

public class CheckValidation {
    public static UtilsMessages isPasswordStrong(String password) {
        if(password.length() < 6)
            return UtilsMessages.SHORT_PASSWORD;
        if(!password.matches(".*[a-z].*"))
            return UtilsMessages.PASSWORD_DOES_NOT_CONTAIN_LOWERCASE;
        if(!password.matches(".*[A-Z].*"))
            return UtilsMessages.PASSWORD_DOES_NOT_CONTAIN_UPPERCASE;
        if(!password.matches(".*[0-9].*"))
            return UtilsMessages.PASSWORD_DOES_NOT_CONTAIN_INTEGER;
        if(!password.matches(".*[!@#$%^&\\-+=?*./_].*"))
            return UtilsMessages.PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER;

        return UtilsMessages.PASSWORD_IS_STRONG;
    }

    public static UtilsMessages isUsernameOk(String username) {
        Database.loadUsers();
        if(!username.matches("[A-Za-z0-9_]+"))
            return UtilsMessages.INVALID_USERNAME;

        if(getUserByUsername(username) != null)
            return UtilsMessages.USERNAME_EXISTS;

        return UtilsMessages.SUCCESS;
    }

    public static UtilsMessages isEmailOk(String email) {
        Database.loadUsers();
        if(!email.matches("([A-Za-z0-9_.]+@[A-Za-z0-9_.]+\\.[A-Za-z0-9_.]+)"))
            return UtilsMessages.INVALID_EMAIL;

        return UtilsMessages.SUCCESS;
    }
}
