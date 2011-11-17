package com.zenjava.playground.cells;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Popup;

public class PopupListItemChooser<ItemType> implements ItemChooser<ItemType>
{
    private Node targetNode;
    private Popup popup;
    private ListView<ItemType> listView;
    private ObjectProperty<ItemType> selectedItem;

    public PopupListItemChooser(Node targetNode, ListView<ItemType> listView)
    {
        this.targetNode = targetNode;
        this.listView = listView;

        this.selectedItem = new SimpleObjectProperty<ItemType>();
        this.popup = new Popup();
        this.popup.getContent().add(this.listView);
        this.popup.setAutoHide(true);
        this.popup.setHideOnEscape(true);
        this.popup.setAutoFix(true);

        this.listView.setOnItemAction(new EventHandler<ItemActionEvent<ItemType>>()
        {
            public void handle(ItemActionEvent<ItemType> event)
            {
                selectedItem.set(event.getItem());
                popup.hide();
            }
        });
    }

    public void setActive(boolean active)
    {
        Parent parent = targetNode.getParent();
        Bounds childBounds = targetNode.getBoundsInParent();
        Bounds parentBounds = parent.localToScene(parent.getBoundsInLocal());
        double layoutX = childBounds.getMinX()
                + parentBounds.getMinX() + parent.getScene().getX() + parent.getScene().getWindow().getX();
        double layoutY = childBounds.getMaxY()
                + parentBounds.getMinY() + parent.getScene().getY() + parent.getScene().getWindow().getY();
        popup.show(targetNode, layoutX, layoutY);
    }

    public ObjectProperty<ItemType> selectedItemProperty()
    {
        return selectedItem;
    }
}
