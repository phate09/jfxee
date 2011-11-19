package com.zenjava.playground.browser2.activity;

import javafx.beans.property.BooleanProperty;

public interface Activatable
{
    void setActive(boolean active);

    boolean isActive();

    BooleanProperty activeProperty();
}
