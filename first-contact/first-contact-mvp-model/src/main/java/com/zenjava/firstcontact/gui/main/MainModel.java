package com.zenjava.firstcontact.gui.main;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

public class MainModel
{
    private ObjectProperty<Node> content;

    public MainModel()
    {
        this.content = new SimpleObjectProperty<Node>();
    }

    public ObjectProperty<Node> contentProperty()
    {
        return content;
    }

    public Node getContent()
    {
        return content.get();
    }

    public void setContent(Node content)
    {
        this.content.set(content);
    }
}
