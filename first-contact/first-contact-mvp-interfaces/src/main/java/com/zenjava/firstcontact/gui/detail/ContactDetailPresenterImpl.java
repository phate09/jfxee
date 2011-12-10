package com.zenjava.firstcontact.gui.detail;

import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.service.Contact;
import com.zenjava.firstcontact.service.ContactService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

public class ContactDetailPresenterImpl implements ContactDetailPresenter
{
    private ContactDetailView view;
    private MainPresenter mainPresenter;
    private ContactService contactService;

    public ContactDetailPresenterImpl(ContactDetailView view,
                                      MainPresenter mainPresenter,
                                      ContactService contactService)
    {
        this.view = view;
        this.mainPresenter = mainPresenter;
        this.contactService = contactService;
        this.view.setPresenter(this);
    }

    public ContactDetailView getView()
    {
        return view;
    }

    public void setContact(final Long contactId)
    {
        view.setContact(null);
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
                    view.setContact(loadTask.getValue());
                }
            }
        });

        new Thread(loadTask).start();
    }

    public void cancel()
    {
        mainPresenter.showSearchContacts();
    }

    public void save()
    {
        final Contact updatedContact = view.getContact();
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
