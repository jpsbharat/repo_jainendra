package com.jainendra.event.model.provider;

import java.time.LocalDate;

import com.jainendra.event.model.RecurrenceMonthly;
import com.jainendra.event.model.ScheduleSpec;

public abstract class MonthlyScheduleOccurrenceProvider extends
		AbstractScheduleOccurrenceProvider {

	public MonthlyScheduleOccurrenceProvider(ScheduleSpec scheduleSpec) {
		super(scheduleSpec);
	}

	protected int findNumberOfCycles(LocalDate startDate, LocalDate endDate) {
		RecurrenceMonthly recurrence = (RecurrenceMonthly) this.scheduleSpec
				.getRecurrence();
		int result = Integer.MAX_VALUE;
		if (startDate != null && endDate != null) {
			int count = 0;
			while (startDate.isBefore(endDate) || endDate.isEqual(startDate)) {
				startDate = startDate.plusMonths(recurrence.getFrequency());
				count++;
			}
			result = count;
		}
		return result;
	}
}
