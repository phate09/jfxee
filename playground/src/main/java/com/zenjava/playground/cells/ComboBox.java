package com.zenjava.playground.cells;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuButton;

public class ComboBox<ItemType> extends MenuButton // using a menu button just for ease - would obviously be a Control + Skin
{
    private Cell<ItemType> currentItemCell;
    private ItemChooser<ItemType> itemChooser;

    public ComboBox(Cell<ItemType> currentItemCell)
    {
        this.currentItemCell = currentItemCell;

        setGraphic(currentItemCell.getNode());
        setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
                ComboBox.this.itemChooser.setActive(true);
            }
        });
    }

    public void setItemChooser(ItemChooser<ItemType> itemChooser)
    {
        this.itemChooser = itemChooser;
        this.itemChooser.selectedItemProperty().addListener(new ChangeListener<ItemType>()
        {
            public void changed(ObservableValue<? extends ItemType> source, ItemType oldValue, ItemType newValue)
            {
                currentItemCell.setValue(newValue);
            }
        });
    }
}
