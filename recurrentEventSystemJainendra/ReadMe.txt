###################################################################################################################################
                                  PROBLEM STATEMENT
###################################################################################################################################
Requirement:
    Design and implement a library to represent a schedule of recurring events. 
	The pseudo-code below:
	shows typical usage of the library:
		recur:Schedule = Schedule.for(<Schedule specification>)
		print("Schedule: "+ recur.getAllOccurrences() )
		Features, to be supported, by the library, are summarized in the following APIs -
		interface Schedule {
			startDate():date;
			endDate():date;
			getOccurrences(limitNumberOfOccurences: int): list of dates
			getOccurrencesFrom(startDate:Date, numberOfOccurences:int):list of dates;
			getAllOccurrences():list of dates;
			getNumberOfOccurences():int // return #of occurrences for bounded schedules.
		}
		
    The behaviour of the library is similar to scheduling a recurring event on a calendar with start date, end date, and a repeat schedule.

	Following are some of the examples of different kinds of recurring schedule use cases that need to be supported:
		1. Every second Saturday is a holiday.
		2. Remind me to pay my phone bill on the 10th of every month.
		3. 2nd Sep is my anniversary.
		4. Every Tuesday and Thursday is team catch-up.
		5. Every 1st and 3rd Sunday, I need to visit the hospital.
		6. 2nd Dec 2017 we have a school reunion. (non-recurrent event)
		7. Every alternate Wednesday our sprint ends.
		8. Once in 2 months, on the 10th I need to pay my credit card bill.
		9. Once in every quarter, 5th we have shareholders’ meeting.
		
Tasks To Be Implemented:
		1. The first step is to design a schedule format for providing schedules (listed above) and write
		the DSL or parser for the same. Schedule format need not be free-form text. It can be any
		format you are comfortable with, e.g. comma separated, JSON, YAML, your language of
		choice, etc. An example of using a map of key-value pairs with JSON format is as follows -
			● For Schedule: Stand-up Everyday , input should be given as
			{eventName:"Stand up", repeat:"daily"}
			● For Schedule: Anniversary every year , input should be given as
			{eventName:"Anniversary", date:"2nd", month:"Sep", repeat:"yearly"}
			● For Schedule: Weekly Catch-up , input should be given as
			{eventName:"Catch-up", day:"Tuesday, Thursday", repeat:"weekly"}
			● For Schedule: Monthly Second Saturday Holiday , input should be given as
			{eventName:"Holiday", ordinal:"2", day:"Saturday", repeat:"monthly"}
			
		2. In the next step, implement the API that takes the schedule specification, designed in the
		previous step, and produces a Schedule object
		schedule:Schedule = Schedule.for(' {eventName:"Stand up", repeat:"daily"}')
		
		3. Now implement rest of the API of the Schedule interface.
			print("Stand-ups are on: "+ schedule.getAllOccurrences())
			print("First 3 Stand-ups are on: "+ schedule.getOccurrences(3))
			print("Stand-ups start from: "+ schedule.startDate())
			print("Stand-ups end on: "+ schedule.endDate())
			print("Number of meetings: "+ schedule.getNumberOfOccurences())
			
		4. Implement unit tests to showcase the various kinds of schedules (9 schedules are shown in
		the previous section), supported by your library. The program must not accept any console
		input.

###################################################################################################################################
                                  ABOUT THE SOLUTION 
###################################################################################################################################
About the project:
1> It is an eclipse project which can be imported in eclipse to build and run.
2> It has build.xml also which can be used to build and run the program on command line.
3> It has following files & directories:
   build.xml:- It is an ant build file to build the project using ant.
   src:- It is directory containing the source code.
   docs:- It contains following documents:
         InputFormat.docs describing schedule specification.
         AboutInputFile.docs describing about the contents of input file.
         BuildAndRun.docs describing about building and running the project. 
   inputFile:- It is directory containing sample input files.
   outputFile:- It is directory containing sample output files.

Following are the steps to run and build this project:
1> Go through InputFormat.docs to have an idea about schedule DSL.
2> Go through AboutInputFile.docs to have an idea about input file contents.
3> Go through BuildAndRun.docs to build and run the project.
4> input.txt present in inputFiles directory contains all the test cases mentioned in assignment pdf.


About Source Code:
Following are some points about design of the project:
1> Facade Pattern: ScheduleProcessor is facade class which can be used to interact with library from outside.
   It gives the schedule object as follows:
         ISchedule schedule = ScheduleProcessor.scheduleFor(eventStr)

2> Factory Method Pattern: ScheduleProcessor uses ScheduleFactory to create IScheduleOccurrenceProvider object.

3> Bridge Pattern: 
    > ISchedule uses IScheduleOccurrenceProvider provide separate out the schedule calculation logic from ISchedule object.
    > IScheduleOccurrenceProvider uses ScheduleSpec object while calculations.
    > ScheduleSpec contains Recurrence object which represents specific types of schedule, ie, weekly, monthly.
