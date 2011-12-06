package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.service.Contact;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ContactDetailView extends GridPane
{
    private ContactDetailController controller;
    private TextField firstNameField;
    private TextField lastNameField;

    public ContactDetailView()
    {
        buildView();
    }

    public void setController(ContactDetailController controller)
    {
        this.controller = controller;
    }

    public void clear()
    {
        firstNameField.setText("");
        lastNameField.setText("");
    }

    public void setContact(Contact contact)
    {
        if (contact != null)
        {
            firstNameField.setText(contact.getFirstName());
            lastNameField.setText(contact.getLastName());
        }
        else
        {
            clear();
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

        Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                controller.goBack();
            }
        });
        add(backButton, 0, row, 1, 2);
    }
}
