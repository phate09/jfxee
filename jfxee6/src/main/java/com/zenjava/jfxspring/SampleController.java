package com.zenjava.jfxspring;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;


public class SampleController
{
    @FXML private Node view;
    @Inject private Person person;

    public Node getView()
    {
        return view;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public Person getPerson()
    {
        return person;
    }

    public void print(ActionEvent event)
    {
        System.out.println("Well done, " + person.getFirstName() + "!");
    }
}
