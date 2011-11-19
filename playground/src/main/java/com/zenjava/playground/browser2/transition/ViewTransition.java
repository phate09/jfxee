package com.zenjava.playground.browser2.transition;

import javafx.animation.Animation;
import javafx.geometry.Bounds;

public interface ViewTransition
{
    void setupBeforeAnimation(Bounds bounds);

    Animation getAnimation();

    void cleanupAfterAnimation();
}
