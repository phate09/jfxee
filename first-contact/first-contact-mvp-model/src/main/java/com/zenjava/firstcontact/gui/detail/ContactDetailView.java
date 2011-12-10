package com.zenjava.firstcontact.gui.detail;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ContactDetailView extends GridPane
{
    private ContactDetailModel model;
    private ContactDetailPresenter presenter;
    private TextField firstNameField;
    private TextField lastNameField;

    public ContactDetailView(ContactDetailModel model)
    {
        this.model = model;
        buildView();
    }

    public void setPresenter(ContactDetailPresenter presenter)
    {
        this.presenter = presenter;
    }

    protected void buildView()
    {
        setHgap(10);
        setVgap(10);
        int row = 0;

        add(new Label("First name"), 0, row);
        firstNameField = new TextField();
        firstNameField.textProperty().bindBidirectional(model.firstNameProperty());
        add(firstNameField, 1, row);

        row++;

        add(new Label("Last name"), 0, row);
        lastNameField = new TextField();
        lastNameField.textProperty().bindBidirectional(model.lastNameProperty());
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
