package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

// Test class for BasicTimer
class BasicTimerTest {

    BasicTimer test1;
    BasicTimer oneParam;
    BasicTimer zeroSec;
    BasicTimer oneSec;
    BasicTimer jsonConsTest;


    @BeforeEach
    public void runBefore() {
        test1 = new BasicTimer(10, "test1", "Desc for test1");
        jsonConsTest = new BasicTimer(10, "json", "desc json",
                true, 1000, false, 10);
        oneParam = new BasicTimer(3);
        zeroSec = new BasicTimer(0);
        oneSec = new BasicTimer(1);
    }

    @Test
    public void testConstructorOneParam() {
        int ansSecs = oneParam.getSeconds();
        String ansTitle = oneParam.getTitle();
        String ansDes = oneParam.getDescription();
        boolean ansIsRunning = oneParam.getIsRunning();
        boolean ansIsFinished = oneParam.getIsFinished();

        assertEquals(3, ansSecs);
        assertEquals("No title", ansTitle);
        assertEquals("No description", ansDes);
        assertFalse(ansIsRunning);
        assertFalse(ansIsFinished);
    }

    @Test
    public void testConstructorThreeParam() {
        int ansSecs = test1.getSeconds();
        String ansTitle = test1.getTitle();
        String ansDes = test1.getDescription();
        boolean ansIsRunning = test1.getIsRunning();
        boolean ansIsFinished = test1.getIsFinished();

        assertEquals(10, ansSecs);
        assertEquals("test1", ansTitle);
        assertEquals("Desc for test1", ansDes);
        assertFalse(ansIsRunning);
        assertFalse(ansIsFinished);
    }

    @Test
    public void testJsonConstructorParam() {
        int ansSecs = jsonConsTest.getSeconds();
        String ansTitle = jsonConsTest.getTitle();
        String ansDes = jsonConsTest.getDescription();
        boolean ansIsRunning = jsonConsTest.getIsRunning();
        boolean ansIsFinished = jsonConsTest.getIsFinished();

        assertEquals(10, ansSecs);
        assertEquals("json", ansTitle);
        assertEquals("desc json", ansDes);
        assertTrue(ansIsRunning);
        assertFalse(ansIsFinished);

    }

    @Test
    public void testGetOrigTime() {
        assertEquals(10, test1.getOrigTime());
    }

    @Test
    public void testPlay() {
        test1.play();
        boolean ans = test1.getIsRunning();
        assertTrue(ans);
    }

    @Test
    public void testPause() {
        test1.play();
        boolean ans = test1.getIsRunning();
        assertTrue(ans);
        test1.pause();
        ans = test1.getIsRunning();
        assertFalse(ans);
    }

    @Test
    public void testActionPerformed() {
        ActionEvent a = new ActionEvent(oneParam, 1, "asd");

        zeroSec.play();
        oneSec.pause();
        test1.play();

        try {
            zeroSec.actionPerformed(a);
            oneSec.actionPerformed(a);
            test1.actionPerformed(a);
            Thread.sleep(1001);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(1, oneSec.getSeconds());
        assertEquals(9, test1.getSeconds());

        assertEquals(-1, zeroSec.getSeconds());
        assertTrue(zeroSec.getIsFinished());
    }

    @Test
    public void testAddTimer() {
        test1.addTimer(oneSec);
        assertEquals(11, test1.getSeconds());
    }

    @Test
    public void testReset() {
        test1.play();
        test1.reset();
        assertEquals(10, test1.getSeconds());
    }

}