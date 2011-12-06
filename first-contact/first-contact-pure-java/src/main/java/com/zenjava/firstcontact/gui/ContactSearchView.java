package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.service.Contact;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

import java.util.List;

public class ContactSearchView extends BorderPane
{
    private ContactSearchController controller;
    private TextField searchField;
    private ListView<Contact> resultsList;

    public ContactSearchView()
    {
        buildView();
    }

    public void setController(ContactSearchController controller)
    {
        this.controller = controller;
    }

    public void clear()
    {
        searchField.setText("");
        resultsList.getItems().clear();
    }

    public void setSearchResults(List<Contact> searchResults)
    {
        resultsList.getItems().clear();
        if (searchResults != null)
        {
            resultsList.getItems().addAll(searchResults);
        }
    }

    protected void buildView()
    {
        HBox searchBar = new HBox(10);

        searchBar.getChildren().add(new Label("Search"));
        searchField = new TextField();
        searchField.setPrefColumnCount(20);
        searchField.textProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> source, String oldValue, String newValue)
            {
                controller.search(newValue);
            }
        });
        searchBar.getChildren().add(searchField);

        BorderPane.setMargin(searchBar, new Insets(10, 10, 10, 10));
        setTop(searchBar);

        resultsList = new ListView<Contact>();
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
                            controller.contactSelected(contact.getId());
                        }
                    }
                });
                return cell;
            }
        });
        BorderPane.setMargin(resultsList, new Insets(10, 10, 10, 10));
        HBox.setHgrow(resultsList, Priority.ALWAYS);
        setCenter(resultsList);
    }
}
