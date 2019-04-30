package com.jainendra.graph.model;

public abstract class NodeDecorator<T> extends NodeBase<T> {
	protected NodeBase<T> targetNode;

	public NodeDecorator(NodeBase<T> targetNode) {
		this.targetNode = targetNode;
	}

	public NodeBase<T> getTargetNode() {
		return targetNode;
	}
}
