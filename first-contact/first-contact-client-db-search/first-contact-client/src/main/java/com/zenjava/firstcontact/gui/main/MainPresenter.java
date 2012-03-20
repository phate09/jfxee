package com.zenjava.firstcontact.gui.main;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;

public class MainPresenter
{
    @FXML private Parent root;
    @FXML private BorderPane contentArea;

    @Inject private ContactSearchPresenter contactSearchPresenter;
    @Inject private ContactDetailPresenter contactDetailPresenter;

    public Parent getView()
    {
        return root;
    }

    public void showSearchContacts()
    {
        contactSearchPresenter.search(null);
        contentArea.setCenter(contactSearchPresenter.getView());
    }

    public void showAddContact()
    {
        contactDetailPresenter.setContact(null);
        contentArea.setCenter(contactDetailPresenter.getView());
    }

    public void showContactDetail(Long contactId)
    {
        contactDetailPresenter.setContact(contactId);
        contentArea.setCenter(contactDetailPresenter.getView());
    }
}
