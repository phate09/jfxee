package com.zenjava.playground.browser.transition;

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
