package org.jleaf.error;

/**
 * leaf
 * 14-3-1 下午5:13.
 */
@SuppressWarnings("serial")
public class RenderError extends Error {

    public RenderError() {
        super();
    }

    public RenderError(String message) {
        super(message);
    }

    public RenderError(Throwable cause) {
        super(cause);
    }
}
