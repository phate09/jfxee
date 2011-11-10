package com.zenjava.playground.browser;

public class Place
{
    private String name;

    public Place(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return String.format("Place[%s]", name);
    }
}
