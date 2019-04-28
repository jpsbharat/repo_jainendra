package com.jainendra.event.model.provider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.jainendra.event.model.Recurrence;
import com.jainendra.event.model.ScheduleBound;
import com.jainendra.event.model.ScheduleSpec;
import com.jainendra.event.model.ScheduleBound.TimeBound;

public abstract class AbstractScheduleOccurrenceProvider implements
		IScheduleOccurrenceProvider {
	protected ScheduleSpec scheduleSpec;

	protected AbstractScheduleOccurrenceProvider(ScheduleSpec scheduleSpec) {
		this.scheduleSpec = scheduleSpec;
	}

	public String getName() {
		return this.scheduleSpec.getEventName();
	}

	@Override
	public Collection<LocalDate> getOccurrences(int limitNumberOfOccurences) {
		Recurrence recurrence = (Recurrence) this.scheduleSpec.getRecurrence();
		ScheduleBound scheduleBound = recurrence.getScheduleBound();
		LocalDate startDate = scheduleBound.getStartBound().getDate();
		return getOccurrencesFrom(startDate, limitNumberOfOccurences);
	}

	@Override
	public LocalDate startDate() {
		Recurrence recurrence = (Recurrence) this.scheduleSpec.getRecurrence();
		ScheduleBound scheduleBound = recurrence.getScheduleBound();
		if (scheduleBound.getStartBound() != null) {
			if (scheduleBound.getStartBound().getDate() != null) {
				return scheduleBound.getStartBound().getDate();
			}
		}
		return findStartDate();
	}

	@Override
	public LocalDate endDate() {
		Recurrence recurrence = (Recurrence) this.scheduleSpec.getRecurrence();
		ScheduleBound scheduleBound = recurrence.getScheduleBound();
		if (scheduleBound.getEndBound() != null) {
			if (scheduleBound.getEndBound().getDate() != null) {
				return scheduleBound.getEndBound().getDate();
			}
		}
		return findEndDate();
	}

	@Override
	public Collection<LocalDate> getAllOccurrences() {
		Recurrence recurrence = (Recurrence) this.scheduleSpec.getRecurrence();
		ScheduleBound scheduleBound = recurrence.getScheduleBound();
		TimeBound startBound = scheduleBound.getStartBound();
		TimeBound endBound = scheduleBound.getEndBound();
		Integer occurLimit = scheduleBound.getAllowedOccurnces();
		Collection<LocalDate> occurences = new ArrayList<LocalDate>();
		if (startBound != null && endBound != null) {
			LocalDate startDate = startBound.getDate();
			LocalDate endDate = endBound.getDate();
			if (endDate.equals(LocalDate.MAX)) {
				occurences.add(LocalDate.MAX);
			} else if (startDate != null && endDate != null) {
				int numberOfOccurences = findNumberOfOccurences(startDate, endDate);
				occurences = getOccurrencesFrom(startDate, numberOfOccurences);
			} else if (startDate != null && occurLimit != null) {
				occurences = getOccurrencesFrom(startDate, occurLimit);
			} else if (endDate != null && occurLimit != null) {
				LocalDate now = LocalDate.now();
				occurences = getOccurrencesFrom(now, occurLimit);
			} else if (endDate != null) {
				LocalDate now = LocalDate.now();
				int numberOfOccurences = findNumberOfOccurences(now, endDate);
				occurences = getOccurrencesFrom(startDate, numberOfOccurences);
			}
		}

		return occurences;
	}

	@Override
	public int getNumberOfOccurences() {
		Recurrence recurrence = (Recurrence) this.scheduleSpec.getRecurrence();
		ScheduleBound scheduleBound = recurrence.getScheduleBound();
		TimeBound startBound = scheduleBound.getStartBound();
		TimeBound endBound = scheduleBound.getEndBound();
		int result = Integer.MAX_VALUE;
		if (startBound != null && endBound != null) {
			LocalDate endDate = endBound.getDate();
			if (scheduleBound.getAllowedOccurnces() != null
					&& !scheduleBound.getAllowedOccurnces().equals(
							Integer.MAX_VALUE)) {
				result = scheduleBound.getAllowedOccurnces();
			} else if (endDate.equals(LocalDate.MAX)) {
				result = Integer.MAX_VALUE;
			} else {
				result = findNumberOfOccurences(startBound.getDate(),
						endBound.getDate());
			}
		}
		return result;
	}

	protected LocalDate findStartDate() {
		return LocalDate.MIN;
	}

	protected LocalDate findEndDate() {
		return LocalDate.MAX;
	}

	protected int findNumberOfOccurences(LocalDate startDate, LocalDate endDate) {
		return findNumberOfCycles(startDate, endDate) * getOccurrenceInACycle();
	}

	protected boolean checkBound(LocalDate currentDate) {
		Recurrence recurrence = (Recurrence) this.scheduleSpec.getRecurrence();
		ScheduleBound scheduleBound = recurrence.getScheduleBound();
		TimeBound endBound = scheduleBound.getEndBound();
		LocalDate scheduleEndDate = endBound.getDate();
		boolean result = false;
		if (scheduleEndDate.equals(LocalDate.MAX)
				|| scheduleEndDate.isAfter(currentDate)
				|| currentDate.isEqual(scheduleEndDate)) {
			result = true;
		}
		return result;
	}

	protected LocalDate getEventStartDate(LocalDate givenDate) {
		Recurrence recurrence = (Recurrence) this.scheduleSpec.getRecurrence();
		ScheduleBound scheduleBound = recurrence.getScheduleBound();
		TimeBound startBound = scheduleBound.getStartBound();
		LocalDate startDate = givenDate;
		if (startDate.isBefore(startBound.getDate())) {
			startDate = startBound.getDate();
		}
		return startDate;
	}

	protected int getOccurrenceInACycle() {
		return 1;
	}

	protected abstract int findNumberOfCycles(LocalDate startDate,
			LocalDate endDate);
}
