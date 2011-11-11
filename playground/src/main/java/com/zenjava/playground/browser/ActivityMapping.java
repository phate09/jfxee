package com.zenjava.playground.browser;

/**
 * Provides a mapping between a Place and an Activity. The exact strategy used for mapping the Place to the Activity is
 * up to the implementing class.
 */
public interface ActivityMapping
{
    /**
     * Determines whether this mapping is a match for the specified pPlace or not. Implementations should use their
     * specific matching strategy to determine whether the Place is a match or not.
     *
     * @param place the Place to match to this mapping.
     * @return true if the Place matches this mapping, false if it does not.
     */
    public boolean isMatch(Place place);

    /**
     * Retrieves the Activity that this mapping is for. If a Place is a match for this mapping then, this is the
     * Activity that should be used.
     *
     * @return the Activity for this mapping.
     */
    public Activity getActivity();
}
