package com.jainendra.event;

import com.jainendra.event.factory.ScheduleFactory;
import com.jainendra.event.model.ISchedule;

public class ScheduleProcessor {

	public static ISchedule scheduleFor(String eventStr){
		return ScheduleFactory.getINSTANCE().createSchedule(eventStr);
	}

}
