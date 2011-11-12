package com.zenjava.playground.browser.demo2;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zenjava.playground.browser.ActivityBrowser;
import com.zenjava.playground.browser.Place;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContactApplication extends Application
{
    public static void main(String[] args)
    {
        Application.launch(ContactApplication.class, args);
    }

    public void start(Stage stage) throws Exception
    {
        Injector injector = Guice.createInjector(new ContactsModule());
        ActivityBrowser browser = injector.getInstance(ActivityBrowser.class);
        browser.getNavigationManager().goTo(new Place(ContactsModule.SEARCH_CONTACTS_PLACE));
        Scene scene = new Scene(browser.getView(), 800, 600);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.show();
    }
}
