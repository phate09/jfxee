package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;
import com.zenjava.firstcontact.service.ContactService;
import com.zenjava.firstcontact.service.SimpleContactService;
import com.zenjava.jfxflow.actvity.FxmlLoader;
import com.zenjava.jfxflow.control.Browser;
import com.zenjava.jfxflow.navigation.DefaultNavigationManager;
import com.zenjava.jfxflow.navigation.NavigationManager;
import com.zenjava.jfxflow.navigation.RegexPlaceResolver;

public class FirstContactAppFactory
{
    private FxmlLoader loader = new FxmlLoader();
    private NavigationManager navigationManager;
    private Browser browser;
    private ContactSearchPresenter contactSearchPresenter;
    private ContactDetailPresenter contactDetailPresenter;
    private ContactService contactService;

    public NavigationManager getNavigationManager()
    {
        if (navigationManager == null)
        {
            navigationManager = new DefaultNavigationManager();
        }
        return navigationManager;
    }


    public Browser getBrowser()
    {
        if (browser == null)
        {
            browser = new Browser(getNavigationManager());
            browser.setHeader(new BrowserHeader());
            browser.getPlaceResolvers().add(new RegexPlaceResolver("search", getContactSearchPresenter()));
            browser.getPlaceResolvers().add(new RegexPlaceResolver("detail", getContactDetailPresenter()));
        }
        return browser;
    }

    public ContactSearchPresenter getContactSearchPresenter()
    {
        if (contactSearchPresenter == null)
        {
            contactSearchPresenter = loader.load("/fxml/ContactSearch.fxml");
            contactSearchPresenter.setContactService(getContactService());
            contactSearchPresenter.setNavigationManager(getNavigationManager());
        }
        return contactSearchPresenter;
    }

    public ContactDetailPresenter getContactDetailPresenter()
    {
        if (contactDetailPresenter == null)
        {
            contactDetailPresenter = loader.load("/fxml/ContactDetail.fxml");
            contactDetailPresenter.setContactService(getContactService());
            contactDetailPresenter.setNavigationManager(navigationManager);
        }
        return contactDetailPresenter;
    }

    public ContactService getContactService()
    {
        if (contactService == null)
        {
            contactService = new SimpleContactService();
        }
        return contactService;
    }
}
