package com.zenjava.firstcontact.gui.search;

import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.service.Contact;
import com.zenjava.firstcontact.service.ContactService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ContactSearchPresenter implements Initializable
{
    @FXML private Node root;
    @FXML private TextField searchField;
    @FXML private ListView<Contact> resultsList;

    @Inject private MainPresenter mainPresenter;
    @Inject private ContactService contactService;

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

    public Node getView()
    {
        return root;
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
                else if (newState.equals(Worker.State.FAILED))
                {
                    searchTask.getException().printStackTrace();
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
