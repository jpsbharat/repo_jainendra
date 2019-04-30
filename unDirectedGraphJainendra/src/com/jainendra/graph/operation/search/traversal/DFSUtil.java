package com.jainendra.graph.operation.search.traversal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jainendra.graph.Graph;
import com.jainendra.graph.model.DFSForest;
import com.jainendra.graph.model.DFSTree;
import com.jainendra.graph.model.Edge;
import com.jainendra.graph.model.EdgeDFSTree;
import com.jainendra.graph.model.NodeBase;
import com.jainendra.graph.model.NodeDFSTree;

public class DFSUtil<T> {
	private Map<T, NodeDFSTree<T>> treeNodeMap = new LinkedHashMap<T, NodeDFSTree<T>>();
	private Map<String, EdgeDFSTree<T>> edgesMap = new LinkedHashMap<String, EdgeDFSTree<T>>();

	public DFSUtil(Graph<T> graph) {
		for(Entry<T, NodeBase<T>> entry : graph.getNodeMap().entrySet()){
			NodeDFSTree<T> treeNode = createTreeNode(entry.getKey(), entry.getValue());
			this.treeNodeMap.put(entry.getKey(), treeNode);
			for(Edge<T> edge : entry.getValue().getSuccessors()){
				NodeDFSTree<T> source = createTreeNode(edge.getSourceNode().getData(), edge.getSourceNode());
				NodeDFSTree<T> destination = createTreeNode(edge.getDestinationNode().getData(), edge.getDestinationNode());
				EdgeDFSTree<T> successor = new EdgeDFSTree<T>(source, destination, edge.getWeight());
				treeNode.addSuccessor(successor);
				this.edgesMap.put(successor.getId(), successor);
			}
		}
	}

	private NodeDFSTree<T> createTreeNode(T key, NodeBase<T> node) {
		NodeDFSTree<T> treeNode = null;
		if (this.treeNodeMap.containsKey(key)) {
			treeNode = this.treeNodeMap.get(key);
		} else {
			treeNode = new NodeDFSTree<T>(node);
			treeNode.setData(key);
			this.treeNodeMap.put(key, treeNode);
		}
		return treeNode;
	}

	public void dfs(T label) {
		NodeBase<T> node = treeNodeMap.get(label);
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

	public DFSForest<T> getDFSForest() {
		this.markAllNodesUnvisited();
		DFSForest<T> dfsForest = new DFSForest<T>();
		for (Entry<T, NodeDFSTree<T>> entry : this.treeNodeMap.entrySet()) {
			if (!entry.getValue().isVisited()) {
				NodeBase<T> node = entry.getValue();
				DFSTree<T> dfsTree = this.getDFSTree(node);
				dfsForest.addDFSTree(dfsTree);
			}
		}
		return dfsForest;
	}

	public DFSTree<T> getDFSTree(T t) {
		this.markAllNodesUnvisited();
		NodeBase<T> node = this.treeNodeMap.get(t);
		DFSTree<T> dfsTree = this.getDFSTree(node);
		return dfsTree;
	}

	public DFSTree<T> getDFSTree(NodeBase<T> node) {
		DFSTree<T> dfsTree = new DFSTree<T>();
		NodeDFSTree<T> treeNode = this.treeNodeMap.get(node.getData());
		dfsTree.setRoot(treeNode);
		dfsTree.resetTime();
		this.createDFSTree(dfsTree, treeNode);
		dfsTree.createTreeNodeMap();
		return dfsTree;
	}

	private void createDFSTree(DFSTree<T> dfsTree, NodeDFSTree<T> treeNode) {
		treeNode.setStatus(NodeBase.STATUS.BEING_VISITED);
		treeNode.setDiscoveryTime(dfsTree.getTime());
		treeNode.setLowTime(dfsTree.getTime());
		dfsTree.incrementTime();
		for (Edge<T> edge : treeNode.getSuccessors()) {
			EdgeDFSTree<T> edgeDFSTree = (EdgeDFSTree<T>) edge;
			NodeDFSTree<T> tNode = edgeDFSTree.getTargetNodeDFSTree();
			if (tNode.getStatus() == NodeBase.STATUS.UN_VISITED) {
				treeNode.addChild(tNode);
				tNode.setParent(treeNode);
				dfsTree.addTreeEdges(edgeDFSTree);
				createDFSTree(dfsTree, tNode);
				treeNode.setLowTime(Math.min(tNode.getLowTime(),
						treeNode.getLowTime()));
			} else if (tNode.isBeingVisited()
					&& !treeNode.getParent().equals(tNode)) {
				treeNode.setLowTime(Math.min(tNode.getDiscoveryTime(),
						treeNode.getLowTime()));
				dfsTree.addBackEdges(edgeDFSTree);
				treeNode.addBackReference(tNode);
			}
		}
		treeNode.setStatus(NodeBase.STATUS.VISITED);
		treeNode.setFinishTime(dfsTree.getTime());
		dfsTree.incrementTime();
	}

	public Map<DFSTree<T>, List<NodeDFSTree<T>>> getArticulationPoints() {
		Map<DFSTree<T>, List<NodeDFSTree<T>>> treeToAPMap = new HashMap<DFSTree<T>, List<NodeDFSTree<T>>>();
		DFSForest<T> dfsForest = this.getDFSForest();
		for (DFSTree<T> tree : dfsForest.getDFSTreeList()) {
			List<NodeDFSTree<T>> aps = this.getArticulationPoints(tree);
			treeToAPMap.put(tree, aps);
		}
		return treeToAPMap;
	}

	public List<NodeDFSTree<T>> getArticulationPoints(DFSTree<T> dfsTree) {
		List<NodeDFSTree<T>> result = new ArrayList<NodeDFSTree<T>>();
		Iterator<EdgeDFSTree<T>> itr = dfsTree.getTreeEdgeIterator();
		while (itr.hasNext()) {
			EdgeDFSTree<T> edge = itr.next();
			NodeDFSTree<T> node = edge.getSourceNodeDFSTree();
			NodeDFSTree<T> child = edge.getTargetNodeDFSTree();
			if (node.getParent() == null && node.getChildren().size() > 1) {
				result.add(node);
			} else if (node.getParent() != null
					&& child.getLowTime() >= node.getDiscoveryTime()) {
				result.add(node);
			}
		}
		return result;
	}

	public void markAllNodesUnvisited() {
		for (T key : this.treeNodeMap.keySet()) {
			NodeBase<T> node = this.treeNodeMap.get(key);
			node.setStatus(NodeBase.STATUS.UN_VISITED);
		}
	}

	public Map<T, NodeDFSTree<T>> getTreeNodeMap() {
		return this.treeNodeMap;
	}

	public Map<String, EdgeDFSTree<T>> getEdgeDFSTreeMap() {
		return edgesMap;
	}
}
