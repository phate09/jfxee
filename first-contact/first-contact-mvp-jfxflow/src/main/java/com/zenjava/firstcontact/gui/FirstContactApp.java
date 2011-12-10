package com.zenjava.firstcontact.gui;

import com.zenjava.jfxflow.control.Browser;
import com.zenjava.jfxflow.navigation.Place;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FirstContactApp extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage) throws Exception
    {
        FirstContactAppFactory factory = new FirstContactAppFactory();
        Browser browser = factory.getBrowser();
        browser.getNavigationManager().goTo(new Place("search"));
        Scene scene = new Scene(browser, 800, 600);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.setTitle("First Contact - JavaFX Contact Management");
        stage.show();
    }
}
