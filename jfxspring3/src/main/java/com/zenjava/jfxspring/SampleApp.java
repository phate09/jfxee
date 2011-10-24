package com.zenjava.jfxspring;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
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

        HBox box = new HBox(10);
        EnterDetailsController enterDetailsController = context.getBean(EnterDetailsController.class);
        box.getChildren().add(enterDetailsController.getView());
        WelcomeController welcomeController = context.getBean(WelcomeController.class);
        box.getChildren().add(welcomeController.getView());

        Scene scene = new Scene(box, 600, 200);
        scene.getStylesheets().add("fxmlapp.css");
        stage.setScene(scene);
        stage.setTitle("JFX2.0 Sprung");
        stage.show();
    }
}
