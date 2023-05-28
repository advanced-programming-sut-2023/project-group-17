package View;

import Controller.AvatarMenuController;
import Controller.ProfileMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AvatarMenu extends Application {
    private final AvatarMenuController controller = new AvatarMenuController();
    public File file;
    public Button backToProfile;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(ProfileMenu.class.getResource("/fxml/AvatarMenu.fxml"));
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/Backgrounds/avatarMenu.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
//        Dragboard dragboard = new
        HBox hBox = new HBox();
        FileChooser fileChooser = new FileChooser();
        Button browse = new Button("Browse");
        browse.setPrefWidth(120);
        setActionBrowse(browse, fileChooser, stage, hBox);
        hBox.getChildren().add(browse);
        hBox.setAlignment(Pos.CENTER); hBox.setLayoutX(500); hBox.setLayoutY(450);
        hBox.setSpacing(10);
        pane.getChildren().add(hBox);
        stage.setScene(new Scene(pane));
        stage.setFullScreen(true);
        stage.show();
    }

    private void setActionBrowse(Button browse, FileChooser fileChooser, Stage stage, HBox hBox) {
        browse.setOnAction((event) ->
        {
            file = fileChooser.showOpenDialog(stage);
            ImageView imageView = new ImageView(file.toURI().toString());
            imageView.setFitWidth(160);
            imageView.setFitHeight(160);
            controller.changeAvatarCustom(file.toURI().toString());
            if (hBox.getChildren().size() > 1) hBox.getChildren().remove(1);
            hBox.getChildren().add(imageView);
        });
    }

    public void backToProfile(ActionEvent actionEvent) throws Exception {
        new ProfileMenu().start(Main.stage);
    }
}
