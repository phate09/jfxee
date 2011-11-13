package com.zenjava.playground.browser2.activity;

import javafx.scene.Node;

public interface HasNode<NodeType extends Node>
{
    NodeType getNode();
}
