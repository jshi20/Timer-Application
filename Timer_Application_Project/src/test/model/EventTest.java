package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


// Tests adapted from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

// Test for Event class
public class EventTest {
    private Event event;
    private Date date;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        event = new Event("TestBasicTimer1 has began playing");   // (1)
        date = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("TestBasicTimer1 has began playing", event.getDescription());
        assertEquals(date, event.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "TestBasicTimer1 has began playing", event.toString());
    }
}
