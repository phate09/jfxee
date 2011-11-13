package com.zenjava.playground.browser2.activity;

import javafx.beans.property.ReadOnlyBooleanProperty;

import java.util.Map;

public interface Activatable
{
    void activate(Map<String, Object> parameters);

    void deactivate();

    boolean isActive();

    ReadOnlyBooleanProperty activeProperty();
}
