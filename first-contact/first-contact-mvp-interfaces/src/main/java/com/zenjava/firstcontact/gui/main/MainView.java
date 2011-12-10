package com.zenjava.firstcontact.gui.main;

import javafx.scene.Node;

public interface MainView
{
    Node getNode();

    void setContent(Node content);

    void setPresenter(MainPresenter presenter);
}
