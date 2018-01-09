package gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class GUI {
    boolean packFrame = false;

    /**
     * Construct the application
     */
    public GUI() {
        MainWindow frame = new MainWindow();
        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.testAndMakeReport. from their layout
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }
        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GUI();
    }
}