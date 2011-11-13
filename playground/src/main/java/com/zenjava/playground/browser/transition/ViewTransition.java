package com.zenjava.playground.browser.transition;

import javafx.animation.Animation;
import javafx.scene.Parent;

public interface ViewTransition
{
    void setupBeforeAnimation(Parent animationCanvas);

    Animation getAnimation();

    void cleanupAfterAnimation();
}
