package uk.co.unclealex.rokta.filter;

public class IllegalFilterEncodingException extends Exception {

	public IllegalFilterEncodingException() {
	}

	public IllegalFilterEncodingException(String message) {
		super(message);
	}

	public IllegalFilterEncodingException(Throwable cause) {
		super(cause);
	}

	public IllegalFilterEncodingException(String message, Throwable cause) {
		super(message, cause);
	}

}
