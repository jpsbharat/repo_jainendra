package com.jainendra.graph.model;

import java.util.ArrayList;
import java.util.List;

public class NodeDFSTree<T>  extends NodeDecorator<T> {
	private int lowTime;
	private int finishTime;
	private int discoveryTime;

	private NodeDFSTree<T> parent;
	private List<NodeDFSTree<T>> children;
	private List<NodeDFSTree<T>> backReferences;

	public NodeDFSTree(NodeBase<T> node) {
		super(node);
		this.children = new ArrayList<NodeDFSTree<T>>();
		this.backReferences = new ArrayList<NodeDFSTree<T>>();
	}

	public int getLowTime() {
		return lowTime;
	}

	public void setLowTime(int lowTime) {
		this.lowTime = lowTime;
	}

	public int getDiscoveryTime() {
		return discoveryTime;
	}

	public void setDiscoveryTime(int discoveryTime) {
		this.discoveryTime = discoveryTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public NodeDFSTree<T> getParent() {
		return parent;
	}

	public void setParent(NodeDFSTree<T> parent) {
		this.parent = parent;
	}

	public List<NodeDFSTree<T>> getChildren() {
		return children;
	}

	public void addChild(NodeDFSTree<T> child) {
		this.children.add(child);
	}

	public List<NodeDFSTree<T>> getBackReferences() {
		return backReferences;
	}

	public void addBackReference(NodeDFSTree<T> backReference) {
		this.backReferences.add(backReference);
	}
}
