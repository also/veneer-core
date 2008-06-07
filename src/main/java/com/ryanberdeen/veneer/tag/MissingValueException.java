package com.ryanberdeen.veneer.tag;

public class MissingValueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MissingValueException() {
	}

	public MissingValueException(String message) {
		super(message);
	}

	public MissingValueException(Throwable cause) {
		super(cause);
	}

	public MissingValueException(String message, Throwable cause) {
		super(message, cause);
	}

}
