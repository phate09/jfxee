package com.zenjava.playground.cells;

import javafx.scene.Node;

public interface Cell<ValueType>
{
    Node getNode();

    void setValue(ValueType value);
}
