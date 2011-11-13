package com.zenjava.playground.browser.demo2;

import com.google.inject.Inject;
import com.zenjava.playground.browser.AbstractActivity;
import com.zenjava.playground.browser.ActivityParameter;
import com.zenjava.playground.browser.NavigationManager;
import com.zenjava.playground.browser.demo2.service.Contact;
import com.zenjava.playground.browser.demo2.service.ContactsService;
import com.zenjava.playground.browser.transition.*;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewContactActivity extends AbstractActivity<Node>
        implements HasEntryTransition, HasExitTransition
{
    @ActivityParameter private LongProperty contactId;

    @Inject private NavigationManager navigationManager;
    @Inject private ContactsService contactsService;

    @FXML private Label titleLabel;
    @FXML private Label firstNameField;
    @FXML private Label lastNameField;
    @FXML private Label companyField;
    @FXML private ImageView photo;

    private Image noPhotoImage;

    public ViewContactActivity()
    {
        this.contactId = new SimpleLongProperty();
        noPhotoImage = new Image(getClass().getResourceAsStream("/images/no-photo.jpg"));
    }

    public ViewTransition getEntryTransition()
    {
        return new FlyInTransition(getView());
    }

    public ViewTransition getExitTransition()
    {
        return new FlyOutTransition(getView());
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

    protected void setContact(Contact contact)
    {
        if (contact != null)
        {
            photo.setImage(noPhotoImage);
            titleLabel.setText(String.format("%s %s", contact.getFirstName(), contact.getLastName()));
            firstNameField.setText(contact.getFirstName());
            lastNameField.setText(contact.getLastName());
            companyField.setText(contact.getCompany());
        }
        else
        {
            photo.setImage(noPhotoImage);
            titleLabel.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            companyField.setText("");
        }
    }
}