package com.jainendra.event.model;

import java.time.DayOfWeek;
import java.util.Set;
import java.util.TreeSet;

public class DayWiseOccurrenceInfo {
	private Set<DayOfWeek> days = null;
	private Set<Integer> ordinals = null;

	protected DayWiseOccurrenceInfo() {
		this.days = new TreeSet<DayOfWeek>();
		this.ordinals = new TreeSet<Integer>();
	}

	public Set<DayOfWeek> getDays() {
		return days;
	}

	public void addDay(DayOfWeek day) {
		this.days.add(day);
	}

	public Set<Integer> getOrdinals() {
		return ordinals;
	}

	public void addOrdinal(Integer ordinal) {
		this.ordinals.add(ordinal);
	}
}
