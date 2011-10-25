package com.zenjava.jfxspring;

import javafx.fxml.FXMLLoader;
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
        return (MainController) loadController("/Main.fxml");
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


    protected Object loadController(String url) throws IOException
    {
        InputStream fxmlStream = null;
        try
        {
            fxmlStream = getClass().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return loader.getController();
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
