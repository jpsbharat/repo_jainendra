package com.jainendra.event.model;

import java.time.DayOfWeek;
import java.util.Set;

public class RecurrenceMonthlyDayWise extends RecurrenceMonthly {
	private DayWiseOccurrenceInfo dayWiseOccurrenceInfo = null;

	public RecurrenceMonthlyDayWise(int frequency) {
		super(frequency);
		this.dayWiseOccurrenceInfo = new DayWiseOccurrenceInfo();
	}

	public Set<DayOfWeek> getDays() {
		return this.dayWiseOccurrenceInfo.getDays();
	}

	public void addDay(DayOfWeek day) {
		this.dayWiseOccurrenceInfo.addDay(day);
	}

	public Set<Integer> getOrdinals() {
		return this.dayWiseOccurrenceInfo.getOrdinals();
	}

	public void addOrdinal(Integer ordinal) {
		this.dayWiseOccurrenceInfo.addOrdinal(ordinal);
	}
}
