package com.jainendra.event.model.provider;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;

import com.jainendra.event.model.RecurrenceYearlyDayWise;
import com.jainendra.event.model.ScheduleSpec;

public class YearlyScheduleOccurrenceProviderDayWise extends
		YearlyScheduleOccurrenceProvider {
	public YearlyScheduleOccurrenceProviderDayWise(ScheduleSpec scheduleSpec) {
		super(scheduleSpec);
	}

	@Override
	protected LocalDate fillOccurences(Collection<LocalDate> occurences,
			LocalDate givenDate, int givenNoOfOccurrences) {
		RecurrenceYearlyDayWise recurrence = (RecurrenceYearlyDayWise) this.scheduleSpec
				.getRecurrence();
		LocalDate currentDate = givenDate;
		for (Integer ordinal : recurrence.getOrdinals()) {
			for (DayOfWeek dayOfWeek : recurrence.getDays()) {
				currentDate = currentDate.with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek));
				occurences.add(currentDate);
			}
		}
		currentDate = currentDate.with(TemporalAdjusters.firstDayOfMonth());
		return currentDate;
	}

	@Override
	protected int getOccurrenceInACycle() {
		RecurrenceYearlyDayWise recurrence = (RecurrenceYearlyDayWise) this.scheduleSpec
				.getRecurrence();
		int factor = (recurrence.getOrdinals().isEmpty()) ? 4 : recurrence
				.getOrdinals().size();
		return recurrence.getDays().size() * factor;
	}
}
