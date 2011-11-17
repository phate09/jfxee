package com.zenjava.playground.cells;

import javafx.beans.property.ObjectProperty;

public interface ItemChooser<ItemType>
{
    void setActive(boolean active);

    ObjectProperty<ItemType> selectedItemProperty();
}
