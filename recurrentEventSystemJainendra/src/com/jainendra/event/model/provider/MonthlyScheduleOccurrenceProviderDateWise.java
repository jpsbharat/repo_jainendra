package com.jainendra.event.model.provider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.jainendra.event.model.RecurrenceMonthlyDateWise;
import com.jainendra.event.model.ScheduleSpec;

public class MonthlyScheduleOccurrenceProviderDateWise extends
		MonthlyScheduleOccurrenceProvider {

	public MonthlyScheduleOccurrenceProviderDateWise(ScheduleSpec scheduleSpec) {
		super(scheduleSpec);
	}

	@Override
	public Collection<LocalDate> getOccurrencesFrom(LocalDate givenDate,
			int givenNoOfOccurrences) {
		RecurrenceMonthlyDateWise recurrence = (RecurrenceMonthlyDateWise) this.scheduleSpec
				.getRecurrence();
		Collection<LocalDate> occurences = new ArrayList<LocalDate>();
		LocalDate adjustedFromDate = getEventStartDate(givenDate);
		LocalDate startDate = adjustedFromDate;
		int maxAllowedOccurences = recurrence.getScheduleBound()
				.getAllowedOccurnces();
		if (givenNoOfOccurrences > maxAllowedOccurences) {
			givenNoOfOccurrences = maxAllowedOccurences;
		}
		int numberOfOccurences = givenNoOfOccurrences;

		for (Integer dayOfMonth : recurrence.getDates()) {
			LocalDate localDate = LocalDate.of(startDate.getYear(),
					startDate.getMonth(), dayOfMonth);
			if(!checkBound(localDate) || givenNoOfOccurrences <= occurences.size()){
				return occurences;
			}
			if (localDate.isAfter(startDate) || localDate.isEqual(startDate)) {
				occurences.add(localDate);
				numberOfOccurences--;
			}
		}

		startDate = startDate.plusMonths(recurrence.getFrequency());
		while (numberOfOccurences > 0) {
			for (Integer dayOfMonth : recurrence.getDates()) {
				LocalDate localDate = LocalDate.of(startDate.getYear(),
						startDate.getMonth(), dayOfMonth);
				if(!checkBound(localDate) || givenNoOfOccurrences <= occurences.size()){
					return occurences;
				}
				occurences.add(localDate);
			}
			startDate = startDate.plusMonths(recurrence.getFrequency());
			numberOfOccurences--;
		}
		return occurences;
	}

	protected int findNumberOfOccurences() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected int getOccurrenceInACycle() {
		RecurrenceMonthlyDateWise recurrence = (RecurrenceMonthlyDateWise) this.scheduleSpec
				.getRecurrence();
		return recurrence.getDates().size();
	}
}
