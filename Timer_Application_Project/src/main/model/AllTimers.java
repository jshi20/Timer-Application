package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;

// Class to represent all timer's created
public class AllTimers implements Writeable {

    private List<TimerTraits> allTimers;
    private String name;

    // EFFECTS: initializes empty lists of timers
    public AllTimers() {
        name = "All Timers";
        allTimers = new ArrayList<>();
    }


    // EFFECTS: get name of this obj
    public String getName() {
        return this.name;
    }

    // MODIFIES: this
    // EFFECTS: adds a TimerTrait obj to allTimers and logs the event
    public void addTimer(TimerTraits t) {
        String titleAndCreate = t.getTitle() + " has been created";
        allTimers.add(t);
        if (t instanceof BasicTimer) {
            EventLog.getInstance().logEvent(new Event("BasicTimer " + titleAndCreate));
        }

        if (t instanceof GroupTimer) {
            EventLog.getInstance().logEvent(new Event("GroupTimer " + titleAndCreate));
        }

    }

    // EFFECTS: returns size of allTimers
    public int getAllTimersSize() {
        return allTimers.size();
    }

    // EFFECTS: returns list of all Timers so far
    public List<TimerTraits> getAllTimers() {
        return allTimers;
    }

    // EFFECTS: turns object into json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("allTimers", timersToJson());
        return json;
    }

    // EFFECTS: Returns all objects in AllTimers as JSON Array
    private JSONArray timersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TimerTraits t : allTimers) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
