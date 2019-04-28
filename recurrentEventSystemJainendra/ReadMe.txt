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

2> Factory Pattern: ScheduleProcessor uses IScheduleFactory which is a factory to create ISchedule object.

3> Factory Method Pattern: ScheduleFactory internally uses factory method pattern to create IScheduleOccurrenceProvider object.

4> Bridge Pattern: 
    > ISchedule uses IScheduleOccurrenceProvider provide separate out the schedule calculation logic from ISchedule object.
    > IScheduleOccurrenceProvider uses ScheduleSpec object while calculations.
    > ScheduleSpec contains Recurrence object which represents specific types of schedule, ie, weekly, monthly.
