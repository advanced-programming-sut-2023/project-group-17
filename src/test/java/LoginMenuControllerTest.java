import Server.controller.LoginMenuController;
import Server.controller.SignupMenuController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static Server.enums.Messages.LoginMenuMessages.WRONG_PASSWORD;

public class LoginMenuControllerTest {
    LoginMenuController controller = new LoginMenuController();

    @Test
    public void testIncorrectPassword() {
        SignupMenuController signupMenuController = new SignupMenuController();
        signupMenuController.createUser("Ahmadi", "amirKasra123@", "amirKasra123@",
                "kkk@yahoo.com", "amir", "nadaram");
        Assertions.assertEquals(WRONG_PASSWORD, controller.loginUser("Ahmadi", "eshtebahe", false));
    }
}
