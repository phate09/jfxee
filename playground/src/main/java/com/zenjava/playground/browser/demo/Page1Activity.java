package com.zenjava.playground.browser.demo;

import com.zenjava.playground.browser.AbstractActivity;
import com.zenjava.playground.browser.NavigationManager;
import com.zenjava.playground.browser.Place;
import com.zenjava.playground.browser.control.PlaceButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Page1Activity extends AbstractActivity<VBox>
{
    public Page1Activity(final NavigationManager navigationManager)
    {
        VBox rootPane = new VBox(10);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.getChildren().add(new Label("Page 1"));
        rootPane.getChildren().add(new PlaceButton(
                "Go to page 2", navigationManager, new Place(DemoApplication.PLACE_PAGE2)));
        setView(rootPane);
    }
}
