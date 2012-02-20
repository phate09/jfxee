package com.zenjava.firstcontact.gui.detail;

import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.service.Contact;
import com.zenjava.firstcontact.service.ContactService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class ContactDetailPresenter
{
    @FXML private Node root;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;

    @Inject private ContactService contactService;
    @Inject private MainPresenter mainPresenter;

    private Long contactId;

    public void setContactService(ContactService contactService)
    {
        this.contactService = contactService;
    }

    public void setMainPresenter(MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
    }

    public Node getView()
    {
        return root;
    }
    
    public void setContact(final Long contactId)
    {
        this.contactId = contactId;
        firstNameField.setText("");
        lastNameField.setText("");
        final Task<Contact> loadTask = new Task<Contact>()
        {
            protected Contact call() throws Exception
            {
                return contactService.getContact(contactId);
            }
        };

        loadTask.stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState)
            {
                if (newState.equals(Worker.State.SUCCEEDED))
                {
                    Contact contact = loadTask.getValue();
                    firstNameField.setText(contact.getFirstName());
                    lastNameField.setText(contact.getLastName());
                }
            }
        });

        new Thread(loadTask).start();
    }

    public void cancel(ActionEvent event)
    {
        mainPresenter.showSearchContacts();
    }

    public void save(ActionEvent event)
    {
        final Contact updatedContact = new Contact(
                contactId,
                firstNameField.getText(),
                lastNameField.getText()
        );
        final Task<Contact> saveTask = new Task<Contact>()
        {
            protected Contact call() throws Exception
            {
                return contactService.updateContact(updatedContact);
            }
        };

        saveTask.stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState)
            {
                if (newState.equals(Worker.State.SUCCEEDED))
                {
                    mainPresenter.showSearchContacts();
                }
            }
        });

        new Thread(saveTask).start();
    }
}
