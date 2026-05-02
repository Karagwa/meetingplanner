# BSE3211 UNIT TESTING – INFORMAL TEST PLAN

1. Objective

The purpose of this testing exercise is to verify the correctness, robustness, and reliability of the Meeting Planner system. The system supports scheduling meetings, booking vacations, checking availability, and printing agendas for both people and rooms.

The tests aim to confirm:
- Correct handling of valid operations
- Proper rejection or handling of invalid inputs
- Accurate detection of scheduling conflicts
- Correct behavior of exception handling
- System stability under edge cases and boundary conditions

2. Scope of Testing

The following components were tested:
- `Calendar` – core scheduling logic
- `Person` – user-level interaction with calendar
- `Meeting` – meeting representation and data handling
- `Room` – room-level calendar delegation
- `Organization` – lookup of default employees and rooms
- `TimeConflictException` – custom exception handling

3. Test Strategy

Unit testing with JUnit; white-box tests exercised the classes directly. The approach covered:

A. Normal Functionality Tests
- Adding valid meetings
- Checking availability
- Printing agendas
- Removing meetings

B. Boundary Value Tests
- Back-to-back meetings (end time == next start time)
- Full-day meetings (0–23)
- Edge time values (0 and 23)

C. Invalid Input Tests
- Invalid days (e.g., Feb 30)
- Invalid months (e.g., month 13)
- Invalid time ranges (start > end)

D. Conflict Detection Tests
- Overlapping meetings
- Double booking same time slot
- Vacation (full-day) vs meeting conflicts

E. Exception Handling Tests
- Ensure `TimeConflictException` is thrown for conflicts/invalid input
- Verify exception messages include useful context (person/room details)

4. Test Cases Summary (by test file)

- `CalendarTest.java`
  - `testAddMeeting_holiday` — add full-day holiday, day becomes busy
  - `testAddMeeting_normalTime` — add timed meeting, isBusy returns true
  - `testAddMeeting_conflict` — same time twice throws `TimeConflictException`
  - `testAddMeeting_overlap` — overlapping times throw exception
  - `testAddMeeting_backToBack` — current implementation treats back-to-back as conflict
  - `testAddMeeting_invalidTime` — invalid hour (25) rejected
  - `testAddMeeting_invalidMonth` — month 13 rejected
  - `testAddMeeting_invalidDay` — behavior observed for invalid day (Feb 30)
  - `testAddMeeting_invalidTimeRange` — start after end rejected
  - `testClearSchedule` — clearing a day removes meetings
  - `testRemoveMeeting` — remove meeting then slot freed
  - `testDecemberBug` — regression test for December handling

- `PersonTest.java`
  - `testGetName` — name stored correctly
  - `testAddMeeting_success` — person becomes busy after adding meeting
  - `testAddMeeting_conflictMessageIncludesName` — conflict message contains person name
  - `testPrintAgenda_day` — daily agenda contains meeting description
  - `testPrintAgenda_month` — monthly agenda contains meeting description
  - `testIsBusy_falseWhenFree` — person free when no meetings
  - `testRemoveMeeting` — removal frees time
  - `testGetMeeting` — retrieve meeting and description

- `RoomTest.java` (tests cover)
  - default constructor and ID behavior
  - `testAddMeetingMakesRoomBusy` — room busy after meeting
  - `testGetMeetingReturnsAddedMeeting` — retrieval from room
  - `testRemoveMeetingMakesRoomAvailable` — removal frees room
  - `testPrintAgendaForDayIncludesMeeting` — agenda contains meeting details
  - `testAddMeetingThrowsRoomConflictMessageForOverlap` — room-specific conflict message
  - `testIsBusyThrowsExceptionForInvalidTime` — invalid time handling

- `MeetingTest.java` (tests cover)
  - constructors: full-day, timed, detailed
  - getters/setters
  - adding/removing attendees
  - `toString()` formatting

- `OrganizationTest.java` (tests cover)
  - default employees and rooms present
  - lookups return correct `Person`/`Room`
  - unknown lookups throw exceptions with clear messages

- `TimeConflictExceptionTest.java`
  - `testDefaultConstructor` — null message
  - `testMessageConstructor` — message stored
  - `testCauseConstructor` — cause stored
  - `testMessageAndCauseConstructor` — message + cause
  - `testFullConstructor` — full-parameter constructor behavior

5. Key Findings

- Finding 1: Invalid Date Handling is Inconsistent
  - Observation: invalid calendar dates (e.g., Feb 30) are not consistently rejected; code sometimes uses "Day does not exist" placeholders.
  - Impact: invalid dates may accept bookings; weakens logical integrity.
  - Severity: High
  - Recommendation: implement strict date validation per month or use calendar-aware types.

- Finding 2: Null Field Vulnerability in `Meeting`
  - Observation: `Meeting.toString()` assumes `room` and `attendees` are non-null; tests that create minimal `Meeting` objects caused `NullPointerException` during agenda printing.
  - Impact: crashes when printing agendas for partially-populated meetings.
  - Severity: High
  - Recommendation: add null checks or default values; or ensure tests always supply room/attendees.

- Finding 3: Back-to-Back Meeting Handling
  - Observation: end time == next start is treated as conflict (inclusive boundary logic).
  - Impact: blocks valid scheduling scenarios.
  - Severity: Medium
  - Recommendation: treat end as exclusive (use < rather than <= when checking overlap).

- Finding 4: Over-reliance on String-based Logic
  - Observation: code uses string markers like "Day does not exist" to control logic.
  - Impact: fragile and error-prone.
  - Severity: Medium
  - Recommendation: use boolean flags or explicit validation states instead.

- Finding 5: Exception Messages Are Informative (Positive)
  - Observation: `TimeConflictException` messages include context (room/person, conflicting times).
  - Impact: helps debugging and user feedback.
  - Severity: Positive feature

6. Overall Assessment

The Meeting Planner implements core scheduling and conflict detection and is test-covered for many normal cases. However, edge cases around invalid dates, null fields, and boundary comparisons reduce robustness. Addressing the key findings will improve reliability.

7. Conclusion

The system is suitable for basic scheduling but requires work on input validation, null-safety, and boundary semantics to be production-ready.

8. Test Files (location)

All tests referenced are in `src/test/java/edu/sc/bse3211/meetingplanner/` inside the project root. Run them with:

```powershell
mvn test
```

---

*Document created from existing test sources and observed behavior during unit test review.*
