package com.jainendra.event.model;

import com.jainendra.event.model.provider.IScheduleOccurrenceProvider;

public abstract class AbstractSchedule implements ISchedule{
	protected IScheduleOccurrenceProvider scheduleOccurrenceProvider;
	protected AbstractSchedule(IScheduleOccurrenceProvider scheduleOccurrenceProvider) {
		this.scheduleOccurrenceProvider = scheduleOccurrenceProvider;
	}
	
	public String getName(){
		return this.scheduleOccurrenceProvider.getName();
	}
}
