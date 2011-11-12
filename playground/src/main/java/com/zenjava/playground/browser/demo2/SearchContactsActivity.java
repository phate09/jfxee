package com.zenjava.playground.browser.demo2;

import com.zenjava.playground.browser.AbstractActivity;
import com.zenjava.playground.browser.NavigationManager;
import com.zenjava.playground.browser.Place;
import com.zenjava.playground.browser.control.PlaceHyperlink;
import com.zenjava.playground.browser.demo2.service.Contact;
import com.zenjava.playground.browser.demo2.service.ContactsService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.util.List;

public class SearchContactsActivity extends AbstractActivity<Node>
{
    private NavigationManager navigationManager;
    private ContactsService contactsService;

    private TextField searchField;
    private ListView<Contact> resultsList;

    public SearchContactsActivity(NavigationManager navigationManager, ContactsService contactsService)
    {
        this.navigationManager = navigationManager;
        this.contactsService = contactsService;
        setView(buildView());
    }

    protected void activate()
    {
        search();
    }

    protected void search()
    {
        final String[] keywords = searchField.getText().split("\\s+");
        final Task<List<Contact>> task = new Task<List<Contact>>()
        {
            protected List<Contact> call() throws Exception
            {
                return contactsService.searchContacts(keywords);
            }
        };
        task.stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState)
            {
                if (newState.equals(Worker.State.SUCCEEDED))
                {
                    resultsList.getItems().setAll(task.getValue());
                }
            }
        });
        new Thread(task).start();
    }


    private Node buildView()
    {
        VBox rootPane = new VBox(10);
        rootPane.getStyleClass().add("search-contacts");

        Label titleLabel = new Label("Search Contacts");
        titleLabel.getStyleClass().add("title");
        rootPane.getChildren().add(titleLabel);

        Node searchArea = buildSearchArea();
        BorderPane.setMargin(searchArea, new Insets(0, 0, 10, 0));
        rootPane.getChildren().add(searchArea);

        Node resultsArea = buildResultsArea();
        VBox.setVgrow(resultsArea, Priority.ALWAYS);
        rootPane.getChildren().add(resultsArea);

        return rootPane;
    }

    private Node buildSearchArea()
    {
        HBox box = new HBox(10);

        searchField = new TextField();
        searchField.textProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> source, String oldValue, String newValue)
            {
                search();
            }
        });
        searchField.setPrefColumnCount(25);
        box.getChildren().add(searchField);

        Button searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                search();
            }
        });
        box.getChildren().add(searchButton);

        return box;
    }

    private Node buildResultsArea()
    {
        resultsList = new ListView<Contact>();
        resultsList.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>()
        {
            public ListCell<Contact> call(ListView<Contact> contactListView)
            {
                final ResultRow resultRow = new ResultRow();
                return new ListCell<Contact>()
                {
                    protected void updateItem(Contact contact, boolean empty)
                    {
                        super.updateItem(contact, empty);
                        setGraphic(empty ? null : resultRow);
                        resultRow.setContact(contact);
                    }
                };
            }
        });

        return resultsList;
    }

    //-------------------------------------------------------------------------

    private class ResultRow extends GridPane
    {
        private Image noPhotoImage;
        private ImageView photo;
        private PlaceHyperlink nameLink;
        private Label companyLabel;

        private ResultRow()
        {
            setHgap(10);
            setVgap(10);

            noPhotoImage = new Image(getClass().getResourceAsStream("/images/no-photo.jpg"));
            photo = new ImageView(noPhotoImage);
            add(photo, 0, 0, 1, 2);

            nameLink = new PlaceHyperlink(navigationManager);
            add(nameLink, 1, 0);

            companyLabel = new Label();
            companyLabel.getStyleClass().add("company");
            add(companyLabel, 1, 1);
        }

        public void setContact(Contact contact)
        {
            if (contact != null)
            {
                nameLink.setText(String.format("%s %s", contact.getFirstName(), contact.getLastName()));
                Place place = new Place(ContactApplication.VIEW_CONTACT_PLACE);
                place.getParameters().put("contactId", contact.getContactId());
                nameLink.setPlace(place);

                companyLabel.setText(contact.getCompany());
            }
            else
            {
                nameLink.setText(null);
                nameLink.setPlace(null);
                companyLabel.setText(null);
                photo.setImage(noPhotoImage);
            }
        }
    }
}
