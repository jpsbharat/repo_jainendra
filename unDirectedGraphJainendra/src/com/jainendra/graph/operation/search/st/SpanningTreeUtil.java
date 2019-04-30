package com.jainendra.graph.operation.search.st;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.jainendra.graph.Graph;
import com.jainendra.graph.model.DFSForest;
import com.jainendra.graph.model.DFSTree;
import com.jainendra.graph.model.Edge;
import com.jainendra.graph.model.EdgeDFSTree;
import com.jainendra.graph.model.NodeBase;
import com.jainendra.graph.operation.search.cc.ConnectedComponentUtil;
import com.jainendra.graph.operation.search.traversal.DFSUtil;
import com.jainendra.graph.util.DisjointSet;

public class SpanningTreeUtil<T> {
	private DFSUtil<T> dfsUtil = null;
	private ConnectedComponentUtil<T> ccUtil = null;

	public SpanningTreeUtil(DFSUtil<T> dfsUtil) {
		this.dfsUtil = dfsUtil;
		this.ccUtil = new ConnectedComponentUtil<T>(this.dfsUtil);
	}

	public List<List<Edge<T>>> getSpanningTree() {
		List<List<Edge<T>>> spanningTree = new ArrayList<List<Edge<T>>>();
		DFSForest<T> dfsForest = this.dfsUtil.getDFSForest();
		List<DFSTree<T>> dfsTreeList = dfsForest.getDFSTreeList();
		for (DFSTree<T> dfsTree : dfsTreeList) {
			List<Edge<T>> st = new ArrayList<Edge<T>>();
			Iterator<EdgeDFSTree<T>> itr = dfsTree.getTreeEdgeIterator();
			while (itr.hasNext()) {
				EdgeDFSTree<T> edge = itr.next();
				st.add(new Edge<T>(edge.getSourceNodeDFSTree().getTargetNode(),
						edge.getTargetNodeDFSTree().getTargetNode(), edge
								.getWeight()));
			}
			spanningTree.add(st);
		}
		return spanningTree;
	}

	public List<List<Edge<T>>> getMstKruskal() {
		List<Graph<T>> ccList = this.ccUtil.getConnectedComponents();
		List<List<Edge<T>>> mstList = new ArrayList<List<Edge<T>>>();
		for (Graph<T> graph : ccList) {
			List<Edge<T>> mst = new ArrayList<Edge<T>>();
			Set<Edge<T>> sortedEdges = new TreeSet<Edge<T>>(graph.getEdges());
			DisjointSet<NodeBase<T>> disjointSet = new DisjointSet<NodeBase<T>>(
					graph.getNodeMap().values());
			for (Edge<T> edge : sortedEdges) {
				NodeBase<T> sourceNode = edge.getSourceNode();
				NodeBase<T> destinationNode = edge.getDestinationNode();
				if (disjointSet.find(sourceNode) != disjointSet
						.find(destinationNode)) {
					mst.add(edge);
					disjointSet.union(sourceNode, destinationNode);
				}
			}
			mstList.add(mst);
		}
		return mstList;
	}
}
