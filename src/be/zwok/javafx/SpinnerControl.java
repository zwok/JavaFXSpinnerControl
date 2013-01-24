package be.zwok.javafx;

import javafx.animation.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.util.Duration;

/**
 * SpinnerControl
 * Author: Yannick Kalokerinos
 * Date: 24/01/13
 */
public class SpinnerControl extends AnchorPane {
    private VBox valueVBox;
    private VBox overlayVBox;
    private ScrollPane valueScrollPane;
    private ObservableList<String> valueList = null;
    private double itemHeight = 0.0;
    private Timeline timeline = new Timeline();

    public SpinnerControl() {
        this.getStyleClass().add("spinner-control");
        createLayout();
        registerBehaviour();

    }

    private void createLayout() {
        this.setStyle("-fx-background-color: transparent;");
        this.setPrefSize(50, 150);
 

        Region rect1 = RegionBuilder.create().style("-fx-background-color: #000000").opacity(0.2).prefHeight(2).build();
        Region rect2 = RegionBuilder.create().style("-fx-background-color: #000000").opacity(0.8).prefHeight(2).build();
        Region rect3 = RegionBuilder.create().style("-fx-background-color: #000000").opacity(0.8).prefHeight(2).build();
        Region rect4 = RegionBuilder.create().style("-fx-background-color: #000000").opacity(0.2).prefHeight(2).build();
        overlayVBox = VBoxBuilder.create().fillWidth(true).children(rect1, rect2, rect3, rect4).disable(true).build();

        valueVBox = VBoxBuilder.create().fillWidth(true).spacing(2).alignment(Pos.CENTER).build();
        valueVBox.setStyle("-fx-background-color: transparent;");

        valueScrollPane = ScrollPaneBuilder.create().prefHeight(100).content(valueVBox).vbarPolicy(ScrollPane.ScrollBarPolicy.NEVER).hbarPolicy(ScrollPane.ScrollBarPolicy.NEVER).pannable(true).fitToWidth(true).build();
        valueScrollPane.setStyle("-fx-background-color: transparent;");

        this.getChildren().addAll(valueScrollPane, overlayVBox);
        this.setBottomAnchor(valueScrollPane, 0.0);
        this.setTopAnchor(valueScrollPane, 0.0);
        this.setLeftAnchor(valueScrollPane, 0.0);
        this.setRightAnchor(valueScrollPane, 0.0);
        this.setBottomAnchor(overlayVBox, 0.0);
        this.setTopAnchor(overlayVBox, 0.0);
        this.setLeftAnchor(overlayVBox, 0.0);
        this.setRightAnchor(overlayVBox, 0.0);
    }

    private void registerBehaviour() {

    }

    public ObservableList<String> getItems() {
        return valueList;
    }

    public void setItems(ObservableList<String> items) {
        valueList = items;
        if (valueList != null) {
            valueList.addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    refreshValues();
                }
            });
        }
        refreshValues();
    }

    public void goToItem(String item) {
        for (String string : valueList) {
            if (string.compareTo(item) == 0) {
                int valueIndex = valueList.indexOf(string);
                double yPosInVBox = (valueIndex * itemHeight) + ((valueIndex) * valueVBox.getSpacing());
                double scrollV = yPosInVBox / (valueVBox.getHeight() - valueScrollPane.getHeight());

                if (timeline.getStatus() == Animation.Status.RUNNING) {
                    timeline.stop();
                }
                KeyValue keyValue = new KeyValue(valueScrollPane.vvalueProperty(), scrollV, Interpolator.EASE_BOTH);
                KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);
                timeline.getKeyFrames().add(keyFrame);
                timeline.play();
            }
        }
    }

    public String getItem() {
        return "";
    }

    private void refreshValues() {
        Label label = null;
        valueVBox.getChildren().clear();
        if (valueList != null) {
            label = LabelBuilder.create().text("").alignment(Pos.CENTER).build();
            valueVBox.getChildren().add(label);
            label = LabelBuilder.create().text("").alignment(Pos.CENTER).build();
            valueVBox.getChildren().add(label);
            for (String value : valueList) {
                label = LabelBuilder.create().text(value).alignment(Pos.CENTER).build();
                valueVBox.getChildren().add(label);
            }
            label = LabelBuilder.create().text("").alignment(Pos.CENTER).build();
            valueVBox.getChildren().add(label);
            label = LabelBuilder.create().text("").alignment(Pos.CENTER).build();
            valueVBox.getChildren().add(label);
        }
        if (label != null) {
            label.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number1) {
                    itemHeight = number1.doubleValue();
                    overlayVBox.setSpacing(itemHeight);
                    overlayVBox.setPadding(new Insets(itemHeight, 0, itemHeight, 0));
                    setPrefHeight(itemHeight * 5 + 8);
                }
            });
        }
    }
}
