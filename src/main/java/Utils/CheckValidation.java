package Utils;

import View.Enums.Messages.SignupAndLoginMenuMessages;
import View.Enums.Messages.UtilsMessages;

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
        if(!password.matches(".*[!@#$%^&\\-+?*.].*"))
            return UtilsMessages.PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER;

        return UtilsMessages.PASSWORD_IS_STRONG;
    }
}
