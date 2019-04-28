package com.jainendra.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Scanner;

import com.jainendra.event.ScheduleProcessor;
import com.jainendra.event.model.ISchedule;
import com.jainendra.event.util.Constant;

public class Test {
	// private static String test1 = "Every second Saturday of the month is a holiday|M:1#SAT:2|";
	private static String test1 = "Every third Saturday and monday of the month are holidays|M:1#SAT,MON:3|S:2019-03-31#E:2019-05-25";
	// private static String test1 = "Every Saturday and monday of the month are holidays|M:1#SAT,MON|";
	// private static String test1 = "Every 5 and 15 of the month are holidays|M:1#5,15|";
	// private static String test1 = "Every alternate 5 and 15 of the month are holidays|M:2#5,15|";
	private static String test2 = "Monthly phone bill reminder|M:1#10|";
	// private static String test3 = "My Anniversary|Y:1#09:02|";
	// private static String test3 = "My anual meeting|Y:1#05:12,21|";
	private static String test3 = "My anual meeting|Y:1#04:MON,TUE:1,3|";
	private static String test4 = "Weekly team catch-up|W:1#TUE,THU|";
	private static String test5 = "Monthly hospital visit|M:1#SUN:1,3|";
	private static String test6 = "School reunion|N#2019:12:02|";
	private static String test7 = "Sprint ends on alternate wednesday|W:2#WED|";
	private static String test8 = "Periodic credit card bill payment|O:1#M:10:2|";
	private static String test9 = "Quarterly meeting|O:1#Q:5|";

	public static void main(String[] args) {
		if(args.length <= 0){
			System.out.println("Usage: Please provide inputFilePath");
			testAll();
		}else{
			String inputFilePath = args[0];
			File file = new File(inputFilePath);
			if(file.exists() && file.isFile()){
				executeTests(file);
			}else{
				System.out.println("outputFile[" + file + "] does not exist. Please check third line of input.txt");
			}
		}
	}

	public static void executeTests(File file){
		Scanner sc;
		try {
			StringBuilder resultBuilder = new StringBuilder();
			sc = new Scanner(new FileInputStream(file));
			String startDateStr = sc.nextLine();
			LocalDate fromDate = LocalDate.parse(startDateStr.trim());
			System.out.println(fromDate);
			String noOfOccurStr = sc.nextLine();
			int noOfOccur = Integer.parseInt(noOfOccurStr.trim());
			System.out.println(noOfOccur);
			String outputFilePath = sc.nextLine();
			File outputFile = new File(outputFilePath);
			System.out.println(outputFilePath);
			while (sc.hasNext()) {
				try {
					testEvent(sc.nextLine(), fromDate, noOfOccur, resultBuilder);
				} catch (Exception e) {

				}
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
			bw.write(resultBuilder.toString());
			sc.close();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void testAll() {
		StringBuilder resultBuilder = new StringBuilder();
		LocalDate fromDate = LocalDate.now();
		int noOfOccur = 5;
		testEvent(test1, fromDate, noOfOccur, resultBuilder);
		testEvent(test2, fromDate, noOfOccur, resultBuilder);
		testEvent(test3, fromDate, noOfOccur, resultBuilder);
		testEvent(test4, fromDate, noOfOccur, resultBuilder);
		testEvent(test5, fromDate, noOfOccur, resultBuilder);
		testEvent(test6, fromDate, noOfOccur, resultBuilder);
		testEvent(test7, fromDate, noOfOccur, resultBuilder);
		testEvent(test8, fromDate, noOfOccur, resultBuilder);
		testEvent(test9, fromDate, noOfOccur, resultBuilder);
		System.out.println(resultBuilder.toString());
	}

	public static void testEvent(String eventStr,LocalDate fromDate, int noOfOccur, StringBuilder resultBuilder) {
		ISchedule schedule = ScheduleProcessor.scheduleFor(eventStr);
		resultBuilder.append("###########################################################" + Constant.SEPARATOR_LINE);
		resultBuilder.append("#################Executetion of Event<" + schedule.getName() +"> starts #################" + Constant.SEPARATOR_LINE);
		resultBuilder.append("###########################################################" + Constant.SEPARATOR_LINE);
		resultBuilder.append(Constant.SEPARATOR_LINE);
		testStartDate(schedule, resultBuilder);
		testEndDate(schedule, resultBuilder);
		testOccurrencesFrom(schedule, fromDate, noOfOccur, resultBuilder);
		testOccurrences(schedule, noOfOccur, resultBuilder);
		testAllOccurrences(schedule, resultBuilder);
		testNoOfOccurences(schedule, resultBuilder);
		resultBuilder.append("###########################################################" + Constant.SEPARATOR_LINE);
		resultBuilder.append("#################Executetion of Event<" + schedule.getName() +"> ends #################" + Constant.SEPARATOR_LINE);
		resultBuilder.append("###########################################################" + Constant.SEPARATOR_LINE);
		resultBuilder.append(Constant.SEPARATOR_LINE);
		resultBuilder.append(Constant.SEPARATOR_LINE);
	}

	public static void testOccurrencesFrom(ISchedule schedule,
			LocalDate fromDate, int noOfOccur, StringBuilder resultBuilder) {
		Collection<LocalDate> occur = schedule.getOccurrencesFrom(fromDate,
				noOfOccur);
		resultBuilder.append("Execution result of Schedule.getOccurrencesFrom(LocalDate startDate, int numberOfOccurences): from " + fromDate + Constant.SEPARATOR_LINE);
		resultBuilder.append(occur + Constant.SEPARATOR_LINE);
		resultBuilder.append(Constant.SEPARATOR_LINE);
	}

	public static void testOccurrences(ISchedule schedule, int noOfOccur, StringBuilder resultBuilder) {
		Collection<LocalDate> occur = schedule.getOccurrences(
				noOfOccur);
		resultBuilder.append("Execution result of Schedule.getOccurrencesFrom(int limitNumberOfOccurences): from Today" + Constant.SEPARATOR_LINE);
		resultBuilder.append(occur + Constant.SEPARATOR_LINE);
		resultBuilder.append(Constant.SEPARATOR_LINE);
	}

	public static void testAllOccurrences(ISchedule schedule, StringBuilder resultBuilder) {
		Collection<LocalDate> occur = schedule.getAllOccurrences();
		resultBuilder.append("Execution result of Schedule.getAllOccurrences() >> null represents unbounded result" + Constant.SEPARATOR_LINE);
		resultBuilder.append(occur + Constant.SEPARATOR_LINE);
		resultBuilder.append(Constant.SEPARATOR_LINE);
	}

	public static void testNoOfOccurences(ISchedule schedule, StringBuilder resultBuilder) {
		int noOfOccur = schedule.getNumberOfOccurences();
		resultBuilder.append("Execution result of Schedule.getNumberOfOccurences()" + Constant.SEPARATOR_LINE);
		resultBuilder.append("Event<" + schedule.getName() +"> happens " + noOfOccur + " times" + Constant.SEPARATOR_LINE);
		resultBuilder.append(Constant.SEPARATOR_LINE);
	}

	public static void testStartDate(ISchedule schedule, StringBuilder resultBuilder) {
		LocalDate startDate = schedule.startDate();
		resultBuilder.append("Execution result of Schedule.startDate()" + Constant.SEPARATOR_LINE);
		resultBuilder.append("Event starts at " + startDate + Constant.SEPARATOR_LINE);
		resultBuilder.append(Constant.SEPARATOR_LINE);
	}

	public static void testEndDate(ISchedule schedule, StringBuilder resultBuilder) {
		LocalDate endDate = schedule.endDate();
		resultBuilder.append("Execution result of Schedule.endDate()" + Constant.SEPARATOR_LINE);
		resultBuilder.append("Event ends at " + endDate + Constant.SEPARATOR_LINE);
		resultBuilder.append(Constant.SEPARATOR_LINE);
	}
}
