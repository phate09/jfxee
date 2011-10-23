package com.zenjava.jfxspring;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SampleApp extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage) throws Exception
    {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(SampleAppFactory.class);

        SampleController sampleController = context.getBean(SampleController.class);
        Scene scene = new Scene((Parent) sampleController.getView(), 320, 240);
        scene.getStylesheets().add("fxmlapp.css");
        stage.setScene(scene);
        stage.setTitle("JFX2.0 Sprung");
        stage.show();
    }
}
