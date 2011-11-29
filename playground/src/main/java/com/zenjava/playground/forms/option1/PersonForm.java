package com.zenjava.playground.forms.option1;

import com.zenjava.playground.forms.data.Gender;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonForm extends GridPane
{
    private TextField firstNameField;
    private TextField lastNameField;
    private ChoiceBox<Gender> genderChoiceBox;

    public PersonForm()
    {
        buildView();
    }

    private void buildView()
    {
        setStyle("-fx-padding: 10");
        setHgap(4);
        setVgap(4);

        int row = 0;

        add(new Label("First Name"), 0, row);
        firstNameField = new TextField();
        add(firstNameField, 1, row);

        row++;

        add(new Label("Last Name"), 0, row);
        lastNameField = new TextField();
        add(lastNameField, 1, row);

        row++;

        add(new Label("Gender"), 0, row);
        genderChoiceBox = new ChoiceBox<Gender>(FXCollections.observableArrayList(Gender.values()));
        add(genderChoiceBox, 1, row);
    }
}
