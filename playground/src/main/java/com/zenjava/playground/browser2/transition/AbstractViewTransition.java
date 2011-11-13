package com.zenjava.playground.browser2.transition;

import javafx.scene.Parent;

public abstract class AbstractViewTransition implements ViewTransition
{
    public void setupBeforeAnimation(Parent animationCanvas)
    {
    }

    public void cleanupAfterAnimation()
    {
    }
}
