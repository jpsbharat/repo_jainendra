package com.jainendra.event.model.provider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.jainendra.event.model.RecurrencePeriodic;
import com.jainendra.event.model.ScheduleSpec;

public class PeriodicScheduleOccurrenceProvider extends
		AbstractScheduleOccurrenceProvider {

	public PeriodicScheduleOccurrenceProvider(ScheduleSpec scheduleSpec) {
		super(scheduleSpec);
	}

	@Override
	public Collection<LocalDate> getOccurrencesFrom(LocalDate givenDate,
			int givenNoOfOccurrences) {
		RecurrencePeriodic recurrence = (RecurrencePeriodic) this.scheduleSpec
				.getRecurrence();
		Collection<LocalDate> occurences = new ArrayList<LocalDate>();
		givenDate = getEventStartDate(givenDate);
		int maxAllowedOccurences = recurrence.getScheduleBound()
				.getAllowedOccurnces();
		if (givenNoOfOccurrences > maxAllowedOccurences) {
			givenNoOfOccurrences = maxAllowedOccurences;
		}
		int numberOfOccurences = givenNoOfOccurrences;

		while (numberOfOccurences > 0) {
			if(!checkBound(givenDate) || givenNoOfOccurrences <= occurences.size()){
				return occurences;
			}
			occurences.add(givenDate);
			numberOfOccurences--;
			givenDate = givenDate.plusMonths(recurrence.getFrequency()
					* recurrence.getPeriod());
		}
		return occurences;
	}

	protected int findNumberOfCycles(LocalDate startDate, LocalDate endDate) {
		RecurrencePeriodic recurrence = (RecurrencePeriodic) this.scheduleSpec
				.getRecurrence();
		int result = Integer.MAX_VALUE;
		if (startDate != null && endDate != null) {
			int count = 0;
			while (startDate.isBefore(endDate) || endDate.isEqual(startDate)) {
				startDate = startDate.plusMonths(recurrence.getFrequency()
						* recurrence.getPeriod());
				count++;
			}
			result = count;
		}
		return result;
	}
}
