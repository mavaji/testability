package gui;

import testsystem.TestSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * @author Vahid Mavaji
 * @version 1.0 2004
 */

public class TestWindow extends JDialog {
    JPanel panel = new JPanel();
    JTextField txtReportPath = new JTextField();
    JTextField txtThreshold = new JTextField();
    JButton btnBrowse = new JButton();
    JLabel lblReportPath = new JLabel();
    JLabel lblThreshold = new JLabel();
    JButton btnOK = new JButton();

    private String vFilePath;
    private TestSystem testSystem;

    public TestWindow(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public TestWindow(String vFilePath) {
        this(null, "", false);
        this.vFilePath = new String(vFilePath);
    }

    void jbInit() throws Exception {
        panel.setLayout(null);
        this.setResizable(false);
        this.getContentPane().setBackground(SystemColor.inactiveCaption);
        this.setModal(true);
        panel.setBackground(SystemColor.inactiveCaption);
        txtReportPath.setEnabled(true);
        txtReportPath.setEditable(false);
        txtReportPath.setBounds(new Rectangle(18, 62, 300, 26));
        txtThreshold.setBounds(new Rectangle(21, 129, 103, 24));
        btnBrowse.setText("Browse...");
        btnBrowse.setBounds(new Rectangle(336, 60, 92, 29));
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBrowse_actionPerformed(e);
            }
        });
        lblReportPath.setForeground(Color.white);
        lblReportPath.setText("Report File");
        lblReportPath.setBounds(new Rectangle(20, 38, 82, 20));
        lblThreshold.setForeground(Color.white);
        lblThreshold.setText("Fault Coverage required for Pseudo Random TG");
        lblThreshold.setBounds(new Rectangle(21, 102, 284, 22));
        btnOK.setText("OK");
        btnOK.setBounds(new Rectangle(172, 183, 79, 27));
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnOK_actionPerformed(e);
            }
        });
        getContentPane().add(panel);
        panel.add(txtReportPath, null);
        panel.add(lblReportPath, null);
        panel.add(lblThreshold, null);
        panel.add(txtThreshold, null);
        panel.add(btnOK, null);
        panel.add(btnBrowse, null);
    }

    public void initiate() {
        this.setSize(450, 260);
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


    void btnOK_actionPerformed(ActionEvent e) {
        String reportPath = txtReportPath.getText();
        String thresholdStr = txtThreshold.getText();

        if (reportPath != null && thresholdStr != null) {
            testSystem = new TestSystem(vFilePath, reportPath, Double.valueOf(thresholdStr).doubleValue());
            testSystem.testAndMakeReport();
            this.setVisible(false);
        }
    }

    void btnBrowse_actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int state;

        do {
            state = fileChooser.showSaveDialog(this);
        } while (!(state == JFileChooser.APPROVE_OPTION || state == JFileChooser.CANCEL_OPTION));

        if (state == JFileChooser.APPROVE_OPTION) {
            try {
                File f = fileChooser.getSelectedFile();
                f.createNewFile();
                txtReportPath.setText(f.getPath());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}