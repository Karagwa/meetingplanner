# Assigned Class Test Summary

This document explains the purpose of the three assigned production classes and the unit tests created for them.

## Meeting.java

`Meeting` represents one meeting, holiday, vacation day, or blocked time period in the planner system.

Its main responsibility is to store meeting information:

- month and day
- start and end time
- attendees
- room
- description

It also allows attendees to be added or removed and provides a `toString()` method for printing meeting details.

Important note: `Meeting` does not validate whether dates or times are legal. For example, it does not reject February 35 or a start time after the end time. That validation is handled by `Calendar`.

### MeetingTest.java

The tests created for `Meeting` check that the class stores and returns meeting data correctly.

- `testFullDayConstructorSetsDateAndAllDayTimes`
  - Checks that `new Meeting(month, day)` creates a full-day meeting from hour `0` to `23`.

- `testFullDayConstructorWithDescriptionSetsDescription`
  - Checks that `new Meeting(month, day, description)` creates a full-day meeting and stores the description.

- `testTimedConstructorSetsDateAndTimes`
  - Checks that `new Meeting(month, day, start, end)` stores the date and time values correctly.

- `testDetailedConstructorSetsAllMeetingFields`
  - Checks that the full constructor stores the date, time, attendee list, room, and description.

- `testSettersUpdateMeetingFields`
  - Checks that the setter methods update the meeting fields correctly.

- `testAddAndRemoveAttendee`
  - Checks that attendees can be added to and removed from a meeting.

- `testToStringFormatsMeetingDetails`
  - Checks that `toString()` produces the expected formatted meeting details.

## Organization.java

`Organization` stores the default employees and rooms available in the meeting planner.

Its main responsibility is to initialize the system with known people and rooms, then allow the program to search for a person or room by name or ID.

Default employees:

- Namugga Martha
- Shema Collins
- Acan Brenda
- Kazibwe Julius
- Kukunda Lynn

Default rooms:

- LLT6A
- LLT6B
- LLT3A
- LLT2C
- LAB2

It also throws an exception if a requested employee or room does not exist.

### OrganizationTest.java

The tests created for `Organization` check both normal lookups and invalid lookup requests.

- `testDefaultConstructorCreatesExpectedEmployees`
  - Checks that the organization creates the expected five default employees.

- `testDefaultConstructorCreatesExpectedRooms`
  - Checks that the organization creates the expected five default rooms.

- `testGetEmployeeReturnsMatchingEmployee`
  - Checks that searching for an existing employee returns the correct `Person`.

- `testGetRoomReturnsMatchingRoom`
  - Checks that searching for an existing room returns the correct `Room`.

- `testGetEmployeeThrowsExceptionForUnknownEmployee`
  - Checks that searching for an employee who does not exist throws an exception with the expected message.

- `testGetRoomThrowsExceptionForUnknownRoom`
  - Checks that searching for a room that does not exist throws an exception with the expected message.

## Room.java

`Room` represents a meeting room in the planner system.

Each room has:

- a room ID
- its own calendar

The room delegates scheduling work to its internal `Calendar`. This means `Room` can add meetings, check whether it is busy, print its agenda, retrieve meetings, and remove meetings.

`Room` also wraps calendar conflict errors with a room-specific message, such as `Conflict for room LLT6A`.

### RoomTest.java

The tests created for `Room` check that a room correctly uses its calendar and reports room-specific conflicts.

- `testDefaultConstructorSetsEmptyID`
  - Checks that the default constructor creates a room with an empty ID.

- `testConstructorSetsRoomID`
  - Checks that the constructor stores the given room ID.

- `testAddMeetingMakesRoomBusy`
  - Checks that after a meeting is added, the room is busy during that meeting time.

- `testGetMeetingReturnsAddedMeeting`
  - Checks that a meeting added to a room can be retrieved from that room.

- `testRemoveMeetingMakesRoomAvailable`
  - Checks that after removing a meeting, the room is no longer busy at that time.

- `testPrintAgendaForDayIncludesMeeting`
  - Checks that the daily room agenda includes the scheduled meeting details.

- `testAddMeetingThrowsRoomConflictMessageForOverlap`
  - Checks that overlapping meetings in the same room throw a `TimeConflictException` and that the message identifies the room.

- `testIsBusyThrowsExceptionForInvalidTime`
  - Checks that checking availability with an invalid time range throws the expected exception.

## Calendar.java

`Calendar` is the core scheduling class. It stores meetings by month and day, checks for time conflicts, verifies invalid dates and times, clears schedules, and removes meetings.

This class is the main engine behind the planner system because `Person` and `Room` both delegate to it.

### CalendarTest.java

The tests created for `Calendar` check normal scheduling, conflict detection, invalid input handling, and schedule maintenance.

- `testAddMeeting_holiday`
  - Checks that a full-day meeting such as a holiday can be added successfully and makes the day busy.

- `testAddMeeting_normalTime`
  - Checks that a normal timed meeting can be added and later detected as busy.

- `testAddMeeting_conflict`
  - Checks that two meetings at the exact same time cause a `TimeConflictException`.

- `testAddMeeting_overlap`
  - Checks that overlapping meetings are rejected.

- `testAddMeeting_backToBack`
  - Checks how the system handles meetings that start when another one ends.

- `testAddMeeting_invalidTime`
  - Checks that invalid times such as hour 25 are rejected.

- `testAddMeeting_invalidMonth`
  - Checks that invalid months such as month 13 are rejected.

- `testAddMeeting_invalidDay`
  - Checks behavior when an invalid day such as February 30 is used.

- `testAddMeeting_invalidTimeRange`
  - Checks that a meeting where the start time is after the end time is rejected.

- `testClearSchedule`
  - Checks that clearing a schedule removes meetings for that day.

- `testRemoveMeeting`
  - Checks that removing a meeting makes the time available again.

- `testDecemberBug`
  - Checks the December case to expose a bug in the calendar implementation.

## Person.java

`Person` represents an employee in the meeting planner. Each person has a calendar and can have meetings added, removed, checked for availability, and printed as an agenda.

This class wraps calendar behavior and adds person-specific conflict messages.

### PersonTest.java

The tests created for `Person` check basic identity, availability, agenda printing, meeting removal, and conflict reporting.

- `testGetName`
  - Checks that the person's name is stored and returned correctly.

- `testAddMeeting_success`
  - Checks that adding a meeting makes the person busy during that time.

- `testAddMeeting_conflictMessageIncludesName`
  - Checks that when a conflict occurs, the exception message includes the person's name.

- `testPrintAgenda_day`
  - Checks that the daily agenda contains the meeting description.

- `testPrintAgenda_month`
  - Checks that the monthly agenda contains the meeting description.

- `testIsBusy_falseWhenFree`
  - Checks that the person is reported as free when no meetings exist.

- `testRemoveMeeting`
  - Checks that removing a meeting makes the time available again.

- `testGetMeeting`
  - Checks that a meeting can be retrieved from the person's calendar.

## TimeConflictException.java

`TimeConflictException` is the custom exception used throughout the planner when scheduling fails because of invalid input or a conflict.

### TimeConflictExceptionTest.java

The tests created for `TimeConflictException` check that each constructor stores the expected message or cause.

- `testDefaultConstructor`
  - Checks that the default constructor creates an exception with a null message.

- `testMessageConstructor`
  - Checks that a message passed to the constructor is stored correctly.

- `testCauseConstructor`
  - Checks that a cause passed to the constructor is stored correctly.

- `testMessageAndCauseConstructor`
  - Checks that both the message and cause are stored correctly.

- `testFullConstructor`
  - Checks the constructor that takes message, cause, suppression, and writable stack trace values.

## Why Invalid Date Tests Are Not In These Three Files

The assignment asks for both normal and illegal input tests. For this project, illegal date and time validation is mainly handled by `Calendar`, not by `Meeting`, `Organization`, or `Room`.

For example:

- February 35 is rejected by `Calendar.checkTimes()`.
- Start time after end time is rejected by `Calendar.checkTimes()`.
- Overlapping bookings are rejected by `Calendar.addMeeting()`.

Because `Room` uses `Calendar`, `RoomTest` includes invalid time and overlapping meeting tests. However, direct invalid date tests are more appropriate in `CalendarTest`.

## Test Command

Run all tests from the project root:

```powershell
mvn test
```

