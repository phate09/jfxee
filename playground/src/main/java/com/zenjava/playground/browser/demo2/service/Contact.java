package com.zenjava.playground.browser.demo2.service;

public class Contact
{
    private int contactId;
    private String firstName;
    private String lastName;
    private String company;

    public Contact(int contactId, String firstName, String lastName, String company)
    {
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
    }

    public int getContactId()
    {
        return contactId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getCompany()
    {
        return company;
    }
}
