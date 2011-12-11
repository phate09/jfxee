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
import com.zenjava.jfxflow.worker.DefaultErrorHandler;
import com.zenjava.jfxflow.worker.ErrorHandler;

public class FirstContactAppFactory
{
    private FxmlLoader loader = new FxmlLoader();
    private NavigationManager navigationManager;
    private ErrorHandler errorHandler;
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

    public ErrorHandler getErrorHandler()
    {
        if (errorHandler == null)
        {
            errorHandler = new DefaultErrorHandler();
        }
        return errorHandler;
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
            contactSearchPresenter.setErrorHandler(getErrorHandler());
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
            contactDetailPresenter.setErrorHandler(getErrorHandler());
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
