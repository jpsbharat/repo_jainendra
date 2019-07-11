package com.jainendra.graph.operation.search.path;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

	public List<List<NodeBase<T>>> getAllPathsDFSRecur(T source, T destination) {
		NodeBase<T> sourceNode = this.graph.getNodeMap().get(source);
		NodeBase<T> destinationNode = this.graph.getNodeMap().get(destination);
		List<List<NodeBase<T>>> paths = new ArrayList<List<NodeBase<T>>>();
		getAllPathsDFSRecur(sourceNode, destinationNode, paths);
		return paths;
	}

	private void getAllPathsDFSRecur(NodeBase<T> sourceNode,
			NodeBase<T> destinationNode, List<List<NodeBase<T>>> paths) {
		Stack<NodeBase<T>> path = new Stack<NodeBase<T>>();
		getAllPathsDFSRecur0(sourceNode, destinationNode, path, paths);
	}

	private void getAllPathsDFSRecur0(NodeBase<T> sourceNode,
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
					getAllPathsDFSRecur0(edge.getDestinationNode(),
							destinationNode, curPath, paths);
				}
			}
		}

		sourceNode.setStatus(NodeBase.STATUS.UN_VISITED);
		curPath.pop();
	}

	public List<List<NodeBase<T>>> getAllPathsDFSIter(T source, T destination) {
		NodeBase<T> sourceNode = this.graph.getNodeMap().get(source);
		NodeBase<T> destinationNode = this.graph.getNodeMap().get(destination);
		List<List<NodeBase<T>>> paths = new ArrayList<List<NodeBase<T>>>();
		getAllPathsDFSIter(sourceNode, destinationNode, paths);
		return paths;
	}

	private void getAllPathsDFSIter(NodeBase<T> sourceNode,
			NodeBase<T> destinationNode, List<List<NodeBase<T>>> paths) {
		Stack<List<NodeBase<T>>> pathsOnStack = new Stack<>();
		List<NodeBase<T>> path = new ArrayList<>();
		path.add(sourceNode);
		pathsOnStack.push(path);
		NodeBase<T> currNode = null;
		while (!pathsOnStack.empty()) {
			path = pathsOnStack.pop();
			currNode = path.get(path.size() - 1);
			if (currNode == destinationNode) {
				paths.add(path);
				continue;
			}

			if (!currNode.getSuccessors().isEmpty()) {
				for (Edge<T> edge : currNode.getSuccessors()) {
					if (!path.contains(edge.getDestinationNode())) {
						List<NodeBase<T>> tempPath = new ArrayList<>(path);
						tempPath.add(edge.getDestinationNode());
						pathsOnStack.push(tempPath);
					}
				}
			}
		}
	}

	public List<List<NodeBase<T>>> getAllPathsBFSIter(T source, T destination) {
		NodeBase<T> sourceNode = this.graph.getNodeMap().get(source);
		NodeBase<T> destinationNode = this.graph.getNodeMap().get(destination);
		List<List<NodeBase<T>>> paths = new ArrayList<List<NodeBase<T>>>();
		getAllPathsBFSIter(sourceNode, destinationNode, paths);
		return paths;
	}

	private void getAllPathsBFSIter(NodeBase<T> sourceNode,
			NodeBase<T> destinationNode, List<List<NodeBase<T>>> paths) {
		Queue<List<NodeBase<T>>> pathsOnQueue = new LinkedList<>();
		List<NodeBase<T>> path = new ArrayList<>();
		path.add(sourceNode);
		pathsOnQueue.offer(path);
		NodeBase<T> currNode = null;
		while (!pathsOnQueue.isEmpty()) {
			path = pathsOnQueue.poll();
			currNode = path.get(path.size() - 1);
			if (currNode == destinationNode) {
				paths.add(path);
				continue;
			}

			if (!currNode.getSuccessors().isEmpty()) {
				for (Edge<T> edge : currNode.getSuccessors()) {
					if (!path.contains(edge.getDestinationNode())) {
						List<NodeBase<T>> tempPath = new ArrayList<>(path);
						tempPath.add(edge.getDestinationNode());
						pathsOnQueue.offer(tempPath);
					}
				}
			}
		}
	}
}
