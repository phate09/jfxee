package com.zenjava.playground.combo;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ComboBoxApplication extends Application
{
    public static void main(String[] args)
    {
        Application.launch(ComboBoxApplication.class, args);
    }

public void start(Stage stage) throws Exception
{
    FlowPane root = new FlowPane();
    root.setStyle("-fx-padding: 30");

    root.getChildren().add(new Label("Choose your destiny:"));

    ComboBox<String> combo = new ComboBox<String>();
    combo.setStyle("-fx-border-color: black; -fx-border-width: 1");
    combo.getItems().addAll("Make a difference", "Do no harm", "Start a Krispy Kreme franchise");
    combo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>()
    {
        public void changed(ObservableValue<? extends Object> source, Object oldValue, Object newValue)
        {
            System.out.println("Your destiny: " + newValue);
        }
    });
    root.getChildren().add(combo);

    Scene scene = new Scene(root, 300, 200);
    stage.setScene(scene);
    stage.show();
}
}
