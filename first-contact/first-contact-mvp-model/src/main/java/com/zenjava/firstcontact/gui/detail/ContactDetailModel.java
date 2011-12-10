package com.zenjava.firstcontact.gui.detail;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContactDetailModel
{
    private LongProperty id;
    private StringProperty firstName;
    private StringProperty lastName;

    public ContactDetailModel()
    {
        id = new SimpleLongProperty(-1);
        firstName = new SimpleStringProperty("");
        lastName = new SimpleStringProperty("");
    }

    public void clear()
    {
        setId(-1);
        setFirstName("");
        setLastName("");
    }

    public LongProperty idProperty()
    {
        return id;
    }

    public long getId()
    {
        return id.get();
    }

    public void setId(long id)
    {
        this.id.set(id);
    }

    public StringProperty firstNameProperty()
    {
        return firstName;
    }

    public String getFirstName()
    {
        return firstName.get();
    }

    public void setFirstName(String firstName)
    {
        this.firstName.set(firstName);
    }

    public StringProperty lastNameProperty()
    {
        return lastName;
    }

    public String getLastName()
    {
        return lastName.get();
    }

    public void setLastName(String lastName)
    {
        this.lastName.set(lastName);
    }

}
