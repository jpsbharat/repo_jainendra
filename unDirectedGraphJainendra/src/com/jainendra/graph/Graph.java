package com.jainendra.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jainendra.graph.model.NodeBase;
import com.jainendra.graph.model.Edge;
import com.jainendra.graph.model.Node;

public class Graph<T> {
	private List<Edge<T>> edges = new ArrayList<Edge<T>>();
	private Map<T, NodeBase<T>> nodeMap = new LinkedHashMap<T, NodeBase<T>>();

	public Graph() {
	}

	public Graph(List<Edge<T>> edges, Map<T, NodeBase<T>> nodeMap) {
		this.edges = edges;
		this.nodeMap = nodeMap;
	}

	public Map<T, NodeBase<T>> getNodeMap() {
		return this.nodeMap;
	}

	public List<Edge<T>> getEdges() {
		return edges;
	}

	private NodeBase<T> addNode(T label) {
		NodeBase<T> theNode = null;
		if (this.nodeMap.containsKey(label)) {
			theNode = this.nodeMap.get(label);
		} else {
			theNode = new Node<T>();
			theNode.setData(label);
			this.nodeMap.put(label, theNode);
		}
		return theNode;
	}

	public void add(T source, T destination, int weight) {
		NodeBase<T> sourceNode = addNode(source);
		NodeBase<T> destinationNode = addNode(destination);
		Edge<T> edge = new Edge<T>(sourceNode, destinationNode, weight);
		sourceNode.addSuccessor(edge);
		this.edges.add(edge);

		edge = new Edge<T>(destinationNode, sourceNode, weight);
		destinationNode.addSuccessor(edge);
		this.edges.add(edge);
	}

	public void markAllNodesUnvisited() {
		for (T key : this.nodeMap.keySet()) {
			NodeBase<T> node = this.nodeMap.get(key);
			node.setStatus(NodeBase.STATUS.UN_VISITED);
		}
	}

	public void dfs(T label) {
		NodeBase<T> node = this.nodeMap.get(label);
		node.setStatus(NodeBase.STATUS.VISITED);
		System.out.println();
		dfs0(node);
	}

	private void dfs0(NodeBase<T> node) {
		System.out.print(node + " ");
		for (Edge<T> edge : node.getSuccessors()) {
			if (!edge.getDestinationNode().isVisited()) {
				edge.getDestinationNode().setStatus(NodeBase.STATUS.VISITED);
				dfs0(edge.getDestinationNode());
			}
		}
	}

	public boolean hasCycle(T label) {
		NodeBase<T> node = this.getNodeMap().get(label);
		return hasCycle(node);
	}

	public boolean hasCycle(NodeBase<T> n) {
		this.markAllNodesUnvisited();
		return hasCycle0(n);
	}

	private boolean hasCycle0(NodeBase<T> node) {
		node.setStatus(NodeBase.STATUS.BEING_VISITED);
		for (Edge<T> edge : node.getSuccessors()) {
			NodeBase<T> destinationNode = edge.getDestinationNode();
			if (destinationNode.isBeingVisited()) {
				return true;
			}

			if (!destinationNode.isVisited()) {
				if (hasCycle0(edge.getDestinationNode())) {
					return true;
				}
			}
		}

		node.setStatus(NodeBase.STATUS.VISITED);
		return false;
	}

	public boolean hasCycle() {
		this.markAllNodesUnvisited();
		for (T key : this.getNodeMap().keySet()) {
			NodeBase<T> node = this.getNodeMap().get(key);
			if (!node.isVisited()) {
				if (hasCycle0(node)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Edge<T> edge : edges) {
			sb.append(edge + System.lineSeparator());
		}
		return sb.toString();
	}
}
