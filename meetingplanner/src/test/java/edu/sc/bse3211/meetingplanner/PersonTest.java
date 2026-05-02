package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {

    private Person person;

    @Before
    public void setUp() {
        person = new Person("Treasure");
    }

    // Basic functionality tests

    @Test
    public void testGetName() {
        assertEquals("Treasure", person.getName());
    }

    @Test
    public void testAddMeeting_success() {
        try {
            Meeting meeting = new Meeting(5, 10, 10, 12);

            person.addMeeting(meeting);

            boolean busy = person.isBusy(5, 10, 10, 12);

            assertTrue("Person should be busy after adding meeting", busy);

        } catch (TimeConflictException e) {
            fail("Should not throw exception");
        }
    }

    
    //CONFLICT HANDLING

    @Test
    public void testAddMeeting_conflictMessageIncludesName() {
        try {
            Meeting m1 = new Meeting(5, 10, 10, 12);
            m1.setDescription("Meeting 1");
            Meeting m2 = new Meeting(5, 10, 11, 13);
            m2.setDescription("Meeting 2");

            person.addMeeting(m1);
            person.addMeeting(m2);

            fail("Should throw TimeConflictException");

        } catch (TimeConflictException e) {
            assertTrue("Error message should include person's name",
                    e.getMessage().contains("Treasure"));
        }
    }

    
    // AGENDA TESTS
    

    @Test
    public void testPrintAgenda_day() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            Room room = new Room("Conference Room A");
            attendees.add(person);
            Meeting meeting = new Meeting(5, 10, 10, 12, attendees, room, "Team Meeting");
            person.addMeeting(meeting);
            String agenda = person.printAgenda(5, 10);

            assertTrue("Agenda should contain meeting description",
                    agenda.contains("Team Meeting"));

        } catch (TimeConflictException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void testPrintAgenda_month() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            Room room = new Room("Conference Room A");
            attendees.add(person);
            Meeting meeting = new Meeting(5, 10, 10, 12, attendees, room, "Monthly Review");
            person.addMeeting(meeting);

            String agenda = person.printAgenda(5);

            assertTrue("Month agenda should contain meeting",
                    agenda.contains("Monthly Review"));

        } catch (TimeConflictException e) {
            fail("Should not throw exception");
        }
    }

    
    // BUSY CHECK
    

    @Test
    public void testIsBusy_falseWhenFree() {
        try {
            boolean busy = person.isBusy(5, 10, 10, 12);

            assertFalse("Person should not be busy if no meetings exist", busy);

        } catch (TimeConflictException e) {
            fail("Should not throw exception");
        }
    }

    
    // REMOVE MEETING
    

    @Test
    public void testRemoveMeeting() {
        try {
            Meeting meeting = new Meeting(5, 10, 10, 12);
            person.addMeeting(meeting);

            person.removeMeeting(5, 10, 0);

            boolean busy = person.isBusy(5, 10, 10, 12);

            assertFalse("Meeting should be removed from person's calendar", busy);

        } catch (TimeConflictException e) {
            fail("Should not throw exception");
        }
    }

    // =========================
    //  GET MEETING
    // =========================

    @Test
    public void testGetMeeting() {
        try {
            Meeting meeting = new Meeting(5, 10, 10, 12);
            meeting.setDescription("Check");

            person.addMeeting(meeting);

            Meeting retrieved = person.getMeeting(5, 10, 0);

            assertEquals("Retrieved meeting should match inserted one",
                    "Check", retrieved.getDescription());

        } catch (TimeConflictException e) {
            fail("Should not throw exception");
        }
    }
}