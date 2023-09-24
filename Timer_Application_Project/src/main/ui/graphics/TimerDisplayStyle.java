package ui.graphics;

import model.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import static ui.graphics.DisplayAll.BUTTON_HEIGHT;
import static ui.graphics.DisplayAll.BUTTON_WIDTH;

// Class to represent how timers are displayed and updated in GUI
public class TimerDisplayStyle extends JPanel {
    public static final String SPACING = "   ";
    public static final Border blackLine = BorderFactory.createLineBorder(Color.black);
    private AllTimers allTimers;

    // EFFECTS: Creates grid and places timers for display
    @SuppressWarnings("methodlength")
    public TimerDisplayStyle(AllTimers allTimers) {
        this.allTimers = allTimers;
        setLayout(new GridLayout(10, 1));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED),
                BorderFactory.createEmptyBorder(20, 20, 20, 30)));

        for (TimerTraits t : allTimers.getAllTimers()) {
            if (t instanceof BasicTimer) {
                GraphicalBasicTimer ans = (GraphicalBasicTimer) t;
                JLabel timer = ans.getLabel();
                timer.setBorder(blackLine);
                add(timer);
                add(playButton(t));
            }

            if (t instanceof GroupTimer) {
                GraphicalGroupTimer gt = (GraphicalGroupTimer) t;
                JLabel groupTimer = gt.getGroupLabel();
                groupTimer.setBorder(blackLine);
                add(groupTimer);
                add(playButton(gt));
                add(addAddButton(gt));
            }

        }

        setVisible(true);
    }

    // EFFECTS: Adds a new BasicTimer to the GUI
    public void addBasicTimer(GraphicalBasicTimer graphicalBasicTimer) {
        JLabel timer = graphicalBasicTimer.getLabel();
        timer.setBorder(blackLine);
        add(timer);
        add(playButton(graphicalBasicTimer));
    }


    // EFFECTS: Add button for GroupTimers for ability to add BasicTimers
    //
    public JButton addAddButton(GroupTimer t) {
        JButton addButton = new JButton("Add BasicTimer into GroupTimer");
        addButton.setPreferredSize(new Dimension(BUTTON_WIDTH / 20, BUTTON_HEIGHT / 20));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBasicTimerToGroupTimer(t);
            }
        });
        return addButton;
    }

    // EFFECTS: Displays popup for adding BasicTimer to GroupTimer
    public Optional<String[]> addButtonGroupTimerInstructions() {
        JTextField addByTitle = new JTextField();

        JPanel allInstructions = new JPanel();
        allInstructions.setLayout(new GridLayout(0, 2));
        allInstructions.add(new JLabel("BasicTimer Title: "));
        allInstructions.add(addByTitle);

        Object[] specificOptions = {"Confirm",
                "Cancel"};

        int result = JOptionPane.showOptionDialog(this, allInstructions,
                "Add BasicTimer into GroupTimer", JOptionPane.OK_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                specificOptions,
                specificOptions[0]);

        if (result == JOptionPane.OK_OPTION) {
            String[] ans = new String[]{
                    addByTitle.getText(),
            };
            return Optional.of(ans);
        }

        return Optional.empty();
    }

    // EFFECTS: Completes action of adding BasicTimer to GroupTimer if valid input
    public void addBasicTimerToGroupTimer(GroupTimer t) {
        Optional<String[]> gt = addButtonGroupTimerInstructions();
        if (gt.isPresent()) {
            String[] ans = gt.get();
            BasicTimer ansObj = alreadyExistingBasicTimer(ans);
            if (ansObj == null) {
                JOptionPane.showMessageDialog(this,
                        "This BasicTimer does not exist",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            t.addTimer(ansObj);
            GraphicalGroupTimer ggt = (GraphicalGroupTimer) t;
            ggt.setTextGroupLabel(ansObj.getTitle());

            revalidate();
            repaint();
        }
    }

    // EFFECTS: Returns BasicTimer by title if it exists, otherwise null
    public BasicTimer alreadyExistingBasicTimer(String[] ans) {
        String title = ans[0];
        for (TimerTraits t : allTimers.getAllTimers()) {
            if (t instanceof BasicTimer && t.getTitle().equals(title)) {
                return (BasicTimer) t;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: playButton for TimerTraits subclasses and calls play() method depending on actual class
    public JButton playButton(TimerTraits t) {
        JButton playPause = new JButton("Play");
        playPause.setPreferredSize(new Dimension(BUTTON_WIDTH / 20, BUTTON_HEIGHT / 20));
        if (t instanceof BasicTimer) {
            BasicTimer bt = (BasicTimer) t;
            playPause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    bt.play();
                }
            });
        }

        if (t instanceof GroupTimer) {
            GroupTimer gt = (GroupTimer) t;
            playPause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gt.play();
                }
            });
        }

        return playPause;
    }

}


