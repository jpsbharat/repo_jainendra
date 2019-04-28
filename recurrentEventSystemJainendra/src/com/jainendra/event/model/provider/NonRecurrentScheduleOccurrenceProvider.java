package com.jainendra.event.model.provider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.jainendra.event.model.RecurrenceNone;
import com.jainendra.event.model.ScheduleSpec;

public class NonRecurrentScheduleOccurrenceProvider extends
		AbstractScheduleOccurrenceProvider {

	public NonRecurrentScheduleOccurrenceProvider(ScheduleSpec scheduleSpec) {
		super(scheduleSpec);
	}

	@Override
	public Collection<LocalDate> getOccurrencesFrom(LocalDate startDate,
			int numberOfOccurences) {
		RecurrenceNone recurrence = (RecurrenceNone) this.scheduleSpec
				.getRecurrence();
		Collection<LocalDate> occurences = new ArrayList<LocalDate>();
		occurences.add(recurrence.getDate());
		return occurences;
	}

	@Override
	public Collection<LocalDate> getAllOccurrences() {
		RecurrenceNone recurrence = (RecurrenceNone) this.scheduleSpec
				.getRecurrence();
		Collection<LocalDate> occurences = new ArrayList<LocalDate>();
		occurences.add(recurrence.getDate());
		return occurences;
	}

	protected int findNumberOfCycles(LocalDate startDate, LocalDate endDate) {
		return 1;
	}

	protected LocalDate findStartDate() {
		RecurrenceNone recurrence = (RecurrenceNone) this.scheduleSpec
				.getRecurrence();
		return recurrence.getDate();
	}

	protected LocalDate findEndDate() {
		RecurrenceNone recurrence = (RecurrenceNone) this.scheduleSpec
				.getRecurrence();
		return recurrence.getDate();
	}

	@Override
	public int getNumberOfOccurences() {
		return 1;
	}
}
