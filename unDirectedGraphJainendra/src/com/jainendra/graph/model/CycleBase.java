package com.jainendra.graph.model;

import java.util.ArrayList;
import java.util.Collection;

public class CycleBase<T> {
	private Collection<Cycle<T>> cycles = new ArrayList<Cycle<T>>();

	public Collection<Cycle<T>> getCycles() {
		return cycles;
	}

	public void addCycle(Cycle<T> cycle) {
		this.cycles.add(cycle);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("CycleBase[" + System.lineSeparator());
		for(Cycle<T> cycle : cycles){
			sb.append("  " + cycle + System.lineSeparator());
		}
		sb.append("]" + System.lineSeparator());
		return sb.toString();
	}
}
