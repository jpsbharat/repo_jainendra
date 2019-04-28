package com.jainendra.event.model.provider;

import java.time.LocalDate;
import java.util.Collection;

import com.jainendra.event.model.RecurrenceYearlyDateWise;
import com.jainendra.event.model.ScheduleSpec;

public class YearlyScheduleOccurrenceProviderDateWise extends
		YearlyScheduleOccurrenceProvider {

	public YearlyScheduleOccurrenceProviderDateWise(ScheduleSpec scheduleSpec) {
		super(scheduleSpec);
	}

	@Override
	protected LocalDate fillOccurences(Collection<LocalDate> occurences,
			LocalDate currentDate, int givenNoOfOccurrences) {
		RecurrenceYearlyDateWise recurrence = (RecurrenceYearlyDateWise) this.scheduleSpec
				.getRecurrence();
		LocalDate localDate = currentDate;
		for (Integer dayOfMonth : recurrence.getDates()) {
			if(!checkBound(localDate) || givenNoOfOccurrences <= occurences.size()){
				break;
			}
			localDate = LocalDate.of(currentDate.getYear(),
					currentDate.getMonth(), dayOfMonth);
			occurences.add(localDate);
		}
		return localDate;
	}

	@Override
	protected int getOccurrenceInACycle() {
		RecurrenceYearlyDateWise recurrence = (RecurrenceYearlyDateWise) this.scheduleSpec
				.getRecurrence();
		return recurrence.getDates().size();
	}
}
