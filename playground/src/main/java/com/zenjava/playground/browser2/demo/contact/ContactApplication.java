package com.zenjava.playground.browser2.demo.contact;

import com.zenjava.playground.browser2.navigation.Browser;
import com.zenjava.playground.browser2.navigation.Place;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContactApplication extends Application
{
    public static void main(String[] args)
    {
        Application.launch(ContactApplication.class, args);
    }

    public void start(Stage stage) throws Exception
    {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(ContactsApplicationFactory.class);
        Browser browser = context.getBean(Browser.class);
        browser.getNavigationManager().goTo(new Place(ContactsApplicationFactory.SEARCH_CONTACTS_PLACE));
        Scene scene = new Scene(browser.getNode(), 800, 600);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.show();
    }
}
