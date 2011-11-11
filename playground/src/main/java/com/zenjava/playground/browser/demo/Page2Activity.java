package com.zenjava.playground.browser.demo;

import com.zenjava.playground.browser.AbstractActivity;
import com.zenjava.playground.browser.NavigationManager;
import com.zenjava.playground.browser.Place;
import com.zenjava.playground.browser.control.PlaceHyperlink;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Page2Activity extends AbstractActivity<VBox>
{
    public Page2Activity(final NavigationManager navigationManager)
    {
        VBox rootPane = new VBox(10);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.getChildren().add(new Label("Page 2"));
        rootPane.getChildren().add(new PlaceHyperlink(
                "Go to page 1", navigationManager, new Place(DemoApplication.PLACE_PAGE1)));
        setView(rootPane);
    }
}
