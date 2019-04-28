package com.jainendra.event.model;

import java.util.Set;

public class RecurrenceMonthlyDateWise extends RecurrenceMonthly {
	private DateWiseOccurrenceInfo dateWiseOccurrenceInfo = null;

	public RecurrenceMonthlyDateWise(int frequency) {
		super(frequency);
		this.dateWiseOccurrenceInfo = new DateWiseOccurrenceInfo();
	}

	public Set<Integer> getDates() {
		return this.dateWiseOccurrenceInfo.getDates();
	}

	public void addDate(Integer date) {
		this.dateWiseOccurrenceInfo.addDate(date);
	}
}
