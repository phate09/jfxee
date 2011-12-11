package com.zenjava.firstcontact.gui.detail;

import com.zenjava.firstcontact.service.Contact;
import com.zenjava.firstcontact.service.ContactService;
import com.zenjava.jfxflow.actvity.AbstractActivity;
import com.zenjava.jfxflow.actvity.Param;
import com.zenjava.jfxflow.navigation.NavigationManager;
import com.zenjava.jfxflow.worker.BackgroundTask;
import com.zenjava.jfxflow.worker.ErrorHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ContactDetailPresenter extends AbstractActivity
{
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;

    @Param private Long contactId;

    private ContactService contactService;
    private NavigationManager navigationManager;
    private ErrorHandler errorHandler;

    public void setContactService(ContactService contactService)
    {
        this.contactService = contactService;
    }

    public void setNavigationManager(NavigationManager navigationManager)
    {
        this.navigationManager = navigationManager;
    }

    public void setErrorHandler(ErrorHandler errorHandler)
    {
        this.errorHandler = errorHandler;
    }

    protected void activated()
    {
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
        navigationManager.goBack();
    }

    public void save(ActionEvent event)
    {
        final Contact updatedContact = new Contact(
                contactId,
                firstNameField.getText(),
                lastNameField.getText()
        );
        final BackgroundTask<Contact> saveTask = new BackgroundTask<Contact>(errorHandler)
        {
            protected Contact call() throws Exception
            {
                return contactService.updateContact(updatedContact);
            }

            protected void onSuccess(Contact value)
            {
                navigationManager.goBack();
            }
        };
        executeTask(saveTask);
    }
}
