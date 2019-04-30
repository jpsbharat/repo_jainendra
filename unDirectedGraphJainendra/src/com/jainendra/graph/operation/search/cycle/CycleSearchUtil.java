package com.jainendra.graph.operation.search.cycle;

import com.jainendra.graph.Graph;
import com.jainendra.graph.model.Cycle;
import com.jainendra.graph.model.CycleBase;
import com.jainendra.graph.model.Cycles;
import com.jainendra.graph.model.Edge;
import com.jainendra.graph.model.EdgeDFSTree;
import com.jainendra.graph.model.NodeBase;
import com.jainendra.graph.model.NodeDFSTree;
import com.jainendra.graph.operation.search.traversal.DFSUtil;

public class CycleSearchUtil<T> {
	private DFSUtil<T> dfsUtil = null;
	private CycleBaseUtil<T> cycleBaseUtil = null;

	public CycleSearchUtil(Graph<T> graph) {
		this.dfsUtil = new DFSUtil<T>(graph);
		this.cycleBaseUtil = new CycleBaseUtil<T>(graph);
	}

	public Cycles<T> getAllCycles() {
		this.dfsUtil.markAllNodesUnvisited();
		Cycles<T> cycles = new Cycles<T>();
		for (T key : this.dfsUtil.getTreeNodeMap().keySet()) {
			NodeDFSTree<T> node = this.dfsUtil.getTreeNodeMap().get(key);
			if (!node.isVisited()) {
				findAllCycles(node, cycles);
				break;
			}
		}
		return cycles;
	}

	private void findAllCycles(NodeDFSTree<T> node, Cycles<T> cycles) {
		node.setStatus(NodeBase.STATUS.BEING_VISITED);
		for (Edge<T> edge : node.getSuccessors()) {
			EdgeDFSTree<T> edgeDFSTree = (EdgeDFSTree<T>) edge;
			NodeDFSTree<T> destinationNode = (NodeDFSTree<T>) edgeDFSTree
					.getTargetNodeDFSTree();
			if (destinationNode.getStatus() == NodeBase.STATUS.UN_VISITED) {
				destinationNode.setParent(node);
				findAllCycles(destinationNode, cycles);
			} else if (destinationNode.isBeingVisited()
					&& !node.getParent().equals(destinationNode)) {
				Cycle<T> cycle = new Cycle<T>();
				NodeDFSTree<T> currNode = destinationNode;
				while (currNode != null && currNode != node) {
					cycle.addNodeToCycle(currNode);
					currNode = currNode.getParent();
				}
				cycle.addNodeToCycle(node);
				cycles.addCycle(cycle);
			}
		}
		node.setStatus(NodeBase.STATUS.VISITED);
	}

	public CycleBase<T> getCycleBase() {
		CycleBase<T> cycleBase = this.cycleBaseUtil.getCycleBase();
		return cycleBase;
	}

	public Cycles<T> getAllSimpleCycles() {
		CycleBase<T> cycleBase = this.cycleBaseUtil.getCycleBase();
		Cycles<T> cycles = new Cycles<T>(cycleBase,
				this.dfsUtil.getEdgeDFSTreeMap());
		return cycles;
	}
}
