package Client.view;

import Client.Utils.Pair;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;

public class MoveAnimation extends Transition {
    private ImageView imageView;
    private GridPane gridPane;
    private ArrayList<Pair> pairs;
    private Timeline timeline;
    private int i;

    public MoveAnimation(ImageView imageView, GridPane gridPane, ArrayList<Pair> pairs) {
        i = pairs.size() - 1;
        this.imageView = imageView;
        this.gridPane = gridPane;
        this.pairs = pairs;
        this.setCycleCount(1);
        this.setCycleDuration(Duration.millis(1000));
//        this.timeline = new Timeline();
//        this.timeline.setCycleCount(Timeline.INDEFINITE);
//        this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
//                new KeyValue(this.rotate.angleProperty(), 360)));
//        this.setCycleDuration(Duration.INDEFINITE);
        Timeline oneSecondsWonder = new Timeline(new KeyFrame(Duration.millis(2000), (ActionEvent event1) -> {
            if (i > 0 ) {
                i--;
                nexMove(i);
            }
        }));
        this.timeline = oneSecondsWonder;
        oneSecondsWonder.setCycleCount(Timeline.INDEFINITE);
    }

    private void nexMove(int i) {
        gridPane.getChildren().remove(imageView);
        gridPane.add(imageView, pairs.get(i).getCol(), pairs.get(i).getRow());
    }

    @Override
    protected void interpolate(double frac) {
        for (int i = 0; i < 3; i++) {

        }
        this.timeline.play();
    }
}
