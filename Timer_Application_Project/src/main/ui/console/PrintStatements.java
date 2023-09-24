package ui.console;

import model.BasicTimer;

// Class to hold all println statements that need be printed
public class PrintStatements {

    // EFFECTS: prints out seconds remaining of a Timer
    public static void printSeconds(BasicTimer t) {
        System.out.println(t.getSeconds());

    }

    // EFFECTS: prints a message for when timer is finished
    public static void completeTimer() {
        System.out.println("TIMER IS UP!");
    }

}
