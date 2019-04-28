package com.jainendra.event.model;

public class RecurrenceYearly extends Recurrence {
	protected Integer month = null;

	public RecurrenceYearly(int frequency, Integer month) {
		super(frequency);
		this.month = month;
	}

	public Integer getMonth() {
		return month;
	}
}
