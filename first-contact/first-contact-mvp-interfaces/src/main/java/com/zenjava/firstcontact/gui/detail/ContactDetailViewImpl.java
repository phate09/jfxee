package com.zenjava.firstcontact.gui.detail;

import com.zenjava.firstcontact.service.Contact;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ContactDetailViewImpl extends GridPane implements ContactDetailView
{
    private ContactDetailPresenter presenter;
    private Long contactId;
    private TextField firstNameField;
    private TextField lastNameField;

    public ContactDetailViewImpl()
    {
        buildView();
    }

    public Node getNode()
    {
        return this;
    }

    public void setPresenter(ContactDetailPresenter presenter)
    {
        this.presenter = presenter;
    }

    public Contact getContact()
    {
        return new Contact(
                contactId,
                firstNameField.getText(),
                lastNameField.getText()
        );
    }

    public void setContact(Contact contact)
    {
        if (contact != null)
        {
            contactId = contact.getId();
            firstNameField.setText(contact.getFirstName());
            lastNameField.setText(contact.getLastName());
        }
        else
        {
            contactId = null;
            firstNameField.setText("");
            lastNameField.setText("");
        }
    }

    protected void buildView()
    {
        setHgap(10);
        setVgap(10);
        int row = 0;

        add(new Label("First name"), 0, row);
        firstNameField = new TextField();
        add(firstNameField, 1, row);

        row++;

        add(new Label("Last name"), 0, row);
        lastNameField = new TextField();
        add(lastNameField, 1, row);

        row++;

        HBox buttonBar = new HBox(10);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                presenter.save();
            }
        });
        buttonBar.getChildren().add(saveButton);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                presenter.cancel();
            }
        });
        buttonBar.getChildren().add(cancelButton);

        add(buttonBar, 0, row, 1, 2);
    }
}
