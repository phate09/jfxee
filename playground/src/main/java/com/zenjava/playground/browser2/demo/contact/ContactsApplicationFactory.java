package com.zenjava.playground.browser2.demo.contact;

import com.zenjava.playground.browser2.activity.FxmlLoader;
import com.zenjava.playground.browser2.demo.contact.service.ContactsService;
import com.zenjava.playground.browser2.navigation.Browser;
import com.zenjava.playground.browser2.navigation.DefaultNavigationManager;
import com.zenjava.playground.browser2.navigation.NavigationManager;
import com.zenjava.playground.browser2.navigation.RegexPlaceResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactsApplicationFactory
{
    // developer can define these anywhere - might need to think about a 'best practice' to define for them however
    public static final String SEARCH_CONTACTS_PLACE = "contact/search";
    public static final String VIEW_CONTACT_PLACE = "contact/view";

    private FxmlLoader loader = new FxmlLoader();

    @Bean
    public Browser browser()
    {
        Browser browser = new Browser(navigationManager());
        browser.getPlaceResolvers().add(new RegexPlaceResolver(SEARCH_CONTACTS_PLACE, searchContactsActivity()));
        browser.getPlaceResolvers().add(new RegexPlaceResolver(VIEW_CONTACT_PLACE, viewContactActivity()));
        return browser;
    }

    @Bean
    public NavigationManager navigationManager()
    {
        return new DefaultNavigationManager();
    }

    @Bean
    public SearchContactsActivity searchContactsActivity()
    {
        return (SearchContactsActivity) loader.load("/fxml2/SearchContacts.fxml");
    }

    @Bean
    public ViewContactActivity viewContactActivity()
    {
        return (ViewContactActivity) loader.load("/fxml2/ViewContact.fxml");
    }

    @Bean
    public ContactsService contactsService()
    {
        return new ContactsService();
    }
}
