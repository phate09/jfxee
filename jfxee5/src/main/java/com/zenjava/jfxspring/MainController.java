package com.zenjava.jfxspring;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;

public class MainController extends AbstractController
{
    @FXML private HBox buttonArea;
    @FXML private BorderPane contentArea;

    private Map<String, Controller> controllers;

    public MainController()
    {
        controllers = new HashMap<String, Controller>();
    }

    public void addPage(final String name, Controller controller)
    {
        controllers.put(name, controller);
        Button button = new Button(name);
        button.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent actionEvent)
            {
                showPage(name);
            }
        });
        buttonArea.getChildren().add(button);
    }

    public void showPage(String name)
    {
        Controller controller = controllers.get(name);
        contentArea.setCenter(controller.getView());
    }
}
