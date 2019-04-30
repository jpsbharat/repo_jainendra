package com.jainendra.graph.model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Cycle<T> {
	Set<NodeDFSTree<T>> cycle = new LinkedHashSet<NodeDFSTree<T>>();
	Map<String, EdgeDFSTree<T>> cyleEdges = new LinkedHashMap<String, EdgeDFSTree<T>>();

	public Set<NodeDFSTree<T>> getCycle() {
		return cycle;
	}

	public Map<String, EdgeDFSTree<T>> getCyleEdges() {
		return cyleEdges;
	}

	public void addNodeToCycle(NodeDFSTree<T> node) {
		this.cycle.add(node);
	}

	public void addEdgeToCycle(String key, EdgeDFSTree<T> edge) {
		this.cyleEdges.put(key, edge);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cycle(");
		if(!cycle.isEmpty()){
			for(String key: cyleEdges.keySet()){
				sb.append(" " + key + " ");
			}
		}
		
		sb.append(")");
		return sb.toString();
	}
}
