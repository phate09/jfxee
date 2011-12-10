package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.detail.ContactDetailModel;
import com.zenjava.firstcontact.gui.detail.ContactDetailView;
import com.zenjava.firstcontact.gui.main.MainModel;
import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchModel;
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
            MainModel model = new MainModel();
            MainView view = new MainView(model);
            mainPresenter = new MainPresenter(model, view);
            mainPresenter.setContactDetailPresenter(getContactDetailPresenter());
            mainPresenter.setContactSearchPresenter(getContactSearchPresenter());
        }
        return mainPresenter;
    }

    public ContactSearchPresenter getContactSearchPresenter()
    {
        if (contactSearchPresenter == null)
        {
            ContactSearchModel model = new ContactSearchModel();
            ContactSearchView view = new ContactSearchView(model);
            contactSearchPresenter = new ContactSearchPresenter(
                    model, view, getContactService(), getMainPresenter());
        }
        return contactSearchPresenter;
    }

    public ContactDetailPresenter getContactDetailPresenter()
    {
        if (contactDetailPresenter == null)
        {
            ContactDetailModel model = new ContactDetailModel();
            ContactDetailView view = new ContactDetailView(model);
            contactDetailPresenter = new ContactDetailPresenter(
                    model, view, getContactService(), getMainPresenter());
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
