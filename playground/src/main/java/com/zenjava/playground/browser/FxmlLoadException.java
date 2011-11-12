package com.zenjava.playground.browser;

public class FxmlLoadException extends RuntimeException
{
    public FxmlLoadException(String message)
    {
        super(message);
    }

    public FxmlLoadException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
