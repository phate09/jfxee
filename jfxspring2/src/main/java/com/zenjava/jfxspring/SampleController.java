package com.zenjava.jfxspring;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;

public class SampleController
{
    @FXML private Node view;
    @Autowired private Person person;

    public Node getView()
    {
        return view;
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
