package com.zenjava.playground.browser2.transition;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Duration;

public class FlyInTransition extends AbstractViewTransition
{
    private Node targetNode;
    private double startY;

    public FlyInTransition(Node targetNode)
    {
        this.targetNode = targetNode;
    }

    public void setupBeforeAnimation(Parent animationCanvas)
    {
        startY = animationCanvas.getBoundsInParent().getMaxY();
        targetNode.setTranslateY(startY);
    }

    public Animation getAnimation()
    {
        TranslateTransition translation = new TranslateTransition(Duration.millis(500), targetNode);
        translation.setFromY(startY);
        translation.setToY(0);
        return translation;
    }
}
