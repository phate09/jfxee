package com.zenjava.playground.browser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDefaultActivityManager
{
    private DefaultActivityManager activityManager;

    @Before
    public void setup()
    {
        activityManager = new DefaultActivityManager();
    }

    @Test(expected = UnsupportedPlaceException.class)
    public void testUnsupportedPlace() throws UnsupportedPlaceException
    {
        activityManager.activate(new Place("somewhere"));
    }

    @Test
    public void testSimpleActivation() throws Exception
    {
        DefaultActivityManager activityManager = new DefaultActivityManager();

        SimpleActivity activity1 = new SimpleActivity("Activity1");

        activityManager.getActivityMappings().add(new RegexActivityMapping("test", activity1));
        activityManager.activate(new Place("test"));
        assertEquals("Current activity is not as expected", activity1, activityManager.getCurrentActivity());
    }

    @Test
    public void testRegExActivation() throws Exception
    {
        DefaultActivityManager activityManager = new DefaultActivityManager();

        SimpleActivity activity1 = new SimpleActivity("Activity1");
        SimpleActivity activity2 = new SimpleActivity("Activity2");

        activityManager.getActivityMappings().add(new RegexActivityMapping("[ab]", activity1));
        activityManager.getActivityMappings().add(new RegexActivityMapping("[cd]", activity2));

        activityManager.activate(new Place("a"));
        assertEquals("Current activity is not as expected", activity1, activityManager.getCurrentActivity());

        activityManager.activate(new Place("c"));
        assertEquals("Current activity is not as expected", activity2, activityManager.getCurrentActivity());

        activityManager.activate(new Place("b"));
        assertEquals("Current activity is not as expected", activity1, activityManager.getCurrentActivity());

        activityManager.activate(new Place("d"));
        assertEquals("Current activity is not as expected", activity2, activityManager.getCurrentActivity());
    }

    @Test
    public void testOrderOfActivation() throws Exception
    {
        DefaultActivityManager activityManager = new DefaultActivityManager();

        SimpleActivity activity1 = new SimpleActivity("Activity1");
        SimpleActivity activity2 = new SimpleActivity("Activity2");

        activityManager.getActivityMappings().add(new RegexActivityMapping("test", activity1));
        activityManager.getActivityMappings().add(new RegexActivityMapping("test", activity2));

        activityManager.activate(new Place("test"));
        assertEquals("Current activity is not as expected", activity2, activityManager.getCurrentActivity());
    }

    @Test
    public void testMappingRemoval() throws Exception
    {
        DefaultActivityManager activityManager = new DefaultActivityManager();

        SimpleActivity activity1 = new SimpleActivity("Activity1");
        SimpleActivity activity2 = new SimpleActivity("Activity2");
        SimpleActivity activity3 = new SimpleActivity("Activity3");

        activityManager.getActivityMappings().add(new RegexActivityMapping("test", activity1));
        activityManager.getActivityMappings().add(new RegexActivityMapping("test", activity2));
        activityManager.getActivityMappings().add(new RegexActivityMapping("test", activity3));

        activityManager.activate(new Place("test"));
        assertEquals("Current activity is not as expected", activity3, activityManager.getCurrentActivity());

        activityManager.getActivityMappings().remove(1);
        activityManager.activate(new Place("test"));
        assertEquals("Current activity is not as expected", activity3, activityManager.getCurrentActivity());

        activityManager.getActivityMappings().remove(1);
        activityManager.activate(new Place("test"));
        assertEquals("Current activity is not as expected", activity1, activityManager.getCurrentActivity());
    }

}
