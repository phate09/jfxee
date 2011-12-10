package com.zenjava.firstcontact.gui.search;

import com.zenjava.firstcontact.service.Contact;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;

public class ContactSearchViewImpl extends VBox implements ContactSearchView
{
    private ContactSearchPresenter presenter;
    private TextField searchField;
    private ListView<Contact> resultsList;

    public ContactSearchViewImpl()
    {
        buildView();
    }

    public Node getNode()
    {
        return this;
    }

    public void setPresenter(ContactSearchPresenter presenter)
    {
        this.presenter = presenter;
    }

    public String getSearchPhrase()
    {
        return searchField.getText();
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
        setSpacing(10);
        HBox searchBar = new HBox(10);

        searchBar.getChildren().add(new Label("Search"));
        searchField = new TextField();
        searchField.setPrefColumnCount(20);
        searchField.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                presenter.search();
            }
        });
        searchBar.getChildren().add(searchField);

        Button searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                presenter.search();
            }
        });
        searchBar.getChildren().add(searchButton);

        getChildren().add(searchBar);

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
                            presenter.contactSelected(contact.getId());
                        }
                    }
                });
                return cell;
            }
        });
        VBox.setVgrow(resultsList, Priority.ALWAYS);
        getChildren().add(resultsList);
    }
}
