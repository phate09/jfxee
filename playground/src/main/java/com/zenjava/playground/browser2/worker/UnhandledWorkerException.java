package com.zenjava.playground.browser2.worker;

public class UnhandledWorkerException extends RuntimeException
{
    public UnhandledWorkerException(String message)
    {
        super(message);
    }

    public UnhandledWorkerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
