package com.jainendra.event.model.provider;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;

import com.jainendra.event.model.RecurrenceMonthlyDayWise;
import com.jainendra.event.model.ScheduleSpec;

public class MonthlyScheduleOccurrenceProviderDayWise extends
		MonthlyScheduleOccurrenceProvider {

	public MonthlyScheduleOccurrenceProviderDayWise(ScheduleSpec scheduleSpec) {
		super(scheduleSpec);
	}

	@Override
	public Collection<LocalDate> getOccurrencesFrom(LocalDate givenDate,
			int givenNoOfOccurrences) {
		RecurrenceMonthlyDayWise recurrence = (RecurrenceMonthlyDayWise) this.scheduleSpec
				.getRecurrence();
		int maxAllowedOccurences = recurrence.getScheduleBound()
				.getAllowedOccurnces();
		if (givenNoOfOccurrences > maxAllowedOccurences) {
			givenNoOfOccurrences = maxAllowedOccurences;
		}
		int numberOfOccurences = givenNoOfOccurrences;
		Collection<LocalDate> occurences = new ArrayList<LocalDate>();
		LocalDate adjustedFromDate = getEventStartDate(givenDate);
		LocalDate currentDate = adjustedFromDate;
		
		for (Integer ordinal : recurrence.getOrdinals()) {
			for (DayOfWeek dayOfWeek : recurrence.getDays()) {
				currentDate = currentDate.with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek));
				if(!checkBound(currentDate) || givenNoOfOccurrences <= occurences.size()){
					return occurences;
				}

				if(currentDate.isAfter(adjustedFromDate) || currentDate.isEqual(adjustedFromDate)){
					occurences.add(currentDate);
					numberOfOccurences--;
				}
			}
		}

		currentDate = currentDate.plusMonths(recurrence.getFrequency());
		
		while (numberOfOccurences > 0) {
			for (Integer ordinal : recurrence.getOrdinals()) {
				for (DayOfWeek dayOfWeek : recurrence.getDays()) {
					currentDate = currentDate.with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek));
					if(!checkBound(currentDate) || givenNoOfOccurrences <= occurences.size()){
						return occurences;
					}

					if(currentDate.isAfter(adjustedFromDate) || currentDate.isEqual(adjustedFromDate)){
						occurences.add(currentDate);
					}
				}
			}
			currentDate = currentDate.with(TemporalAdjusters.firstDayOfMonth());
			currentDate = currentDate.plusMonths(recurrence.getFrequency());
			numberOfOccurences--;
		}
		return occurences;
	}

	@Override
	protected int getOccurrenceInACycle() {
		RecurrenceMonthlyDayWise recurrence = (RecurrenceMonthlyDayWise) this.scheduleSpec
				.getRecurrence();
		int factor = (recurrence.getOrdinals().isEmpty()) ? 4 : recurrence
				.getOrdinals().size();
		return recurrence.getDays().size() * factor;
	}
}
