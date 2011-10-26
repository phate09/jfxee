package com.zenjava.jfxspring;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;

public class MainController extends AbstractController
{
    @FXML private BorderPane contentArea;

    @Autowired private Page1Controller page1Controller;
    @Autowired private Page2Controller page2Controller;

    public void showPage1(ActionEvent event)
    {
        contentArea.setCenter(page1Controller.getView());
    }

    public void showPage2(ActionEvent event)
    {
        contentArea.setCenter(page2Controller.getView());
    }
}
