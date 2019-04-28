package com.jainendra.event.model;

import java.time.LocalDate;
import java.util.Collection;

import com.jainendra.event.model.provider.IScheduleOccurrenceProvider;

public class DefaultSchedule extends AbstractSchedule {
	public DefaultSchedule(
			IScheduleOccurrenceProvider scheduleOccurrenceProvider) {
		super(scheduleOccurrenceProvider);
	}

	@Override
	public LocalDate startDate() {
		return this.scheduleOccurrenceProvider.startDate();
	}

	@Override
	public LocalDate endDate() {
		return this.scheduleOccurrenceProvider.endDate();
	}

	@Override
	public Collection<LocalDate> getOccurrences(int limitNumberOfOccurences) {
		return this.scheduleOccurrenceProvider
				.getOccurrences(limitNumberOfOccurences);
	}

	@Override
	public Collection<LocalDate> getOccurrencesFrom(LocalDate startDate,
			int numberOfOccurences) {
		return this.scheduleOccurrenceProvider.getOccurrencesFrom(startDate,
				numberOfOccurences);
	}

	@Override
	public Collection<LocalDate> getAllOccurrences() {
		return this.scheduleOccurrenceProvider.getAllOccurrences();
	}

	@Override
	public int getNumberOfOccurences() {
		return this.scheduleOccurrenceProvider.getNumberOfOccurences();
	}
}
