package com.jainendra.graph.model;

public class NodeDijkstra<T> extends NodeDecorator<T> implements
		Comparable<NodeDijkstra<T>> {
	private double minDistance = Double.POSITIVE_INFINITY;
	private NodeDijkstra<T> previousNode = null;

	public NodeDijkstra(NodeBase<T> targetNode) {
		super(targetNode);
	}

	public double getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(double minDistance) {
		this.minDistance = minDistance;
	}

	public NodeDijkstra<T> getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(NodeDijkstra<T> previousNode) {
		this.previousNode = previousNode;
	}

	public int compareTo(NodeDijkstra<T> otherNode) {
		return Double.compare(minDistance, otherNode.minDistance);
	}
}
