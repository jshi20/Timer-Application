package model;

import org.json.JSONObject;

// Abstract class to represent similar behaviours of a GroupTimer and BasicTimer
public abstract class TimerTraits {
    protected String title;
    protected String description;

    public abstract void play();

    // EFFECTS: Returns title of BasicTimer object
    public String getTitle() {
        return this.title;
    }

    // EFFECTS: Returns description of BasicTimer object
    public String getDescription() {
        return this.description;
    }

    public abstract void addTimer(BasicTimer t);

    public abstract JSONObject toJson();
}
