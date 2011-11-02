package com.zenjava.jfxee7;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloClient extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage) throws Exception
    {
        Injector injector = Guice.createInjector(new HelloClientModule());
        HelloController helloController = injector.getInstance(HelloController.class);
        Scene scene = new Scene((Parent) helloController.getView(), 320, 240);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.setTitle("JFX2.0 Sprung");
        stage.show();
    }
}
