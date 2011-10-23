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
    public Person person()
    {
        return new Person("Richard");
    }

    @Bean
    public SampleController sampleController() throws IOException
    {
        return (SampleController) loadController("/sample.fxml");
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
