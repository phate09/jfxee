package com.zenjava.playground.cells.demo;

import com.zenjava.playground.cells.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class CellsDemoApplication extends Application
{
    public static void main(String[] args)
    {
        Application.launch(CellsDemoApplication.class, args);
    }

    public void start(Stage stage) throws Exception
    {
        FlowPane root = new FlowPane();

        // person list is shared by all
        ObservableList<Person> persons = FXCollections.observableArrayList(
            new Person("Cathy", "Freeman"),
            new Person("Albert", "Namatjira"),
            new Person("Noel", "Pearson"),
            new Person("Oodgeroo", "Nooncal")
        );

        // person cell factory is a common 'renderer' for all Person views
        CellFactory<Person> personCellFactory = new CellFactory<Person>()
        {
            public Cell<Person> createCell()
            {
                return new PersonCell();
            }
        };

        // create a person list view
        ListView<Person> personListView = new ListView<Person>(persons, personCellFactory);
        root.getChildren().add(personListView);

        // create a person combo box
        ComboBox<Person> personComboBox = new ComboBox<Person>(personCellFactory.createCell());

        // create a custom 'chooser' - the combo box should have a default one, but haven't bothered for this demo
        ListView<Person> choosePersonList = new ListView<Person>(persons, personCellFactory);
        PopupListItemChooser<Person> chooser = new PopupListItemChooser<Person>(personComboBox, choosePersonList);
        personComboBox.setItemChooser(chooser);

        root.getChildren().add(personComboBox);


        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
