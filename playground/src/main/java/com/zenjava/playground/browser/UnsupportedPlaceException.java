package com.zenjava.playground.browser;

public class UnsupportedPlaceException extends RuntimeException
{
    public UnsupportedPlaceException(String message)
    {
        super(message);
    }

    public UnsupportedPlaceException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
