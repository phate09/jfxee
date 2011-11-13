package com.zenjava.playground.browser2.activity;

public class ActivationException extends RuntimeException
{
    public ActivationException(String message)
    {
        super(message);
    }

    public ActivationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
