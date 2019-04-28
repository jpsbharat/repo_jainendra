package com.jainendra.objectPool.test;

import com.jainendra.objectPool.Poolable;

public class SampelPoolableObject implements Poolable {
	private boolean closed = false;

	@Override
	public void close() {
		this.closed = true;
	}

	@Override
	public boolean isClosed() {
		return this.closed;
	}

	public void setClosed(boolean colsed) {
		this.closed = colsed;
	}
}
