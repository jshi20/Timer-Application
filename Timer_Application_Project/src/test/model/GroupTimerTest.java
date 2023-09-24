package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test class for GroupTimer
public class GroupTimerTest {

    private GroupTimer test;
    private BasicTimer t1;
    private BasicTimer t2;
    private BasicTimer t3;
    private GroupTimer twoParams;

    @BeforeEach
    public void runBefore() {
        test = new GroupTimer();
        t1 = new BasicTimer(1);
        t2 = new BasicTimer(2);
        t3 = new BasicTimer(3);
        twoParams = new GroupTimer("title", "desc");

    }

    @Test
    public void testConstructor() {
        List<BasicTimer> ans = test.getAllTimes();
        assertEquals(0, ans.size());
    }

    @Test
    public void testConstructorTwoParams() {
        List<BasicTimer> ans = twoParams.getAllTimes();
        assertEquals(0, ans.size());
        assertEquals("title", twoParams.getTitle());
        assertEquals("desc", twoParams.getDescription());
    }

    @Test
    public void testAddTimer() {
        twoParams.addTimer(t1);
        twoParams.addTimer(t2);
        twoParams.addTimer(t3);
        List<BasicTimer> ans = twoParams.getAllTimes();
        assertEquals(3, ans.size());
        assertEquals(t1, ans.get(0));
        assertEquals(t2, ans.get(1));
        assertEquals(t3, ans.get(2));
    }

    @Test
    public void testPlay() {
        twoParams.addTimer(t1);
        twoParams.addTimer(t1);
        twoParams.play();
        assertTrue(twoParams.getAllTimes().get(0).getIsRunning());
        assertTrue(twoParams.getAllTimes().get(1).getIsRunning());
    }

}
