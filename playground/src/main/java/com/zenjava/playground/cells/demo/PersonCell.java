package com.zenjava.playground.cells.demo;

import com.zenjava.playground.cells.Cell;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PersonCell extends HBox implements Cell<Person>
{
    private Label firstNameLabel;
    private Label lastNameLabel;

    public PersonCell()
    {
        setSpacing(10);
        firstNameLabel = new Label();
        lastNameLabel = new Label();
        getChildren().addAll(firstNameLabel, lastNameLabel);
    }

    public Node getNode()
    {
        return this;
    }

    public void setValue(Person person)
    {
        if (person != null)
        {
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
        }
        else
        {
            firstNameLabel.setText(null);
            lastNameLabel.setText(null);
        }
    }
}
