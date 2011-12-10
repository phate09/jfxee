package com.zenjava.firstcontact.gui.detail;

import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.service.Contact;
import com.zenjava.firstcontact.service.ContactService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

public class ContactDetailPresenter
{
    private ContactDetailModel model;
    private ContactDetailView view;
    private ContactService contactService;
    private MainPresenter mainPresenter;

    public ContactDetailPresenter(ContactDetailModel model,
                                  ContactDetailView view,
                                  ContactService contactService,
                                  MainPresenter mainPresenter)
    {
        this.model = model;
        this.view = view;
        this.contactService = contactService;
        this.mainPresenter = mainPresenter;
        this.view.setPresenter(this);
    }

    public ContactDetailView getView()
    {
        return view;
    }

    public void setContact(final Long contactId)
    {
        model.clear();
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
                    model.setId(contact.getId());
                    model.setFirstName(contact.getFirstName());
                    model.setLastName(contact.getLastName());
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
        final Contact updatedContact = new Contact(
                model.getId(),
                model.getFirstName(),
                model.getLastName()
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
