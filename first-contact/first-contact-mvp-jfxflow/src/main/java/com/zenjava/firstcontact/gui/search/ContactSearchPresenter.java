package com.zenjava.firstcontact.gui.search;

import com.zenjava.firstcontact.service.Contact;
import com.zenjava.firstcontact.service.ContactService;
import com.zenjava.jfxflow.actvity.AbstractActivity;
import com.zenjava.jfxflow.navigation.NavigationManager;
import com.zenjava.jfxflow.navigation.PlaceBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ContactSearchPresenter extends AbstractActivity implements Initializable
{
    @FXML private TextField searchField;
    @FXML private ListView<Contact> resultsList;

    private NavigationManager navigationManager;
    private ContactService contactService;

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // unfortunately FXML does not currently have a clean way to set a custom CellFactory
        // like we need so we have to manually add this here in code
        resultsList.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>()
        {
            public ListCell<Contact> call(ListView<Contact> contactListView)
            {
                final ListCell<Contact> cell = new ListCell<Contact>()
                {
                    protected void updateItem(Contact contact, boolean empty)
                    {
                        super.updateItem(contact, empty);
                        if (!empty)
                        {
                            setText(String.format("%s %s", contact.getFirstName(), contact.getLastName()));
                        }
                    }
                };
                cell.setOnMouseClicked(new EventHandler<Event>()
                {
                    public void handle(Event event)
                    {
                        Contact contact = cell.getItem();
                        if (contact != null)
                        {
                            contactSelected(contact.getId());
                        }
                    }
                });
                return cell;
            }
        });
    }

    public void setNavigationManager(NavigationManager navigationManager)
    {
        this.navigationManager = navigationManager;
    }

    public void setContactService(ContactService contactService)
    {
        this.contactService = contactService;
    }

    protected void activated()
    {
        search(null);
    }

    public void search(ActionEvent event)
    {
        String searchPhrase = searchField.getText();
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
                    resultsList.getItems().setAll(searchTask.getValue());
                }
            }
        });
        executeTask(searchTask);
    }

    public void contactSelected(Long contactId)
    {
        navigationManager.goTo(new PlaceBuilder("detail")
                .parameter("contactId", contactId)
                .build());
    }
}
