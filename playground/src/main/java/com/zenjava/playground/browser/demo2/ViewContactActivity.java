package com.zenjava.playground.browser.demo2;

import com.zenjava.playground.browser.AbstractActivity;
import com.zenjava.playground.browser.ActivityParameter;
import com.zenjava.playground.browser.NavigationManager;
import com.zenjava.playground.browser.demo2.service.Contact;
import com.zenjava.playground.browser.demo2.service.ContactsService;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ViewContactActivity extends AbstractActivity<Node>
{
    @ActivityParameter private LongProperty contactId;

    private NavigationManager navigationManager;
    private ContactsService contactsService;

    private Label titleLabel;
    private Label firstNameField;
    private Label lastNameField;
    private Label companyField;
    private ImageView photo;
    private Image noPhotoImage;

    public ViewContactActivity(NavigationManager navigationManager, ContactsService contactsService)
    {
        this.navigationManager = navigationManager;
        this.contactsService = contactsService;
        this.contactId = new SimpleLongProperty();
        setView(buildView());
    }

    protected void setContact(Contact contact)
    {
        if (contact != null)
        {
            titleLabel.setText(String.format("%s %s", contact.getFirstName(), contact.getLastName()));
            firstNameField.setText(contact.getFirstName());
            lastNameField.setText(contact.getLastName());
            companyField.setText(contact.getCompany());
        }
        else
        {
            titleLabel.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            companyField.setText("");
        }
    }

    protected void activate()
    {
        setContact(null);

        final long contactId = this.contactId.get();
        final Task<Contact> task = new Task<Contact>()
        {
            protected Contact call() throws Exception
            {
                return contactsService.getContact(contactId);
            }
        };
        task.stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState)
            {
                if (newState.equals(Worker.State.SUCCEEDED))
                {
                    setContact(task.getValue());
                }
            }
        });
        new Thread(task).start();
    }

    private Node buildView()
    {
        VBox rootPane = new VBox(20);
        rootPane.getStyleClass().add("view-contact");

        titleLabel = new Label();
        titleLabel.getStyleClass().add("title");
        rootPane.getChildren().add(titleLabel);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(4);

        noPhotoImage = new Image(getClass().getResourceAsStream("/images/no-photo.jpg"));
        photo = new ImageView(noPhotoImage);
        grid.add(photo, 0, 0, 1, 2);

        grid.add(new Label("First name:"), 1, 0);
        firstNameField = new Label();
        grid.add(firstNameField, 2, 0);

        grid.add(new Label("Last name:"), 1, 1);
        lastNameField = new Label();
        grid.add(lastNameField, 2, 1);

        grid.add(new Label("Company:"), 1, 2);
        companyField = new Label();
        grid.add(companyField, 2, 2);

        rootPane.getChildren().add(grid);

        return rootPane;
    }
}