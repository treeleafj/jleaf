package org.jleaf.error;

/**
 * leaf
 * 14-3-1 下午6:44.
 */
public class ResultError extends Error {

    public ResultError() {
        super();
    }

    public ResultError(String message) {
        super(message);
    }

    public ResultError(Throwable cause) {
        super(cause);
    }
}
