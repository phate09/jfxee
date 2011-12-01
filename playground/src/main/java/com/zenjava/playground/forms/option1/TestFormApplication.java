package com.zenjava.playground.forms.option1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestFormApplication extends Application
{
    public static void main(String[] args)
    {
        Application.launch(TestFormApplication.class, args);
    }

    public void start(Stage stage) throws Exception
    {
        BorderPane root = new BorderPane();

        PersonForm personForm = new PersonForm();
        root.setCenter(personForm);

        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().add("form-styles.css");
        stage.setScene(scene);
        stage.show();
    }
}
