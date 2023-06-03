package View;

import Controller.HeadquarterMenuController;
import Model.Database;
import Model.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;

import static View.Main.stage;

public class SetHeadquarters extends Application {
    public HeadquarterMenuController controller = new HeadquarterMenuController();
    ArrayList<TextField> textFields = new ArrayList<>();
    ArrayList<Text> texts = new ArrayList<>();
    public Text user1Text;
    public Text user2Text;
    public Text user3Text;
    public Text user4Text;
    public Text user5Text;
    public Text user6Text;
    public Text user7Text;
    public Text user8Text;
    public TextField user1X;
    public TextField user1Y;
    public TextField user2X;
    public TextField user2Y;
    public TextField user3X;
    public TextField user3Y;
    public TextField user4X;
    public TextField user4Y;
    public TextField user5X;
    public TextField user5Y;
    public TextField user6X;
    public TextField user6Y;
    public TextField user7X;
    public TextField user7Y;
    public TextField user8X;
    public TextField user8Y;
    public Button enterGame;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/SetHeadquarters.fxml"));
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/Backgrounds/headquartersMenu.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));

        stage.getScene().setRoot(pane);
        stage.setFullScreen(true);
        stage.show();
    }


    @FXML
    public void initialize() {
        texts.add(user1Text); texts.add(user2Text);
        texts.add(user3Text); texts.add(user4Text);
        texts.add(user5Text); texts.add(user6Text);
        texts.add(user7Text); texts.add(user8Text);
        textFields.add(user1X); textFields.add(user1Y);
        textFields.add(user2X); textFields.add(user2Y);
        textFields.add(user3X); textFields.add(user3Y);
        textFields.add(user4X); textFields.add(user4Y);
        textFields.add(user5X); textFields.add(user5Y);
        textFields.add(user6X); textFields.add(user6Y);
        textFields.add(user7X); textFields.add(user7Y);
        textFields.add(user8X); textFields.add(user8Y);
        enterGame.setDisable(true);

        for (int i = 0; i < controller.numberOfPlayerInTheGame(); i++) {
            texts.get(i).setText(controller.getUsernameByNumber(i));
        }

        for (int i = 0; i < controller.numberOfPlayerInTheGame() * 2; i++) {
            textFields.get(i).setVisible(true);
            makeListenerForEnterGameButton(textFields.get(i));
        }
    }

    private void makeListenerForEnterGameButton(TextField textField) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.equals("")) {
                enterGame.setDisable(false);
                for (TextField textField1 : textFields) {
                    if (textField1.isVisible() && textField1.getText().equals(""))
                        enterGame.setDisable(true);
                    if (!textField1.getText().equals("") && !textField1.getText().matches("\\d+"))
                        enterGame.setDisable(true);
                }
            } else enterGame.setDisable(true);
        });
    }

    public void enterGame(ActionEvent actionEvent) throws Exception {
        for (int i = 0; i < controller.numberOfPlayerInTheGame() * 2; i++) {
            if (i % 2 == 1) {
                controller.setHeadquartersByNumber(i / 2, textFields.get(i - 1).getText(), textFields.get(i).getText());
            }
        }
        new GameMenu().start(stage);
    }
}
