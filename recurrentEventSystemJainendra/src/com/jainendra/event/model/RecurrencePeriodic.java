package com.jainendra.event.model;

import java.time.LocalDate;

public class RecurrencePeriodic extends Recurrence {
	private int period;
	private LocalDate localDate = null;

	public RecurrencePeriodic(int frequency, int period) {
		super(frequency);
		this.period = period;
	}

	public int getPeriod() {
		return period;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

}
