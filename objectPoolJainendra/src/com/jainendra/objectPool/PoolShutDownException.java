package com.jainendra.objectPool;

public class PoolShutDownException extends Exception {
	private static final long serialVersionUID = -6109627199465485670L;

	public PoolShutDownException() {
		super();
	}

	public PoolShutDownException(String message) {
		super(message);
	}

}
