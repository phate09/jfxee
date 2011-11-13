package com.zenjava.playground.browser2.navigation;

import com.zenjava.playground.browser2.activity.AbstractActivity;
import com.zenjava.playground.browser2.activity.Activatable;
import com.zenjava.playground.browser2.activity.HasNode;
import com.zenjava.playground.browser2.navigation.control.BackButton;
import com.zenjava.playground.browser2.navigation.control.ForwardButton;
import com.zenjava.playground.browser2.transition.*;
import javafx.animation.SequentialTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class Browser extends AbstractActivity<Parent>
{
    private StackPane contentArea;
    private Node glassPane;
    private ObjectProperty<NavigationManager> navigationManager;
    private ObjectProperty<HasNode> currentPage;
    private ObservableList<PlaceResolver> placeResolvers;

    public Browser()
    {
        this(new DefaultNavigationManager());
    }

    public Browser(NavigationManager navigationManager)
    {
        this.navigationManager = new SimpleObjectProperty<NavigationManager>();
        this.currentPage = new SimpleObjectProperty<HasNode>();
        this.placeResolvers = FXCollections.observableArrayList();

        // manage current place

        final ChangeListener<? super Place> currentPlaceListener = new ChangeListener<Place>()
        {
            public void changed(ObservableValue<? extends Place> source, Place oldPlace, Place newPlace)
            {
                HasNode newPage = null;
                if (newPlace != null)
                {
                    for (PlaceResolver resolver : placeResolvers)
                    {
                        newPage = resolver.resolvePlace(newPlace);
                        if (newPage != null)
                        {
                            HasNode oldPage = currentPage.get();
                            if (oldPage instanceof Activatable)
                            {
                                ((Activatable) oldPage).deactivate();
                            }

                            if (newPage instanceof Activatable)
                            {
                                ((Activatable) newPage).activate(newPlace.getParameters());
                            }
                            currentPage.set(newPage);
                            return;
                        }
                    }
                }
                currentPage.set(newPage);
            }
        };
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


        // manage current page

        this.currentPage.addListener(new ChangeListener<HasNode>()
        {
            public void changed(ObservableValue<? extends HasNode> source, HasNode oldPage, HasNode newPage)
            {
                transition(oldPage, newPage);
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

        setNode(rootPane);

        setNavigationManager(navigationManager);
    }

    public NavigationManager getNavigationManager()
    {
        return navigationManager.get();
    }

    public void setNavigationManager(NavigationManager navigationManager)
    {
        this.navigationManager.set(navigationManager);
    }

    public ObservableList<PlaceResolver> getPlaceResolvers()
    {
        return placeResolvers;
    }

    protected void transition(final HasNode oldPage, HasNode newPage)
    {
        SequentialTransition transition = new SequentialTransition();

        ViewTransition exit = null;
        if (oldPage != null)
        {
            if (oldPage instanceof HasExitTransition)
            {
                exit = ((HasExitTransition) oldPage).getExitTransition();
            }
            else
            {
                exit = new FadeOutTransition(oldPage.getNode());
            }
            exit.setupBeforeAnimation(contentArea);
            transition.getChildren().add(exit.getAnimation());
        }

        ViewTransition entry = null;
        if (newPage != null)
        {
            if (newPage instanceof HasEntryTransition)
            {
                entry = ((HasEntryTransition) newPage).getEntryTransition();
            }
            else
            {
                entry = new FadeInTransition(newPage.getNode());
            }
            entry.setupBeforeAnimation(contentArea);
            transition.getChildren().add(entry.getAnimation());
            contentArea.getChildren().add(newPage.getNode());
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
                if (oldPage != null)
                {
                    contentArea.getChildren().remove(oldPage.getNode());
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
}
