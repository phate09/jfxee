package com.zenjava.firstcontact.gui.search;

import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.service.Contact;
import com.zenjava.firstcontact.service.ContactService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.List;

public class ContactSearchPresenter
{
    private ContactSearchView view;
    private MainPresenter mainPresenter;
    private ContactService contactService;

    public ContactSearchPresenter(ContactSearchView view,
                                  MainPresenter mainPresenter,
                                  ContactService contactService)
    {
        this.view = view;
        this.mainPresenter = mainPresenter;
        this.contactService = contactService;
        this.view.setPresenter(this);
    }

    public ContactSearchView getView()
    {
        return view;
    }

    public void search()
    {
        String searchPhrase = view.getSearchPhrase();
        final String[] keywords = searchPhrase != null ? searchPhrase.split("\\s+") : null;
        final Task<List<Contact>> searchTask = new Task<List<Contact>>()
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
        mainPresenter.showContactDetail(contactId);
    }
}
