package com.zenjava.firstcontact.gui.main;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class MainPresenter
{
    private static final Logger log = LoggerFactory.getLogger(MainPresenter.class);

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
        log.info("Showing SearchContacts page");
        contactSearchPresenter.search(null);
        contentArea.setCenter(contactSearchPresenter.getView());
    }

    public void showAddContact()
    {
        log.info("Showing AddContact page");
        contactDetailPresenter.setContact(null);
        contentArea.setCenter(contactDetailPresenter.getView());
    }

    public void showContactDetail(Long contactId)
    {
        log.info("Showing ContactDetail page for Contact with ID '{}'", contactId);
        contactDetailPresenter.setContact(contactId);
        contentArea.setCenter(contactDetailPresenter.getView());
    }
}
