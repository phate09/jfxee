package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.detail.ContactDetailPresenterImpl;
import com.zenjava.firstcontact.gui.detail.ContactDetailView;
import com.zenjava.firstcontact.gui.detail.ContactDetailViewImpl;
import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.gui.main.MainPresenterImpl;
import com.zenjava.firstcontact.gui.main.MainView;
import com.zenjava.firstcontact.gui.main.MainViewImpl;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenterImpl;
import com.zenjava.firstcontact.gui.search.ContactSearchView;
import com.zenjava.firstcontact.gui.search.ContactSearchViewImpl;
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
            MainView view = new MainViewImpl();
            MainPresenterImpl presenter = new MainPresenterImpl(view);
            mainPresenter = presenter;
            presenter.setContactDetailPresenter(getContactDetailPresenter());
            presenter.setContactSearchPresenter(getContactSearchPresenter());
            presenter.setView(view);
        }
        return mainPresenter;
    }

    public ContactSearchPresenter getContactSearchPresenter()
    {
        if (contactSearchPresenter == null)
        {
            ContactSearchView view = new ContactSearchViewImpl();
            contactSearchPresenter = new ContactSearchPresenterImpl(
                    view, getContactService(), getMainPresenter());
        }
        return contactSearchPresenter;
    }

    public ContactDetailPresenter getContactDetailPresenter()
    {
        if (contactDetailPresenter == null)
        {
            ContactDetailView view = new ContactDetailViewImpl();
            contactDetailPresenter = new ContactDetailPresenterImpl(
                    view, getMainPresenter(), getContactService());
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
