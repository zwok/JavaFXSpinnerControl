package be.zwok.javafx;

import com.javafx.experiments.scenicview.ScenicView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.text.DateFormatSymbols;
import java.util.Date;

/**
 * Simul
 * Author: Yannick Kalokerinos
 * Institution: Universiteit Hasselt
 * Date: 24/01/13
 */
public class TestSpinnerControl extends Application {
    private Scene scene;

    private SpinnerControl spinnerControlDay;
    private SpinnerControl spinnerControlMonth;
    private SpinnerControl spinnerControlYear;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = createLayout();
        scene = SceneBuilder.create().root(root).width(600).height(400).build();
        stage.setScene(scene);
        stage.show();

        // Add values to the spinner controls
        String[] months = new DateFormatSymbols().getMonths();
        ObservableList<String> dayList = FXCollections.observableArrayList();
        ObservableList<String> monthList = FXCollections.observableArrayList(months);
        ObservableList<String> yearList = FXCollections.observableArrayList();
        for (int i = 1; i <= 31; i++) {
            dayList.add(Integer.toString(i));
        }
        for (int i = 1970; i <=  2013; i++) {
            yearList.add(Integer.toString(i));
        }
        spinnerControlDay.setItems(dayList);
        spinnerControlMonth.setItems(monthList);
        spinnerControlYear.setItems(yearList);

        //ScenicView.show(scene);
    }

    private Parent createLayout() {
        spinnerControlDay = new SpinnerControl();
        spinnerControlDay.setStyle("-fx-font-size: 20");
        spinnerControlMonth = new SpinnerControl();
        spinnerControlMonth.setStyle("-fx-font-size: 20");
        spinnerControlYear = new SpinnerControl();
        spinnerControlYear.setStyle("-fx-font-size: 20");
        Button button = ButtonBuilder.create().onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                spinnerControlDay.goToItem("31");
            }
        }).prefHeight(50).text("GO").build();
        HBox hBox = HBoxBuilder.create().fillHeight(false).children(spinnerControlDay, spinnerControlMonth, spinnerControlYear, button).spacing(25).alignment(Pos.CENTER).build();



        return hBox;
    }
}
