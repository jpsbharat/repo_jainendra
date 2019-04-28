package com.jainendra.event.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Set;

public class TimeUtil {

	public static LocalDate getNextScheduledDateInMonth(LocalDate givenDate,
			Set<DayOfWeek> scheduledDays, Set<Integer> scheduledWeeksInMonth) {
		LocalDate targetDate = givenDate;
		DayOfWeek givenDayOfWeek = givenDate.getDayOfWeek();
		DayOfWeek firstScheduledDayOfWeek = null;
		for (DayOfWeek dayOfWeek : scheduledDays) {
			if (givenDayOfWeek.ordinal() <= dayOfWeek.ordinal()) {
				firstScheduledDayOfWeek = dayOfWeek;
				break;
			}
		}
 
		if(firstScheduledDayOfWeek == null){
			firstScheduledDayOfWeek = scheduledDays.iterator().next();
		}

		LocalDate nextDate = givenDate.with(TemporalAdjusters
				.nextOrSame(firstScheduledDayOfWeek));
		if((givenDate.getYear() < nextDate.getYear()) || (givenDate.getMonthValue() < nextDate.getMonthValue())){
			return nextDate;
		}

		if(givenDayOfWeek.ordinal() == firstScheduledDayOfWeek.ordinal()){
			return targetDate;
		}

		Integer weeksToAdd = 0;
		if(givenDayOfWeek.ordinal() > firstScheduledDayOfWeek.ordinal()){
			targetDate = targetDate.with(TemporalAdjusters
					.nextOrSame(firstScheduledDayOfWeek));
			weeksToAdd = -1;
		}

		Integer weekOfMonth = getWeekOfMonth(givenDate);
		if (!scheduledWeeksInMonth.isEmpty()
				&& scheduledWeeksInMonth.size() != 4) {
			for (Integer weekIndex : scheduledWeeksInMonth) {
				if (weekIndex >= weekOfMonth) {
					weeksToAdd = weeksToAdd + weekIndex - weekOfMonth;
					break;
				}else{
					if(weekOfMonth == 4){
						weeksToAdd = weeksToAdd + weekIndex;
						break;
					}
				}
			}
		}
		
		weeksToAdd = updateWeeksToAdd(weeksToAdd, givenDate, firstScheduledDayOfWeek);
		targetDate = givenDate.plusWeeks(weeksToAdd);
		targetDate = targetDate.with(TemporalAdjusters
				.nextOrSame(firstScheduledDayOfWeek));
		return targetDate;
	}

	private static int updateWeeksToAdd(int weeksToAdd, LocalDate givenDate, DayOfWeek firstScheduledDayOfWeek){
		LocalDate targetDate = givenDate.with(TemporalAdjusters
				.nextOrSame(firstScheduledDayOfWeek));
		if((givenDate.getYear() < targetDate.getYear()) || (givenDate.getMonthValue() < targetDate.getMonthValue())){
			weeksToAdd--;
		}
		return weeksToAdd;
	}

	public static LocalDate getUpcommingDayOfWeek(LocalDate givenDate, DayOfWeek dayOfWeek){
		LocalDate targetDate = givenDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
		return targetDate;
	}

	public static int getWeekOfMonth(LocalDate givenDate){
		TemporalField womField = WeekFields.of(Locale.getDefault()).weekOfMonth();
		Integer weekOfMonth = givenDate.get(womField);
		return weekOfMonth;
	}

	public static int getWeekOfYear(LocalDate givenDate){
		TemporalField woyField = WeekFields.of(Locale.getDefault()).weekOfYear();
		Integer weekOfMonth = givenDate.get(woyField);
		return weekOfMonth;
	}
}
