package com.jainendra.event.model.provider;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;

import com.jainendra.event.model.RecurrenceWeekly;
import com.jainendra.event.model.ScheduleSpec;

public class WeeklyScheduleOccurrenceProvider extends
		AbstractScheduleOccurrenceProvider {

	public WeeklyScheduleOccurrenceProvider(ScheduleSpec scheduleSpec) {
		super(scheduleSpec);
	}

	@Override
	public Collection<LocalDate> getOccurrencesFrom(LocalDate givenDate,
			int givenNoOfOccurrences) {
		RecurrenceWeekly recurrence = (RecurrenceWeekly) this.scheduleSpec
				.getRecurrence();
		Collection<LocalDate> occurences = new ArrayList<LocalDate>();
		LocalDate adjustedFromDate = getEventStartDate(givenDate);
		LocalDate currentDate = adjustedFromDate;

		int maxAllowedOccurences = recurrence.getScheduleBound()
				.getAllowedOccurnces();
		if (givenNoOfOccurrences > maxAllowedOccurences) {
			givenNoOfOccurrences = maxAllowedOccurences;
		}
		int numberOfOccurences = givenNoOfOccurrences;

		while (numberOfOccurences > 0) {
			for (DayOfWeek dayOfWeek : recurrence.getDays()) {
				currentDate = currentDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
				if(!checkBound(currentDate) || givenNoOfOccurrences <= occurences.size()){
					return occurences;
				}
				occurences.add(currentDate);
			}
			
			currentDate = currentDate.minusDays(currentDate.getDayOfWeek().ordinal() - DayOfWeek.MONDAY.ordinal());
			currentDate = currentDate.plusWeeks(recurrence.getFrequency());
			numberOfOccurences--;
		}
		return occurences;
	}

	protected int findNumberOfCycles(LocalDate startDate, LocalDate endDate) {
		RecurrenceWeekly recurrence = (RecurrenceWeekly) this.scheduleSpec
				.getRecurrence();
		int result = Integer.MAX_VALUE;
		if (startDate != null && endDate != null) {
			int count = 0;
			while (startDate.isBefore(endDate) || endDate.isEqual(startDate)) {
				startDate = startDate.plusWeeks(recurrence.getFrequency());
				count++;
			}
			result = count;
		}
		return result;
	}

	@Override
	protected int getOccurrenceInACycle() {
		RecurrenceWeekly recurrence = (RecurrenceWeekly) this.scheduleSpec
				.getRecurrence();
		return recurrence.getDays().size();
	}
}
