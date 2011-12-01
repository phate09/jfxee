package com.zenjava.playground.forms.data;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Person implements Serializable
{
    @NotNull
    private Gender gender;

    @NotEmpty
    @Size(min = 2, max = 10)
    private String firstName;

    @NotEmpty
    @Size(min = 4, max = 10)
    private String lastName;

    public Person()
    {
    }

    public Person(Gender gender, String firstName, String lastName)
    {
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
