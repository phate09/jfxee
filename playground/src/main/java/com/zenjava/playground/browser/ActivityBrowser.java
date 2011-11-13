package com.zenjava.playground.browser;

import com.zenjava.playground.browser.control.BackButton;
import com.zenjava.playground.browser.control.ForwardButton;
import com.zenjava.playground.browser.transition.*;
import javafx.animation.SequentialTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class ActivityBrowser extends AbstractActivity<Parent>
{
    private StackPane contentArea;
    private Node glassPane;

    private ObjectProperty<ActivityManager> activityManager;
    private ObjectProperty<NavigationManager> navigationManager;
    private ChangeListener<? super Activity> currentActivityListener;
    private ChangeListener<? super Place> currentPlaceListener;

    public ActivityBrowser()
    {
        this(new DefaultNavigationManager());
    }

    public ActivityBrowser(NavigationManager navigationManager)
    {
        this(navigationManager, new DefaultActivityManager());
    }

    public ActivityBrowser(NavigationManager navigationManager, ActivityManager activityManager)
    {
        this.activityManager = new SimpleObjectProperty<ActivityManager>();
        this.navigationManager = new SimpleObjectProperty<NavigationManager>();
        this.currentActivityListener = new CurrentPlaceListener();
        this.currentPlaceListener = new CurrentActivityListener();

        this.activityManager.addListener(new ChangeListener<ActivityManager>()
        {
            public void changed(ObservableValue<? extends ActivityManager> source,
                                ActivityManager oldActivityManager,
                                ActivityManager newActivityManager)
            {
                if (oldActivityManager != null)
                {
                    oldActivityManager.currentActivityProperty().removeListener(currentActivityListener);
                }
                if (newActivityManager != null)
                {
                    newActivityManager.currentActivityProperty().addListener(currentActivityListener);
                }
            }
        });

        this.navigationManager.addListener(new ChangeListener<NavigationManager>()
        {
            public void changed(ObservableValue<? extends NavigationManager> source,
                                NavigationManager oldNavigationManager,
                                NavigationManager newNavigationManager)
            {
                if (oldNavigationManager != null)
                {
                    oldNavigationManager.currentPlaceProperty().removeListener(currentPlaceListener);
                }
                if (newNavigationManager != null)
                {
                    newNavigationManager.currentPlaceProperty().addListener(currentPlaceListener);

                }
            }
        });

        // todo use control + skin

        StackPane rootPane = new StackPane();
        rootPane.getStyleClass().add("activity-browser");

        BorderPane rootPaneLayout = new BorderPane();

        HBox navBar = new HBox(4);
        navBar.getStyleClass().add("toolbar");

        BackButton backButton = new BackButton("<");
        backButton.navigationManagerProperty().bind(this.navigationManager);
        navBar.getChildren().add(backButton);

        ForwardButton forwardButton = new ForwardButton(">");
        forwardButton.navigationManagerProperty().bind(this.navigationManager);
        navBar.getChildren().add(forwardButton);

        rootPaneLayout.setTop(navBar);

        this.contentArea = new StackPane();
        this.contentArea.getStyleClass().add("content");
        rootPaneLayout.setCenter(contentArea);

        rootPane.getChildren().add(rootPaneLayout);

        this.glassPane = new BorderPane();
        this.glassPane.setStyle("-fx-cursor: wait"); // todo use control + skin + default css
        this.glassPane.setVisible(false);
        rootPane.getChildren().add(this.glassPane);

        setView(rootPane);

        setActivityManager(activityManager);
        setNavigationManager(navigationManager);
    }

    /**
     * Convenience method for adding an Activity with a regular expression based mapping. The mapping is added to the
     * end of the list of mapping for this ActivityBrowser.
     *
     * @param pathExpression the regular expression to map the activity to.
     * @param activity       the activity to be registered.
     * @return the mapping created for the activity.
     */
    public RegexActivityMapping registerActivity(String pathExpression, Activity activity)
    {
        RegexActivityMapping mapping = new RegexActivityMapping(pathExpression, activity);
        getActivityManager().getActivityMappings().add(mapping);
        return mapping;
    }

    public ActivityManager getActivityManager()
    {
        return activityManager.get();
    }

    public void setActivityManager(ActivityManager activityManager)
    {
        this.activityManager.set(activityManager);
    }

    public NavigationManager getNavigationManager()
    {
        return navigationManager.get();
    }

    public void setNavigationManager(NavigationManager navigationManager)
    {
        this.navigationManager.set(navigationManager);
    }

    protected void transition(final Activity oldActivity, Activity newActivity)
    {
        SequentialTransition transition = new SequentialTransition();

        ViewTransition exit = null;
        if (oldActivity != null)
        {
            if (oldActivity instanceof HasExitTransition)
            {
                exit = ((HasExitTransition) oldActivity).getExitTransition();
            }
            else
            {
                exit = new FadeOutTransition(oldActivity.getView());
            }
            exit.setupBeforeAnimation(contentArea);
            transition.getChildren().add(exit.getAnimation());
        }

        ViewTransition entry = null;
        if (newActivity != null)
        {
            if (newActivity instanceof HasEntryTransition)
            {
                entry = ((HasEntryTransition) newActivity).getEntryTransition();
            }
            else
            {
                entry = new FadeInTransition(newActivity.getView());
            }
            entry.setupBeforeAnimation(contentArea);
            transition.getChildren().add(entry.getAnimation());
            contentArea.getChildren().add(newActivity.getView());
        }

        final ViewTransition finalExit = exit;
        final ViewTransition finalEntry = entry;
        transition.setOnFinished(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                if (finalEntry != null)
                {
                    finalEntry.cleanupAfterAnimation();
                }
                if (oldActivity != null)
                {
                    contentArea.getChildren().remove(oldActivity.getView());
                }
                if (finalExit != null)
                {
                    finalExit.cleanupAfterAnimation();
                }
                glassPane.setVisible(false);
            }
        });

        glassPane.setVisible(true);
        transition.play();

    }

    //-------------------------------------------------------------------------

    private class CurrentPlaceListener implements ChangeListener<Activity>
    {
        public void changed(ObservableValue<? extends Activity> source, Activity oldActivity, Activity newActivity)
        {
            transition(oldActivity, newActivity);
        }
    }

    private class CurrentActivityListener implements ChangeListener<Place>
    {
        public void changed(ObservableValue<? extends Place> source, Place oldPlace, Place newPlace)
        {
            ActivityManager manager = activityManager.get();
            if (manager != null)
            {
                manager.activate(newPlace);
            }
        }
    }
}
