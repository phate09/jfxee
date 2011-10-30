package com.zenjava.jfxspring;

import com.google.inject.AbstractModule;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.InputStream;

public class SampleAppModule extends AbstractModule
{
    protected void configure()
    {
        bind(Person.class).toInstance(new Person("Richard"));

        // OPTION 1: bind to an instance
        bind(SampleController.class).toInstance((SampleController) loadController("/sample.fxml"));
    }

    // OPTION 2: use a provider
//    @Provides
//    public SampleController sampleController(Person person)
//    {
//        System.out.println("Creating controller");
//        SampleController controller = (SampleController) loadController("/sample.fxml");
//        controller.setPerson(person);
//        return controller;
//    }

    protected Object loadController(String url)
    {
        InputStream fxmlStream = null;
        try
        {
            fxmlStream = getClass().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return loader.getController();
        }
        catch (Exception e)
        {
            // FXML load exceptions are really system failures, and can be treated as RuntimeExceptions
            throw new RuntimeException(String.format("Error loading FXML file '%s'", url), e);
        }
        finally
        {
            if (fxmlStream != null)
            {
                try
                {
                    fxmlStream.close();
                }
                catch (IOException e)
                {
                    System.out.println("Warning: failed to close FXML file");
                }
            }
        }
    }
}
