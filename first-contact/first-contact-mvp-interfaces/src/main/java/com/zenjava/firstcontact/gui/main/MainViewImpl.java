package com.zenjava.firstcontact.gui.main;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainViewImpl extends BorderPane implements MainView
{
    private MainPresenter presenter;
    private BorderPane contentArea;

    public MainViewImpl()
    {
        buildView();
    }

    public Node getNode()
    {
        return this;
    }

    public void setPresenter(MainPresenter presenter)
    {
        this.presenter = presenter;
    }

    public void setContent(Node content)
    {
        contentArea.setCenter(content);
    }

    protected void buildView()
    {
        VBox topArea = new VBox(5);
        topArea.getStyleClass().add("header");

        Label titleLabel = new Label("First Contact");
        titleLabel.getStyleClass().add("title");
        topArea.getChildren().add(titleLabel);

        Label tagLine = new Label("JavaFX 2.0 Contact Management System");
        tagLine.getStyleClass().add("tag-line");
        topArea.getChildren().add(tagLine);

        setTop(topArea);

        // we use this contentPane just so we can add style (i.e. padding)
        contentArea = new BorderPane();
        contentArea.getStyleClass().add("body");
        setCenter(contentArea);
    }
}
