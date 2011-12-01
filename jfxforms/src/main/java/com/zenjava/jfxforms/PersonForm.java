package com.zenjava.jfxforms;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.hibernate.validator.engine.PathImpl;

import javax.validation.*;
import java.util.*;

public class PersonForm extends VBox
{
    private CheckBox autoValidateCheckBox;
    private CheckBox showMarkersCheckBox;
    private CheckBox showHighlightsCheckBox;
    private CheckBox showTooltipsBox;

    private Label validationSummaryLabel;
    private TextField firstNameField;
    private TextField lastNameField;
    private ChoiceBox<Gender> genderChoiceBox;
    private Button submitButton;
    private Button clearButton;
    private Label submitSuccessMessageLabel;

    private ObservableList<ConstraintViolation<Person>> constraintViolations;
    private Map<Path, Node> formFields;

    public PersonForm()
    {
        constraintViolations = FXCollections.observableArrayList();
        formFields = new HashMap<Path, Node>();
        buildView();
    }

    public void submit()
    {
        if (validate())
        {
            System.out.println("All fields are valid, saving to database");
            Person person = getPerson();
            // do submission of person, i.e. save to database, etc
            submitSuccessMessageLabel.setVisible(true);
        }
        else
        {
            // validation errors, from now on auto validate whenever the user changes something
            submitSuccessMessageLabel.setVisible(false);
            autoValidateCheckBox.setSelected(true);
        }
    }

    public boolean validate()
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Person>> violations = validator.validate(getPerson());
        this.constraintViolations.setAll(violations);
        return violations.isEmpty();
    }

    public void setPerson(Person person)
    {
        autoValidateCheckBox.setSelected(false);
        constraintViolations.clear();
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

    public Person getPerson()
    {
        return new Person(
                genderChoiceBox.getSelectionModel().getSelectedItem(),
                firstNameField.getText(),
                lastNameField.getText()
        );
    }

    protected Node createFormField(String name, Node field)
    {
        StackPane fieldContainer = new StackPane();

        HBox box = new HBox(); // needed to left align in stack pane
        box.getChildren().add(field);
        fieldContainer.getChildren().add(box);

        AnchorPane anchor = new AnchorPane();
        anchor.setMouseTransparent(true);
        ImageView marker = new ImageView(new Image(getClass().getResourceAsStream("/exclamation.png")));
        marker.setVisible(false);
        marker.setId("error-marker");
        marker.setTranslateX(4);
        marker.setTranslateY(-4);
        AnchorPane.setRightAnchor(marker, 0D);
        AnchorPane.setTopAnchor(marker, 0D);
        anchor.getChildren().add(marker);
        fieldContainer.getChildren().add(anchor);

        formFields.put(PathImpl.createPathFromString(name), fieldContainer);

        if (field instanceof TextField)
        {
            ((TextField) field).textProperty().addListener(new ChangeListener<String>()
            {
                public void changed(ObservableValue<? extends String> observableValue, String s, String s1)
                {
                    if (autoValidateCheckBox.isSelected())
                    {
                        validate();
                    }
                }
            });
        }
        else if (field instanceof ChoiceBox)
        {
            ((ChoiceBox) field).getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
            {
                public void changed(ObservableValue source, Object oldValue, Object newValue)
                {
                    if (autoValidateCheckBox.isSelected())
                    {
                        validate();
                    }
                }
            });
        }
        else
        {
            throw new IllegalArgumentException(String.format("Unsupported field type: %s", field.getClass().getName()));
        }

        return fieldContainer;
    }

    private void buildView()
    {
        setSpacing(10);
        setStyle("-fx-padding: 10");
        getChildren().add(buildFormControlArea());
        getChildren().add(buildHeaderArea());
        getChildren().add(new Separator(Orientation.HORIZONTAL));
        getChildren().add(buildSummaryArea());
        getChildren().add(buildFieldArea());
        getChildren().add(new Separator(Orientation.HORIZONTAL));
        getChildren().add(buildButtonArea());
        getChildren().add(buildSubmitFeedbackArea());
    }

    private Node buildFormControlArea()
    {
        HBox box = new HBox(10);
        box.getStyleClass().add("form-control-bar");

        autoValidateCheckBox = new CheckBox("Auto validate");
        autoValidateCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue)
            {
                if (newValue)
                {
                    validate();
                }
            }
        });
        box.getChildren().add(autoValidateCheckBox);

        showHighlightsCheckBox = new CheckBox("Highlights");
        showHighlightsCheckBox.setSelected(true);
        showHighlightsCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue)
            {
                if (autoValidateCheckBox.isSelected())
                {
                    validate();
                }
            }
        });
        box.getChildren().add(showHighlightsCheckBox);

        showTooltipsBox = new CheckBox("Tooltips");
        showTooltipsBox.setSelected(true);
        showTooltipsBox.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue)
            {
                if (autoValidateCheckBox.isSelected())
                {
                    validate();
                }
            }
        });
        box.getChildren().add(showTooltipsBox);

        showMarkersCheckBox = new CheckBox("Markers");
        showMarkersCheckBox.setSelected(true);
        showMarkersCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue)
            {
                if (autoValidateCheckBox.isSelected())
                {
                    validate();
                }
            }
        });
        box.getChildren().add(showMarkersCheckBox);

        return box;
    }

    private Node buildHeaderArea()
    {
        Label label = new Label("Enter details for person");
        label.getStyleClass().add("title");
        return label;
    }

    private Node buildSummaryArea()
    {
        validationSummaryLabel = new Label();
        validationSummaryLabel.getStyleClass().add("validation-summary");
        validationSummaryLabel.setVisible(false);
        constraintViolations.addListener(new ListChangeListener<ConstraintViolation<Person>>()
        {
            public void onChanged(Change<? extends ConstraintViolation<Person>> change)
            {
                Map<Path, List<ConstraintViolation<Person>>> violationsByField
                        = new HashMap<Path, List<ConstraintViolation<Person>>>();
                for (ConstraintViolation<Person> violation : constraintViolations)
                {
                    List<ConstraintViolation<Person>> violations = violationsByField.get(violation.getPropertyPath());
                    if (violations == null)
                    {
                        violations = new ArrayList<ConstraintViolation<Person>>();
                        violationsByField.put(violation.getPropertyPath(), violations);
                    }
                    violations.add(violation);
                }

                // update summary
                int count = violationsByField.size();
                validationSummaryLabel.setText(
                        String.format("Please fix the %s %s below before continuing", count, (count == 1 ? "error" : "errors")));
                validationSummaryLabel.setVisible(count > 0);

                // update individual fields
                for (Path path : formFields.keySet())
                {
                    Node fieldContainer = formFields.get(path);
                    List<ConstraintViolation<Person>> violations = violationsByField.get(path);
                    Tooltip toolTip = null;

                    if (showTooltipsBox.isSelected() && violations != null && !violations.isEmpty())
                    {
                        StringBuilder builder = new StringBuilder();
                        for (ConstraintViolation<Person> violation : violations)
                        {
                            builder.append(violation.getMessage()).append("\n");
                        }
                        toolTip = new Tooltip(builder.toString());
                        Tooltip.install(fieldContainer, toolTip);
                    }
                    else
                    {
                        Tooltip.install(fieldContainer, null);
                    }


                    if (showHighlightsCheckBox.isSelected() && violations != null && !violations.isEmpty())
                    {
                        if (!fieldContainer.getStyleClass().contains("error"))
                        {
                            fieldContainer.getStyleClass().add("error");
                        }
                    }
                    else
                    {
                        fieldContainer.getStyleClass().remove("error");
                    }

                    if (showMarkersCheckBox.isSelected() && violations != null && !violations.isEmpty())
                    {
                        fieldContainer.lookup("#error-marker").setVisible(true);
                    }
                    else
                    {
                        fieldContainer.lookup("#error-marker").setVisible(false);
                    }
                }
            }
        });
        return validationSummaryLabel;
    }

    private Node buildFieldArea()
    {
        GridPane pane = new GridPane();
        pane.setHgap(4);
        pane.setVgap(4);

        int row = 0;

        pane.add(new Label("First Name"), 0, row);
        firstNameField = new TextField();
        pane.add(createFormField("firstName", firstNameField), 1, row);

        row++;

        pane.add(new Label("Last Name"), 0, row);
        lastNameField = new TextField();
        pane.add(createFormField("lastName", lastNameField),1, row);

        row++;

        pane.add(new Label("Gender"), 0, row);
        genderChoiceBox = new ChoiceBox<Gender>(FXCollections.observableArrayList(Gender.values()));
        pane.add(createFormField("gender", genderChoiceBox), 1, row);

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
                setPerson(null);
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
