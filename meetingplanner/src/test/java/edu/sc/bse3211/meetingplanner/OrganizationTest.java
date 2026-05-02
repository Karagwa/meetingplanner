package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class OrganizationTest {
	@Test
	public void testDefaultConstructorCreatesExpectedEmployees() {
		Organization organization = new Organization();
		ArrayList<Person> employees = organization.getEmployees();

		assertEquals("Organization should create five default employees", 5, employees.size());
		assertEquals("First employee should match the default data", "Namugga Martha", employees.get(0).getName());
		assertEquals("Second employee should match the default data", "Shema Collins", employees.get(1).getName());
		assertEquals("Third employee should match the default data", "Acan Brenda", employees.get(2).getName());
		assertEquals("Fourth employee should match the default data", "Kazibwe Julius", employees.get(3).getName());
		assertEquals("Fifth employee should match the default data", "Kukunda Lynn", employees.get(4).getName());
	}

	@Test
	public void testDefaultConstructorCreatesExpectedRooms() {
		Organization organization = new Organization();
		ArrayList<Room> rooms = organization.getRooms();

		assertEquals("Organization should create five default rooms", 5, rooms.size());
		assertEquals("First room should match the default data", "LLT6A", rooms.get(0).getID());
		assertEquals("Second room should match the default data", "LLT6B", rooms.get(1).getID());
		assertEquals("Third room should match the default data", "LLT3A", rooms.get(2).getID());
		assertEquals("Fourth room should match the default data", "LLT2C", rooms.get(3).getID());
		assertEquals("Fifth room should match the default data", "LAB2", rooms.get(4).getID());
	}

	@Test
	public void testGetEmployeeReturnsMatchingEmployee() {
		Organization organization = new Organization();

		try {
			Person employee = organization.getEmployee("Acan Brenda");

			assertNotNull("Existing employee lookup should return a person", employee);
			assertEquals("Returned employee should have the requested name", "Acan Brenda", employee.getName());
		} catch(Exception e) {
			fail("Existing employee lookup should not throw exception: " + e.getMessage());
		}
	}

	@Test
	public void testGetRoomReturnsMatchingRoom() {
		Organization organization = new Organization();

		try {
			Room room = organization.getRoom("LLT3A");

			assertNotNull("Existing room lookup should return a room", room);
			assertEquals("Returned room should have the requested ID", "LLT3A", room.getID());
		} catch(Exception e) {
			fail("Existing room lookup should not throw exception: " + e.getMessage());
		}
	}

	@Test
	public void testGetEmployeeThrowsExceptionForUnknownEmployee() {
		Organization organization = new Organization();

		try {
			organization.getEmployee("Unknown Person");
			fail("Unknown employee lookup should throw an exception");
		} catch(Exception e) {
			assertEquals("Exception should explain that the employee does not exist",
					"Requested employee does not exist", e.getMessage());
		}
	}

	@Test
	public void testGetRoomThrowsExceptionForUnknownRoom() {
		Organization organization = new Organization();

		try {
			organization.getRoom("UNKNOWN");
			fail("Unknown room lookup should throw an exception");
		} catch(Exception e) {
			assertEquals("Exception should explain that the room does not exist",
					"Requested room does not exist", e.getMessage());
		}
	}
}
