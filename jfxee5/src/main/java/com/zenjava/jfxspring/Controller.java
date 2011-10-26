package com.zenjava.jfxspring;

import javafx.scene.Node;

public interface Controller
{
    Node getView();

    /**
     * This method is needed only to allow the loadController() method in SampleAppFactory to
     * inject the view into this controller. Ideally FXML would allow us to specify @FXML on
     * in AbstractController on the 'view' field and this method would not be needed.
     */
    void setView(Node view);
}
