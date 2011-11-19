package com.zenjava.playground.browser2.transition;

import javafx.geometry.Bounds;

public abstract class AbstractViewTransition implements ViewTransition
{
    public void setupBeforeAnimation(Bounds bounds)
    {
    }

    public void cleanupAfterAnimation()
    {
    }
}
