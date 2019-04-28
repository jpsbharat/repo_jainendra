package com.jainendra.event.model;

import java.time.LocalDate;

public class RecurrenceNone extends Recurrence {
	private LocalDate date = null;

	public RecurrenceNone(int frequency) {
		super(frequency);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
