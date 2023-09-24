package ui.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

// Class for specifically displaying buttons and timers
// clockImg Image found on: https://www.vecteezy.com/free-vector/timer
// multiClock Image found on: https://play.google.com/store/apps/details?id=com.persapps.multitimer&hl=en&pli=1
public class DisplayAll extends JPanel implements ActionListener {

    private TimerAppGUI app;
    private JButton basicCreate;
    private JButton groupCreate;
    private JButton saveTimers;
    private JButton loadTimers;
    protected static final String saveCommand = "Save Timers";
    protected static final String loadCommand = "Load Timers";

    protected static final int BUTTON_WIDTH = 200;
    protected static final int BUTTON_HEIGHT = 2;
    protected static ImageIcon clockImg =
            new ImageIcon("data/timer-stopwatch-outline-style-icon-vector.jpg");
    protected static ImageIcon multiClock =
            new ImageIcon("data/multi-timer.png");


    // EFFECTS: In charge of entire display
    public DisplayAll(TimerAppGUI app) {
        this.app = app;
        setLayout(new GridLayout(14, 1, 0, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        initializeOptions();
    }

    // EFFECTS: Loads all buttons for display
    public void initializeOptions() {
        initializeCreate();
    }

    private void initializeCreate() {
        basicCreate = new JButton("Create new BasicTimer");
        basicCreate.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        basicCreate.addActionListener(this);

        groupCreate = new JButton("Create new GroupTimer");
        groupCreate.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        groupCreate.addActionListener(this);

        saveTimers = new JButton(saveCommand);
        saveTimers.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        saveTimers.addActionListener(this);

        loadTimers = new JButton(loadCommand);
        loadTimers.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        loadTimers.addActionListener(this);


        add(basicCreate);
        add(groupCreate);
        add(saveTimers);
        add(loadTimers);
    }


    // MODIFIES: this, app
    // EFFECTS: Adds new BasicTimer to the GUI based on input from addBasicTimerInstructions
    public void addNewBasicTimerAction() {
        Optional<String[]> bt = addBasicTimerInstructions();
        if (bt.isPresent()) {
            String[] ans = bt.get();
            if (!validBasicTimer(ans)) {
                JOptionPane.showMessageDialog(this,
                        "You need a title AND Seconds must be an integer",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String title = ans[0];
            String desc = ans[1];
            int seconds = Integer.parseInt(ans[2]);
            GraphicalBasicTimer graphicalBasicTimer = new GraphicalBasicTimer(seconds, title, desc);
            app.getAllTimers().addTimer(graphicalBasicTimer);
            app.test.addBasicTimer(graphicalBasicTimer);

            revalidate();
            repaint();
        }
    }

    // MODIFIES: this, app
    // EFFECTS: Adds new GroupTimer to the GUI based on input from addBasicTimerInstructions
    public void addNewGroupTimerAction() {
        Optional<String[]> gt = addNewGroupTimerInstructions();
        if (gt.isPresent()) {
            String[] ans = gt.get();
            if (!validGroupTimer(ans)) {
                JOptionPane.showMessageDialog(this,
                        "You need a title",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String title = ans[0];
            String desc = ans[1];
            app.getAllTimers().addTimer(new GraphicalGroupTimer(title, desc));
            app.displayTimers();

            revalidate();
            repaint();
        }
    }


    // EFFECTS: Creates prompt to make a new BasicTimer, returns user input
    private Optional<String[]> addBasicTimerInstructions() {
        JTextField title = new JTextField();
        JTextField description = new JTextField();
        JTextField seconds = new JTextField();

        JPanel allInstructions = new JPanel();
        allInstructions.setLayout(new GridLayout(0, 2));
        allInstructions.add(new JLabel("Title: "));
        allInstructions.add(title);
        allInstructions.add(new JLabel("Description: "));
        allInstructions.add(description);
        allInstructions.add(new JLabel("Seconds: "));
        allInstructions.add(seconds);

        int result = createJOptionPane(allInstructions, clockImg);

        if (result == JOptionPane.OK_OPTION) {
            String[] ans = new String[]{
                    title.getText(),
                    description.getText(),
                    seconds.getText()
            };
            return Optional.of(ans);
        }

        return Optional.empty();
    }

    // EFFECTS: Creates prompt to make a new GroupTimer, returns user input
    public Optional<String[]> addNewGroupTimerInstructions() {
        JTextField title = new JTextField();
        JTextField description = new JTextField();

        JPanel allInstructions = new JPanel();
        allInstructions.setLayout(new GridLayout(0, 2));
        allInstructions.add(new JLabel("Title: "));
        allInstructions.add(title);
        allInstructions.add(new JLabel("Description: "));
        allInstructions.add(description);

        int result = createJOptionPane(allInstructions, multiClock);

        if (result == JOptionPane.OK_OPTION) {
            String[] ans = new String[]{
                    title.getText(),
                    description.getText(),
            };
            return Optional.of(ans);
        }
        return Optional.empty();
    }


    // EFFECTS: Returns true if input given is valid to create a new BasicTimer
    public boolean validBasicTimer(String[] ans) {
        try {
            Integer.parseInt(ans[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        return !ans[0].isEmpty() && !ans[1].isEmpty();
    }

    // EFFECTS: Returns true if input given is valid to create a new GroupTimer
    public boolean validGroupTimer(String[] ans) {
        return ans != null;
    }

    // MODIFIES: this
    // EFFECTS: Rescales an ImageIcon to fit popup
    public static ImageIcon rescaleImage(ImageIcon img) {
        int size = 50;
        Image tempImg = img.getImage();
        Image newImg = tempImg.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }


    // EFFECTS: Creates JOptionPane based on JPanel and image
    public int createJOptionPane(JPanel allInstructions, ImageIcon img) {

        Object[] specificOptions = {"Create", "Cancel"};

        int ans = JOptionPane.showOptionDialog(app, allInstructions,
                "Add GroupTimer", JOptionPane.OK_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                rescaleImage(img),
                specificOptions,
                specificOptions[0]);

        return ans;
    }

    // EFFECTS: Listener for JButtons
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Create new BasicTimer":
                addNewBasicTimerAction();
                break;
            case "Create new GroupTimer":
                addNewGroupTimerAction();
                break;
            case saveCommand:
                app.saveAllTimersGraphical();
                break;
            case loadCommand:
                app.loadAllTimersGraphical();
                app.displayTimers();
                revalidate();
                repaint();
                break;
        }

    }


}
