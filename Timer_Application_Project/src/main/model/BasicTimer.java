package model;

import org.json.JSONObject;
import persistence.Writeable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import static ui.console.PrintStatements.completeTimer;
import static ui.console.PrintStatements.printSeconds;

// Represents a timer with a title, description, time in seconds, and boolean to tell when it should tick away
public class BasicTimer extends TimerTraits implements ActionListener, Writeable {
    public static final int DELAY = 1000;
    protected int seconds;
    protected boolean isRunning;
    protected boolean isFinished;
    protected int origTime;

    protected Timer timer;

    // EFFECTS: Creates a BasicTimer object with the time in seconds without title and description
    //          isRunning set to false
    public BasicTimer(int seconds) {
        this.seconds = seconds;
        this.title = "No title";
        this.description = "No description";
        this.isRunning = false;
        this.timer = new Timer(DELAY, this);
        this.isFinished = false;
        this.origTime = seconds;
    }

    // EFFECTS: Creates a BasicTimer object with the time in seconds, a title and description
    //          isRunning set to false
    public BasicTimer(int seconds, String title, String description) {
        this.seconds = seconds;
        this.title = title;
        this.description = description;
        this.isRunning = false;
        this.timer = new Timer(DELAY, this);
        this.isFinished = false;
        this.origTime = seconds;
    }

    // EFFECTS: Constructor for persistence package to make object based on all fields
    public BasicTimer(int seconds, String title, String description, Boolean isRunning,
                      int delay, Boolean isFinished, int origTime) {
        this.seconds = seconds;
        this.title = title;
        this.description = description;
        this.isRunning = isRunning;
        this.timer = new Timer(delay, this);
        this.isFinished = isFinished;
        this.origTime = origTime;
    }


    // EFFECTS: Returns seconds left of BasicTimer object
    public int getSeconds() {
        return this.seconds;
    }

    // EFFECTS: Returns whether Timer is running or not
    public boolean getIsRunning() {
        return this.isRunning;
    }

    // EFFECTS: Returns whether Timer is finished or not
    public boolean getIsFinished() {
        return this.isFinished;
    }

    // MODIFIES: this
    // EFFECTS: Pauses the timer
    public void pause() {
        this.isRunning = false;
    }

    // EFFECTS: Return original time of timer
    public int getOrigTime() {
        return this.origTime;
    }

    // MODIFIES: this
    // EFFECTS: Resets the BasicTimer back to origTime
    public void reset() {
        this.seconds = this.origTime;
    }

    // MODIFIES: this
    // EFFECTS: Begins to play the timer and logs the event
    @Override
    public void play() {
        if (this.seconds <= 0) {
            this.seconds = this.origTime;
        }
        this.timer.start();
        EventLog.getInstance().logEvent(new Event("Began Playing BasicTimer " + title));
        this.isRunning = true;
    }

    // MODIFIES: this
    // EFFECTS: Increases BasicTimer's time by t's time
    public void addTimer(BasicTimer t) {
        this.seconds += t.getSeconds();
    }

    // MODIFIES: this
    // EFFECTS: Method runs every second when timer is on
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            printSeconds(this);
            this.seconds--;
            if (this.seconds < 0) {
                timer.stop();
                isFinished = true;
                completeTimer();
            }
        }
    }

    // EFFECTS: Returns BasicTime object as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("seconds", seconds);
        json.put("title", title);
        json.put("description", description);
        json.put("isRunning", isRunning);
        json.put("timer", DELAY);
        json.put("isFinished", isFinished);
        json.put("origTime", origTime);
        return json;
    }
}
