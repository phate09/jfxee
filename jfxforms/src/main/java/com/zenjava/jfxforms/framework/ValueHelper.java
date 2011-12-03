package com.zenjava.jfxforms.framework;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextInputControl;

public class ValueHelper
{
    public static ObservableValue valueProperty(Node node)
    {
        if (node instanceof TextInputControl)
        {
            return ((TextInputControl) node).textProperty();
        }
        else if (node instanceof ChoiceBox)
        {
            return ((ChoiceBox) node).getSelectionModel().selectedItemProperty();
        }
        else if (node instanceof CheckBox)
        {
            return ((CheckBox) node).selectedProperty();
        }
        else
        {
            throw new IllegalArgumentException(String.format(
                    "Unsupported node type, unable to determine 'value' property for: %s", node.getClass()));
        }
    }
}
