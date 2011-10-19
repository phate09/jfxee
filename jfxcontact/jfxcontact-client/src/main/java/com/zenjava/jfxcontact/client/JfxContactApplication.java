package com.zenjava.jfxcontact.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JfxContactApplication extends Application
{
    public static void main(String[] args)
    {
        Application.launch(JfxContactApplication.class, args);
    }

    public void start(Stage stage) throws Exception
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JfxContactContext.class);
        JfxContactContext jfxContactContext = context.getBean(JfxContactContext.class);
        stage.setScene(jfxContactContext.mainScene());
        stage.show();


    }
}
