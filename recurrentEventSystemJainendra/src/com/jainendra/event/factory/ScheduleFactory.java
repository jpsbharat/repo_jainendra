package com.jainendra.event.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.jainendra.event.model.DefaultSchedule;
import com.jainendra.event.model.ISchedule;
import com.jainendra.event.model.Recurrence;
import com.jainendra.event.model.ScheduleSpec;
import com.jainendra.event.model.provider.IScheduleOccurrenceProvider;
import com.jainendra.event.util.EventUtil;

public class ScheduleFactory {
	private static ScheduleFactory INSTANCE = new ScheduleFactory();
	private Map<String, String> recurrenceToRecurrenceProviderMap = null;

	private ScheduleFactory() {
		this.recurrenceToRecurrenceProviderMap = new HashMap<String, String>();
		this.recurrenceToRecurrenceProviderMap
				.put("com.jainendra.event.model.RecurrenceDaily",
						"com.jainendra.event.model.provider.DailyScheduleOccurrenceProvider");
		this.recurrenceToRecurrenceProviderMap
				.put("com.jainendra.event.model.RecurrenceNone",
						"com.jainendra.event.model.provider.NonRecurrentScheduleOccurrenceProvider");
		this.recurrenceToRecurrenceProviderMap
				.put("com.jainendra.event.model.RecurrencePeriodic",
						"com.jainendra.event.model.provider.PeriodicScheduleOccurrenceProvider");
		this.recurrenceToRecurrenceProviderMap
				.put("com.jainendra.event.model.RecurrenceWeekly",
						"com.jainendra.event.model.provider.WeeklyScheduleOccurrenceProvider");
		this.recurrenceToRecurrenceProviderMap
				.put("com.jainendra.event.model.RecurrenceYearlyDateWise",
						"com.jainendra.event.model.provider.YearlyScheduleOccurrenceProviderDateWise");
		this.recurrenceToRecurrenceProviderMap
				.put("com.jainendra.event.model.RecurrenceYearlyDayWise",
						"com.jainendra.event.model.provider.YearlyScheduleOccurrenceProviderDayWise");
		this.recurrenceToRecurrenceProviderMap
				.put("com.jainendra.event.model.RecurrenceMonthlyDateWise",
						"com.jainendra.event.model.provider.MonthlyScheduleOccurrenceProviderDateWise");
		this.recurrenceToRecurrenceProviderMap
				.put("com.jainendra.event.model.RecurrenceMonthlyDayWise",
						"com.jainendra.event.model.provider.MonthlyScheduleOccurrenceProviderDayWise");
	}

	public static ScheduleFactory getINSTANCE() {
		return INSTANCE;
	}

	public ISchedule createSchedule(String eventStr) {
		ScheduleSpec scheduleSpec = EventUtil.parseEvent(eventStr);
		IScheduleOccurrenceProvider scheduleOccurrenceProvider = createScheduleOccurrenceProvider(scheduleSpec);
		ISchedule schedule = new DefaultSchedule(scheduleOccurrenceProvider);
		return schedule;
	}

	private IScheduleOccurrenceProvider createScheduleOccurrenceProvider(
			ScheduleSpec scheduleSpec) {
		Recurrence recurrence = scheduleSpec.getRecurrence();
		String providerClassName = this.recurrenceToRecurrenceProviderMap
				.get(recurrence.getClass().getTypeName());
		try {
			Class<?> providerClass = Class.forName(providerClassName);
			Constructor<?> constructor = providerClass
					.getConstructor(new Class[] { ScheduleSpec.class });
			IScheduleOccurrenceProvider scheduleOccurrenceProvider = (IScheduleOccurrenceProvider) constructor
					.newInstance(scheduleSpec);
			return scheduleOccurrenceProvider;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
