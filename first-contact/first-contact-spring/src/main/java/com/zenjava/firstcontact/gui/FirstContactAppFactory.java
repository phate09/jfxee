package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;
import com.zenjava.firstcontact.service.ContactService;
import com.zenjava.firstcontact.service.SimpleContactService;
import javafx.fxml.FXMLLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FirstContactAppFactory
{
    @Bean
    public MainPresenter mainPresenter()
    {
        return loadPresenter("/fxml/Main.fxml");
    }

    @Bean
    public ContactSearchPresenter contactSearchPresenter()
    {
        return loadPresenter("/fxml/ContactSearch.fxml");
    }

    @Bean
    public ContactDetailPresenter contactDetailPresenter()
    {
        return loadPresenter("/fxml/ContactDetail.fxml");
    }

    @Bean
    public ContactService contactService()
    {
        return new SimpleContactService();
    }

    private <T> T loadPresenter(String fxmlFile)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.load(getClass().getResourceAsStream(fxmlFile));
            return (T) loader.getController();
        }
        catch (IOException e)
        {
            throw new RuntimeException(String.format("Unable to load FXML file '%s'", fxmlFile), e);
        }
    }
}
