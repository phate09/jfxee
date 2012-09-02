package com.zenjava.jfxbrowser;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

public class JfxBrowser extends Application {

    private ObjectProperty<HistoryEntry> currentPlace;
    private ObservableList<HistoryEntry> backHistory;
    private ObservableList<HistoryEntry> forwardHistory;

    private TextField urlField;
    private Node progressIndicator;
    private BorderPane contentArea;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        this.currentPlace = new SimpleObjectProperty<HistoryEntry>();
        this.backHistory = FXCollections.observableArrayList();
        this.forwardHistory = FXCollections.observableArrayList();

        Scene scene = new Scene(buildView(), 1200, 768);
        stage.setScene(scene);
        stage.show();

        goTo("http://zenjava.com/demo/browser/fxml/hello-world.fxml");
    }

    public void goTo(final String urlPath) {

        final HistoryEntry historyEntry = new HistoryEntry(urlPath);
        historyEntry.setNode(progressIndicator);

        this.forwardHistory.clear();
        HistoryEntry currentPlace = this.currentPlace.get();
        if (currentPlace != null) {
            backHistory.add(backHistory.size(), currentPlace);
        }
        this.currentPlace.set(historyEntry);

        Task<Node> task = new Task<Node>() {
            @Override
            protected Node call() throws Exception {
                // is it ok to load a node in a background thread?
                URL url = new URL(urlPath);
                return FXMLLoader.load(url);
            }

            @Override
            protected void failed() {
                StringWriter errors = new StringWriter();
                getException().printStackTrace(new PrintWriter(errors));
                TextArea textArea = new TextArea(errors.getBuffer().toString());
                historyEntry.setNode(textArea);
            }

            @Override
            protected void succeeded() {
                Node node = getValue();
                wireUpHyperlinks(node);
                historyEntry.setNode(node);
            }
        };

        new Thread(task).start();
    }

    public void goBack() {
        if (backHistory.size() > 0) {
            HistoryEntry currentPlace = this.currentPlace.get();
            if (currentPlace != null) {
                forwardHistory.add(0, currentPlace);
            }

            HistoryEntry backPlace = backHistory.remove(backHistory.size() - 1);
            this.currentPlace.set(backPlace);
        }
    }

    public void goForward() {
        if (forwardHistory.size() > 0) {
            HistoryEntry currentPlace = this.currentPlace.get();
            if (currentPlace != null) {
                backHistory.add(backHistory.size(), currentPlace);
            }

            HistoryEntry backPlace = forwardHistory.remove(0);
            this.currentPlace.set(backPlace);
        }
    }

    private void wireUpHyperlinks(Node node) {

        if (node instanceof Hyperlink) {
            final Hyperlink link = (Hyperlink) node;
            link.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    String url = (String) link.getUserData();
                    if (url != null) {
                        goTo(url);
                    }
                }
            });

        } else if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                wireUpHyperlinks(child);
            }
        }
    }


    private Parent buildView() {

        GridPane progressView = new GridPane();
        progressView.setAlignment(Pos.CENTER);
        progressView.getChildren().add(new ProgressIndicator());
        progressIndicator = progressView;
        BorderPane.setAlignment(progressIndicator, Pos.CENTER);

        BorderPane pane = new BorderPane();

        pane.setTop(buildNavBar());

        contentArea = new BorderPane();
        currentPlace.addListener(new ChangeListener<HistoryEntry>() {
            @Override
            public void changed(ObservableValue<? extends HistoryEntry> source, HistoryEntry oldPlace, HistoryEntry newPlace) {
                contentArea.centerProperty().unbind();
                contentArea.setCenter(null);
                if (newPlace != null) {
                    urlField.setText(newPlace.getUrl());
                    contentArea.centerProperty().bind(newPlace.nodeProperty());
                }
            }
        });
        pane.setCenter(contentArea);

        return pane;
    }

    private Node buildNavBar() {

        HBox navBar = new HBox(10);
        navBar.setStyle("-fx-background-color:linear-gradient(#ddd, #999); -fx-padding: 10px;");

        final Button backButton = new Button("<");
        backButton.setDisable(true);
        backHistory.addListener(new InvalidationListener() {
            public void invalidated(Observable source) {
                backButton.setDisable(backHistory.isEmpty());
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                goBack();
            }
        });
        navBar.getChildren().add(backButton);

        final Button forwardButton = new Button(">");
        forwardButton.setDisable(true);
        forwardHistory.addListener(new InvalidationListener() {
            public void invalidated(Observable source) {
                forwardButton.setDisable(forwardHistory.isEmpty());
            }
        });
        forwardButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                goForward();
            }
        });
        navBar.getChildren().add(forwardButton);

        urlField = new TextField();
        HBox.setHgrow(urlField, Priority.ALWAYS);
        urlField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String url = urlField.getText();
                goTo(url);
            }
        });
        navBar.getChildren().add(urlField);

        return navBar;
    }

    //-------------------------------------------------------------------------

    private class HistoryEntry {

        private String url;
        private ObjectProperty<Node> node;

        private HistoryEntry(String url) {
            this.url = url;
            this.node = new SimpleObjectProperty<Node>();
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public ObjectProperty<Node> nodeProperty() {
            return node;
        }

        public Node getNode() {
            return node.get();
        }

        public void setNode(Node node) {
            this.node.set(node);
        }
    }
}
