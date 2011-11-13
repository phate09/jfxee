package com.zenjava.playground.browser2.demo.simple;

import com.zenjava.playground.browser2.activity.AbstractActivity;
import com.zenjava.playground.browser2.navigation.NavigationManager;
import com.zenjava.playground.browser2.navigation.Place;
import com.zenjava.playground.browser2.navigation.control.PlaceHyperlink;
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
                "Go to page 1", navigationManager, new Place(SimpleApplication.PLACE_PAGE1)));
        setNode(rootPane);
    }
}
