package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;

// A group of timers of arbitrary size dependent on user
public class GroupTimer extends TimerTraits implements Writeable {
    private List<BasicTimer> allTimes;

    // EFFECTS: Constructs empty list
    public GroupTimer() {
        this.allTimes = new ArrayList<>();
    }

    // EFFECTS: Constructs empty list with title and description
    public GroupTimer(String title, String description) {
        this.allTimes = new ArrayList<>();
        this.title = title;
        this.description = description;
    }

    // EFFECTS: Constructor for persistence package to make object based on all fields
    public GroupTimer(List<BasicTimer> bt, String title, String description) {
        this.allTimes = bt;
        this.title = title;
        this.description = description;
    }

    // EFFECTS: Returns the arraylist
    public List<BasicTimer> getAllTimes() {
        return this.allTimes;
    }


    // MODIFIES: this
    // EFFECTS: adds basic timer to end of list and logs the event
    public void addTimer(BasicTimer t) {
        allTimes.add(t);
        EventLog.getInstance().logEvent(new Event("GroupTimer " + this.title
                + " has added " + t.getTitle() + " to its timers"));
    }

    // MODIFIES: this, BasicTimer
    // EFFECTS: plays all BasicTimers in the list
    @Override
    public void play() {
        EventLog.getInstance().logEvent(new Event("GroupTimer " + this.title + " has began playing all its timers"));
        for (int i = 0; i < allTimes.size(); i++) {
            allTimes.get(i).reset();
            allTimes.get(i).play();

        }

    }

    // EFFECTS: turns object into json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("allBasicTimes", basicTimersToJson());
        json.put("title", title);
        json.put("description", description);
        return json;
    }

    // EFFECTS: Returns all objects in allTimes as JSON Array
    public JSONArray basicTimersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (BasicTimer t : allTimes) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
