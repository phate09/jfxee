package com.zenjava.playground.browser2.demo.contact;

import com.zenjava.playground.browser2.activity.AbstractActivity;
import com.zenjava.playground.browser2.activity.Param;
import com.zenjava.playground.browser2.demo.contact.service.Contact;
import com.zenjava.playground.browser2.demo.contact.service.ContactsService;
import com.zenjava.playground.browser2.navigation.NavigationManager;
import com.zenjava.playground.browser2.transition.*;
import com.zenjava.playground.browser2.worker.BackgroundTask;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;

public class ViewContactActivity extends AbstractActivity<Node>
        implements HasEntryTransition, HasExitTransition
{
    @FXML private Label titleLabel;
    @FXML private Label firstNameField;
    @FXML private Label lastNameField;
    @FXML private Label companyField;
    @FXML private ImageView photo;

    @Autowired private NavigationManager navigationManager;
    @Autowired private ContactsService contactsService;

    @Param private IntegerProperty contactId;

    private Image noPhotoImage;

    public ViewContactActivity()
    {
        contactId = new SimpleIntegerProperty();
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

    public void activated()
    {
        setContact(null);

        final int id = contactId.get();
        final BackgroundTask<Contact> task = new BackgroundTask<Contact>()
        {
            protected Contact call() throws Exception
            {
                return contactsService.getContact(id);
            }

            protected void onSuccess(Contact contact)
            {
                setContact(contact);
            }
        };
        executeTask(task);
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