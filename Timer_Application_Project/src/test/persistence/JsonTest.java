package persistence;

// Code modeled on CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.BasicTimer;
import model.GroupTimer;
import ui.graphics.GraphicalBasicTimer;
import ui.graphics.GraphicalGroupTimer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Class with methods that can be utilized by both Json test classes
public class JsonTest {

    // EFFECTS: Check whether BasicTimer matches with bt
    protected void checkBasicTimer(int seconds, String title, String desc, Boolean isRunning,
                                   int delay, Boolean isFinished, int orig, BasicTimer bt) {
        assertEquals(seconds, bt.getSeconds());
        assertEquals(title, bt.getTitle());
        assertEquals(desc, bt.getDescription());
        assertEquals(isRunning, bt.getIsRunning());
        assertEquals(delay, 1000);
        assertEquals(isFinished, bt.getIsFinished());
        assertEquals(orig, bt.getOrigTime());
    }

    // EFFECTS: Check whether GraphicalBasicTimer matches with bt
    protected void checkGraphicalBasicTimer(int seconds, String title, String desc, Boolean isRunning,
                                            int delay, Boolean isFinished,
                                            int orig, String stringLabel, BasicTimer bt) {
        GraphicalBasicTimer gbt = (GraphicalBasicTimer) bt;
        assertEquals(seconds, gbt.getSeconds());
        assertEquals(title, gbt.getTitle());
        assertEquals(desc, gbt.getDescription());
        assertEquals(isRunning, gbt.getIsRunning());
        assertEquals(delay, 1000);
        assertEquals(isFinished, gbt.getIsFinished());
        assertEquals(orig, gbt.getOrigTime());
        assertEquals(stringLabel, gbt.getStringLabel());
    }

    // EFFECTS: Check whether GroupTimer matches with bt
    protected void checkGroupTimer(List<BasicTimer> bts, String title, String desc, GroupTimer gt) {
        assertEquals(bts, gt.getAllTimes());
        assertEquals(title, gt.getTitle());
        assertEquals(desc, gt.getDescription());
    }

    // EFFECTS: Check whether GraphicalGroupTimer matches with bt
    protected void checkGraphicalGroupTimer(List<BasicTimer> bts,
                                            String title,
                                            String desc,
                                            String groupStringLabel,
                                            GroupTimer gt) {
        GraphicalGroupTimer ggt = (GraphicalGroupTimer) gt;
        assertEquals(bts, ggt.getAllTimes());
        assertEquals(title, ggt.getTitle());
        assertEquals(desc, ggt.getDescription());
        assertEquals(groupStringLabel, ggt.getGroupStringLabel());
    }

}
