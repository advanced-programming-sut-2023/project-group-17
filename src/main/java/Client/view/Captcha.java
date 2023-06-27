package Client.view;

import Server.controller.DataAnalysisController;
import javafx.scene.image.Image;

import java.util.ArrayList;


public class Captcha {
    int answer;
    Image captchaImage;
    private ArrayList<String> captchas;
    private DataAnalysisController controller;

    Captcha() {
        this.controller = new DataAnalysisController();
        this.captchas = controller.getCaptchas();
        generateNewCaptcha();
    }

    public void generateNewCaptcha() {
        int size = captchas.size();
        int index = (int) (Math.random() * size);
        this.answer = Integer.parseInt(captchas.get(index));
        String path = getClass().getResource("/Captcha/" + answer + ".png").toExternalForm();
        this.captchaImage = new Image(path, 80, 80, false, false);
    }

    public int getAnswer() {
        return answer;
    }

    public Image getCaptchaImage() {
        return captchaImage;
    }
}
