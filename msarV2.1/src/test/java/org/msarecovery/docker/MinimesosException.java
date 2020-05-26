package org.msarecovery.docker;

public class MinimesosException extends RuntimeException {

    public MinimesosException(String message) {
        super(message);
    }

    public MinimesosException(String message, Throwable cause) {
        super(message, cause);
    }

}