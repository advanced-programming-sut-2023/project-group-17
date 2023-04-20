package Utils;

import View.Enums.Messages.SignupAndLoginMenuMessages;

public class CheckValidation {
    public static SignupAndLoginMenuMessages isPasswordStrong(String password) {
        if(password.length() < 6)
            return SignupAndLoginMenuMessages.SHORT_PASSWORD;
        if(!password.matches(".*[a-z].*"))
            return SignupAndLoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_LOWERCASE;
        if(!password.matches(".*[A-Z].*"))
            return SignupAndLoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_UPPERCASE;
        if(!password.matches(".*[0-9].*"))
            return SignupAndLoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_INTEGER;
        if(!password.matches(".*[!@#$%^&\\-+?*.].*"))
            return SignupAndLoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER;

        return SignupAndLoginMenuMessages.PASSWORD_IS_STRONG;
    }
}
