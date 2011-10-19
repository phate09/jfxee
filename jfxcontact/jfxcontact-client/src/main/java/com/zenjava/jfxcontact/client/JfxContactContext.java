package com.zenjava.jfxcontact.client;

import com.zenjava.jfxcontact.client.search.SearchController;
import com.zenjava.jfxcontact.server.JfxContactService;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.CommonsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class JfxContactContext
{
    //-------------------------------------------------------------------------
    // General system resources

    @Bean
    public JfxContactConfig config()
    {
        return new JfxContactConfig("/jfxcontact-client.properties");
    }

    //-------------------------------------------------------------------------
    // Controllers

    @Bean
    public Scene mainScene()
    {
        BorderPane rootNode = new BorderPane();
        rootNode.setCenter(searchController().getView());
        Scene scene = new Scene(rootNode, 1024, 768);
        scene.getStylesheets().add("styles.css");
        return scene;
    }

    @Bean
    public SearchController searchController()
    {
        return new SearchController();
    }

    //-------------------------------------------------------------------------
    // Services

    @Bean
    public JfxContactService jfxContactService()
    {
        return getService(JfxContactService.class);
    }

    //-------------------------------------------------------------------------
    // Helper methods

    @SuppressWarnings("unchecked")
    private <T> T getService(Class<T> type)
    {
        HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
        factory.setServiceUrl(config().getServerUrl() + "/" + type.getSimpleName());
        factory.setServiceInterface(type);
        factory.setHttpInvokerRequestExecutor(httpInvokerRequestExecutor());
        factory.afterPropertiesSet();
        return (T) factory.getObject();
    }

    @Bean
    public CommonsHttpInvokerRequestExecutor httpInvokerRequestExecutor()
    {
        CommonsHttpInvokerRequestExecutor invoker
                = new CommonsHttpInvokerRequestExecutor();
        JfxContactConfig config = config();
        invoker.getHttpClient().getParams().setSoTimeout(config.getServerTimeout());
        return invoker;
    }

}

