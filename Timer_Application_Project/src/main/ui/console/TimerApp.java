package ui.console;

import model.AllTimers;
import model.BasicTimer;
import model.GroupTimer;
import model.TimerTraits;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// My timer application
// Code related to JSON inspired by CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class TimerApp {
    private Scanner input;
    //private List<TimerTraits> allTimes = new ArrayList<>();
    private AllTimers allTimers;
    private static final String MAKE_GROUP_TIMER = "make a new group timer";
    private static final String GROUP_TIMERS = "group timers";
    private static final String PRINT_ALL_TIMERS = "list of timers";
    private static final String SAVE = "save";
    private static final String LOAD = "load";
    public static final String JSON_STORE = "./data/allTimes.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: Runs timer app
    public TimerApp() {
        allTimers = new AllTimers();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        run();
    }

    // MODIFIES: this
    // EFFECTS: run and process user input
    private void run() {
        boolean isOn = true;
        String command = null;

        initialize();
        displayMenu();

        while (isOn) {
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("quit")) {
                isOn = false;
            } else {
                takeCommand(command);
            }
        }

        System.out.println("\nThe Application has Stopped");
    }

    // EFFECTS: initialize the scanner
    private void initialize() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");

    }

    // MODIFIES: this
    // EFFECTS: process all user commands
    @SuppressWarnings("methodlength")
    private void takeCommand(String command) {
        if (command.equals("commands")) {
            displayCommands();
        } else if (command.equals("make a new timer")) {
            makeBasicTimer();
        } else if (command.equals(MAKE_GROUP_TIMER)) {
            makeGroupTimer();
        } else if (command.equals(GROUP_TIMERS)) {
            groupTimers();
        } else if (command.equals(PRINT_ALL_TIMERS)) {
            printAllTimes();
        } else if (command.equals(SAVE)) {
            saveAllTimers();
            System.out.println("All timers saved!");
        } else if (command.equals(LOAD)) {
            loadAllTimers();
            System.out.println("All timers loaded!");
        } else if (command.equals("play")) {
            if (allTimers.getAllTimersSize() == 0) {
                System.out.println("There are no timers right now");
            } else {
                playTimer();
            }
        } else {
            System.out.println("Invalid command");
        }

    }

    // EFFECTS: Displays the menu
    private void displayMenu() {
        System.out.println("\nType \"commands\" to see all valid commands");
        //System.out.println("\tType \"Make a new timer\" to make a new Timer");
    }

    // EFFECTS: Displays all the valid commands user can give
    private void displayCommands() {
        System.out.println("\tType \"make a new timer\" to make a new Timer");
        System.out.println("\tType \"" + MAKE_GROUP_TIMER + "\" to make a new group Timer");
        System.out.println("\tType \"" + GROUP_TIMERS + "\" to group timers together");
        System.out.println("\tType \"" + PRINT_ALL_TIMERS + "\" to view all timers");
        System.out.println("\tType \"play\" to find an existing Timer to play!");
        System.out.println("\tType \"" + SAVE + "\" to save all timers");
        System.out.println("\tType \"" + LOAD + "\" to load all timers");
        //System.out.println("\tType \"pause TimeNameHere\" to pause a Timer");
        System.out.println("\tType \"quit\" to quit this application");

    }


    // REQUIRES: time must be an integer
    // MODIFIES: this
    // EFFECTS: Creates a new BasicTimer
    public void makeBasicTimer() {
        Integer time = null;
        String title = null;
        String description = null;

        while (!(time instanceof Integer)) {
            System.out.println("Set timer in seconds");
            time = input.nextInt();

            if (time <= 0) {
                System.out.println("Invalid Time!");
                time = null;
            }
        }

        while (!(title instanceof String)) {
            System.out.println("Name your timer");
            title = input.next();
        }

        while (!(description instanceof String)) {
            System.out.println("Write a description");
            description = input.next();
        }
        System.out.println("Your new BasicTimer has been created!");
        allTimers.addTimer(new BasicTimer(time, title, description));
    }

    // MODIFIES: this
    // EFFECTS: Makes a new group timer
    public void makeGroupTimer() {
        String title = null;
        String description = null;

        while (!(title instanceof String)) {
            System.out.println("Name your group timer");
            title = input.next();
        }

        while (!(description instanceof String)) {
            System.out.println("Write a description");
            description = input.next();
        }
        System.out.println("Your new group timer has been created!");
        allTimers.addTimer(new GroupTimer(title, description));
    }

    // EFFECTS: Finds a valid GroupTimer based on input
    public void groupTimers() {
        String name = null;
        boolean foundIt = false;
        boolean isGroupTimer = false;

        System.out.println("Choose a group timer");


        while (!(name instanceof String) && !foundIt && !isGroupTimer) {
            name = input.next();

            for (TimerTraits gt : allTimers.getAllTimers()) {
                if (gt.getTitle().equals(name) && gt instanceof GroupTimer) {
                    foundIt = true;
                    isGroupTimer = true;
                    addIt(gt);
                }
            }

            if (!foundIt || !isGroupTimer) {
                System.out.println("Please choose a valid group timer");
                name = null;
            }


        }

    }

    // MODIFIES: this, GroupTimer
    // EFFECTS: Adds a valid BasicTimer to chosen GroupTimer
    public void addIt(TimerTraits gt) {
        String name = null;
        Boolean foundIt = false;

        System.out.println("Choose a timer to add");

        while (!(name instanceof String) && !foundIt) {
            name = input.next();

            for (TimerTraits t : allTimers.getAllTimers()) {

                if (t.getTitle().equals(name)) {
                    foundIt = true;
                    gt.addTimer((BasicTimer) t);
                    System.out.println("Added successfully");
                }

            }

            if (!foundIt) {
                System.out.println("Please choose a valid timer");
                name = null;
            }

        }

    }

    // EFFECTS: Prints out a list of all timers created to console
    public void printAllTimes() {
        for (TimerTraits t : allTimers.getAllTimers()) {
            System.out.println("Title: " + t.getTitle() + "\tDescription: " + t.getDescription());
        }
    }

    // MODIFIES: this, BasicTimer
    // EFFECTS: Begins to play timer of subclass TimerTraits based on their play() method
    public void playTimer() {
        String name = null;
        boolean foundIt = false;

        System.out.println("Choose a timer's title to play!");
        System.out.println("Here is a list of timers so far:");

        printAllTimes();

        while (!(name instanceof String) && !foundIt) {
            name = input.next();
            for (TimerTraits t : allTimers.getAllTimers()) {
                if (t.getTitle().equals(name)) {
                    t.play();
                    foundIt = true;
                }
            }
            if (!foundIt) {
                System.out.println("Please choose a valid Timer to play");
                name = null;
            }
        }
    }

    // EFFECT: Saves allTimers to file
    public void saveAllTimers() {
        try {
            jsonWriter.open();
            jsonWriter.write(allTimers);
            jsonWriter.close();
            System.out.println("Saved all timers to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads allTimers from file
    public void loadAllTimers() {
        try {
            allTimers = jsonReader.read();
            System.out.println("Loaded all times");
        } catch (IOException e) {
            System.out.println("Unable to read from " + JSON_STORE);
        }
    }

}
