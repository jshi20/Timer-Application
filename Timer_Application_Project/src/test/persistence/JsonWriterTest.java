package persistence;

import model.AllTimers;
import model.BasicTimer;
import model.GroupTimer;
import model.TimerTraits;
import org.junit.jupiter.api.Test;
import ui.graphics.GraphicalBasicTimer;
import ui.graphics.GraphicalGroupTimer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code modeled on CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Test class for JsonWriter
public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            AllTimers a = new AllTimers();
            JsonWriter w = new JsonWriter("./data/my\0illegal:fileName.json");
            w.open();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyAllTimers() {
        try {
            AllTimers a = new AllTimers();
            JsonWriter w = new JsonWriter("./data/testWriterEmptyAllTimers.json");
            w.open();
            w.write(a);
            w.close();

            JsonReader r = new JsonReader("./data/testWriterEmptyAllTimers.json");
            a = r.read();
            assertEquals(0, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }
    }

    @Test
    public void testWriterBasicTimer() {
        try {
            AllTimers a = new AllTimers();
            a.addTimer(new BasicTimer(10, "test1", "desc for test1", false,
                    1000, false, 10));
            JsonWriter w = new JsonWriter("./data/testWriterBasicTimer.json");
            w.open();
            w.write(a);
            w.close();

            JsonReader r = new JsonReader("./data/testWriterBasicTimer.json");
            a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            assertEquals(1, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            checkBasicTimer(10, "test1", "desc for test1", false,
                    1000, false, 10, (BasicTimer) ans.get(0));
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }

    }

    @Test
    public void testWriterGraphicalBasicTimer() {
        try {
            AllTimers a = new AllTimers();
            a.addTimer(new GraphicalBasicTimer(1, "graphicalTimer1",
                    "desc for graphicalTimer1", false,
                    1000, false, 1,
                    "BasicTimer Title: graphicalTimer1   Seconds: 1   Description: desc for graphicalTimer1"));
            JsonWriter w = new JsonWriter("./data/testWriterGraphicalBasicTimer.json");
            w.open();
            w.write(a);
            w.close();

            JsonReader r = new JsonReader("./data/testWriterGraphicalBasicTimer.json");
            a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            assertEquals(1, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            checkGraphicalBasicTimer(1, "graphicalTimer1", "desc for graphicalTimer1",
                    false,
                    1000, false, 1,
                    "BasicTimer Title: graphicalTimer1   Seconds: 1   Description: desc for graphicalTimer1",
                    (BasicTimer) ans.get(0));
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }

    }


    @Test
    public void testWriterEmptyGroupTimer() {
        try {
            AllTimers a = new AllTimers();
            a.addTimer(new GroupTimer(new ArrayList<>(), "test1", "desc for test1"));
            JsonWriter w = new JsonWriter("./data/testWriterEmptyGroupTimer.json");
            w.open();
            w.write(a);
            w.close();

            JsonReader r = new JsonReader("./data/testWriterEmptyGroupTimer.json");
            a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            assertEquals(1, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            checkGroupTimer(new ArrayList<>(), "test1", "desc for test1", (GroupTimer) ans.get(0));
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }

    }

    @Test
    public void testWriterEmptyGraphicalGroupTimer() {
        try {
            AllTimers a = new AllTimers();
            String stringLab = "GroupTimer Title: test1   Description: desc for test1   Includes: ";
            a.addTimer(new GraphicalGroupTimer(new ArrayList<>(),
                    "test1",
                    "desc for test1",
                    stringLab));
            JsonWriter w = new JsonWriter("./data/testWriterEmptyGraphicalGroupTimer.json");
            w.open();
            w.write(a);
            w.close();

            JsonReader r = new JsonReader("./data/testWriterEmptyGraphicalGroupTimer.json");
            a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            assertEquals(1, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            checkGraphicalGroupTimer(new ArrayList<>(), "test1", "desc for test1",
                    stringLab, (GroupTimer) ans.get(0));
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }

    }

    @Test
    public void testWriterGroupTimer() {
        try {
            AllTimers a = new AllTimers();
            List<BasicTimer> bts = new ArrayList<>();
            BasicTimer b1 = new BasicTimer(1, "b1", "desc for b1");
            BasicTimer b2 = new BasicTimer(2, "b2", "desc for b2");
            bts.add(b1);
            bts.add(b2);
            a.addTimer(new GroupTimer(bts, "test1", "desc for test1"));
            JsonWriter w = new JsonWriter("./data/testWriterGroupTimer.json");
            w.open();
            w.write(a);
            w.close();

            JsonReader r = new JsonReader("./data/testWriterGroupTimer.json");
            a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            GroupTimer gtt = (GroupTimer) ans.get(0);
            bts = gtt.getAllTimes();
            assertEquals(1, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            checkGroupTimer(bts, "test1", "desc for test1", (GroupTimer) ans.get(0));
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }

    }

    @Test
    @SuppressWarnings("methodlength")
    public void testWriterGraphicalGroupTimer() {
        try {
            AllTimers a = new AllTimers();
            List<BasicTimer> bts = new ArrayList<>();
            String groupLabel = "GroupTimer Title: test1   Description: desc for test1   Includes: bt1, bt2, ";
            GraphicalBasicTimer b1 = new GraphicalBasicTimer(1, "b1", "desc for b1");
            BasicTimer b2 = new GraphicalBasicTimer(2, "b2", "desc for b2");
            bts.add(b1);
            bts.add(b2);
            a.addTimer(new GraphicalGroupTimer(bts, "test1", "desc for test1", groupLabel));
            JsonWriter w = new JsonWriter("./data/testWriterGraphicalGroupTimer.json");
            w.open();
            w.write(a);
            w.close();

            JsonReader r = new JsonReader("./data/testWriterGraphicalGroupTimer.json");
            a = r.read();
            List<TimerTraits> ans = a.getAllTimers();
            GroupTimer gtt = (GroupTimer) ans.get(0);
            bts = gtt.getAllTimes();
            assertEquals(1, a.getAllTimersSize());
            assertEquals("All Timers", a.getName());
            checkGraphicalGroupTimer(bts, "test1", "desc for test1", groupLabel, (GroupTimer) ans.get(0));
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }

    }

}