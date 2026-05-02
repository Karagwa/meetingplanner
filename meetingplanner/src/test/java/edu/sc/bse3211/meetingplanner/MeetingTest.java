package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class MeetingTest {
	@Test
	public void testFullDayConstructorSetsDateAndAllDayTimes() {
		Meeting meeting = new Meeting(2, 16);

		assertEquals("Month should be set by the constructor", 2, meeting.getMonth());
		assertEquals("Day should be set by the constructor", 16, meeting.getDay());
		assertEquals("Full-day meetings should start at hour 0", 0, meeting.getStartTime());
		assertEquals("Full-day meetings should end at hour 23", 23, meeting.getEndTime());
	}

	@Test
	public void testFullDayConstructorWithDescriptionSetsDescription() {
		Meeting meeting = new Meeting(2, 16, "Janan Luwum Day");

		assertEquals("Month should be set by the constructor", 2, meeting.getMonth());
		assertEquals("Day should be set by the constructor", 16, meeting.getDay());
		assertEquals("Full-day meetings should start at hour 0", 0, meeting.getStartTime());
		assertEquals("Full-day meetings should end at hour 23", 23, meeting.getEndTime());
		assertEquals("Description should be set by the constructor", "Janan Luwum Day", meeting.getDescription());
	}

	@Test
	public void testTimedConstructorSetsDateAndTimes() {
		Meeting meeting = new Meeting(3, 12, 9, 11);

		assertEquals("Month should be set by the constructor", 3, meeting.getMonth());
		assertEquals("Day should be set by the constructor", 12, meeting.getDay());
		assertEquals("Start time should be set by the constructor", 9, meeting.getStartTime());
		assertEquals("End time should be set by the constructor", 11, meeting.getEndTime());
	}

	@Test
	public void testDetailedConstructorSetsAllMeetingFields() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		Person attendee = new Person("Namugga Martha");
		Room room = new Room("LLT6A");
		attendees.add(attendee);

		Meeting meeting = new Meeting(4, 20, 10, 12, attendees, room, "Project update");

		assertEquals("Month should be set by the constructor", 4, meeting.getMonth());
		assertEquals("Day should be set by the constructor", 20, meeting.getDay());
		assertEquals("Start time should be set by the constructor", 10, meeting.getStartTime());
		assertEquals("End time should be set by the constructor", 12, meeting.getEndTime());
		assertSame("Constructor should store the supplied attendee list", attendees, meeting.getAttendees());
		assertSame("Constructor should store the supplied room", room, meeting.getRoom());
		assertEquals("Description should be set by the constructor", "Project update", meeting.getDescription());
	}

	@Test
	public void testSettersUpdateMeetingFields() {
		Meeting meeting = new Meeting();
		Room room = new Room("LAB2");

		meeting.setMonth(5);
		meeting.setDay(9);
		meeting.setStartTime(14);
		meeting.setEndTime(16);
		meeting.setRoom(room);
		meeting.setDescription("Testing workshop");

		assertEquals("setMonth should update the month", 5, meeting.getMonth());
		assertEquals("setDay should update the day", 9, meeting.getDay());
		assertEquals("setStartTime should update the start time", 14, meeting.getStartTime());
		assertEquals("setEndTime should update the end time", 16, meeting.getEndTime());
		assertSame("setRoom should update the room", room, meeting.getRoom());
		assertEquals("setDescription should update the description", "Testing workshop", meeting.getDescription());
	}

	@Test
	public void testAddAndRemoveAttendee() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		Person first = new Person("Shema Collins");
		Person second = new Person("Acan Brenda");
		Meeting meeting = new Meeting(6, 3, 8, 10, attendees, new Room("LLT3A"), "Planning");

		meeting.addAttendee(first);
		meeting.addAttendee(second);

		assertEquals("Both attendees should be added", 2, meeting.getAttendees().size());
		assertTrue("First attendee should be present", meeting.getAttendees().contains(first));
		assertTrue("Second attendee should be present", meeting.getAttendees().contains(second));

		meeting.removeAttendee(first);

		assertEquals("One attendee should remain after removal", 1, meeting.getAttendees().size());
		assertFalse("Removed attendee should no longer be present", meeting.getAttendees().contains(first));
		assertTrue("Unremoved attendee should still be present", meeting.getAttendees().contains(second));
	}

	@Test
	public void testToStringFormatsMeetingDetails() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		attendees.add(new Person("Kazibwe Julius"));
		attendees.add(new Person("Kukunda Lynn"));
		Meeting meeting = new Meeting(7, 18, 13, 15, attendees, new Room("LLT2C"), "Design review");

		String expected = "7/18, 13 - 15,LLT2C: Design review\nAttending: Kazibwe Julius,Kukunda Lynn";

		assertEquals("toString should include date, time, room, description, and attendees", expected, meeting.toString());
	}
}
