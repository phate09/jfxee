package com.zenjava.playground.browser.demo2;

import com.zenjava.playground.browser.ActivityBrowser;
import com.zenjava.playground.browser.Place;
import com.zenjava.playground.browser.demo2.service.ContactsService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContactApplication extends Application
{
    // developer can define these anywhere - might need to think about a 'best practice' to define for them however
    public static final String SEARCH_CONTACTS_PLACE = "contact/search";
    public static final String VIEW_CONTACT_PLACE = "contact/view";

    public static void main(String[] args)
    {
        Application.launch(ContactApplication.class, args);
    }

    public void start(Stage stage) throws Exception
    {
        ContactsService contactsService = new ContactsService();

        ActivityBrowser browser = new ActivityBrowser();

        SearchContactsActivity searchContactsActivity = new SearchContactsActivity(
                browser.getNavigationManager(), contactsService);
        browser.registerActivity(SEARCH_CONTACTS_PLACE, searchContactsActivity);

        ViewContactActivity viewContactActivity = new ViewContactActivity(
                browser.getNavigationManager(), contactsService);
        browser.registerActivity(VIEW_CONTACT_PLACE, viewContactActivity);

        browser.getNavigationManager().goTo(new Place(SEARCH_CONTACTS_PLACE));

        Scene scene = new Scene(browser.getView(), 800, 600);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.show();
    }
}
