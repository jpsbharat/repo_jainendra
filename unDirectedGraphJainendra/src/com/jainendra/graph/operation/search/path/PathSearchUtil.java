package com.jainendra.graph.operation.search.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.jainendra.graph.Graph;
import com.jainendra.graph.model.Edge;
import com.jainendra.graph.model.NodeBase;

public class PathSearchUtil<T> {
	private Graph<T> graph;

	public PathSearchUtil(Graph<T> graph) {
		super();
		this.graph = graph;
	}

	public List<List<NodeBase<T>>> getAllPaths(T source, T destination) {
		NodeBase<T> sourceNode = this.graph.getNodeMap().get(source);
		NodeBase<T> destinationNode = this.graph.getNodeMap().get(destination);
		List<List<NodeBase<T>>> paths = new ArrayList<List<NodeBase<T>>>();
		getAllPaths(sourceNode, destinationNode, paths);
		return paths;
	}

	private void getAllPaths(NodeBase<T> sourceNode,
			NodeBase<T> destinationNode, List<List<NodeBase<T>>> paths) {
		Stack<NodeBase<T>> path = new Stack<NodeBase<T>>();
		getAllPaths0(sourceNode, destinationNode, path, paths);
	}

	private void getAllPaths0(NodeBase<T> sourceNode,
			NodeBase<T> destinationNode, Stack<NodeBase<T>> curPath,
			List<List<NodeBase<T>>> paths) {
		curPath.push(sourceNode);
		if (sourceNode == destinationNode) {
			paths.add(new ArrayList<NodeBase<T>>(curPath));
		}

		sourceNode.setStatus(NodeBase.STATUS.VISITED);

		if (!sourceNode.getSuccessors().isEmpty()) {
			for (Edge<T> edge : sourceNode.getSuccessors()) {
				if (!edge.getDestinationNode().isVisited()) {
					getAllPaths0(edge.getDestinationNode(), destinationNode,
							curPath, paths);
				}
			}
		}

		sourceNode.setStatus(NodeBase.STATUS.UN_VISITED);
		curPath.pop();
	}
}
