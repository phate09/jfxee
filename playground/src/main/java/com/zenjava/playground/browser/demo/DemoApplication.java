package com.zenjava.playground.browser.demo;

import com.zenjava.playground.browser.ActivityBrowser;
import com.zenjava.playground.browser.Place;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DemoApplication extends Application
{
    // developer can define these anywhere - might need to think about a 'best practice' to define for them however
    public static final String PLACE_PAGE1 = "page1";
    public static final String PLACE_PAGE2 = "page2";

    public static void main(String[] args)
    {
        Application.launch(DemoApplication.class, args);
    }

    public void start(Stage stage) throws Exception
    {
        ActivityBrowser browser = new ActivityBrowser();

        Page1Activity page1Activity = new Page1Activity(browser.getNavigationManager());
        browser.registerActivity(PLACE_PAGE1, page1Activity);

        Page2Activity page2Activity = new Page2Activity(browser.getNavigationManager());
        browser.registerActivity(PLACE_PAGE2, page2Activity);

        browser.getNavigationManager().goTo(new Place(PLACE_PAGE1));

        Scene scene = new Scene(browser.getView(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
