package com.epf.rentmanager.Exception;

public class DaoException extends Exception {
    public DaoException(Throwable rootCause) {
        super(rootCause);
    }

    public DaoException() {
        super("Dao Exception");
    }
}
