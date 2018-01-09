package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.StringTokenizer;

/**
 * @author Vahid Mavaji
 * @version 1.0 2004
 */
public class MainWindow extends JFrame {
    JPanel contentPane;
    TextArea textArea1 = new TextArea();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenu1 = new JMenu();
    JMenu jMenu2 = new JMenu();
    JMenuItem jMenuItem1 = new JMenuItem();
    JMenuItem jMenuItem2 = new JMenuItem();
    JMenuItem jMenuItem3 = new JMenuItem();
    JMenuItem jMenuItem4 = new JMenuItem();
    JMenu jMenu3 = new JMenu();
    JMenuItem jMenuItem5 = new JMenuItem();

    private String vFilePath = new String();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JLabel jLabel9 = new JLabel();
    JLabel jLabel10 = new JLabel();
    JLabel jLabel11 = new JLabel();
    JLabel jLabel12 = new JLabel();

    /**
     * Construct the frame
     */
    public MainWindow() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
            AboutWindow aboutWindow = new AboutWindow();
            aboutWindow.initiate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Component initialization
     */
    private void jbInit() throws Exception {
        textArea1.setBounds(new Rectangle(19, 57, 474, 325));
        //setIconImage(Toolkit.getDefaultToolkit().createImage(MainWindow.class.getResource("[Your Icon]")));
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(null);
        this.getContentPane().setBackground(SystemColor.inactiveCaption);
        this.setJMenuBar(jMenuBar1);
        this.setResizable(false);
        this.setSize(new Dimension(764, 490));
        this.setTitle("Fault Detection Utility...");
        jMenu1.setText("File");
        jMenu2.setText("Help");
        jMenuItem1.setText("About...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItem1_actionPerformed(e);
            }
        });
        jMenuItem2.setText("Load");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItem2_actionPerformed(e);
            }
        });
        jMenuItem3.setText("Save");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItem3_actionPerformed(e);
            }
        });
        jMenuItem4.setText("Exit");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItem4_actionPerformed(e);
            }
        });
        jMenu3.setText("Test");
        jMenuItem5.setText("Do Test Generation!");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItem5_actionPerformed(e);
            }
        });
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel1.setForeground(Color.orange);
        jLabel1.setText("Syntax of H/W description is:");
        jLabel1.setBounds(new Rectangle(506, 49, 171, 36));
        jLabel2.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel2.setForeground(Color.white);
        jLabel2.setText("GateType [#delay] [transport]");
        jLabel2.setBounds(new Rectangle(514, 74, 180, 32));
        jLabel3.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel3.setForeground(Color.white);
        jLabel3.setText("[GateName] (Output,Input1,Input2,...)");
        jLabel3.setBounds(new Rectangle(516, 98, 209, 26));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel4.setForeground(Color.orange);
        jLabel4.setText("Syntax of delay is:");
        jLabel4.setBounds(new Rectangle(509, 139, 157, 24));
        jLabel5.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel5.setForeground(Color.white);
        jLabel5.setText("(rise[,fall,turnoff])");
        jLabel5.setBounds(new Rectangle(520, 163, 124, 24));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel6.setForeground(Color.orange);
        jLabel6.setText("Each of rise, fall or turnoff syntax is:");
        jLabel6.setBounds(new Rectangle(508, 204, 204, 22));
        jLabel7.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel7.setForeground(Color.white);
        jLabel7.setText("min[:typ:max]");
        jLabel7.setBounds(new Rectangle(515, 225, 79, 22));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel8.setForeground(Color.orange);
        jLabel8.setText("Examples:");
        jLabel8.setBounds(new Rectangle(508, 263, 87, 21));
        jLabel9.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel9.setForeground(Color.white);
        jLabel9.setText("and #(1:2:3,4:5:6) transport and0_2 (k,e,f)");
        jLabel9.setBounds(new Rectangle(517, 282, 239, 21));
        jLabel10.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel10.setForeground(Color.white);
        jLabel10.setText("or #(2) or1_1 (n,k,l)");
        jLabel10.setBounds(new Rectangle(516, 302, 124, 22));
        jLabel11.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel11.setForeground(Color.white);
        jLabel11.setText("and (j,c,d)");
        jLabel11.setBounds(new Rectangle(517, 326, 65, 17));
        jLabel12.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel12.setForeground(Color.green);
        jLabel12.setText("Note: if the code is not saved, befor running test generation you " +
                "must save it.");
        jLabel12.setBounds(new Rectangle(22, 398, 475, 18));
        contentPane.add(textArea1, null);
        contentPane.add(jLabel12, null);
        contentPane.add(jLabel1, null);
        contentPane.add(jLabel2, null);
        contentPane.add(jLabel3, null);
        contentPane.add(jLabel4, null);
        contentPane.add(jLabel5, null);
        contentPane.add(jLabel6, null);
        contentPane.add(jLabel7, null);
        contentPane.add(jLabel8, null);
        contentPane.add(jLabel9, null);
        contentPane.add(jLabel10, null);
        contentPane.add(jLabel11, null);
        jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu3);
        jMenuBar1.add(jMenu2);
        jMenu2.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jMenuItem3);
        jMenu1.addSeparator();
        jMenu1.add(jMenuItem4);
        jMenu3.add(jMenuItem5);
    }

    /**
     * Overridden so we can exit when window is closed
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    void jMenuItem1_actionPerformed(ActionEvent e) {
        AboutWindow aboutWindow = new AboutWindow();
        aboutWindow.initiate();
    }

    void jMenuItem4_actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void jMenuItem2_actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int state;
        do {
            state = fileChooser.showOpenDialog(this);
        } while (!(state == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()
                || state == JFileChooser.CANCEL_OPTION));
        if (state == JFileChooser.APPROVE_OPTION) {
            vFilePath = new String(fileChooser.getSelectedFile().getPath());
            try {
                BufferedReader reader = new BufferedReader(new FileReader(vFilePath));
                textArea1.setText("");
                String line = new String();
                while (reader.ready()) {
                    line += reader.readLine() + "\n";
                }
                textArea1.setText(line);
                reader.close();
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
    }

    void jMenuItem3_actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int state;
        do {
            state = fileChooser.showSaveDialog(this);
        } while (!(state == JFileChooser.APPROVE_OPTION
                || state == JFileChooser.CANCEL_OPTION));
        if (state == JFileChooser.APPROVE_OPTION) {
            try {
                File f = fileChooser.getSelectedFile();
                f.createNewFile();
                vFilePath = f.getPath();

                String s = textArea1.getText();
                BufferedWriter wr = new BufferedWriter(new FileWriter(f));

                StringTokenizer st = new StringTokenizer(s, "\n");
                if (st.hasMoreTokens()) {
                    wr.write(st.nextToken());
                }
                while (st.hasMoreTokens()) {
                    wr.newLine();
                    wr.write(st.nextToken());
                }
                wr.close();
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
    }

    void jMenuItem5_actionPerformed(ActionEvent e) {
        TestWindow testWindow = new TestWindow(vFilePath);
        testWindow.initiate();
    }
}