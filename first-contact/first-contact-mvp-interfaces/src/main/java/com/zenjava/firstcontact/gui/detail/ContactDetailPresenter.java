package com.zenjava.firstcontact.gui.detail;

public interface ContactDetailPresenter
{
    ContactDetailView getView();

    void setContact(final Long contactId);

    void cancel();

    void save();
}