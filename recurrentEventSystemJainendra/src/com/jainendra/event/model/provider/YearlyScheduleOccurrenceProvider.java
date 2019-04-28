package com.jainendra.event.model.provider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.jainendra.event.model.RecurrenceYearly;
import com.jainendra.event.model.ScheduleSpec;

public abstract class YearlyScheduleOccurrenceProvider extends
		AbstractScheduleOccurrenceProvider {

	public YearlyScheduleOccurrenceProvider(ScheduleSpec scheduleSpec) {
		super(scheduleSpec);
	}

	@Override
	public Collection<LocalDate> getOccurrencesFrom(LocalDate startDate,
			int givenNoOfOccurrences) {
		RecurrenceYearly recurrence = (RecurrenceYearly) this.scheduleSpec
				.getRecurrence();
		Collection<LocalDate> occurences = new ArrayList<LocalDate>();
		LocalDate adjustedDate = adjustMonthAndYear(startDate);
		int maxAllowedOccurences = recurrence.getScheduleBound()
				.getAllowedOccurnces();

		if (givenNoOfOccurrences > maxAllowedOccurences) {
			givenNoOfOccurrences = maxAllowedOccurences;
		}
		int numberOfOccurences = givenNoOfOccurrences;

		while (numberOfOccurences > 0) {
			if(!checkBound(adjustedDate) || givenNoOfOccurrences <= occurences.size()){
				return occurences;
			}
			adjustedDate = fillOccurences(occurences, adjustedDate, givenNoOfOccurrences);
			adjustedDate = adjustedDate.plusYears(recurrence.getFrequency());
			numberOfOccurences--;
		}
		return occurences;
	}

	protected LocalDate adjustMonthAndYear(LocalDate startDate) {
		RecurrenceYearly recurrence = (RecurrenceYearly) this.scheduleSpec
				.getRecurrence();
		startDate = getEventStartDate(startDate);
		int givenMonth = startDate.getMonthValue();
		int scheduledMonth = recurrence.getMonth();
		if (scheduledMonth < givenMonth) {
			startDate = startDate.plusYears(1).minusMonths(
					givenMonth - scheduledMonth);
		} else if (scheduledMonth > givenMonth) {
			startDate = startDate.plusMonths(scheduledMonth - givenMonth);
		}

		return startDate;
	}

	protected int findNumberOfCycles(LocalDate startDate, LocalDate endDate) {
		RecurrenceYearly recurrence = (RecurrenceYearly) this.scheduleSpec
				.getRecurrence();
		int result = Integer.MAX_VALUE;
		if (startDate != null && endDate != null) {
			int count = 0;
			while (startDate.isBefore(endDate) || endDate.isEqual(startDate)) {
				startDate = startDate.plusYears(recurrence.getFrequency());
				count++;
			}
			result = count;
		}
		return result;
	}

	protected LocalDate findStartDate() {
		return LocalDate.MIN;
	}

	protected LocalDate findEndDate() {
		return LocalDate.MAX;
	}

	protected abstract LocalDate fillOccurences(
			Collection<LocalDate> occurences, LocalDate currentDate, int givenNoOfOccurrences);
}
