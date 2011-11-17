package com.zenjava.playground.cells.demo;

public class Person
{
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName)
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

    public String toString()
    {
        return String.format("Person[firstName='%s', lastName='%s']", firstName, lastName);
    }
}
