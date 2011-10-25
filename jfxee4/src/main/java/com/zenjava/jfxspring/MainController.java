package com.zenjava.jfxspring;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;

public class MainController
{
    @FXML private Parent view;
    @FXML private BorderPane contentArea;

    @Autowired private Page1Controller page1Controller;
    @Autowired private Page2Controller page2Controller;

    public Parent getView()
    {
        return view;
    }

    public void showPage1(ActionEvent event)
    {
        contentArea.setCenter(page1Controller.getView());
    }

    public void showPage2(ActionEvent event)
    {
        contentArea.setCenter(page2Controller.getView());
    }
}
