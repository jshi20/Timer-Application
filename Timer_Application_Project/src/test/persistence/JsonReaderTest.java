package persistence;

import model.AllTimers;
import model.BasicTimer;
import model.GroupTimer;
import model.TimerTraits;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code modeled on CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Test class for JsonReader
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReadNonExistentFile() {
        JsonReader r = new JsonReader("./data/noSuchFile.json");
        try {
            AllTimers a = r.read();
            fail("IOException Expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testReadEmptyAllTimers() {
        JsonReader r = new JsonReader("./data/testReaderEmptyAllTimers.json");
        try {
            AllTimers a = r.read();
            assertEquals(0, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderOneBasicTimer() {
        JsonReader r = new JsonReader("./data/testReaderOneBasicTimer.json");
        try {
            AllTimers a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            assertEquals(1, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            checkBasicTimer(10, "test1", "desc for test1", false,
                    1000, false, 10, (BasicTimer) ans.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGraphicalBasicTimer() {
        JsonReader r = new JsonReader("./data/testReaderGraphicalBasicTimer.json");
        String label = "BasicTimer Title: test1   Seconds: 10   Description: desc for test1";
        try {
            AllTimers a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            assertEquals(1, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            checkGraphicalBasicTimer(10, "test1", "desc for test1", false,
                    1000, false, 10, label, (BasicTimer) ans.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderThreeBasicTimer() {
        JsonReader r = new JsonReader("./data/testReaderThreeBasicTimer.json");
        try {
            AllTimers a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            assertEquals(3, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            checkBasicTimer(10, "test1", "desc for test1", false,
                    1000, false, 10, (BasicTimer) ans.get(0));
            checkBasicTimer(2, "test2", "desc for test2", false,
                    1000, false, 2, (BasicTimer) ans.get(1));
            checkBasicTimer(3, "test3", "desc for test3", false,
                    1000, false, 3, (BasicTimer) ans.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderEmptyGraphicalGroupTimer() {
        JsonReader r = new JsonReader("./data/testReaderEmptyGraphicalGroupTimer.json");
        String groupLabel = "GroupTimer Title: tg1   Description: desc for tg1   Includes: ";
        try {
            AllTimers a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            List<BasicTimer> gtAns = new ArrayList<>();
            assertEquals(1, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            checkGraphicalGroupTimer(gtAns, "tg1", "desc for tg1", groupLabel, (GroupTimer) ans.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }

    @Test
    public void testReaderGroupTimer() {
        JsonReader r = new JsonReader("./data/testReaderGroupTimer.json");
        try {
            AllTimers a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            GroupTimer gt = (GroupTimer) ans.get(0);
            List<BasicTimer> gtAns = gt.getAllTimes();
            assertEquals(3, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            assertEquals(2, gtAns.size());
            checkGroupTimer(gtAns, "tg1", "desc for tg1", (GroupTimer) ans.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }

    @Test
    public void testReaderGraphicalGroupTimer() {
        JsonReader r = new JsonReader("./data/testReaderGraphicalGroupTimer.json");
        String groupLabel = "GroupTimer Title: tg1   Description: desc for tg1   Includes: bt1, bt2, ";
        try {
            AllTimers a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            GroupTimer gt = (GroupTimer) ans.get(2);
            List<BasicTimer> gtAns = gt.getAllTimes();
            assertEquals(3, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            assertEquals(2, gtAns.size());
            checkGraphicalGroupTimer(gtAns, "tg1", "desc for tg1", groupLabel, (GroupTimer) ans.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }

}
