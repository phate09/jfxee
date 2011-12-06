package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.service.Contact;
import com.zenjava.firstcontact.service.ContactService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

public class ContactDetailController
{
    private ContactDetailView view;
    private ContactService contactService;
    private MainController mainController;
    private Task<Contact> loadTask;

    public void setView(ContactDetailView view)
    {
        this.view = view;
    }

    public void setContactService(ContactService contactService)
    {
        this.contactService = contactService;
    }

    public void setMainController(MainController mainController)
    {
        this.mainController = mainController;
    }

    public ContactDetailView getView()
    {
        return view;
    }

    public void setContact(final Long contactId)
    {
        if (loadTask != null)
        {
            loadTask.cancel();
        }

        view.clear();
        loadTask = new Task<Contact>()
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
                    view.setContact(loadTask.getValue());
                }
            }
        });

        new Thread(loadTask).start();
    }

    public void goBack()
    {
        mainController.showSearchContacts();
    }
}
