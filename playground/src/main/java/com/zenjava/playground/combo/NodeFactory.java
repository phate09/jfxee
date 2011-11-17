package com.zenjava.playground.combo;

import javafx.scene.Node;

public interface NodeFactory<DataType>
{
    Node createNode(DataType data);
}
