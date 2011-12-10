package com.zenjava.firstcontact.gui.search;

public interface ContactSearchPresenter
{
    ContactSearchView getView();

    void search();

    void contactSelected(Long contactId);
}
