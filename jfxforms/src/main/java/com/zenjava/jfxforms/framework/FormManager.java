package com.zenjava.jfxforms.framework;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.hibernate.validator.engine.PathImpl;

import javax.validation.*;
import java.util.*;

public class FormManager<BeanType>
{
    private ObjectProperty<Validator> validator;
    private ObjectProperty<HasData<BeanType>> form;
    private ObservableMap<Path, AnnotatedNode> fields;
    private ObservableList<ConstraintViolation<BeanType>> constraintViolations;
    private BooleanProperty autoValidating;
    private BooleanProperty showHighlights;
    private BooleanProperty showAnnotations;
    private BooleanProperty showTooltips;

    public FormManager(HasData<BeanType> form)
    {
        this(Validation.buildDefaultValidatorFactory(), form);
    }

    public FormManager(ValidatorFactory validatorFactory, HasData<BeanType> form)
    {
        this(validatorFactory.getValidator(), form);
    }

    public FormManager(Validator validator, HasData<BeanType> form)
    {
        this.validator = new SimpleObjectProperty<Validator>(validator);
        this.form = new SimpleObjectProperty<HasData<BeanType>>(form);
        this.fields = FXCollections.observableHashMap();
        this.constraintViolations = FXCollections.observableArrayList();
        this.autoValidating = new SimpleBooleanProperty();
        this.showHighlights = new SimpleBooleanProperty();
        this.showAnnotations = new SimpleBooleanProperty();
        this.showTooltips = new SimpleBooleanProperty();

        ChangeListener<Object> changeListener = new ChangeListener<Object>()
        {
            public void changed(ObservableValue<?> source, Object oldValue, Object newValue)
            {
                if (isAutoValidating())
                {
                    validate();
                }
            }
        };
        this.validator.addListener(changeListener);
        this.form.addListener(changeListener);
        this.autoValidating.addListener(changeListener);
        this.showHighlights.addListener(changeListener);
        this.showTooltips.addListener(changeListener);
        this.showAnnotations.addListener(changeListener);

        this.constraintViolations.addListener(new ListChangeListener<ConstraintViolation<BeanType>>()
        {
            public void onChanged(Change<? extends ConstraintViolation<BeanType>> change)
            {
                Map<Path, List<ConstraintViolation<BeanType>>> violationsByField
                        = new HashMap<Path, List<ConstraintViolation<BeanType>>>();
                for (ConstraintViolation<BeanType> violation : constraintViolations)
                {
                    List<ConstraintViolation<BeanType>> violations = violationsByField.get(violation.getPropertyPath());
                    if (violations == null)
                    {
                        violations = new ArrayList<ConstraintViolation<BeanType>>();
                        violationsByField.put(violation.getPropertyPath(), violations);
                    }
                    violations.add(violation);
                }

                // update individual fields
                for (Path path : fields.keySet())
                {
                    Node fieldContainer = fields.get(path);
                    List<ConstraintViolation<BeanType>> violations = violationsByField.get(path);
                    Tooltip toolTip = null;

                    if (isShowTooltips() && violations != null && !violations.isEmpty())
                    {
                        StringBuilder builder = new StringBuilder();
                        for (ConstraintViolation<BeanType> violation : violations)
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


                    if (isShowHighlights() && violations != null && !violations.isEmpty())
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

                    if (isShowAnnotations() && violations != null && !violations.isEmpty())
                    {
                        fieldContainer.lookup("#error-icon").setVisible(true);
                    }
                    else
                    {
                        fieldContainer.lookup("#error-icon").setVisible(false);
                    }
                }
            }
        });        
    }

    public void clear()
    {
        this.constraintViolations.clear();
    }

    public ObservableList<ConstraintViolation<BeanType>> getConstraintViolations()
    {
        return constraintViolations;
    }

    public ObjectProperty<Validator> validatorProperty()
    {
        return validator;
    }

    public Validator getValidator()
    {
        return validator.get();
    }

    public void setValidator(Validator validator)
    {
        this.validator.set(validator);
    }

    public BooleanProperty autoValidatingProperty()
    {
        return autoValidating;
    }

    public boolean isAutoValidating()
    {
        return autoValidating.get();
    }

    public void setAutoValidating(boolean autoValidating)
    {
        this.autoValidating.set(autoValidating);
    }

    public BooleanProperty showHighlightsProperty()
    {
        return showHighlights;
    }

    public boolean isShowHighlights()
    {
        return showHighlights.get();
    }

    public void setShowHighlights(boolean showHighlights)
    {
        this.showHighlights.set(showHighlights);
    }

    public BooleanProperty showAnnotationsProperty()
    {
        return showAnnotations;
    }

    public boolean isShowAnnotations()
    {
        return showAnnotations.get();
    }

    public void setShowAnnotations(boolean showAnnotations)
    {
        this.showAnnotations.set(showAnnotations);
    }

    public BooleanProperty showTooltipsProperty()
    {
        return showTooltips;
    }

    public boolean isShowTooltips()
    {
        return showTooltips.get();
    }

    public void setShowTooltips(boolean showTooltips)
    {
        this.showTooltips.set(showTooltips);
    }

    public boolean validate()
    {
        Validator validator = this.validator.get();
        if (validator == null)
        {
            throw new IllegalStateException("No validator has been set for FormManager");
        }
        
        HasData<BeanType> form = this.form.get();
        if (form == null)
        {
            throw new IllegalStateException("No form has been set for FormManager");
        }
        
        Set<ConstraintViolation<BeanType>> violations = validator.validate(form.getData());
        this.constraintViolations.setAll(violations);
        return violations.isEmpty();
    }
    

    public void registerFormField(String name, Node field)
    {
        Parent parent = field.getParent();
        if (!(parent instanceof AnnotatedNode))
        {
            throw new IllegalArgumentException(String.format(
                    "Field '%s' must be contained within an AnnotatedNode to be used on a form", name));
        }

        AnnotatedNode annotatedNode = (AnnotatedNode) parent;

        ImageView marker = new ImageView(new Image(getClass().getResourceAsStream("/exclamation.png")));
        marker.setVisible(false);
        marker.setId("error-icon");
        marker.setTranslateX(4);
        marker.setTranslateY(-4);
        annotatedNode.getAnnotations().add(marker);

        fields.put(PathImpl.createPathFromString(name), annotatedNode);
        ValueHelper.valueProperty(field).addListener(new ChangeListener()
        {
            public void changed(ObservableValue source, Object oldValue, Object newValue)
            {
                if (isAutoValidating())
                {
                    validate();
                }
            }
        });
    }
}
