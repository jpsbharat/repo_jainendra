package com.jainendra.event.model;

import java.time.DayOfWeek;
import java.util.Set;
import java.util.TreeSet;

public class RecurrenceWeekly extends Recurrence {
	private Set<DayOfWeek> days = null;

	public RecurrenceWeekly(int frequency) {
		super(frequency);
		this.days = new TreeSet<DayOfWeek>();
	}

	public Set<DayOfWeek> getDays() {
		return days;
	}

	public void addDay(DayOfWeek day) {
		this.days.add(day);
	}
}
