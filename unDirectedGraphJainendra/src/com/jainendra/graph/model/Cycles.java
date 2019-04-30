package com.jainendra.graph.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jainendra.graph.util.CommonUtils;

public class Cycles<T> {
	private CycleBase<T> cycleBase = null;
	private Collection<Cycle<T>> cycles = new ArrayList<Cycle<T>>();
	private Map<String, EdgeDFSTree<T>> edgesMap = new LinkedHashMap<String, EdgeDFSTree<T>>();

	public Cycles() {
	}

	public Cycles(CycleBase<T> cycleBase, Map<String, EdgeDFSTree<T>> edgesMap) {
		this.cycleBase = cycleBase;
		for(String key : edgesMap.keySet()){
			EdgeDFSTree<T> edge = edgesMap.get(key);
			String oppositeKey = createOppositeKey(edge);
			if(!this.edgesMap.containsKey(oppositeKey)){
				this.edgesMap.put(edge.getId(), edge);
			}
		}
		this.createCyclesFromCycleBase();
	}

	private void createCyclesFromCycleBase() {
		List<BigInteger> incidenceVectorList = createIncidentVectors();
		Set<List<BigInteger>> powerSetOfIncidenceVectorList = CommonUtils
				.getPowerSet(incidenceVectorList);
		List<BigInteger> incidenceVectorsOfPowerSet = symmetricDifferenceOfAllTheIncidenceVectorsOfThePowerSet(powerSetOfIncidenceVectorList);
		createCycles(incidenceVectorsOfPowerSet);
	}

	private void createCycles(List<BigInteger> incidenceVectorsOfPowerSet) {
		for (BigInteger incidenceVector : incidenceVectorsOfPowerSet) {
			createCycleFromIncidenceVector(incidenceVector);
		}
	}

	private void createCycleFromIncidenceVector(BigInteger incidenceVector) {
		Cycle<T> cycle = new Cycle<T>();
		int noOfEdges = this.edgesMap.size();
		for (int i = 0; i < noOfEdges; i++) {
			if (incidenceVector.testBit(i)) {
				EdgeDFSTree<T> edgeDFSTree = getDFSTreeEdge(noOfEdges - 1 - i);
				NodeDFSTree<T> sourceNode = edgeDFSTree.getSourceNodeDFSTree();
				NodeDFSTree<T> targetNode = edgeDFSTree.getTargetNodeDFSTree();
				cycle.addNodeToCycle(sourceNode);
				cycle.addNodeToCycle(targetNode);
				cycle.addEdgeToCycle(edgeDFSTree.getId(), edgeDFSTree);
			}
		}
		this.cycles.add(cycle);
	}

	private EdgeDFSTree<T> getDFSTreeEdge(int index) {
		int count = 0;
		EdgeDFSTree<T> edge = null;
		for (String key : this.edgesMap.keySet()) {
			edge = this.edgesMap.get(key);
			if (index == count) {
				break;
			}
			count++;
		}
		return edge;
	}

	private List<BigInteger> symmetricDifferenceOfAllTheIncidenceVectorsOfThePowerSet(
			Set<List<BigInteger>> powerSetOfIncidenceVectorList) {
		List<BigInteger> incidenceVectorsOfPowerSet = new ArrayList<BigInteger>();
		for (List<BigInteger> combination : powerSetOfIncidenceVectorList) {
			if (combination.size() > 0) {
				BigInteger result = combination.get(0);
				for (int i = 1; i < combination.size(); i++) {
					result = result.xor(combination.get(i));
				}
				incidenceVectorsOfPowerSet.add(result);
			}
		}
		return incidenceVectorsOfPowerSet;
	}

	private List<BigInteger> createIncidentVectors() {
		List<BigInteger> incidenceVectorList = new ArrayList<BigInteger>();
		for (Cycle<T> cycle : this.cycleBase.getCycles()) {
			BigInteger incidenceVector = createIncidenceVectorOfTheCycle(cycle);
			incidenceVectorList.add(incidenceVector);
		}
		return incidenceVectorList;
	}

	private BigInteger createIncidenceVectorOfTheCycle(Cycle<T> cycle) {
		StringBuilder sb = new StringBuilder();
		for (String edgeKay : this.edgesMap.keySet()) {
			if (cycle.getCyleEdges().containsKey(edgeKay)) {
				sb.append("1");
			} else {
				sb.append("0");
			}
		}

		BigInteger mask = new BigInteger(sb.toString(), 2);
		return mask;
	}

	public void addCycle(Cycle<T> cycle) {
		this.cycles.add(cycle);
	}

	public Collection<Cycle<T>> getCycles() {
		return cycles;
	}

	private String createOppositeKey(EdgeDFSTree<T> edge) {
		return edge.getTargetNodeDFSTree().getTargetNode().getData() + "#"
				+ edge.getSourceNodeDFSTree().getTargetNode().getData();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cycles[" + System.lineSeparator());
		for (Cycle<T> cycle : cycles) {
			sb.append("  " + cycle + System.lineSeparator());
		}
		sb.append("]" + System.lineSeparator());
		return sb.toString();
	}
}
