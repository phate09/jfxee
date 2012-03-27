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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ContactDetailPresenter
{
    private static final Logger log = LoggerFactory.getLogger(ContactDetailPresenter.class);

    @FXML private Node root;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;

    @Inject private ContactService contactService;
    @Inject private MainPresenter mainPresenter;

    private Long contactId;

    public Node getView()
    {
        return root;
    }

    public void setContact(final Long contactId)
    {
        this.contactId = contactId;
        firstNameField.setText("");
        lastNameField.setText("");

        if (contactId != null)
        {
            final Task<Contact> loadTask = new Task<Contact>()
            {
                protected Contact call() throws Exception
                {
                    log.info("Loading details for Contact with ID '{}'", contactId);
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
                        log.info("Contact details loaded, firstName='{}', lastName='{}'",
                                contact.getFirstName(), contact.getLastName());
                        firstNameField.setText(contact.getFirstName());
                        lastNameField.setText(contact.getLastName());
                    }
                    else if (newState.equals(Worker.State.FAILED))
                    {
                        Throwable exception = loadTask.getException();
                        log.error(String.format("Failed to load details for Contact with ID '%s'", contactId), exception);
                    }
                }
            });

            new Thread(loadTask).start();
        }
    }

    public void cancel(ActionEvent event)
    {
        log.info("Saving of ContactDetails was cancelled");
        mainPresenter.showSearchContacts();
    }

    public void save(ActionEvent event)
    {
        log.info("Saving ContactDetails to server");

        final Contact updatedContact = new Contact(
                contactId,
                firstNameField.getText(),
                lastNameField.getText()
        );
        final Task<Contact> saveTask = new Task<Contact>()
        {
            protected Contact call() throws Exception
            {
                log.debug("Updating details for Contact with ID '{}', firstName='{}', lastName='{}'", new Object[] {
                        contactId, updatedContact.getFirstName(), updatedContact.getLastName()
                });
                return contactService.updateContact(updatedContact);
            }
        };

        saveTask.stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState)
            {
                if (newState.equals(Worker.State.SUCCEEDED))
                {
                    log.info("Contact details successfully updated");
                    mainPresenter.showSearchContacts();
                }
                else if (newState.equals(Worker.State.FAILED))
                {
                    Throwable exception = saveTask.getException();
                    log.error("Failed to save contact details", exception);
                }
            }
        });

        new Thread(saveTask).start();
    }
}
