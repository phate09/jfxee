package com.zenjava.playground.browser2.demo.contact;

import com.zenjava.playground.browser2.activity.AbstractActivity;
import com.zenjava.playground.browser2.activity.FxmlLoadException;
import com.zenjava.playground.browser2.demo.contact.service.Contact;
import com.zenjava.playground.browser2.demo.contact.service.ContactsService;
import com.zenjava.playground.browser2.navigation.NavigationManager;
import com.zenjava.playground.browser2.navigation.Place;
import com.zenjava.playground.browser2.navigation.control.PlaceHyperlink;
import com.zenjava.playground.browser2.worker.BackgroundTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchContactsActivity extends AbstractActivity<Node> implements Initializable
{
    @Autowired private NavigationManager navigationManager;
    @Autowired private ContactsService contactsService;

    @FXML private TextField searchField;
    @FXML private ListView<Contact> resultsList;

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        searchField.textProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> source, String oldValue, String newValue)
            {
                search(null);
            }
        });

        resultsList.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>()
        {
            public ListCell<Contact> call(ListView<Contact> contactListView)
            {
                String fxmlFile = "/fxml2/SearchContactsRow.fxml";
                InputStream fxmlStream = null;
                try
                {
                    fxmlStream = getClass().getResourceAsStream(fxmlFile);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setBuilderFactory(new JavaFXBuilderFactory());
                    final Node view = (Node) loader.load(fxmlStream);
                    final ResultRowController controller = (ResultRowController) loader.getController();
                    controller.setNavigationManager(navigationManager);
                    return new ListCell<Contact>()
                    {
                        protected void updateItem(Contact contact, boolean empty)
                        {
                            super.updateItem(contact, empty);
                            setGraphic(empty ? null : view);
                            controller.setContact(contact);
                        }
                    };
                }
                catch (Exception e)
                {
                    // map checked exception to a runtime exception - this is a system failure, not a business logic failure
                    // so using checked exceptions for this is not necessary.
                    throw new FxmlLoadException(String.format(
                            "Unable to load FXML from '%s': %s", fxmlFile, e.getMessage()), e);
                }
                finally
                {
                    if (fxmlStream != null)
                    {
                        try
                        {
                            fxmlStream.close();
                        }
                        catch (IOException e)
                        {
                            System.err.println("WARNING: error closing FXML stream: " + e);
                            e.printStackTrace(System.err);
                        }
                    }
                }
            }
        });
    }

    protected void activated()
    {
        search(null);
    }

    public void search(ActionEvent event)
    {
        final String[] keywords = searchField.getText().split("\\s+");
        final BackgroundTask<List<Contact>> task = new BackgroundTask<List<Contact>>()
        {
            protected List<Contact> call() throws Exception
            {
                return contactsService.searchContacts(keywords);
            }

            protected void onSuccess(List<Contact> results)
            {
                resultsList.getItems().setAll(results);
            }
        };
        executeTask(task);
    }

    //-------------------------------------------------------------------------

    public static class ResultRowController
    {
        @FXML private ImageView photo;
        @FXML private PlaceHyperlink nameLink;
        @FXML private Label companyLabel;

        private Image noPhotoImage;

        public ResultRowController()
        {
            noPhotoImage = new Image(getClass().getResourceAsStream("/images/no-photo.jpg"));
        }

        public void setNavigationManager(NavigationManager navigationManager)
        {
            nameLink.setNavigationManager(navigationManager);
        }

        public void setContact(Contact contact)
        {
            if (contact != null)
            {
                nameLink.setText(String.format("%s %s", contact.getFirstName(), contact.getLastName()));
                Place place = new Place(ContactsApplicationFactory.VIEW_CONTACT_PLACE);
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
