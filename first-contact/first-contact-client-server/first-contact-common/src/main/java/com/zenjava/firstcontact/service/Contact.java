package com.zenjava.firstcontact.service;

import java.io.Serializable;

public class Contact implements Serializable
{
    private long id;
    private String firstName;
    private String lastName;

    public Contact(long id, String firstName, String lastName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }
}
