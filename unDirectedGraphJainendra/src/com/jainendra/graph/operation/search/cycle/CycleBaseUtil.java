package com.jainendra.graph.operation.search.cycle;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jainendra.graph.Graph;
import com.jainendra.graph.model.Cycle;
import com.jainendra.graph.model.CycleBase;
import com.jainendra.graph.model.DFSForest;
import com.jainendra.graph.model.DFSTree;
import com.jainendra.graph.model.EdgeDFSTree;
import com.jainendra.graph.model.NodeDFSTree;
import com.jainendra.graph.operation.search.traversal.DFSUtil;

public class CycleBaseUtil<T> {
	private DFSUtil<T> dfsUtil;

	public CycleBaseUtil(Graph<T> graph) {
		this.dfsUtil = new DFSUtil<T>(graph);
	}

	public CycleBase<T> getCycleBase() {
		CycleBase<T> cycleBase = new CycleBase<T>();
		DFSForest<T> dfsForest = dfsUtil.getDFSForest();
		Map<String, EdgeDFSTree<T>> edgesMap = this.dfsUtil.getEdgeDFSTreeMap();
		List<DFSTree<T>> dfsTreeList = dfsForest.getDFSTreeList();
		for (DFSTree<T> dfsTree : dfsTreeList) {
			Iterator<EdgeDFSTree<T>> itr = dfsTree.getBackEdgeIterator();
			while (itr.hasNext()) {
				EdgeDFSTree<T> edge = itr.next();
				NodeDFSTree<T> tSource = edge.getSourceNodeDFSTree();
				NodeDFSTree<T> tDest = edge.getTargetNodeDFSTree();
				Cycle<T> cycle = new Cycle<T>();
				String edgeKey = null;
				while (tSource != null
						&& !tSource.getTargetNode().getData()
								.equals(tDest.getTargetNode().getData())) {
					cycle.addNodeToCycle(tSource);
					edgeKey = createKey(tSource, tSource.getParent());
					cycle.addEdgeToCycle(edgeKey, edgesMap.get(edgeKey));
					edgeKey = createKey(tSource.getParent(), tSource);
					cycle.addEdgeToCycle(edgeKey, edgesMap.get(edgeKey));
					tSource = tSource.getParent();
				}
				edgeKey = createKey(edge.getSourceNodeDFSTree(), tDest);
				cycle.addEdgeToCycle(edgeKey, edgesMap.get(edgeKey));
				edgeKey = createKey(tDest, edge.getSourceNodeDFSTree());
				cycle.addEdgeToCycle(edgeKey, edgesMap.get(edgeKey));
				cycle.addNodeToCycle(tDest);
				cycleBase.addCycle(cycle);
			}
		}
		return cycleBase;
	}
	
	private String createKey(NodeDFSTree<T> node1, NodeDFSTree<T> node2) {
		return node1.getTargetNode().getData() + "#" + node2.getTargetNode().getData();
	}
}
