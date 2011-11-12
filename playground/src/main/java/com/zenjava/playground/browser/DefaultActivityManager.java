package com.zenjava.playground.browser;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Field;
import java.util.Map;

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
                Activity current = currentActivity.get();
                if (current != null)
                {
                    current.setActive(false);
                }

                Activity activity = mapping.getActivity();
                setParameters(activity, place.getParameters());
                activity.setActive(true);
                currentActivity.set(activity);
                return;
            }
        }
        throw new UnsupportedPlaceException("No activity mapping is registered for path: " + place.getName());
    }

    private void setParameters(Activity activity, Map<String, Object> parameters) throws ActivityParameterException
    {
        for (Field field : activity.getClass().getDeclaredFields())
        {
            ActivityParameter annotation = field.getAnnotation(ActivityParameter.class);
            if (annotation != null)
            {
                String name = annotation.value();
                if (name == null || name.equals(""))
                {
                    name = field.getName();
                }

                Object value = parameters.get(name);

                try
                {
                    field.setAccessible(true);
                    if (WritableValue.class.isAssignableFrom(field.getType()))
                    {
                        WritableValue property = (WritableValue) field.get(activity);
                        property.setValue(value);
                    }
                    else
                    {
                        field.set(activity, value);
                    }
                }
                catch (IllegalAccessException e)
                {
                    throw new ActivityParameterException(
                            String.format("Error setting property '%s' on field '%s' in Activity '%s'",
                                    name, field.getName(), activity), e);
                }
            }
        }
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
