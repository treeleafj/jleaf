package org.jleaf.error;

@SuppressWarnings("serial")
public class HttpMethodMatchingError extends NotFindError {

	public HttpMethodMatchingError() {
		super();
	}

	public HttpMethodMatchingError(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpMethodMatchingError(String message) {
		super(message);
	}

	public HttpMethodMatchingError(Throwable cause) {
		super(cause);
	}

}
