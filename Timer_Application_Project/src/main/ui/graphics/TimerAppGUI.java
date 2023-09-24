package ui.graphics;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ui.console.TimerApp.JSON_STORE;

// Class represents GUI for Phase3 of project
public class TimerAppGUI extends JFrame implements WindowListener {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private DisplayAll displayAll;
    private AllTimers allTimers;
    protected TimerDisplayStyle test;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: Initializes GUI and JSON components for loading and saving
    public TimerAppGUI() {
        super("Timer App");
        allTimers = new AllTimers();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeGraphics();
        addWindowListener(this);
    }

    // EFFECTS: returns allTimers created so far
    public AllTimers getAllTimers() {
        return this.allTimers;
    }

    // EFFECTS: Initialize graphics of GUI
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createOptions();
        displayTimers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Helper method which declares and instantiates all options
    private void createOptions() {
        JPanel optionArea = new JPanel();
        optionArea.setLayout(new GridLayout(0, 1));
        optionArea.setSize(new Dimension(0, 0));
        add(optionArea, BorderLayout.NORTH);
        displayAll = new DisplayAll(this);
        add(displayAll, BorderLayout.EAST);
    }

    // EFFECTS: Displays most recent timers
    public void displayTimers() {
        if (test != null) {
            remove(test);
        }
        test = new TimerDisplayStyle(allTimers);
        add(test);
    }

    // EFFECT: Saves allTimers to file
    public void saveAllTimersGraphical() {
        try {
            jsonWriter.open();
            jsonWriter.write(allTimers);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads allTimers from file
    public void loadAllTimersGraphical() {
        try {
            allTimers = jsonReader.read();
            refreshGroupTimers();
        } catch (IOException e) {
            System.out.println("Unable to read from " + JSON_STORE);
        }
    }

    // MODIFIES: allTimers, this
    // EFFECTS: Ensures that GroupTimers reference only existing BasicTimers
    //          that got referenced by the GroupTimer from previous saving of application
    public void refreshGroupTimers() {
        List<GroupTimer> allGroupTimers = new ArrayList<>();

        for (TimerTraits gt : allTimers.getAllTimers()) {
            if (gt instanceof GroupTimer) {
                allGroupTimers.add((GroupTimer) gt);
            }
        }

        for (GroupTimer gt : allGroupTimers) {
            List<BasicTimer> toAdd = new ArrayList<>();
            List<BasicTimer> toRemove = new ArrayList<>();
            for (BasicTimer bt : gt.getAllTimes()) {
                for (TimerTraits tempTimes : allTimers.getAllTimers()) {
                    if (bt.equals(tempTimes)) {
                        toAdd.add((BasicTimer) tempTimes);
                        toRemove.add(bt);
                    }
                }
            }
            gt.getAllTimes().removeAll(toRemove);
            gt.getAllTimes().addAll(toAdd);
        }

    }

    public static void main(String[] args) {
        new TimerAppGUI();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n");
        }

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
