package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TimeConflictExceptionTest {

    //Test the default constructor

    @Test
    public void testDefaultConstructor() {
        TimeConflictException ex = new TimeConflictException();

        assertNull("Default constructor should have null message", ex.getMessage());
    }

    //Test the constructor that takes a message

    @Test
    public void testMessageConstructor() {
        TimeConflictException ex = new TimeConflictException("Conflict occurred");

        assertEquals("Conflict occurred", ex.getMessage());
    }
    // =========================
    //Test the constructor that takes a cause

    @Test
    public void testCauseConstructor() {
        Throwable cause = new RuntimeException("Root cause");

        TimeConflictException ex = new TimeConflictException(cause);

        assertEquals(cause, ex.getCause());
    }

    // Test the constructor that takes both message and cause

    @Test
    public void testMessageAndCauseConstructor() {
        Throwable cause = new RuntimeException("Root cause");

        TimeConflictException ex =
                new TimeConflictException("Conflict", cause);

        assertEquals("Conflict", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

   //Test the full constructor with all parameters

    @Test
    public void testFullConstructor() {
        Throwable cause = new RuntimeException("Root cause");

        TimeConflictException ex =
                new TimeConflictException("Conflict", cause, true, true);

        assertEquals("Conflict", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
    
}
