package com.jainendra.test.junit;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jainendra.event.ScheduleProcessor;
import com.jainendra.event.model.ISchedule;

public class RecurrentEventTest {
	private int noOfOccurence;
	Object[] expected = new Object[2];
	Object[] actual = new Object[2];

	@Before
	public void setUp() throws Exception {
	}

	private void createActualResult(ISchedule schedule, LocalDate startDate, int noOfOcccurences){
		System.out.println("Schedule Start Date : " + schedule.startDate());
		System.out.println("Schedule End Date : " + schedule.endDate());
		Collection<LocalDate> occurrences = schedule.getOccurrences(noOfOcccurences);
		System.out.println(noOfOcccurences + " Occcurences : " + occurrences);
		actual[0] = occurrences.size();

		occurrences = schedule.getOccurrencesFrom(startDate, noOfOcccurences);
		System.out.println(noOfOcccurences + " Occcurences From " + startDate + " : " + occurrences);
		actual[1] = occurrences.size();

		occurrences = schedule.getAllOccurrences();
		System.out.println("All Occurrences : " + occurrences);

		int noOfOccur = schedule.getNumberOfOccurences();
		System.out.println("Number Of Occurences : " + noOfOccur);
		System.out.println("#########################################################");
		System.out.println("#########################################################");
		Assert.assertArrayEquals(expected, actual);
	}
	
	private void createExpectedResult(int noOfOccurence){
		expected[0] = noOfOccurence;
		expected[1] = noOfOccurence;
	}

	@Test
	public void shouldReturn2ndAnd4thSaturday() {
		String input = "Return2ndAnd4thSaturday|M:1#SAT:2,4";
		noOfOccurence = 5;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void every2ndSaturdayIsAHoliday() {
		String input = "Every second Saturday is a holiday|M:1#SAT:2|";
		noOfOccurence = 5;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void remindMe2PayThePhoneBillOn10ThOfEveryMonth() {
		String input = "Remind me to pay my phone bill on the 10th of every month|M:1#10|";
		noOfOccurence = 5;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void secondSeptIsMyAnniversary() {
		String input = "2nd Sep is my anniversary|Y:1#09:02|";
		noOfOccurence = 5;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void everyTueAndThuIsTeamCatchUp() {
		String input = "Every Tuesday and Thursday is team catch-up|W:1#TUE,THU|";
		noOfOccurence = 5;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void everyIstAnd3rdSundayINeed2VisitTheHospital() {
		String input = "Every 1st and 3rd Sunday, I need to visit the hospital|M:1#SUN:1,3|";
		noOfOccurence = 5;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void everyAltWedOurSprintEnds() {
		String input = "Once in every quarter, 5th we have shareholders’ meeting.|O:1#Q:5|";
		noOfOccurence = 5;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void onceIn2MonthsOnThe10thINeed2PayMyCreditCardBill() {
		String input = "Once in 2 months, on the 10th I need to pay my credit card bill|O:1#M:10:2|";
		noOfOccurence = 5;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void onceInEveryQuarter5ThWeHaveShareHoldersMeeting() {
		String input = "Once in every quarter, 5th we have shareholders’ meeting.|O:1#Q:5|";
		noOfOccurence = 5;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void on2ndDec2017WeHaveASchoolReunion() {
		String input = "2nd Dec 2017 we have a school reunion|N#2017:12:02|";
		noOfOccurence = 1;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void return2ndAnd4thSaturday() {
		String input = "Return2ndAnd4thSaturday|M:1#SAT:2,4|S:2019-06-21#E:2019-08-01";
		noOfOccurence = 3;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.parse("2019-06-21"), noOfOccurence);
	}

	@Test
	public void return2ndAnd4thSaturday1() {
		String input = "Return2ndAnd4thSaturday|M:1#SAT:2,4|S:2019-06-21#O:3";
		noOfOccurence = 3;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.parse("2019-06-21"), noOfOccurence);
	}

	@Test
	public void return2ndAnd4thSaturday2() {
		String input = "Return2ndAnd4thSaturday|M:1#SAT:2,4|E:2019-08-01";
		noOfOccurence = 5;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}

	@Test
	public void return2ndAnd4thSaturday3() {
		String input = "Return2ndAnd4thSaturday|M:1#SAT:2,4|S:2019-06-21";
		noOfOccurence = 8;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.parse("2019-06-21"), noOfOccurence);
	}

	@Test
	public void return2ndAnd4thSaturday4() {
		String input = "Return2ndAnd4thSaturday|M:1#SAT:2,4|O:8";
		noOfOccurence = 8;
		createExpectedResult(noOfOccurence);
		ISchedule schedule = ScheduleProcessor.scheduleFor(input);
		createActualResult(schedule, LocalDate.now(), noOfOccurence);
	}
}
