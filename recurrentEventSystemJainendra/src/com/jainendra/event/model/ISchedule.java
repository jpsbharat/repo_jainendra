package com.jainendra.event.model;

import java.time.LocalDate;
import java.util.Collection;

public interface ISchedule {
	public String getName();
	public LocalDate startDate();
	public LocalDate endDate();
	public Collection<LocalDate> getOccurrences(int limitNumberOfOccurences);// list of  dates
	public Collection<LocalDate> getOccurrencesFrom(LocalDate startDate, int numberOfOccurences); // list of dates;
	public Collection<LocalDate> getAllOccurrences(); // list of dates
	public int getNumberOfOccurences();// return #of occurrences for bounded schedules.
}
