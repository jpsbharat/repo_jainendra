package com.jainendra.event.model;

import java.util.Set;

public class RecurrenceYearlyDateWise extends RecurrenceYearly {
	private DateWiseOccurrenceInfo dateWiseOccurrenceInfo = null;

	public RecurrenceYearlyDateWise(int frequency, Integer month) {
		super(frequency, month);
		this.dateWiseOccurrenceInfo = new DateWiseOccurrenceInfo();
	}

	public Set<Integer> getDates() {
		return this.dateWiseOccurrenceInfo.getDates();
	}

	public void addDate(Integer date) {
		this.dateWiseOccurrenceInfo.addDate(date);
	}
}
