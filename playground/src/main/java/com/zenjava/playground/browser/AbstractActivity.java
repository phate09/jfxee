package com.zenjava.playground.browser;

import javafx.scene.Node;

public abstract class AbstractActivity<ViewType extends Node> implements Activity<ViewType>
{
    private ViewType view;

    protected AbstractActivity()
    {
    }

    protected AbstractActivity(ViewType view)
    {
        setView(view);
    }

    protected void setView(ViewType view)
    {
        this.view = view;
    }

    public ViewType getView()
    {
        return this.view;
    }
}
