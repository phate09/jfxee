package com.zenjava.firstcontact.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BrowserHeader extends VBox
{
    public BrowserHeader()
    {
        super(5);
        getStyleClass().add("header");
        
        Label titleLabel = new Label("First Contact");
        titleLabel.getStyleClass().add("title");
        getChildren().add(titleLabel);
        
        Label tagLineLabel = new Label("JavaFX 2.0 Contact Management System");
        tagLineLabel.getStyleClass().add("tag-line");
        getChildren().add(tagLineLabel);
    }
}
