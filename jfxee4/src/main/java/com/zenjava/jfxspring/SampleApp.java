package com.zenjava.jfxspring;

import javafx.application.Application;
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
        MainController mainController = context.getBean(MainController.class);
        Scene scene = new Scene(mainController.getView(), 600, 200);
        scene.getStylesheets().add("fxmlapp.css");
        stage.setScene(scene);
        stage.setTitle("JFX2.0 and JEE");
        stage.show();
    }
}
