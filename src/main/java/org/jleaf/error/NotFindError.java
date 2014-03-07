package org.jleaf.error;

@SuppressWarnings("serial")
public class NotFindError extends Error {

    public NotFindError() {
        super();
    }

    public NotFindError(String message) {
        super(message);
    }

    public NotFindError(Throwable cause) {
        super(cause);
    }

}
