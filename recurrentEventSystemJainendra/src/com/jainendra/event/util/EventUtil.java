package com.jainendra.event.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jainendra.event.model.Recurrence;
import com.jainendra.event.model.RecurrenceDaily;
import com.jainendra.event.model.RecurrenceMonthlyDateWise;
import com.jainendra.event.model.RecurrenceMonthlyDayWise;
import com.jainendra.event.model.RecurrenceNone;
import com.jainendra.event.model.RecurrencePeriodic;
import com.jainendra.event.model.RecurrenceWeekly;
import com.jainendra.event.model.RecurrenceYearly;
import com.jainendra.event.model.RecurrenceYearlyDateWise;
import com.jainendra.event.model.RecurrenceYearlyDayWise;
import com.jainendra.event.model.ScheduleBound;
import com.jainendra.event.model.ScheduleBound.TimeBound;
import com.jainendra.event.model.ScheduleSpec;

public class EventUtil {

	private static Map<String, DayOfWeek> shorToFullDayNameMap = new HashMap<>();
	static {
		shorToFullDayNameMap.put("SUN", DayOfWeek.SUNDAY);
		shorToFullDayNameMap.put("MON", DayOfWeek.MONDAY);
		shorToFullDayNameMap.put("TUE", DayOfWeek.TUESDAY);
		shorToFullDayNameMap.put("WED", DayOfWeek.WEDNESDAY);
		shorToFullDayNameMap.put("THU", DayOfWeek.THURSDAY);
		shorToFullDayNameMap.put("FRI", DayOfWeek.FRIDAY);
		shorToFullDayNameMap.put("SAT", DayOfWeek.SATURDAY);
	}

	public static ScheduleSpec parseEvent(String eventStr) {
		ScheduleSpec scheduleSpec = new ScheduleSpec();
		String[] eventStrAsArray = eventStr.split(Constant.SEPARATOR_PIPE);
		scheduleSpec.setEventName(eventStrAsArray[0]);
		ScheduleBound scheduleBound = null;
		if (eventStrAsArray.length > 2) {
			scheduleBound = parseBoundry(eventStrAsArray[2]);
		} else {
			scheduleBound = new ScheduleBound();
		}
		Recurrence recurrence = parseRecurrence(eventStrAsArray[1],
				scheduleBound);
		recurrence.setScheduleBound(scheduleBound);
		scheduleSpec.setRecurrence(recurrence);
		return scheduleSpec;
	}

	private static Recurrence parseRecurrence(String recurr,
			ScheduleBound scheduleBound) {
		Recurrence recurrence = null;
		String[] recurrAsArray = recurr.split(Constant.SEPARATOR_HASH);
		String[] refrenceTypeAndFreq = recurrAsArray[0]
				.split(Constant.SEPARATOR_COLON);
		if(refrenceTypeAndFreq.length > 1){
			int frequency = Integer.parseInt(refrenceTypeAndFreq[1].trim());
			if ("Y".equalsIgnoreCase(refrenceTypeAndFreq[0])) {
				recurrence = parseYearlyRecurrence(frequency, recurrAsArray,
						scheduleBound);
			} else if ("M".equalsIgnoreCase(refrenceTypeAndFreq[0])) {
				recurrence = parseMonthlyRecurrence(frequency, recurrAsArray,
						scheduleBound);
			} else if ("W".equalsIgnoreCase(refrenceTypeAndFreq[0])) {
				recurrence = parseWeeklyRecurrence(frequency, recurrAsArray,
						scheduleBound);
			} else if ("D".equalsIgnoreCase(refrenceTypeAndFreq[0])) {
				recurrence = parseDailyRecurrence(frequency, recurrAsArray,
						scheduleBound);
			} else if ("O".equalsIgnoreCase(refrenceTypeAndFreq[0])) {
				recurrence = parseOnceRecurrence(frequency, recurrAsArray,
						scheduleBound);
			} 
		}else{
			recurrence = parseNonRecurrence(recurrAsArray,
					scheduleBound);
		}
		return recurrence;
	}

	private static Recurrence parseYearlyRecurrence(int frequency,
			String[] recurr, ScheduleBound scheduleBound) {
		RecurrenceYearly recurrence = null;
		String recurInfoStr = recurr[1];
		String[] recurInfoStrAsArray = recurInfoStr
				.split(Constant.SEPARATOR_COLON);
		Integer month = Integer.parseInt(recurInfoStrAsArray[0].trim());
		String[] daysOrDates = recurInfoStrAsArray[1]
				.split(Constant.SEPARATOR_COMMA);
		boolean dateWise = checkDateWise(daysOrDates);
		if (dateWise) {
			recurrence = createDateWiseYearlyOccurence(frequency, month,
					daysOrDates, scheduleBound);
		} else {
			String[] ordinals = null;
			if (recurInfoStrAsArray.length > 2) {
				ordinals = recurInfoStrAsArray[2]
						.split(Constant.SEPARATOR_COMMA);
			}
			recurrence = createDayWiseYearlyOccurence(frequency, month,
					daysOrDates, ordinals, scheduleBound);
		}
		return recurrence;
	}

	private static RecurrenceYearly createDayWiseYearlyOccurence(int frequency,
			Integer month, String[] days, String[] ordinals,
			ScheduleBound scheduleBound) {
		RecurrenceYearlyDayWise recurrence = new RecurrenceYearlyDayWise(
				frequency, month);
		recurrence.setScheduleBound(scheduleBound);
		TimeBound startTimeBound = scheduleBound.getStartBound();
		for (String day : days) {
			recurrence.addDay(shorToFullDayNameMap
					.get(day.trim().toUpperCase()));
		}

		if (ordinals != null && ordinals.length > 0) {
			for (String ordinal : ordinals) {
				recurrence.addOrdinal(Integer.parseInt(ordinal.trim()));
			}
		}
		DayOfWeek startScheduledDay = recurrence.getDays().iterator().next();
		LocalDate startDate = LocalDate.of(startTimeBound.getDate().getYear(), month, 1);
		LocalDate targetDate = startDate.with(TemporalAdjusters.nextOrSame(startScheduledDay));
		targetDate = TimeUtil.getNextScheduledDateInMonth(targetDate, recurrence.getDays(), recurrence.getOrdinals());
		startTimeBound.setDate(targetDate);
		return recurrence;
	}

	private static RecurrenceYearly createDateWiseYearlyOccurence(
			int frequency, Integer month, String[] dates,
			ScheduleBound scheduleBound) {
		RecurrenceYearlyDateWise recurrence = new RecurrenceYearlyDateWise(
				frequency, month);
		recurrence.setScheduleBound(scheduleBound);
		TimeBound startTimeBound = scheduleBound.getStartBound();
		boolean flag = false;
		for (String date : dates) {
			int dateInt = Integer.parseInt(date);
			recurrence.addDate(dateInt);
			if(!flag){
				startTimeBound.setDate(LocalDate.of(startTimeBound.getDate().getYear(), startTimeBound.getDate().getMonth(), dateInt));
				flag = true;
			}
		}
		return recurrence;
	}

	private static Recurrence parseMonthlyRecurrence(int frequency,
			String[] recurr, ScheduleBound scheduleBound) {
		Recurrence recurrence = null;
		String recurInfoStr = recurr[1];
		String[] recurInfoStrAsArray = recurInfoStr
				.split(Constant.SEPARATOR_COLON);
		String[] days = recurInfoStrAsArray[0].split(Constant.SEPARATOR_COMMA);
		if (checkDateWise(days)) {
			recurrence = createDateWiseMonthlyOccurence(frequency, days,
					scheduleBound);
		} else {
			String[] ordinals = null;
			if (recurInfoStrAsArray.length > 1) {
				ordinals = recurInfoStrAsArray[1]
						.split(Constant.SEPARATOR_COMMA);
			}
			recurrence = createDayWiseMonthlyOccurence(frequency, days,
					ordinals, scheduleBound);
		}

		return recurrence;
	}

	private static boolean checkDateWise(String[] days) {
		for (String day : days) {
			try {
				Integer.parseInt(day.trim());
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	private static Recurrence createDayWiseMonthlyOccurence(int frequency,
			String[] days, String[] ordinals, ScheduleBound scheduleBound) {
		RecurrenceMonthlyDayWise recurrence = new RecurrenceMonthlyDayWise(
				frequency);
		for (String day : days) {
			recurrence.addDay(shorToFullDayNameMap
					.get(day.trim().toUpperCase()));
		}

		if (ordinals != null && ordinals.length > 0) {
			for (String ordinal : ordinals) {
				recurrence.addOrdinal(Integer.parseInt(ordinal.trim()));
			}
		}

		//TimeBound startTimeBound = scheduleBound.getStartBound();
		//setWeekDay(startTimeBound, recurrence.getDays(),
				//recurrence.getOrdinals());
		return recurrence;
	}

	private static Recurrence createDateWiseMonthlyOccurence(int frequency,
			String[] dates, ScheduleBound scheduleBound) {
		RecurrenceMonthlyDateWise recurrence = new RecurrenceMonthlyDateWise(
				frequency);
		recurrence.setScheduleBound(scheduleBound);
		TimeBound startTimeBound = scheduleBound.getStartBound();
		boolean flag = false;
		for (String date : dates) {
			int dateInt = Integer.parseInt(date);
			recurrence.addDate(dateInt);
			if(!flag){
				LocalDate startDate = LocalDate.of(startTimeBound.getDate().getYear(), startTimeBound.getDate().getMonth(), dateInt);
				if(startDate.isBefore(LocalDate.now())){
					startDate = startDate.plusMonths(1);
				}
				startTimeBound.setDate(startDate);
				flag = true;
			}
		}
		return recurrence;
	}

	private static Recurrence parseWeeklyRecurrence(int frequency,
			String[] recurr, ScheduleBound scheduleBound) {
		RecurrenceWeekly recurrence = new RecurrenceWeekly(frequency);
		String recurInfoStr = recurr[1];
		String[] days = recurInfoStr.split(Constant.SEPARATOR_COMMA);
		recurrence.setScheduleBound(scheduleBound);
		TimeBound startTimeBound = scheduleBound.getStartBound();
		DayOfWeek dayOfWeek = startTimeBound.getDate().getDayOfWeek();
		boolean flag = false;
		for (String day : days) {
			DayOfWeek dow = shorToFullDayNameMap.get(day.trim().toUpperCase());
			recurrence.addDay(dow);
			if (!flag && dayOfWeek.ordinal() <= dow.ordinal()) {
				flag = true;
				LocalDate targetDate = startTimeBound.getDate().with(
						TemporalAdjusters.nextOrSame(dow));
				startTimeBound.setDate(targetDate);
			}
		}

		return recurrence;
	}

	private static Recurrence parseDailyRecurrence(int frequency,
			String[] recurr, ScheduleBound scheduleBound) {
		RecurrenceDaily recurrence = new RecurrenceDaily(frequency);
		recurrence.setScheduleBound(scheduleBound);
		TimeBound startTimeBound = scheduleBound.getStartBound();
		startTimeBound.setDate(LocalDate.now());
		return recurrence;
	}

	private static Recurrence parseOnceRecurrence(int frequency,
			String[] recurr, ScheduleBound scheduleBound) {
		String periodicRecurrentStr = recurr[1];
		String[] periodicRecurrentStrArray = periodicRecurrentStr
				.split(Constant.SEPARATOR_COLON);
		String type = periodicRecurrentStrArray[0].trim();
		int date = Integer.parseInt(periodicRecurrentStrArray[1].trim());
		int period = 0;
		LocalDate currentDate = LocalDate.now();
		LocalDate localDate = currentDate;
		if ("Q".equalsIgnoreCase(type)) {
			period = 3;
			localDate = LocalDate.of(localDate.getYear(), localDate.getMonth()
					.firstMonthOfQuarter(), date);
		} else {
			period = Integer.parseInt(periodicRecurrentStrArray[2].trim());
			localDate = LocalDate.of(localDate.getYear(), localDate.getMonth(),
					date);
		}

		if (date < currentDate.getDayOfMonth()) {
			localDate.plusMonths(period);
		}

		RecurrencePeriodic recurrence = new RecurrencePeriodic(frequency,
				period);
		recurrence.setScheduleBound(scheduleBound);
		TimeBound startTimeBound = scheduleBound.getStartBound();
		recurrence.setLocalDate(localDate);
		startTimeBound.setDate(localDate);
		return recurrence;
	}

	private static Recurrence parseNonRecurrence(String[] recurr, ScheduleBound scheduleBound) {
		RecurrenceNone recurrence = new RecurrenceNone(0);
		recurrence.setScheduleBound(scheduleBound);
		TimeBound startTimeBound = scheduleBound.getStartBound();
		String nonRecurrentStr = recurr[1];
		String[] nonRecurrentStrArray = nonRecurrentStr
				.split(Constant.SEPARATOR_COLON);
		int y = Integer.parseInt(nonRecurrentStrArray[0].trim());
		int m = Integer.parseInt(nonRecurrentStrArray[1].trim());
		String dayOrDate = nonRecurrentStrArray[2].trim();
		boolean flag = checkDateWise(new String[] { dayOrDate });
		LocalDate localDate = null;
		if (flag) {
			String formatPattern = y + Constant.SEPARATOR_DASH + m
					+ Constant.SEPARATOR_DASH + dayOrDate;
			localDate = LocalDate.parse(formatPattern);
		} else {
			int ordinal = Integer.parseInt(nonRecurrentStrArray[1]);
			DayOfWeek dow = shorToFullDayNameMap.get(dayOrDate);
			localDate = LocalDate.of(y, m, 1);
			localDate = localDate.with(TemporalAdjusters.nextOrSame(dow));
			int weekOrdinal = TimeUtil.getWeekOfMonth(localDate);
			localDate = localDate.plusWeeks(ordinal - weekOrdinal);
		}
		recurrence.setDate(localDate);
		startTimeBound.setDate(localDate);
		return recurrence;
	}

	private static ScheduleBound parseBoundry(String boundryInfo) {
		ScheduleBound scheduleBound = null;
		if (boundryInfo != null && !Constant.EMPTY_STR.equals(boundryInfo)) {
			scheduleBound = new ScheduleBound();
			String[] boundryInfoAsArray = boundryInfo
					.split(Constant.SEPARATOR_HASH);
			for (String str : boundryInfoAsArray) {
				fillBoundary(scheduleBound, str);
			}
		}
		return scheduleBound;
	}

	private static void fillBoundary(ScheduleBound scheduleBound,
			String boundryStr) {
		String[] boundryStrArray = boundryStr.split(Constant.SEPARATOR_COLON);
		String limit = boundryStrArray[0];
		String limitValue = boundryStrArray[1];
		if ("S".equalsIgnoreCase(limit)) {
			TimeBound timeBound = createTimeBound(limitValue);
			scheduleBound.setStartBound(timeBound);
		} else if ("E".equalsIgnoreCase(limit)) {
			TimeBound timeBound = createTimeBound(limitValue);
			scheduleBound.setEndBound(timeBound);
		} else if ("O".equalsIgnoreCase(limit)) {
			int allowedOccurnces = Integer.parseInt(limitValue);
			scheduleBound.setAllowedOccurnces(allowedOccurnces);
		}
	}

	private static TimeBound createTimeBound(String limitValue) {
		TimeBound timeBound = new TimeBound();
		String[] boundryStrArray = limitValue.split(Constant.SEPARATOR_DASH);
		if (boundryStrArray.length > 2) {
			int y = Integer.parseInt(boundryStrArray[0].trim());
			int m = Integer.parseInt(boundryStrArray[1].trim());
			int d = Integer.parseInt(boundryStrArray[2].trim());
			LocalDate date = LocalDate.of(y, m, d);
			timeBound.setDate(date);
		} 
		return timeBound;
	}

	public static void setWeekDay(TimeBound timeBound,
			Set<DayOfWeek> scheduledDays, Set<Integer> scheduledWeeksInMonth) {
		LocalDate currentDate = LocalDate.now();
		LocalDate startDate = TimeUtil.getNextScheduledDateInMonth(currentDate,
				scheduledDays, scheduledWeeksInMonth);
		timeBound.setDate(startDate);
	}
}
