package com.zenjava.firstcontact.gui;

import com.zenjava.firstcontact.gui.detail.ContactDetailPresenter;
import com.zenjava.firstcontact.gui.login.LoginPresenter;
import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.gui.search.ContactSearchPresenter;
import com.zenjava.firstcontact.service.ContactService;
import com.zenjava.firstcontact.service.SecurityService;
import javafx.fxml.FXMLLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.CommonsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;

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
    public LoginPresenter loginPresenter()
    {
        return loadPresenter("/fxml/Login.fxml");
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
    public SecurityService securityService()
    {
        return createService("security.service", SecurityService.class);
    }

    @Bean
    public ContactService contactService()
    {
        return createService("contact.service", ContactService.class);
    }

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    protected <T> T createService(String endPoint, Class<T> serviceInterface)
    {
        HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
        String serverUrl = String.format("http://localhost:8080/%s", endPoint);
        factory.setServiceUrl(serverUrl);
        factory.setServiceInterface(serviceInterface);
        factory.setHttpInvokerRequestExecutor(httpInvokerRequestExecutor());
        factory.afterPropertiesSet();
        return (T) factory.getObject();        
    }

    @Bean
    public HttpInvokerRequestExecutor httpInvokerRequestExecutor()
    {
        return new CommonsHttpInvokerRequestExecutor();
    }
}
