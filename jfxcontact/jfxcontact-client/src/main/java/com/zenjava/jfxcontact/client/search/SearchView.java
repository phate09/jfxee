package com.zenjava.jfxcontact.client.search;

import com.zenjava.jfxcontact.server.Contact;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.tbee.javafx.scene.layout.MigPane;

import java.util.List;

// todo replace this with FXML
public class SearchView extends MigPane
{
    private TextField searchField;
    private TableView<Contact> resultsTable;
    private SearchController controller;

    public SearchView(SearchController controller)
    {
        this.controller = controller;
        buildView();
    }

    protected void doSearch()
    {
        String phrase = searchField.getText();
        String[] terms = phrase != null && phrase.trim().length() > 0 ? phrase.split("\\s+") : null;
        this.controller.search(terms);
    }

    public void setSearchResults(List<Contact> contacts)
    {
        resultsTable.setItems(FXCollections.observableArrayList(contacts));
    }

    private void buildView()
    {
        setLayout("fill, ins 60");
        setCols("[grow 0]20[grow]");
        setRows("[grow 0]20[grow]");

        searchField = new TextField();
        searchField.setPrefColumnCount(30);
        searchField.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent actionEvent)
            {
                doSearch();
            }
        });
        add(searchField, "grow");

        Button searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent actionEvent)
            {
                doSearch();
            }
        });
        add(searchButton, "wrap");

        resultsTable = new TableView<Contact>();

        TableColumn<Contact, String> firstNameColumn = new TableColumn<Contact, String>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        firstNameColumn.setPrefWidth(400);
        resultsTable.getColumns().add(firstNameColumn);

        TableColumn<Contact, String> lastNameColumn = new TableColumn<Contact, String>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        lastNameColumn.setPrefWidth(400);
        resultsTable.getColumns().add(lastNameColumn);

        add(resultsTable, "grow, span");
    }

}
