package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test class for AllTimers
public class AllTimersTest {

    AllTimers test1;
    BasicTimer b1;
    GroupTimer g1;

    @BeforeEach
    public void runBefore() {
        test1 = new AllTimers();
        b1 = new BasicTimer(5);
        g1 = new GroupTimer();
    }

    @Test
    public void testConstructor() {
        int ans = test1.getAllTimersSize();
        String name = test1.getName();
        assertEquals(0, ans);
        assertEquals("All Timers", name);
    }

    @Test
    public void testAddTimerBasic() {
        test1.addTimer(b1);
        assertEquals(1, test1.getAllTimersSize());
    }

    @Test
    public void testAddTimerGroup() {
        test1.addTimer(g1);
        assertEquals(1, test1.getAllTimersSize());
    }

    @Test
    public void testGetAllTimers() {
        List<TimerTraits> ans = new ArrayList<>();
        test1.addTimer(b1);
        test1.addTimer(g1);
        ans.add(b1);
        ans.add(g1);
        assertEquals(ans, test1.getAllTimers());
    }

}
