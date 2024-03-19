package com.epf.rentmanager.Exception;

public class ServiceException extends Exception {
    public ServiceException(Throwable rootCause) {
        super(rootCause);
    }

    public ServiceException() {
        super("Service exception");
    }
}