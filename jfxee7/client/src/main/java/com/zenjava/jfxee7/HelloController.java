package com.zenjava.jfxee7;

import com.google.inject.Inject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class HelloController
{
    @FXML private Node view;
    @FXML private Label messageLabel;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;

    @Inject private HelloService helloService;

    public Node getView()
    {
        return view;
    }

    public void submit(ActionEvent event)
    {
        // extract person details from the screen
        final Person person = new Person(
                firstNameField.getText(),
                lastNameField.getText()
        );

        // pass the details to the server in a worker thread
        final Task<String> task = new Task<String>()
        {
            protected String call() throws Exception
            {
                return helloService.sayHello(person);
            }
        };

        // handle the result
        task.stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            public void changed(ObservableValue<? extends Worker.State> source,
                                Worker.State oldState, Worker.State newState)
            {
                if (newState.equals(Worker.State.SUCCEEDED))
                {
                    messageLabel.setText(task.getValue());
                }
                else if (newState.equals(Worker.State.FAILED))
                {
                     // always log your system errors with stack trace (please!!!)
                    messageLabel.setText("Oh no, a system error, we're all going to die!");
                    Throwable exception = task.getException();
                    exception.printStackTrace();
                }
            }
        });

        // start the task in a thread
        messageLabel.setText("Contacting server...");
        new Thread(task).start();
    }
}
