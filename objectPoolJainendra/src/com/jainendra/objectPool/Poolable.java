package com.jainendra.objectPool;

public interface Poolable {
	void close();

	boolean isClosed();
}
