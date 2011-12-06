package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.service.ContactService;
import com.zenjava.firstcontact.service.SimpleContactService;

public class FirstContactAppFactory
{
    private MainController mainController;
    private ContactSearchController contactSearchController;
    private ContactDetailController contactDetailController;
    private ContactService contactService;

    public MainController getMainController()
    {
        if (mainController == null)
        {
            MainView view = new MainView();
            mainController = new MainController();
            mainController.setContactDetailController(getContactDetailController());
            mainController.setContactSearchController(getContactSearchController());
            mainController.setView(view);
            mainController.initialise();
        }
        return mainController;
    }

    public ContactSearchController getContactSearchController()
    {
        if (contactSearchController == null)
        {
            ContactSearchView view = new ContactSearchView();
            contactSearchController = new ContactSearchController();
            contactSearchController.setContactService(getContactService());
            contactSearchController.setMainController(getMainController());
            contactSearchController.setView(view);
            view.setController(contactSearchController);
        }
        return contactSearchController;
    }

    public ContactDetailController getContactDetailController()
    {
        if (contactDetailController == null)
        {
            ContactDetailView view = new ContactDetailView();
            contactDetailController = new ContactDetailController();
            contactDetailController.setContactService(getContactService());
            contactDetailController.setMainController(getMainController());
            contactDetailController.setView(view);
            view.setController(contactDetailController);
        }
        return contactDetailController;
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
