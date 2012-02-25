package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.gui.main.MainPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FirstContactApp extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage) throws Exception
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FirstContactAppFactory.class);
        MainPresenter mainPresenter = context.getBean(MainPresenter.class);
        mainPresenter.showSearchContacts();
        Scene scene = new Scene(mainPresenter.getView(), 800, 600);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.setTitle("First Contact - JavaFX Contact Management");
        stage.show();
    }
}
