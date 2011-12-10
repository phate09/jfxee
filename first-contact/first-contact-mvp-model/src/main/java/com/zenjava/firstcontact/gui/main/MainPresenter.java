package com.zenjava.firstcontact.gui.main;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;

public class MainPresenter
{
    private MainModel model;
    private MainView view;
    private ContactSearchPresenter contactSearchPresenter;
    private ContactDetailPresenter contactDetailPresenter;

    public MainPresenter(MainModel model, MainView view)
    {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void setContactSearchPresenter(ContactSearchPresenter contactSearchPresenter)
    {
        this.contactSearchPresenter = contactSearchPresenter;
    }

    public void setContactDetailPresenter(ContactDetailPresenter contactDetailPresenter)
    {
        this.contactDetailPresenter = contactDetailPresenter;
    }

    public MainView getView()
    {
        return view;
    }

    public void showSearchContacts()
    {
        contactSearchPresenter.search();
        model.setContent(contactSearchPresenter.getView());
    }

    public void showContactDetail(Long contactId)
    {
        contactDetailPresenter.setContact(contactId);
        model.setContent(contactDetailPresenter.getView());
    }
}
