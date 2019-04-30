package com.jainendra.graph.model;

public class Edge<T> implements Comparable<Edge<T>> {
	protected String id;
	private int weight;
	private NodeBase<T> sourceNode;
	private NodeBase<T> destinationNode;

	public Edge(NodeBase<T> sourceNode, NodeBase<T> destinationNode, int weight) {
		this.weight = weight;
		this.sourceNode = sourceNode;
		this.destinationNode = destinationNode;
		this.id = sourceNode.getData() +  "#" + destinationNode.getData();
	}

	protected Edge(int weight) {
		this.weight = weight;
	}

	public NodeBase<T> getSourceNode() {
		return this.sourceNode;
	}

	public NodeBase<T> getDestinationNode() {
		return this.destinationNode;
	}

	public int getWeight() {
		return this.weight;
	}

	public String getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destinationNode == null) ? 0 : destinationNode.hashCode());
		result = prime * result
				+ ((sourceNode == null) ? 0 : sourceNode.hashCode());
		result = prime * result + weight;
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
		Edge<T> other = (Edge<T>) obj;
		if (destinationNode == null) {
			if (other.destinationNode != null)
				return false;
		} else if (!destinationNode.equals(other.destinationNode))
			return false;
		if (sourceNode == null) {
			if (other.sourceNode != null)
				return false;
		} else if (!sourceNode.equals(other.sourceNode))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + this.sourceNode.getData() + ", "
				+ this.destinationNode.getData() + ") : Weight = " + weight;
	}

	@Override
	public int compareTo(Edge<T> edge) {
		// == is not compared so that duplicate values are not eliminated.
		return (this.weight < edge.weight) ? -1 : 1;
	}
}
