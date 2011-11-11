package com.zenjava.playground.browser;

import javafx.scene.control.Label;

public class SimpleActivity extends AbstractActivity<Label>
{
    private String name;

    public SimpleActivity(String name)
    {
        this.name = name;
        setView(new Label(name));
    }

    public String toString()
    {
        return String.format("SimpleActivity[%s]", name);
    }
}
