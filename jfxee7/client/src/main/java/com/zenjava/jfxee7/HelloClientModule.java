package com.zenjava.jfxee7;

import com.caucho.hessian.client.HessianProxyFactory;
import com.google.inject.AbstractModule;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class HelloClientModule extends AbstractModule
{
    protected void configure()
    {
        // NOTE: this is not best-practice for exception handling. Check the
        // Guice documentation for better ways.

        try
        {
            HessianProxyFactory factory = new HessianProxyFactory();
            String serverUrl = "http://localhost:8080/hello";
            HelloService helloService = (HelloService) factory.create(HelloService.class, serverUrl);
            bind(HelloService.class).toInstance(helloService);

            bind(HelloController.class).toInstance((HelloController) loadController("/Hello.fxml"));
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(String.format("Error connecting to server"), e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(String.format("Error loading FXML file"), e);
        }
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
