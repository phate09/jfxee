package com.zenjava.jfxspring;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

public class SpringFxmlLoader
{
    private ApplicationContext context;

    public SpringFxmlLoader(ApplicationContext context)
    {
        this.context = context;
    }

    public Object load(String url, Class<?> controllerClass) throws Exception
    {
        Object instance = context.getBean(controllerClass);
        FXMLLoader loader = new FXMLLoader();
        loader.getNamespace().put("controller", instance);
        return loader.load(controllerClass.getResourceAsStream(url));
    }
}
