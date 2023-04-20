package Utils;

import View.Enums.Messages.LoginMenuMessages;

public class CheckValidation {
    public static LoginMenuMessages isPasswordStrong(String password) {
        if(password.length() < 6)
            return LoginMenuMessages.SHORT_PASSWORD;
        if(!password.matches(".*[a-z].*"))
            return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_LOWERCASE;
        if(!password.matches(".*[A-Z].*"))
            return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_UPPERCASE;
        if(!password.matches(".*[0-9].*"))
            return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_INTEGER;
        if(!password.matches(".*[!@#$%^&\\-+?*.].*"))
            return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER;

        return LoginMenuMessages.PASSWORD_IS_STRONG;
    }
}
