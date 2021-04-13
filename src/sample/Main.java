package sample;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FlowPane root = new FlowPane();
        root.setHgap(50);
        root.setVgap(50);
        primaryStage.setTitle("Animation");

        Button animationControl = new Button("Stop animation");

        Button closeApp = new Button("Exit");
        closeApp.setOnAction(event -> Platform.exit());

        Circle circle = new Circle(35);
        circle.setFill(Color.BROWN);

        Rectangle square = new Rectangle(35, 35);
        square.setFill(Color.ORANGE);

        Rectangle rectangle = new Rectangle(35, 75);
        rectangle.setFill(Color.LIME);

        List<Shape> shapes = new ArrayList<>();
        shapes.add(circle);
        shapes.add(square);
        shapes.add(rectangle);

        List<FadeTransition> fades = new ArrayList<>();
        for (Shape shape:
             shapes) {
            fades.add(setFadeTransition(shape));
        }

        SequentialTransition stay = new SequentialTransition(fades.get(0), fades.get(1), fades.get(2));
        stay.setCycleCount(Timeline.INDEFINITE);
        stay.play();

        AtomicBoolean cond = new AtomicBoolean(true);
        animationControl.setOnAction(event -> {
            if (cond.get()) {
                animationControl.setText("Play");
                stay.pause();
            } else {
                animationControl.setText("Pause");
                stay.play();
            }
            cond.set(!cond.get());
        });

        root.getChildren().addAll(animationControl, closeApp, circle, square, rectangle);
        primaryStage.setScene(new Scene(root, 500, 100));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private @NotNull
    FadeTransition setFadeTransition(Shape shape) {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), shape);
        fade.setAutoReverse(true);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setCycleCount(2);
        return fade;
    }
}
