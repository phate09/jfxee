package com.zenjava.jfxspring;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SampleAppFactory
{
    @Bean
    public MainController mainController() throws IOException
    {
        MainController mainController = (MainController) loadController("/Main.fxml");
        mainController.addPage("Page 1", page1Controller());
        mainController.addPage("Page 2", page2Controller());
        return mainController;
    }

    @Bean
    public Page1Controller page1Controller() throws IOException
    {
        return (Page1Controller) loadController("/Page1.fxml");
    }

    @Bean
    public Page2Controller page2Controller() throws IOException
    {
        return (Page2Controller) loadController("/Page2.fxml");
    }

    protected Controller loadController(String url) throws IOException
    {
        InputStream fxmlStream = null;
        try
        {
            fxmlStream = getClass().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            Node view = (Node) loader.load(fxmlStream);
            Controller controller = (Controller) loader.getController();
            controller.setView(view);
            return controller;
        }
        finally
        {
            if (fxmlStream != null)
            {
                fxmlStream.close();
            }
        }
    }
}
