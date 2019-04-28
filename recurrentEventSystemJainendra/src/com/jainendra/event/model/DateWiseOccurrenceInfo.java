package com.jainendra.event.model;

import java.util.Set;
import java.util.TreeSet;

public class DateWiseOccurrenceInfo{
	private Set<Integer> dates = null;

	protected DateWiseOccurrenceInfo() {
		this.dates = new TreeSet<Integer>();
	}

	public Set<Integer> getDates() {
		return dates;
	}

	public void addDate(Integer date) {
		this.dates.add(date);
	}
}
