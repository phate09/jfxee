package com.zenjava.firstcontact.gui.search;

import com.zenjava.firstcontact.service.Contact;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ContactSearchView extends VBox
{
    private ContactSearchModel model;
    private ContactSearchPresenter presenter;
    private TextField searchField;
    private ListView<Contact> resultsList;

    public ContactSearchView(ContactSearchModel model)
    {
        this.model = model;
        buildView();
    }

    public void setPresenter(ContactSearchPresenter presenter)
    {
        this.presenter = presenter;
    }

    protected void buildView()
    {
        setSpacing(10);
        HBox searchBar = new HBox(10);

        searchBar.getChildren().add(new Label("Search"));
        searchField = new TextField();
        searchField.textProperty().bindBidirectional(model.searchPhraseProperty());
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
        model.getSearchResults().addListener(new ListChangeListener<Contact>()
        {
            public void onChanged(Change<? extends Contact> change)
            {
                // just being lazy here - it would be more efficient to change only the updated values
                resultsList.getItems().setAll(ContactSearchView.this.model.getSearchResults());
            }
        });
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
        BorderPane.setMargin(resultsList, new Insets(10, 10, 10, 10));
        VBox.setVgrow(resultsList, Priority.ALWAYS);
        getChildren().add(resultsList);
    }
}
