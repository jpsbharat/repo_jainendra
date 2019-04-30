package com.jainendra.graph.operation.search.cc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jainendra.graph.Graph;
import com.jainendra.graph.model.DFSForest;
import com.jainendra.graph.model.DFSTree;
import com.jainendra.graph.model.EdgeDFSTree;
import com.jainendra.graph.operation.search.traversal.DFSUtil;

public class ConnectedComponentUtil<T> {
	private DFSUtil<T> dfsUtil = null;

	public ConnectedComponentUtil(DFSUtil<T> dfsUtil) {
		this.dfsUtil = dfsUtil;
	}

	public List<Graph<T>> getConnectedComponents() {
		List<Graph<T>> ccList = new ArrayList<Graph<T>>();
		DFSForest<T> dfsForest = this.dfsUtil.getDFSForest();
		for (DFSTree<T> dfsTree : dfsForest.getDFSTreeList()) {
			Graph<T> graph = new Graph<T>();
			Iterator<EdgeDFSTree<T>> itr = dfsTree.getTreeEdgeIterator();
			while (itr.hasNext()) {
				EdgeDFSTree<T> edge = itr.next();
				graph.add(
						edge.getSourceNodeDFSTree().getTargetNode().getData(),
						edge.getTargetNodeDFSTree().getTargetNode().getData(),
						edge.getWeight());
			}
			ccList.add(graph);
		}
		return ccList;
	}
}
