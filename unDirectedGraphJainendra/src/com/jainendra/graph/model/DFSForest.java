package com.jainendra.graph.model;

import java.util.ArrayList;
import java.util.List;

public class DFSForest<T> {

	private List<DFSTree<T>> dfsTreeList = new ArrayList<DFSTree<T>>();

	public List<DFSTree<T>> getDFSTreeList() {
		return dfsTreeList;
	}

	public void addDFSTree(DFSTree<T> tree) {
		this.dfsTreeList.add(tree);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("DFSForest[" + System.lineSeparator());
		for(DFSTree<T> dfsTree : dfsTreeList){
			sb.append(dfsTree + System.lineSeparator());
		}
		sb.append("]" + System.lineSeparator());
		return sb.toString();
	}
}
