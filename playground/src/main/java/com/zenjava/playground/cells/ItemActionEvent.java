package com.zenjava.playground.cells;

import javafx.event.ActionEvent;
import javafx.event.EventTarget;

public class ItemActionEvent<ItemType> extends ActionEvent
{
    private ItemType item;

    public ItemActionEvent()
    {
    }

    public ItemActionEvent(Object source, EventTarget target, ItemType item)
    {
        super(source, target);
        this.item = item;
    }

    public ItemType getItem()
    {
        return item;
    }
}
