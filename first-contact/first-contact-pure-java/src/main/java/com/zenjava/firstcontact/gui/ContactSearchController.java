package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.service.Contact;
import com.zenjava.firstcontact.service.ContactService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.List;

public class ContactSearchController
{
    private ContactSearchView view;
    private ContactService contactService;
    private MainController mainController;
    private Task<List<Contact>> searchTask;

    public void setView(ContactSearchView view)
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

    public ContactSearchView getView()
    {
        return view;
    }

    public void clear()
    {
        view.clear();
    }

    public void search(String searchPhrase)
    {
        if (searchTask != null)
        {
            searchTask.cancel();
        }

        final String[] keywords = searchPhrase != null ? searchPhrase.split("\\s+") : null;
        searchTask = new Task<List<Contact>>()
        {
            protected List<Contact> call() throws Exception
            {
                return contactService.searchContacts(keywords);
            }
        };

        searchTask.stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState)
            {
                if (newState.equals(Worker.State.SUCCEEDED))
                {
                    view.setSearchResults(searchTask.getValue());
                }
            }
        });

        new Thread(searchTask).start();
    }

    public void contactSelected(Long contactId)
    {
        mainController.showContactDetail(contactId);
    }
}
