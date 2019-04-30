package com.jainendra.graph.operation.search.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import com.jainendra.graph.Graph;
import com.jainendra.graph.model.Edge;
import com.jainendra.graph.model.NodeBase;
import com.jainendra.graph.model.NodeDijkstra;

public class DijkstraShortestPathUtil<T> {
	private Map<String, Edge<T>> edgeMap = new LinkedHashMap<String, Edge<T>>();
	private Map<T, NodeDijkstra<T>> nodeMap = new LinkedHashMap<T, NodeDijkstra<T>>();

	public DijkstraShortestPathUtil(Graph<T> graph, T source) {
		for (Entry<T, NodeBase<T>> entry : graph.getNodeMap().entrySet()) {
			NodeDijkstra<T> treeNode = createNodeDijkstra(entry.getKey(),
					entry.getValue());
			this.nodeMap.put(entry.getKey(), treeNode);
			for (Edge<T> edge : entry.getValue().getSuccessors()) {
				NodeDijkstra<T> sourceNodeDijkstra = createNodeDijkstra(edge
						.getSourceNode().getData(), edge.getSourceNode());
				NodeDijkstra<T> destinationNodeDijkstra = createNodeDijkstra(
						edge.getDestinationNode().getData(),
						edge.getDestinationNode());
				Edge<T> edgeDijkstra = new Edge<T>(sourceNodeDijkstra,
						destinationNodeDijkstra, edge.getWeight());
				treeNode.addSuccessor(edgeDijkstra);
				this.edgeMap.put(edgeDijkstra.getId(), edgeDijkstra);
			}
		}
		NodeDijkstra<T> sourceNode = this.nodeMap.get(source);
		processDijkstra(sourceNode);
	}

	private NodeDijkstra<T> createNodeDijkstra(T key, NodeBase<T> node) {
		NodeDijkstra<T> treeNode = null;
		if (this.nodeMap.containsKey(key)) {
			treeNode = this.nodeMap.get(key);
		} else {
			treeNode = new NodeDijkstra<T>(node);
			treeNode.setData(key);
			this.nodeMap.put(key, treeNode);
		}
		return treeNode;
	}

	private void processDijkstra(NodeDijkstra<T> sourceNodeDijkstra) {
		sourceNodeDijkstra.setMinDistance(0);
		PriorityQueue<NodeDijkstra<T>> nodeQueue = new PriorityQueue<NodeDijkstra<T>>();
		nodeQueue.add(sourceNodeDijkstra);
		while (!nodeQueue.isEmpty()) {
			NodeDijkstra<T> nodeDijkstra = nodeQueue.poll();
			for (Edge<T> edge : nodeDijkstra.getSuccessors()) {
				relax(nodeDijkstra, edge, nodeQueue);
			}
		}
	}

	private void relax(NodeDijkstra<T> nodeDijkstra, Edge<T> edge,
			PriorityQueue<NodeDijkstra<T>> nodeQueue) {
		NodeDijkstra<T> nodeDijkstraDest = (NodeDijkstra<T>) edge.getDestinationNode();
		double weight = edge.getWeight();
		double distanceThroughNodeDijkstraDest = nodeDijkstra.getMinDistance()
				+ weight;
		if (distanceThroughNodeDijkstraDest < nodeDijkstraDest.getMinDistance()) {
			nodeQueue.remove(nodeDijkstraDest);
			nodeDijkstraDest.setMinDistance(distanceThroughNodeDijkstraDest);
			nodeDijkstraDest.setPreviousNode(nodeDijkstra);
			nodeQueue.add(nodeDijkstraDest);
		}
	}

	public List<NodeDijkstra<T>> getShortestPathTo(T destination) {
		NodeDijkstra<T> target = this.nodeMap.get(destination);
		List<NodeDijkstra<T>> path = new ArrayList<NodeDijkstra<T>>();
		for (NodeDijkstra<T> nodeDijkstra = target; nodeDijkstra != null; nodeDijkstra = nodeDijkstra
				.getPreviousNode()) {
			path.add(nodeDijkstra);
		}
		Collections.reverse(path);
		return path;
	}
}
