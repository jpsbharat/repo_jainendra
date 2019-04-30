package com.jainendra.graph.model;

public class EdgeDFSTree<T> extends Edge<T> {
	private NodeDFSTree<T> sourceNodeDFSTree;
	private NodeDFSTree<T> targetNodeDFSTree;

	public EdgeDFSTree(NodeDFSTree<T> sourceNode, NodeDFSTree<T> targetNode,
			int weight) {
		super(weight);
		this.sourceNodeDFSTree = sourceNode;
		this.targetNodeDFSTree = targetNode;
		this.id = this.sourceNodeDFSTree.getTargetNode().getData() +  "#" + this.targetNodeDFSTree.getTargetNode().getData();
	}

	public NodeDFSTree<T> getSourceNodeDFSTree() {
		return sourceNodeDFSTree;
	}

	public void setSourceNodeDFSTree(NodeDFSTree<T> sourceNode) {
		this.sourceNodeDFSTree = sourceNode;
	}

	public NodeDFSTree<T> getTargetNodeDFSTree() {
		return targetNodeDFSTree;
	}

	public void setTargetNodeDFSTree(NodeDFSTree<T> targetNode) {
		this.targetNodeDFSTree = targetNode;
	}

	@Override
	public String toString() {
		return "(" + sourceNodeDFSTree.getTargetNode().getData() + " --> "
				+ targetNodeDFSTree.getTargetNode().getData() + ")";
	}
}
