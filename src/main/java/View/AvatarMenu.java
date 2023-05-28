package View;

import Controller.AvatarMenuController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class AvatarMenu extends Application {
    private final AvatarMenuController controller = new AvatarMenuController();
    public File file;
    public Button backToProfile;
    public ChoiceBox avatarOtherPlayers;
    public ObservableList<String> list;
    private static Pane pane;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(ProfileMenu.class.getResource("/fxml/AvatarMenu.fxml"));
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/Backgrounds/avatarMenu.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
//        Dragboard dragboard = new
        makeHBoxBrowse(pane, stage);
        makeVBoxDefaultAvatars(pane, stage);
        AvatarMenu.pane = pane;
        stage.setScene(new Scene(pane));
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    public void initialize() {
        list = FXCollections.observableArrayList();
        ArrayList<String> pathsList = new ArrayList<>();
        controller.addUsers(pathsList);
        list.addAll(pathsList);
        avatarOtherPlayers.getItems().setAll(list);
    }

    private void makeVBoxDefaultAvatars(Pane pane, Stage stage) {
        HBox hBox = new HBox(); hBox.setAlignment(Pos.CENTER); hBox.setSpacing(10);
        hBox.setLayoutX(650); hBox.setLayoutY(300);
        makeHBoxEachImage(hBox);
        pane.getChildren().add(hBox);
    }

    private void makeHBoxEachImage(HBox hBox) {
        String path;
        for (int i = 1; i < 5; i++) {
            path = "file:/Users/kasrahmi/Documents/AP/StrongHold/target/classes/assets/avatars/" + i + ".png";
            VBox vBox = new VBox(); vBox.setAlignment(Pos.CENTER); vBox.setSpacing(10);
            ImageView imageView = new ImageView(new Image(path , 160 ,160, false, false));
            makeImagesOnClicked(path, imageView);
            vBox.getChildren().add(imageView);
            hBox.getChildren().add(vBox);
        }
    }

    private void makeImagesOnClicked(String path, ImageView imageView) {
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.changeAvatarCustom(path);
                try {
                    backToProfile();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void makeHBoxBrowse(Pane pane, Stage stage) {
        VBox vBox = new VBox();
        FileChooser fileChooser = new FileChooser();
        Button browse = new Button("Browse Photos");
        browse.setPrefWidth(160); browse.setStyle("-fx-background-color: #daa520;");
        setActionBrowse(browse, fileChooser, stage, vBox);
        vBox.getChildren().add(browse);
        vBox.setAlignment(Pos.CENTER); vBox.setLayoutX(475); vBox.setLayoutY(300);
        vBox.setSpacing(10);
        pane.getChildren().add(vBox);
    }

    private void setActionBrowse(Button browse, FileChooser fileChooser, Stage stage, VBox vBox) {
        browse.setOnAction((event) ->
        {
            file = fileChooser.showOpenDialog(stage);
            ImageView imageView = new ImageView(file.toURI().toString());
            imageView.setFitWidth(160);
            imageView.setFitHeight(160);
            controller.changeAvatarCustom(file.toURI().toString());
            if (vBox.getChildren().size() > 1) vBox.getChildren().remove(1);
            vBox.getChildren().add(imageView);
        });
    }

    public void backToProfile() throws Exception {
        new ProfileMenu().start(Main.stage);
    }

    public void getOtherPlayersAvatar(MouseEvent mouseEvent) {
        avatarOtherPlayers.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                String path = controller.getPathByNumber((Integer) new_value);
                ImageView imageView;
                imageView = new ImageView(new Image(path, 160, 160, false, false));
                imageView.setY(350); imageView.setX(180);
                pane.getChildren().add(imageView);
                controller.changeAvatarCustom(path);
            }
        });
    }
}
