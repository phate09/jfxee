package com.zenjava.firstcontact.gui.main;

public interface MainPresenter
{
    MainView getView();

    void showSearchContacts();

    void showContactDetail(Long contactId);
}
