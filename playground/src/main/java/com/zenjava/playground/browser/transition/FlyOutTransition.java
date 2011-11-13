package com.zenjava.playground.browser.transition;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Duration;

public class FlyOutTransition extends AbstractViewTransition
{
    private Node targetNode;
    private double endY;

    public FlyOutTransition(Node targetNode)
    {
        this.targetNode = targetNode;
    }

    public void setupBeforeAnimation(Parent animationCanvas)
    {
        endY = animationCanvas.getBoundsInParent().getMaxY();
        targetNode.setTranslateY(endY);
    }

    public Animation getAnimation()
    {
        TranslateTransition translation = new TranslateTransition(Duration.millis(500), targetNode);
        translation.setFromY(0);
        translation.setToY(endY);
        return translation;
    }
}
