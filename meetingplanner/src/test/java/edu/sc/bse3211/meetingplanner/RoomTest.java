package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class RoomTest {
	private Meeting createMeeting(int month, int day, int start, int end, Room room, String description) {
		ArrayList<Person> attendees = new ArrayList<Person>();
		attendees.add(new Person("Namugga Martha"));
		return new Meeting(month, day, start, end, attendees, room, description);
	}

	@Test
	public void testDefaultConstructorSetsEmptyID() {
		Room room = new Room();

		assertEquals("Default room ID should be an empty string", "", room.getID());
	}

	@Test
	public void testConstructorSetsRoomID() {
		Room room = new Room("LLT6A");

		assertEquals("Constructor should set the room ID", "LLT6A", room.getID());
	}

	@Test
	public void testAddMeetingMakesRoomBusy() {
		Room room = new Room("LLT6B");
		Meeting meeting = createMeeting(3, 10, 9, 11, room, "Planning meeting");

		try {
			room.addMeeting(meeting);

			assertTrue("Room should be busy during the scheduled meeting", room.isBusy(3, 10, 9, 11));
		} catch(TimeConflictException e) {
			fail("Adding a valid meeting should not throw exception: " + e.getMessage());
		}
	}

	@Test
	public void testGetMeetingReturnsAddedMeeting() {
		Room room = new Room("LLT3A");
		Meeting meeting = createMeeting(4, 12, 13, 15, room, "Design review");

		try {
			room.addMeeting(meeting);

			assertSame("getMeeting should return the same meeting object that was added",
					meeting, room.getMeeting(4, 12, 0));
		} catch(TimeConflictException e) {
			fail("Adding a valid meeting should not throw exception: " + e.getMessage());
		}
	}

	@Test
	public void testRemoveMeetingMakesRoomAvailable() {
		Room room = new Room("LLT2C");
		Meeting meeting = createMeeting(5, 8, 10, 12, room, "Sprint review");

		try {
			room.addMeeting(meeting);
			room.removeMeeting(5, 8, 0);

			assertFalse("Room should not be busy after the meeting is removed", room.isBusy(5, 8, 10, 12));
		} catch(TimeConflictException e) {
			fail("Adding or checking a valid meeting should not throw exception: " + e.getMessage());
		}
	}

	@Test
	public void testPrintAgendaForDayIncludesMeeting() {
		Room room = new Room("LAB2");
		Meeting meeting = createMeeting(6, 14, 8, 10, room, "Testing workshop");

		try {
			room.addMeeting(meeting);

			String expected = "Agenda for 6/14:\n"
					+ "6/14, 8 - 10,LAB2: Testing workshop\n"
					+ "Attending: Namugga Martha\n";

			assertEquals("Daily agenda should include the scheduled meeting", expected, room.printAgenda(6, 14));
		} catch(TimeConflictException e) {
			fail("Adding a valid meeting should not throw exception: " + e.getMessage());
		}
	}

	@Test
	public void testAddMeetingThrowsRoomConflictMessageForOverlap() {
		Room room = new Room("LLT6A");
		Meeting first = createMeeting(7, 20, 9, 11, room, "Morning meeting");
		Meeting overlapping = createMeeting(7, 20, 10, 12, room, "Overlapping meeting");

		try {
			room.addMeeting(first);
			room.addMeeting(overlapping);
			fail("Overlapping room meeting should throw a TimeConflictException");
		} catch(TimeConflictException e) {
			assertTrue("Exception should identify the room with the conflict",
					e.getMessage().contains("Conflict for room LLT6A"));
			assertTrue("Exception should describe the existing conflicting meeting",
					e.getMessage().contains("Morning meeting"));
		}
	}

	@Test
	public void testIsBusyThrowsExceptionForInvalidTime() {
		Room room = new Room("LLT6B");

		try {
			room.isBusy(8, 5, 14, 10);
			fail("Checking an invalid time range should throw a TimeConflictException");
		} catch(TimeConflictException e) {
			assertEquals("Exception should explain that the meeting time range is invalid",
					"Meeting starts before it ends.", e.getMessage());
		}
	}

	@Test
	public void testAddMeetingThrowsExceptionForInvalidDay() {
		Room room = new Room("LLT6A");
		Meeting meeting = createMeeting(2, 35, 9, 10, room, "Invalid day meeting");

		try {
			room.addMeeting(meeting);
			fail("Adding a meeting with an invalid day should throw a TimeConflictException");
		} catch(TimeConflictException e) {
			assertTrue("Exception should identify the room with the invalid meeting",
					e.getMessage().contains("Conflict for room LLT6A"));
			assertTrue("Exception should explain that the day does not exist",
					e.getMessage().contains("Day does not exist."));
		}
	}

	@Test
	public void testAddMeetingThrowsExceptionForInvalidMonth() {
		Room room = new Room("LLT6B");
		Meeting meeting = createMeeting(13, 10, 9, 10, room, "Invalid month meeting");

		try {
			room.addMeeting(meeting);
			fail("Adding a meeting with an invalid month should throw a TimeConflictException");
		} catch(TimeConflictException e) {
			assertTrue("Exception should identify the room with the invalid meeting",
					e.getMessage().contains("Conflict for room LLT6B"));
			assertTrue("Exception should explain that the month does not exist",
					e.getMessage().contains("Month does not exist."));
		}
	}

	@Test
	public void testAddMeetingThrowsExceptionForInvalidStartHour() {
		Room room = new Room("LLT3A");
		Meeting meeting = createMeeting(3, 12, -1, 10, room, "Invalid start hour meeting");

		try {
			room.addMeeting(meeting);
			fail("Adding a meeting with an invalid start hour should throw a TimeConflictException");
		} catch(TimeConflictException e) {
			assertTrue("Exception should identify the room with the invalid meeting",
					e.getMessage().contains("Conflict for room LLT3A"));
			assertTrue("Exception should explain that the hour is illegal",
					e.getMessage().contains("Illegal hour."));
		}
	}

	@Test
	public void testAddMeetingThrowsExceptionForInvalidEndHour() {
		Room room = new Room("LLT2C");
		Meeting meeting = createMeeting(3, 12, 9, 24, room, "Invalid end hour meeting");

		try {
			room.addMeeting(meeting);
			fail("Adding a meeting with an invalid end hour should throw a TimeConflictException");
		} catch(TimeConflictException e) {
			assertTrue("Exception should identify the room with the invalid meeting",
					e.getMessage().contains("Conflict for room LLT2C"));
			assertTrue("Exception should explain that the hour is illegal",
					e.getMessage().contains("Illegal hour."));
		}
	}

	@Test
	public void testAddMeetingThrowsExceptionWhenStartEqualsEnd() {
		Room room = new Room("LAB2");
		Meeting meeting = createMeeting(4, 15, 10, 10, room, "Zero length meeting");

		try {
			room.addMeeting(meeting);
			fail("Adding a meeting with the same start and end time should throw a TimeConflictException");
		} catch(TimeConflictException e) {
			assertTrue("Exception should identify the room with the invalid meeting",
					e.getMessage().contains("Conflict for room LAB2"));
			assertTrue("Exception should explain that the meeting time range is invalid",
					e.getMessage().contains("Meeting starts before it ends."));
		}
	}
}
