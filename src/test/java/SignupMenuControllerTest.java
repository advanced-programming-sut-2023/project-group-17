import Server.controller.SignupMenuController;
import Model.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static View.Enums.Messages.SignupMenuMessages.*;

@ExtendWith(MockitoExtension.class)
public class SignupMenuControllerTest {
    @Mock
    Database database;
    @InjectMocks
    SignupMenuController controller;
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
        Assertions.assertEquals(RANDOM_PASSWORD, controller.createUser("shamimRahimi",
                "random", null,
                "shamim@gmail.com", "sham", "noSlogan"));
    }

    @Test
    public void randomSloganCheck() {
        Assertions.assertEquals(RANDOM_SLOGAN, controller.createUser("KasraAhmadi",
                "kkkKKK123@", "kkkKKK123@",
                "kas@gmail.com", "kas", "random"));
    }

    @Test
    public void matchPasswordCheck() {
        Assertions.assertEquals(PASSWORD_DOES_NOT_MATCH, controller.createUser("ShamimTariverdi",
                "shamimM123$%%%", "shamim123$%%%",
                "shamimtar@gmail.com", "sham", "noSlogan"));
    }

    @Test
    public void pickQuestionCheck() {
        Assertions.assertEquals(WRONG_NUMBER ,
                controller.pickQuestion(4, "answer", "answer"));

        Assertions.assertEquals(ANSWER_DOES_NOT_MATCH,
                controller.pickQuestion(1, "answer", "answerkhkhkh"));

        controller.createUser("ShamimRahimi", "shamimM123$%%%", "shamimM123$%%%",
                "shamimrahimi2833@gmail.com", "sham", "noSlogan");
        Assertions.assertEquals(SUCCESS,
                controller.pickQuestion(1, "answer", "answer"));
    }

    @Test
    public void getSecurityQuestionsCheck() {
        Assertions.assertEquals("pick your question : \n" +
                "1. What is my father's name?\n" +
                "2. What was my first pet's name?\n" +
                "3. What is my mother's last name?" + "\n", controller.getSecurityQuestions());
    }

    @Test
    public void isRandomPasswordMatches() {
        Assertions.assertEquals(false ,
                controller.isRandomPasswordsMatches("hiii", "hii"));

        Assertions.assertEquals(true ,
                controller.isRandomPasswordsMatches("hiii", "hiii"));
    }

    @Test
    public void randomPasswordValidationCheck() {
        Assertions.assertTrue(controller.getRandomPassword().matches(".*[A-Z].*"));
        Assertions.assertTrue(controller.getRandomPassword().matches(".*[a-z].*"));
        Assertions.assertTrue(controller.getRandomPassword().matches(".*\\d+.*"));
        Assertions.assertTrue(controller.getRandomPassword().matches(".*[!@#$%^&*_=+\\-/.].*"));
    }

    @Test
    public void randomSloganValidationCheck() {
         String[] Slogans = {
                "Give up or I will make you give up",
                "I shall have my revenge, in this life or in next",
                "Greatest player of all time is playing",
                "I am the reason of your nightmares",
                "You will remember me as a legend",
                "join me or die"
        };
        Assertions.assertTrue(Arrays.asList(Slogans).contains(controller.getRandomSlogan()));
    }

    @Test
    public void verifyCaptchaCheck() {
        controller.verifyingNumber = 12;
        Assertions.assertTrue(controller.validateCaptcha(12));
    }
}
