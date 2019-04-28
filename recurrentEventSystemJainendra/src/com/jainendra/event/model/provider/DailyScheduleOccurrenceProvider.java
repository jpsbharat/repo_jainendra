package com.jainendra.event.model.provider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.jainendra.event.model.RecurrenceDaily;
import com.jainendra.event.model.ScheduleSpec;

public class DailyScheduleOccurrenceProvider extends
		AbstractScheduleOccurrenceProvider {

	public DailyScheduleOccurrenceProvider(ScheduleSpec scheduleSpec) {
		super(scheduleSpec);
	}

	@Override
	public Collection<LocalDate> getOccurrencesFrom(LocalDate startDate,
			int numberOfOccurences) {
		RecurrenceDaily recurrence = (RecurrenceDaily) this.scheduleSpec
				.getRecurrence();
		Collection<LocalDate> occurences = new ArrayList<LocalDate>();

		LocalDate scheduleEndDate = null;
		boolean checkEndBound = false;
		if (recurrence.getScheduleBound().getEndBound() != null
				&& recurrence.getScheduleBound().getEndBound().getDate() != null) {
			scheduleEndDate = recurrence.getScheduleBound().getEndBound()
					.getDate();
			checkEndBound = true;
		}

		int maxAllowedOccurences = recurrence.getScheduleBound()
				.getAllowedOccurnces();
		if (numberOfOccurences > maxAllowedOccurences) {
			numberOfOccurences = maxAllowedOccurences;
		}
		while (numberOfOccurences > 0) {
			if (checkEndBound && !(scheduleEndDate != null
					&& (scheduleEndDate.isAfter(startDate) || startDate
							.isEqual(scheduleEndDate)))) {
				break;
			}
			occurences.add(startDate);
			startDate = startDate.plusDays(recurrence.getFrequency());
		}
		return occurences;
	}

	protected int findNumberOfCycles(LocalDate startDate, LocalDate endDate) {
		RecurrenceDaily recurrence = (RecurrenceDaily) this.scheduleSpec
				.getRecurrence();
		int result = Integer.MAX_VALUE;
		if (startDate != null && endDate != null) {
			int count = 0;
			while (startDate.isBefore(endDate) || endDate.isEqual(startDate)) {
				startDate = startDate.plusDays(recurrence.getFrequency());
				count++;
			}
			result = count;
		}
		return result;
	}
}
