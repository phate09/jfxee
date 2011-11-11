package com.zenjava.playground.browser;

import javafx.scene.Node;

/**
 * A self-contained user 'activity' that consists of the display (i.e. the view) and the control for that display.
 * Activities are the fundamental building block of a navigation-based application and developers should implement this
 * interface to provide custom activities to be shown in an ActivityBrowser or other ActivityContainer.
 *
 * @param <ViewType> the type of view used by this Activity (typically the root node of the view).
 */
public interface Activity<ViewType extends Node>
{
    /**
     * Retrieves the view for rendering this activity. This can be added to a Scene or other Parent node to allow users
     * to interact with this Activity.
     *
     * @return the view for rendering this activity.
     */
    ViewType getView();
}
