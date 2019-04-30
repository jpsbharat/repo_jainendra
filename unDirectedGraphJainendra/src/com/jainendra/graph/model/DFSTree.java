package com.jainendra.graph.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DFSTree<T> {
	private int time = 0;
	private NodeDFSTree<T> root;
	private Map<T, NodeDFSTree<T>> treeNodeMap = null;

	private List<EdgeDFSTree<T>> treeEdges = new ArrayList<EdgeDFSTree<T>>();
	private List<EdgeDFSTree<T>> backEdges = new ArrayList<EdgeDFSTree<T>>();

	public int getTime() {
		return time;
	}

	public void resetTime() {
		this.time = 0;
	}

	public void incrementTime() {
		this.time = this.time + 1;
	}

	public Map<T, NodeDFSTree<T>> getTreeNodeMap() {
		return treeNodeMap;
	}

	public void createTreeNodeMap() {
		for (EdgeDFSTree<T> edge : this.treeEdges) {
			T key = edge.getSourceNodeDFSTree().getTargetNode().getData();
			if (this.treeNodeMap.containsKey(key)) {
				this.treeNodeMap.put(key, edge.getSourceNodeDFSTree());
			}

			key = edge.getTargetNodeDFSTree().getTargetNode().getData();
			if (this.treeNodeMap.containsKey(key)) {
				this.treeNodeMap.put(key, edge.getTargetNodeDFSTree());
			}
		}
	}

	public NodeDFSTree<T> getRoot() {
		return root;
	}

	public void setRoot(NodeDFSTree<T> root) {
		this.root = root;
	}

	public void addTreeEdges(EdgeDFSTree<T> edge) {
		this.treeEdges.add(edge);
	}

	public void addBackEdges(EdgeDFSTree<T> edge) {
		this.backEdges.add(edge);
	}

	public Iterator<EdgeDFSTree<T>> getTreeEdgeIterator() {
		return treeEdges.iterator();
	}

	public Iterator<EdgeDFSTree<T>> getBackEdgeIterator() {
		return backEdges.iterator();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  DFSTree[" + System.lineSeparator());
		sb.append("    TreeEdges" + treeEdges + System.lineSeparator());
		sb.append("    BackEdges" + backEdges + System.lineSeparator());
		sb.append("  ]");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		DFSTree<T> other = (DFSTree<T>) obj;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}
}