package com.zenjava.jfxcontact.server;

import java.io.Serializable;

public class Contact implements Serializable
{
    private String firstName;
    private String lastName;

    public Contact(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
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
