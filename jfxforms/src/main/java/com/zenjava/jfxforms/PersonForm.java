package com.zenjava.jfxforms;

import com.zenjava.jfxforms.framework.AnnotatedNode;
import com.zenjava.jfxforms.framework.FormManager;
import com.zenjava.jfxforms.framework.HasData;
import com.zenjava.jfxforms.framework.ValidationSummaryPane;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PersonForm extends VBox implements HasData<Person>
{
    private FormManager<Person> formManager;
    private ValidationSummaryPane<Person> validationSummaryPane;

    private TextField firstNameField;
    private TextField lastNameField;
    private ChoiceBox<Gender> genderChoiceBox;

    private Button submitButton;
    private Button clearButton;
    private Label submitSuccessMessageLabel;

    public PersonForm()
    {
        buildView();
    }

    public FormManager<Person> getFormManager()
    {
        return formManager;
    }

    public void submit()
    {
        if (formManager.validate())
        {
            System.out.println("All fields are valid, saving to database");
            Person person = getData();
            // do submission of person, i.e. save to database, etc
            submitSuccessMessageLabel.setVisible(true);
        }
        else
        {
            // validation errors, from now on auto validate whenever the user changes something
            submitSuccessMessageLabel.setVisible(false);
            formManager.setAutoValidating(true);
        }
    }

    public Person getData()
    {
        return new Person(
                genderChoiceBox.getSelectionModel().getSelectedItem(),
                firstNameField.getText(),
                lastNameField.getText()
        );
    }

    public void setData(Person person)
    {
        formManager.setAutoValidating(false);
        formManager.clear();
        submitSuccessMessageLabel.setVisible(false);

        if (person != null)
        {
            genderChoiceBox.getSelectionModel().select(person.getGender());
            firstNameField.setText(person.getFirstName());
            lastNameField.setText(person.getLastName());
        }
        else
        {
            genderChoiceBox.getSelectionModel().clearSelection();
            firstNameField.setText("");
            lastNameField.setText("");
        }
    }

    private void buildView()
    {
        formManager = new FormManager<Person>(this);

        getStyleClass().add("person-form");
        setSpacing(10);
        getChildren().add(buildHeaderArea());
        getChildren().add(new Separator(Orientation.HORIZONTAL));
        getChildren().add(buildSummaryArea());
        getChildren().add(buildFieldArea());
        getChildren().add(new Separator(Orientation.HORIZONTAL));
        getChildren().add(buildButtonArea());
        getChildren().add(buildSubmitFeedbackArea());
    }

    private Node buildHeaderArea()
    {
        Label label = new Label("Enter details for person");
        label.getStyleClass().add("title");
        return label;
    }

    private Node buildSummaryArea()
    {
        validationSummaryPane = new ValidationSummaryPane<Person>(formManager.getConstraintViolations());
        validationSummaryPane.setAutoHide(true);
        return validationSummaryPane;
    }

    private Node buildFieldArea()
    {
        GridPane pane = new GridPane();
        pane.setHgap(4);
        pane.setVgap(4);

        int row = 0;

        pane.add(new Label("First Name"), 0, row);
        firstNameField = new TextField();
        pane.add(new AnnotatedNode(firstNameField), 1, row);
        formManager.registerFormField("firstName", firstNameField);

        row++;

        pane.add(new Label("Last Name"), 0, row);
        lastNameField = new TextField();
        pane.add(new AnnotatedNode(lastNameField), 1, row);
        formManager.registerFormField("lastName", lastNameField);

        row++;

        pane.add(new Label("Gender"), 0, row);
        genderChoiceBox = new ChoiceBox<Gender>(FXCollections.observableArrayList(Gender.values()));
        pane.add(new AnnotatedNode(genderChoiceBox), 1, row);
        formManager.registerFormField("gender", genderChoiceBox);

        return pane;
    }

    private Node buildButtonArea()
    {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);

        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                submit();
            }
        });
        box.getChildren().add(submitButton);

        clearButton = new Button("Clear");
        clearButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                setData(null);
            }
        });
        box.getChildren().add(clearButton);

        return box;
    }

    private Node buildSubmitFeedbackArea()
    {
        submitSuccessMessageLabel = new Label("Submitted successfully");
        submitSuccessMessageLabel.setMaxWidth(Integer.MAX_VALUE);
        submitSuccessMessageLabel.setAlignment(Pos.CENTER);
        submitSuccessMessageLabel.getStyleClass().add("submit-success");
        submitSuccessMessageLabel.setVisible(false);
        return submitSuccessMessageLabel;
    }
}
