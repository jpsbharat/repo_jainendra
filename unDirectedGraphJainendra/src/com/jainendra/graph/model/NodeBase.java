package com.jainendra.graph.model;

import java.util.ArrayList;
import java.util.List;

public abstract class NodeBase<T> {
	public enum STATUS {
		VISITED, UN_VISITED, BEING_VISITED
	}

	private T data = null;
	private STATUS status = STATUS.UN_VISITED;
	private List<Edge<T>> successors = null;

	public NodeBase() {
		this.successors = new ArrayList<Edge<T>>();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isVisited() {
		return status == STATUS.VISITED;
	}

	public boolean isBeingVisited() {
		return status == STATUS.BEING_VISITED;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public List<Edge<T>> getSuccessors() {
		return successors;
	}

	public void addSuccessor(Edge<T> successor) {
		this.successors.add(successor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeBase<T> other = (NodeBase<T>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NodeBase [data=" + data + "]";
	}
}
