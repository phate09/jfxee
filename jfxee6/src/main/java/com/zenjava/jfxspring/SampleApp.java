package com.zenjava.jfxspring;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SampleApp extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage) throws Exception
    {
        Injector injector = Guice.createInjector(new SampleAppModule());
        SampleController sampleController = injector.getInstance(SampleController.class);
        Scene scene = new Scene((Parent) sampleController.getView(), 320, 240);
        scene.getStylesheets().add("fxmlapp.css");
        stage.setScene(scene);
        stage.setTitle("JFX2.0 Sprung");
        stage.show();
    }
}
