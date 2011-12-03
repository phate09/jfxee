package com.zenjava.jfxforms.framework;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.HashSet;
import java.util.Set;

// this would be a Control
public class ValidationSummaryPane<BeanType> extends HBox
{
    private Label summaryLabel;
    private BooleanProperty autoHide;
    private ObservableList<ConstraintViolation<BeanType>> constraintViolations;
    private ListChangeListener<ConstraintViolation<BeanType>> violationChangeListener;

    public ValidationSummaryPane()
    {
        this(FXCollections.<ConstraintViolation<BeanType>>observableArrayList());
    }

    public ValidationSummaryPane(ObservableList<ConstraintViolation<BeanType>> violations)
    {
        getStyleClass().add("validation-summary");
        this.summaryLabel = new Label();
        getChildren().add(this.summaryLabel);

        this.autoHide = new SimpleBooleanProperty();
        this.autoHide.addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue)
            {
                if (newValue)
                {
                    setVisible(constraintViolations != null && constraintViolations.size() > 0);
                }
            }
        });

        this.violationChangeListener = new ListChangeListener<ConstraintViolation<BeanType>>()
        {
            public void onChanged(Change<? extends ConstraintViolation<BeanType>> change)
            {
                summaryLabel.setText(buildSummaryMessage());
                if (autoHide.getValue())
                {
                    setVisible(constraintViolations != null && constraintViolations.size() > 0);
                }
            }
        };
        setConstraintViolations(violations);
    }

    public BooleanProperty autoHideProperty()
    {
        return autoHide;
    }

    public boolean isAutoHide()
    {
        return autoHide.get();
    }

    public void setAutoHide(boolean autoHide)
    {
        this.autoHide.set(autoHide);
    }

    public ObservableList<ConstraintViolation<BeanType>> getConstraintViolations()
    {
        return constraintViolations;
    }

    public void setConstraintViolations(ObservableList<ConstraintViolation<BeanType>> constraintViolations)
    {
        if (this.constraintViolations != null)
        {
            this.constraintViolations.removeListener(violationChangeListener);
        }
        this.constraintViolations = constraintViolations;
        if (this.constraintViolations != null)
        {
            this.constraintViolations.addListener(violationChangeListener);
        }
    }

    // this can be overidden to provide custom message formatting
    protected String buildSummaryMessage()
    {
        Set<Path> violatedPaths = new HashSet<Path>();
        if (constraintViolations != null)
        {
            for (ConstraintViolation<BeanType> violation : constraintViolations)
            {
                violatedPaths.add(violation.getPropertyPath());
            }
        }
        int count = violatedPaths.size();
        return String.format("Please fix the %s %s below before continuing",
                count, (count == 1 ? "error" : "errors"));
    }
}
