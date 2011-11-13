package com.zenjava.playground.browser2.demo.simple;

import com.zenjava.playground.browser2.activity.AbstractActivity;
import com.zenjava.playground.browser2.navigation.NavigationManager;
import com.zenjava.playground.browser2.navigation.Place;
import com.zenjava.playground.browser2.navigation.control.PlaceButton;
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
                "Go to page 2", navigationManager, new Place(SimpleApplication.PLACE_PAGE2)));
        setNode(rootPane);
    }
}
