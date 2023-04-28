import Controller.SignupMenuController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static View.Enums.Messages.SignupMenuMessages.*;

public class SignupMenuControllerTest {
    SignupMenuController controller = new SignupMenuController();
    @Test
    public void passwordValidationTest() {
        Assertions.assertEquals(PASSWORD_DOES_NOT_CONTAIN_UPPERCASE, controller.createUser("Shamim",
                "shamim123$%%%", "shamim123$%%%",
                "shamimrahimi83@gmail.com", "sham", "noSlogan"));

        Assertions.assertEquals(PASSWORD_DOES_NOT_CONTAIN_INTEGER, controller.createUser("Shamim",
                "shamimM$%%%", "shamim$%%%",
                "shamimrahimi83@gmail.com", "sham", "noSlogan"));

        Assertions.assertEquals(PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER, controller.createUser("Shamim",
                "shamimM1233", "shamimM1233",
                "shamimrahimi83@gmail.com", "sham", "noSlogan"));

        Assertions.assertEquals(PASSWORD_DOES_NOT_CONTAIN_LOWERCASE, controller.createUser("Shamim",
                "SHAMI1M$%%%", "SHAMI1M$%%%",
                "shamimrahimi83@gmail.com", "sham", "noSlogan"));

        Assertions.assertEquals(SHORT_PASSWORD, controller.createUser("Shamim",
                "SHAMI", "SHAMI",
                "shamimrahimi83@gmail.com", "sham", "noSlogan"));
    }

    @Test
    public void usernameExistsTest() {
        controller.createUser("Shamim", "shamimM123$%%%", "shamimM123$%%%",
                "shamimrahimi833@gmail.com", "sham", "noSlogan");
        Assertions.assertEquals(USERNAME_EXISTS, controller.createUser("Shamim",
                "shamimM123$%%%", "shamimM123$%%%",
                "shamimrahimi833@gmail.com", "sham", "noSlogan"));
    }

    @Test
    public void emailValidationCheck() {
        Assertions.assertEquals(INVALID_EMAIL, controller.createUser("Ashkan", "shamimM123$%%%",
                "shamimM123$%%%", "shamimrahimi83.com", "sham", "noSlogan"));
    }

    @Test
    public void usernameValidationCheck() {
        Assertions.assertEquals(INVALID_USERNAME, controller.createUser("Sham&im", "shamimM123$%%%",
                "shamimM123$%%%", "shamimrahimi83@gmail.com",
                "sham", "noSlogan"));
    }

    @Test
    public void emailExistsCheck() {
        controller.createUser("kasra", "shamimM123$%%%", "shamimM123$%%%",
                "shamimrahimi83@gmail.com", "sham", "noSlogan");
        Assertions.assertEquals(EMAIL_EXISTS, controller.createUser("amirkasra", "shamimM123$%%%",
                "shamimM123$%%%", "shamimrahimi83@gmail.com",
                "sham", "noSlogan"));
    }

    @Test
    public void randomPasswordCheck() {
        Assertions.assertEquals(RANDOM_PASSWORD, controller.createUser("shamimRahimi", "random", null,
                "shamim@gmail.com", "sham", "noSlogan"));
    }

    @Test
    public void randomSloganCheck() {
        Assertions.assertEquals(RANDOM_SLOGAN, controller.createUser("KasraAhmadi", "kkkKKK123@", "kkkKKK123@",
                "kas@gmail.com", "kas", "random"));
    }

    @Test
    public void matchPasswordCheck() {
        Assertions.assertEquals(PASSWORD_DOES_NOT_MATCH, controller.createUser("ShamimTariverdi",
                "shamimM123$%%%", "shamim123$%%%",
                "shamimtar@gmail.com", "sham", "noSlogan"));
    }
}
