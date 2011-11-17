package com.zenjava.playground.cells;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ListView<ItemType> extends VBox
{
    private ObservableList<ItemType> items;
    private CellFactory<ItemType> cellFactory;
    private EventHandler<ItemActionEvent<ItemType>> onItemActionHandler;
    private int itemActionMouseClickCount = 1; // make configurable

    public ListView(ObservableList<ItemType> items, CellFactory<ItemType> cellFactory)
    {
        this.items = items;
        this.cellFactory = cellFactory;
        this.items.addListener(new ListChangeListener<ItemType>()
        {
            public void onChanged(Change<? extends ItemType> change)
            {
                updateCells();
            }
        });
        updateCells();

        setFillWidth(true);
        setStyle("-fx-border-width: 1; -fx-border-color: gray; -fx-padding: 4");
    }

    public ObservableList<ItemType> getItems()
    {
        return items;
    }

    // called 'onAction' as it should also work for enter key, not just mouse click
    public void setOnItemAction(EventHandler<ItemActionEvent<ItemType>> handler)
    {
        this.onItemActionHandler = handler;
    }

    private void updateCells()
    {
        getChildren().clear();
        for (int i = 0; i < items.size(); i++)
        {
            final ItemType item = items.get(i);
            Cell<ItemType> cell = cellFactory.createCell();
            cell.setValue(item);
            cell.getNode().setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                    if (onItemActionHandler != null)
                    {
                        System.out.println("Item selected in list: " + item);
                        onItemActionHandler.handle(new ItemActionEvent<ItemType>(
                                ListView.this, ListView.this, item));
                    }
                }
            });

            HBox row = new HBox();
            HBox.setHgrow(cell.getNode(), Priority.ALWAYS);
            row.getChildren().add(cell.getNode());
            row.setStyle(i % 2 == 0 ? "-fx-background-color: #eef" : "-fx-background-color: white");
            getChildren().add(row);
        }
    }
}
