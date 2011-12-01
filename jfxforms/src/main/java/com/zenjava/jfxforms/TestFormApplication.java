package com.zenjava.jfxforms;

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

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.show();
    }
}
