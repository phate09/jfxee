package com.zenjava.playground.browser;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.ResourceBundle;

public class FxmlActivityLoader
{
    @SuppressWarnings("unchecked")
    public <ActivityType extends Activity> ActivityType loadActivity(String fxmlFile)
            throws FxmlLoadException
    {
        return (ActivityType) loadActivity(fxmlFile, null, null);
    }

    @SuppressWarnings("unchecked")
    public <ActivityType extends Activity> ActivityType loadActivity(String fxmlFile, String resourceBundle)
            throws FxmlLoadException
    {
        return (ActivityType) loadActivity(fxmlFile, ResourceBundle.getBundle(resourceBundle), null);
    }

    @SuppressWarnings("unchecked")
    public <ActivityType extends Activity> ActivityType loadActivity(String fxmlFile,
                                                                     ResourceBundle resources,
                                                                     Map<String, Object> variables)
            throws FxmlLoadException
    {
        InputStream fxmlStream = null;
        try
        {
            fxmlStream = getClass().getResourceAsStream(fxmlFile);
            FXMLLoader loader = new FXMLLoader();
            loader.setBuilderFactory(new JavaFXBuilderFactory());

            File file = new File(fxmlFile);
            loader.setLocation(file.toURI().toURL());

            if (resources != null)
            {
                loader.setResources(resources);
            }

            if (variables != null)
            {
                loader.getNamespace().putAll(variables);
            }

            Node view = (Node) loader.load(fxmlStream);

            ActivityType activity = (ActivityType) loader.getController();
            if (activity instanceof HasFxmlLoadedView)
            {
                ((HasFxmlLoadedView) activity).setView(view);
            }
            return activity;
        }
        catch (Exception e)
        {
            // map checked exception to a runtime exception - this is a system failure, not a business logic failure
            // so using checked exceptions for this is not necessary.
            throw new FxmlLoadException(String.format(
                    "Unable to load FXML from '%s': %s", fxmlFile, e.getMessage()), e);
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
                    System.err.println("WARNING: error closing FXML stream: " + e);
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}
