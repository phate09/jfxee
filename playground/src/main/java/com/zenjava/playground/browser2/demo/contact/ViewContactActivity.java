package com.zenjava.playground.browser2.demo.contact;

import com.zenjava.playground.browser2.activity.AbstractActivity;
import com.zenjava.playground.browser2.activity.Activation;
import com.zenjava.playground.browser2.activity.ActivationParam;
import com.zenjava.playground.browser2.demo.contact.service.Contact;
import com.zenjava.playground.browser2.demo.contact.service.ContactsService;
import com.zenjava.playground.browser2.navigation.NavigationManager;
import com.zenjava.playground.browser2.transition.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;

public class ViewContactActivity extends AbstractActivity<Node>
        implements HasEntryTransition, HasExitTransition
{
    @Autowired private NavigationManager navigationManager;
    @Autowired private ContactsService contactsService;

    @FXML private Label titleLabel;
    @FXML private Label firstNameField;
    @FXML private Label lastNameField;
    @FXML private Label companyField;
    @FXML private ImageView photo;

    private Image noPhotoImage;

    public ViewContactActivity()
    {
        noPhotoImage = new Image(getClass().getResourceAsStream("/images/no-photo.jpg"));
    }

    public ViewTransition getEntryTransition()
    {
        return new FlyInTransition(getNode());
    }

    public ViewTransition getExitTransition()
    {
        return new FlyOutTransition(getNode());
    }

    @Activation
    public void activate(@ActivationParam("contactId") final int contactId)
    {
        setContact(null);

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
                else if (newState.equals(Worker.State.FAILED))
                {
                    task.getException().printStackTrace();
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