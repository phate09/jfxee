package com.zenjava.firstcontact.gui.login;

import com.zenjava.firstcontact.gui.main.MainPresenter;
import com.zenjava.firstcontact.service.SecurityService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import javax.inject.Inject;

public class LoginPresenter
{
    private static final Logger log = LoggerFactory.getLogger(LoginPresenter.class);

    @FXML private Node root;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusText;

    @Inject private SecurityService securityService;
    @Inject private MainPresenter mainPresenter;

    public Node getView()
    {
        return root;
    }

    public void login(ActionEvent event)
    {
        statusText.setText(null);

        final String username = usernameField.getText();
        final String password = passwordField.getText();

        final Task<Void> loginTask = new Task<Void>()
        {
            protected Void call() throws Exception
            {
                // never log password information!
                log.info("Logging in as user '{}'", username);
                securityService.login(username, password);
                return null;
            }
        };

        loginTask.stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            public void changed(ObservableValue<? extends Worker.State> source, Worker.State oldState, Worker.State newState)
            {
                if (newState.equals(Worker.State.SUCCEEDED))
                {
                    log.info("Successfully logged in as user '{}'", username);
                    mainPresenter.showSearchContacts();
                }
                else if (newState.equals(Worker.State.FAILED))
                {
                    Throwable exception = loginTask.getException();
                    if (exception instanceof BadCredentialsException)
                    {
                        log.debug("Invalid login attempt");
                        statusText.setText("Invalid username or password");
                    }
                    else
                    {
                        log.error("Login failed", exception);
                        statusText.setText("Login error: " + exception);
                    }
                }
            }
        });

        new Thread(loginTask).start();
    }
}
