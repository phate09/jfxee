package com.zenjava.firstcontact.gui;

public class MainController
{
    private MainView view;
    private ContactSearchController contactSearchController;
    private ContactDetailController contactDetailController;

    public void initialise()
    {
        showSearchContacts();
    }

    public void setView(MainView view)
    {
        this.view = view;
    }

    public void setContactSearchController(ContactSearchController contactSearchController)
    {
        this.contactSearchController = contactSearchController;
    }

    public void setContactDetailController(ContactDetailController contactDetailController)
    {
        this.contactDetailController = contactDetailController;
    }

    public MainView getView()
    {
        return view;
    }

    public void showSearchContacts()
    {
        view.setCenter(contactSearchController.getView());
    }

    public void showContactDetail(Long contactId)
    {
        contactDetailController.setContact(contactId);
        view.setCenter(contactDetailController.getView());
    }
}
