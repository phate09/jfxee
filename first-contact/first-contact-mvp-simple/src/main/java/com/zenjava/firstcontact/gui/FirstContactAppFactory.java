package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.detail.ContactDetailView;
import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchView;
import com.zenjava.firstcontact.gui.main.MainView;
import com.zenjava.firstcontact.service.ContactService;
import com.zenjava.firstcontact.service.SimpleContactService;

public class FirstContactAppFactory
{
    private MainPresenter mainPresenter;
    private ContactSearchPresenter contactSearchPresenter;
    private ContactDetailPresenter contactDetailPresenter;
    private ContactService contactService;

    public MainPresenter getMainPresenter()
    {
        if (mainPresenter == null)
        {
            MainView view = new MainView();
            mainPresenter = new MainPresenter(view);
            mainPresenter.setContactDetailPresenter(getContactDetailPresenter());
            mainPresenter.setContactSearchPresenter(getContactSearchPresenter());
        }
        return mainPresenter;
    }

    public ContactSearchPresenter getContactSearchPresenter()
    {
        if (contactSearchPresenter == null)
        {
            ContactSearchView view = new ContactSearchView();
            contactSearchPresenter = new ContactSearchPresenter(
                    view, getMainPresenter(), getContactService());
        }
        return contactSearchPresenter;
    }

    public ContactDetailPresenter getContactDetailPresenter()
    {
        if (contactDetailPresenter == null)
        {
            ContactDetailView view = new ContactDetailView();
            contactDetailPresenter = new ContactDetailPresenter(
                    view, getContactService(), getMainPresenter());
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
