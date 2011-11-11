package com.zenjava.playground.browser;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Provides a default implementation of an ActivityManager with standard support for managing the ActivityMappings
 * and processing these to match a specific Place to an Activity. This class can be used as is for standard Activity
 * management, or can be sub-classed for specific requirements.
 */
public class DefaultActivityManager implements ActivityManager
{
    private ObjectProperty<Activity> currentActivity;
    private ObservableList<ActivityMapping> activityMappings;

    public DefaultActivityManager()
    {
        this.currentActivity = new SimpleObjectProperty<Activity>();
        this.activityMappings = FXCollections.observableArrayList();
    }

    public ObservableList<ActivityMapping> getActivityMappings()
    {
        return activityMappings;
    }

    public void activate(Place place) throws UnsupportedPlaceException
    {
        for (int i = activityMappings.size() - 1; i >= 0; i--)
        {
            ActivityMapping mapping = activityMappings.get(i);
            if (mapping.isMatch(place))
            {
                currentActivity.set(mapping.getActivity());
                return;
            }
        }
        throw new UnsupportedPlaceException("No activity mapping is registered for path: " + place.getName());
    }

    public ReadOnlyObjectProperty<Activity> currentActivityProperty()
    {
        return currentActivity;
    }

    public Activity getCurrentActivity()
    {
        return currentActivity.get();
    }
}
