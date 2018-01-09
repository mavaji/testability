package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Vahid Mavaji
 * @version 1.0 2004
 */

public class AboutWindow extends JDialog {
    JPanel panel = new JPanel();
    JLabel lblVahidMavaji = new JLabel();
    JLabel lblTGSystem = new JLabel();
    JLabel lblProjectFor = new JLabel();
    JLabel lblTestability = new JLabel();
    JLabel lblProfessor = new JLabel();
    JLabel lblShahin = new JLabel();
    JLabel lblFall2004 = new JLabel();
    JLabel lblSharif = new JLabel();
    Component component1;

    public AboutWindow(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AboutWindow() {
        this(null, "", false);
    }

    void jbInit() throws Exception {
        component1 = Box.createGlue();
        panel.setLayout(null);
        this.setResizable(false);
        this.getContentPane().setBackground(SystemColor.inactiveCaption);
        this.setModal(true);
        this.setTitle("");
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                this_mouseClicked(e);
            }
        });
        this.getContentPane().setLayout(null);
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setBounds(new Rectangle(10, 10, 246, 268));
        lblVahidMavaji.setBackground(SystemColor.inactiveCaption);
        lblVahidMavaji.setFont(new java.awt.Font("Dialog", 2, 18));
        lblVahidMavaji.setForeground(Color.green);
        lblVahidMavaji.setText("Vahid Mavaji\'s");
        lblVahidMavaji.setBounds(new Rectangle(50, 2, 126, 37));
        lblTGSystem.setFont(new java.awt.Font("Dialog", 0, 20));
        lblTGSystem.setForeground(Color.yellow);
        lblTGSystem.setText("Test Generation System");
        lblTGSystem.setBounds(new Rectangle(3, 29, 218, 36));
        lblProjectFor.setFont(new java.awt.Font("Dialog", 2, 12));
        lblProjectFor.setForeground(Color.white);
        lblProjectFor.setText("a project for");
        lblProjectFor.setBounds(new Rectangle(76, 91, 81, 19));
        lblTestability.setFont(new java.awt.Font("Dialog", 1, 16));
        lblTestability.setForeground(Color.orange);
        lblTestability.setText("Testability Course");
        lblTestability.setBounds(new Rectangle(42, 108, 149, 23));
        lblProfessor.setFont(new java.awt.Font("Dialog", 2, 12));
        lblProfessor.setForeground(Color.white);
        lblProfessor.setText("Professor :");
        lblProfessor.setBounds(new Rectangle(78, 147, 63, 18));
        lblShahin.setFont(new java.awt.Font("Dialog", 1, 16));
        lblShahin.setForeground(Color.orange);
        lblShahin.setText("Dr. Shahin Hessabi");
        lblShahin.setBounds(new Rectangle(39, 165, 157, 18));
        lblFall2004.setFont(new java.awt.Font("Dialog", 2, 12));
        lblFall2004.setForeground(Color.white);
        lblFall2004.setText("Fall 2004");
        lblFall2004.setBounds(new Rectangle(80, 211, 56, 15));
        lblSharif.setFont(new java.awt.Font("Dialog", 2, 12));
        lblSharif.setForeground(Color.white);
        lblSharif.setText("Sharif University of Technology");
        lblSharif.setBounds(new Rectangle(28, 230, 180, 17));
        component1.setBounds(new Rectangle(136, 22, 0, 0));
        getContentPane().add(panel, null);
        panel.add(component1, null);
        panel.add(lblVahidMavaji, null);
        panel.add(lblTGSystem, null);
        panel.add(lblProjectFor, null);
        panel.add(lblTestability, null);
        panel.add(lblProfessor, null);
        panel.add(lblShahin, null);
        panel.add(lblFall2004, null);
        panel.add(lblSharif, null);
    }

    public void initiate() {
        this.setSize(250, 310);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        this.setVisible(true);
    }

    void this_mouseClicked(MouseEvent e) {
        this.setVisible(false);
    }
}