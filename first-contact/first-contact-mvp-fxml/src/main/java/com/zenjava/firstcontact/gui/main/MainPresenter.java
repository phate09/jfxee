package com.zenjava.firstcontact.gui.main;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class MainPresenter
{
    @FXML private Parent root;
    @FXML private BorderPane contentArea;

    private ContactSearchPresenter contactSearchPresenter;
    private ContactDetailPresenter contactDetailPresenter;

    public void setContactSearchPresenter(ContactSearchPresenter contactSearchPresenter)
    {
        this.contactSearchPresenter = contactSearchPresenter;
    }

    public void setContactDetailPresenter(ContactDetailPresenter contactDetailPresenter)
    {
        this.contactDetailPresenter = contactDetailPresenter;
    }

    public Parent getView()
    {
        return root;
    }

    public void showSearchContacts()
    {
        contactSearchPresenter.search(null);
        contentArea.setCenter(contactSearchPresenter.getView());
    }

    public void showContactDetail(Long contactId)
    {
        contactDetailPresenter.setContact(contactId);
        contentArea.setCenter(contactDetailPresenter.getView());
    }
}
