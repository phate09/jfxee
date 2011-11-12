package com.zenjava.playground.browser;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public abstract class AbstractActivity<ViewType extends Node> implements Activity<ViewType>
{
    private ViewType view;
    private BooleanProperty active;

    protected AbstractActivity()
    {
        this.active = new SimpleBooleanProperty();
        this.active.addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue)
            {
                if (newValue)
                {
                    activate();
                }
            }
        });
    }

    protected AbstractActivity(ViewType view)
    {
        setView(view);
    }

    public boolean isActive()
    {
        return active.get();
    }

    public void setActive(boolean active)
    {
        this.active.set(active);
    }

    public BooleanProperty activeProperty()
    {
        return this.active;
    }

    protected void setView(ViewType view)
    {
        this.view = view;
    }

    public ViewType getView()
    {
        return this.view;
    }

    protected void activate()
    {

    }
}
