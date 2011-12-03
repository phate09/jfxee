package com.zenjava.jfxforms;

import com.zenjava.jfxforms.framework.FormManager;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TestFormApplication extends Application
{
    public static void main(String[] args)
    {
        Application.launch(TestFormApplication.class, args);
    }

    public void start(Stage stage) throws Exception
    {
        BorderPane root = new BorderPane();

        PersonForm personForm = new PersonForm();
        root.setTop(buildFormControlArea(personForm.getFormManager()));
        root.setCenter(personForm);

        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.show();
    }

    private Node buildFormControlArea(FormManager formManager)
    {
        HBox box = new HBox(10);
        box.getStyleClass().add("form-control-bar");

        CheckBox autoValidateCheckBox = new CheckBox("Auto validate");
        formManager.autoValidatingProperty().bindBidirectional(autoValidateCheckBox.selectedProperty());
        box.getChildren().add(autoValidateCheckBox);

        CheckBox showHighlightsCheckBox = new CheckBox("Highlights");
        showHighlightsCheckBox.setSelected(true);
        formManager.showHighlightsProperty().bindBidirectional(showHighlightsCheckBox.selectedProperty());
        box.getChildren().add(showHighlightsCheckBox);

        CheckBox showTooltipsBox = new CheckBox("Tooltips");
        showTooltipsBox.setSelected(true);
        formManager.showTooltipsProperty().bindBidirectional(showTooltipsBox.selectedProperty());
        box.getChildren().add(showTooltipsBox);

        CheckBox showAnnotationsCheckBox = new CheckBox("Markers");
        showAnnotationsCheckBox.setSelected(true);
        formManager.showAnnotationsProperty().bindBidirectional(showAnnotationsCheckBox.selectedProperty());
        box.getChildren().add(showAnnotationsCheckBox);

        return box;
    }
}
