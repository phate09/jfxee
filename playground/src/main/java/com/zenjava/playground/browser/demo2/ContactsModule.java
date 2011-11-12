package com.zenjava.playground.browser.demo2;

import com.google.inject.AbstractModule;
import com.zenjava.playground.browser.ActivityBrowser;
import com.zenjava.playground.browser.FxmlActivityLoader;
import com.zenjava.playground.browser.NavigationManager;

public class ContactsModule extends AbstractModule
{
    // developer can define these anywhere - might need to think about a 'best practice' to define for them however
    public static final String SEARCH_CONTACTS_PLACE = "contact/search";
    public static final String VIEW_CONTACT_PLACE = "contact/view";

    protected void configure()
    {
        FxmlActivityLoader loader = new FxmlActivityLoader();

        SearchContactsActivity searchContactsActivity = (SearchContactsActivity) loader.loadActivity("/fxml/SearchContacts.fxml");
        bind(SearchContactsActivity.class).toInstance(searchContactsActivity);

        ViewContactActivity viewContactActivity = (ViewContactActivity) loader.loadActivity("/fxml/ViewContact.fxml");
        bind(ViewContactActivity.class).toInstance(viewContactActivity);

        ActivityBrowser browser = new ActivityBrowser();
        browser.registerActivity(SEARCH_CONTACTS_PLACE, searchContactsActivity);
        browser.registerActivity(VIEW_CONTACT_PLACE, viewContactActivity);
        bind(ActivityBrowser.class).toInstance(browser);
        bind(NavigationManager.class).toInstance(browser.getNavigationManager());
    }
}
