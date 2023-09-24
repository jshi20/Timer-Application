package ui.graphics;

import model.BasicTimer;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

import static ui.graphics.TimerDisplayStyle.SPACING;

// Represents a BasicTimer but for the GUI
public class GraphicalBasicTimer extends BasicTimer {
    protected JLabel label;
    protected String stringLabel;

    // EFFECTS: Constructor to create a new BasicTimer for GUI
    public GraphicalBasicTimer(int seconds, String title, String description) {
        super(seconds, title, description);
        this.label = new JLabel(getText());
        this.timer = new Timer(DELAY, this);
        this.stringLabel = getText();
    }

    // EFFECTS: Returns label text of this timer
    private String getText() {
        return "BasicTimer Title: " + this.getTitle()
                + SPACING + "Seconds: " + this.getSeconds()
                + SPACING + "Description: " + this.getDescription();
    }

    // EFFECTS: Overriding constructor for JsonReader so timer can be saved and loaded
    public GraphicalBasicTimer(int seconds, String title, String description, Boolean isRunning,
                               int delay, Boolean isFinished, int origTime, String stringLabel) {
        super(seconds, title, description, isRunning, delay, isFinished, origTime);

        this.label = new JLabel(stringLabel);
        this.timer = new Timer(DELAY, this);
        this.stringLabel = stringLabel;
    }

    // EFFECTS: Returns this object's JLabel
    public JLabel getLabel() {
        return this.label;
    }

    // EFFECTS: Returns this object's label in String
    public String getStringLabel() {
        return this.stringLabel;
    }

    // EFFECTS: Changes text of timer as seconds is counting down
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            label.setText(getText());
            this.seconds--;
            if (this.seconds < 0) {
                timer.stop();
                isFinished = true;
            }
        }
    }

    // EFFECTS: Returns GraphicalBasicTimer object as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("stringLabel", stringLabel);
        return json;
    }

    // EFFECTS: Checks whether two GraphicalBasicTimers are equivalent based on
    //          stringLabel
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GraphicalBasicTimer that = (GraphicalBasicTimer) o;

        return Objects.equals(stringLabel, that.stringLabel);
    }

    @Override
    public int hashCode() {
        return stringLabel != null ? stringLabel.hashCode() : 0;
    }
}
