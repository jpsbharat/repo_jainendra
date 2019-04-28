package com.jainendra.event.util;

import java.util.regex.Pattern;

public interface Constant {
	String SEPARATOR_LINE = System.lineSeparator();
	String SEPARATOR_PIPE = Pattern.quote("|");
	String SEPARATOR_COLON = Pattern.quote(":");
	String SEPARATOR_HASH = Pattern.quote("#");
	String SEPARATOR_COMMA = Pattern.quote(",");
	String SEPARATOR_DASH = "-";
	String EMPTY_STR = "";

	String RECCURENCE_PROVIDER_DAILY = "com.jainendra.event.model.DailyScheduleOccurrenceProvider";
	String RECCURENCE_PROVIDER_MONTHLY_DAY_WISE = "com.jainendra.event.model.MonthlyScheduleOccurrenceProviderDayWise";
	String RECCURENCE_PROVIDER_MONTHLY_DATE_WISE = "com.jainendra.event.model.MonthlyScheduleOccurrenceProviderDateWise";
	String RECCURENCE_PROVIDER_YEARLY_DAY_WISE = "com.jainendra.event.model.YearlyScheduleOccurrenceProviderDayWise";
	String RECCURENCE_PROVIDER_YEARLY_DATE_WISE = "com.jainendra.event.model.YearlyScheduleOccurrenceProviderDateWise";
	String RECCURENCE_PROVIDER_PERIODIC = "com.jainendra.event.model.PeriodicScheduleOccurrenceProvider";
	String RECCURENCE_PROVIDER_NONE = "com.jainendra.event.model.NonRecurrentScheduleOccurrenceProvider";
}
