package ui.graphics;

import model.BasicTimer;
import model.GroupTimer;
import org.json.JSONObject;

import javax.swing.*;
import java.util.List;

import static ui.graphics.TimerDisplayStyle.SPACING;

// Represents a GroupTimer but for the GUI
public class GraphicalGroupTimer extends GroupTimer {
    protected JLabel groupLabel;
    protected String groupStringLabel;


    // EFFECTS: Constructs empty list with title and description
    public GraphicalGroupTimer(String title, String description) {
        super(title, description);
        this.groupStringLabel = getText();
        this.groupLabel = new JLabel(groupStringLabel);
    }

    // EFFECTS: Returns label text of this GroupTimer
    private String getText() {
        return "GroupTimer Title: " + this.getTitle()
                + SPACING + "Description: " + this.getDescription()
                + SPACING + "Includes: ";
    }

    // EFFECTS: Returns JLabel of this GroupTimer
    public JLabel getGroupLabel() {
        return this.groupLabel;
    }

    // MODIFIES: this
    // EFFECTS: Sets the JLabel of this timer to title
    public void setTextGroupLabel(String title) {
        this.groupLabel.setText(this.groupStringLabel += title + ", ");
    }

    // EFFECTS: Constructor for persistence package to make object based on all fields
    public GraphicalGroupTimer(List<BasicTimer> bt, String title, String description,
                               String groupStringLabel) {
        super(bt, title, description);
        this.groupLabel = new JLabel(groupStringLabel);
        this.groupStringLabel = groupStringLabel;
    }

    // EFFECTS: Returns the arraylist
    public List<BasicTimer> getAllTimes() {
        return super.getAllTimes();
    }

    // EFFECTS: Returns current label in string form
    public String getGroupStringLabel() {
        return this.groupStringLabel;
    }

    // EFFECTS: Returns GraphicalGroupTimer object as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("groupStringLabel", groupStringLabel);
        return json;
    }

}
