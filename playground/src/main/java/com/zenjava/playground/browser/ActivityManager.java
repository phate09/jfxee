package com.zenjava.playground.browser;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;

/**
 * Manages a set of registered activities so that only one of the child activities can be active at any given time.
 * Activities are registered via an ActivityMapping, which maps a Place to a specific Activity.
 */
public interface ActivityManager
{
    /**
     * Activates the Activity registered for the specified Place. If multiple ActivityMappings match the specified Place
     * then the first one registered will be used. The activated Activity will become the current Activity for this
     * ActivityManager, and the previous Activity (if any) will be deactivated.
     *
     * @param place the details of the place to be activated.
     * @throws UnsupportedPlaceException if no ActivityMapping is registered that matches the specified Place.
     */
    void activate(Place place) throws UnsupportedPlaceException;

    /**
     * Retrieves the list of registered ActivityMappings for this ActivityManager. This list can be used to register and
     * deregister mappings. The Mapping list is in reverse order of priority, where the most recently added Mapping
     * (i.e. with a higher index) will override mappings added earlier (i.e. with a lower index).
     *
     * @return the list of registered ActivityMappings for this ActivityManager.
     */
    ObservableList<ActivityMapping> getActivityMappings();

    /**
     * Retrieves the observable property for the current active Activity. This property holds the the last activity
     * activated via the activate(Place) method. This value may be null if no previous activity has been activated
     * (e.g. when the ActivityManager is first created). This property is read-only, the current activity should always
     * be updated via the activate() method.
     *
     * @return the observable property for the current active Activity.
     */
    ReadOnlyObjectProperty<Activity> currentActivityProperty();

    /**
     * Retrieves the current active Activity. This will be the last activity activated via the activate(Place) method.
     * This may be null if no previous activity has been activated (e.g. when the ActivityManager is first created).
     *
     * @return the current active Activity.
     */
    Activity getCurrentActivity();
}
