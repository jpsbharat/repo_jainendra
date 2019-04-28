package com.jainendra.objectPool;

public class NoObjectInThePoolException extends Exception {
	private static final long serialVersionUID = 1776769185741696517L;

	public NoObjectInThePoolException() {
		super();
	}

	public NoObjectInThePoolException(String message) {
		super(message);
	}

}
