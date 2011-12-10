package com.zenjava.firstcontact.gui.main;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;

public class MainPresenterImpl implements MainPresenter
{
    private MainView view;
    private ContactSearchPresenter contactSearchPresenter;
    private ContactDetailPresenter contactDetailPresenter;

    public MainPresenterImpl(MainView view)
    {
        this.view = view;
        this.view.setPresenter(this);
    }

    public void setView(MainView view)
    {
        this.view = view;
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
        view.setContent(contactSearchPresenter.getView().getNode());
    }

    public void showContactDetail(Long contactId)
    {
        contactDetailPresenter.setContact(contactId);
        view.setContent(contactDetailPresenter.getView().getNode());
    }
}
