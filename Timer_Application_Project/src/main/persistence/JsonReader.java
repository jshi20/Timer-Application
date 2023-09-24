package persistence;

import model.AllTimers;
import model.BasicTimer;
import model.GroupTimer;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.graphics.GraphicalBasicTimer;
import ui.graphics.GraphicalGroupTimer;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Code modeled on CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Class to read TimerTraits from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads allTimes from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AllTimers read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTimerTraits(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses allTimers from JSON object and returns it
    private AllTimers parseTimerTraits(JSONObject jsonObject) {
        AllTimers allTimers = new AllTimers();
        addTimers(allTimers, jsonObject);
        return allTimers;
    }

    // MODIFIES: allTimers
    // EFFECTS: parses TimerTraits from JSON object and adds them to allTimes
    private void addTimers(AllTimers allTimers, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allTimers");
        for (Object json : jsonArray) {
            JSONObject nextTimer = (JSONObject) json;

            if (nextTimer.has("stringLabel")) {
                addGraphicalBasicTimer(allTimers, nextTimer);
            } else if (nextTimer.has("origTime")) {
                addBasicTimer(allTimers, nextTimer);
            }

            if (nextTimer.has("groupStringLabel")) {
                addGraphicalGroupTimer(allTimers, nextTimer);
            } else if (nextTimer.has("allBasicTimes")) {
                addGroupTimer(allTimers, nextTimer);
            }

        }

    }

    // MODIFIES: allTimers
    // EFFECTS: parses BasicTimer from JSON object and adds it to List<TimerTraits>
    private void addBasicTimer(AllTimers allTimers, JSONObject jsonObject) {
        allTimers.addTimer(makeBasicTimer(jsonObject));
    }

    // MODIFIES: allTimers
    // EFFECTS: parses GraphicalBasicTimer from JSON object and adds it to List<TimerTraits>
    private void addGraphicalBasicTimer(AllTimers allTimers, JSONObject jsonObject) {
        allTimers.addTimer(makeGraphicalBasicTimer(jsonObject));
    }

    // MODIFIES: allTimers
    // EFFECTS: parses BasicTimer from JSON object and adds it to List<TimerTraits>
    private void addGroupTimer(AllTimers allTimers, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allBasicTimes");
        List<BasicTimer> allBasicTimes = new ArrayList<>();

        for (Object json : jsonArray) {
            allBasicTimes.add(makeBasicTimer((JSONObject) json));
        }

        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        GroupTimer g = new GroupTimer(allBasicTimes, title, description);
        allTimers.addTimer(g);
    }

    // MODIFIES: allTimers
    // EFFECTS: parses GraphicalBasicTimer from JSON object and adds it to List<TimerTraits>
    private void addGraphicalGroupTimer(AllTimers allTimers, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allBasicTimes");
        List<BasicTimer> allGraphicalBasicTimes = new ArrayList<>();

        for (Object json : jsonArray) {
            allGraphicalBasicTimes.add(makeGraphicalBasicTimer((JSONObject) json));
        }

        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        String groupStringLabel = jsonObject.getString("groupStringLabel");
        GraphicalGroupTimer g = new GraphicalGroupTimer(allGraphicalBasicTimes,
                title, description, groupStringLabel);
        allTimers.addTimer(g);
    }

    // MODIFIES: allTimers
    // Makes a new BasicTimer based on jsonObject
    private BasicTimer makeBasicTimer(JSONObject jsonObject) {
        int seconds = jsonObject.getInt("seconds");
        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        Boolean isRunning = jsonObject.getBoolean("isRunning");
        int t = jsonObject.getInt("timer");
        Boolean isFinished = jsonObject.getBoolean("isFinished");
        int origTime = jsonObject.getInt("origTime");
        return new BasicTimer(seconds, title, description, isRunning, t, isFinished, origTime);
    }

    // MODIFIES: allTimers
    // Makes a new GraphicalBasicTimer based on jsonObject
    private BasicTimer makeGraphicalBasicTimer(JSONObject jsonObject) {
        int seconds = jsonObject.getInt("seconds");
        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        Boolean isRunning = jsonObject.getBoolean("isRunning");
        int t = jsonObject.getInt("timer");
        Boolean isFinished = jsonObject.getBoolean("isFinished");
        int origTime = jsonObject.getInt("origTime");
        String stringLabel = jsonObject.getString("stringLabel");
        return new GraphicalBasicTimer(seconds, title, description, isRunning, t,
                isFinished, origTime, stringLabel);
    }

}
