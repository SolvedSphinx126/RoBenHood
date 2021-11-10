package com.robenhood.gui;

import javax.swing.*;
import java.awt.event.*;

public class GUIMain extends JDialog {
    private JPanel contentPane;
    private JButton buttonQuit;
    private JTabbedPane tabbedPane1;
    private JTextField tickerTextField;

    public GUIMain() {
        setTitle("RoBen Hood");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonQuit);


        buttonQuit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onQuit();
            }
        });

        // call onQuit() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onQuit();
            }
        });

        // call onQuit() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onQuit();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onQuit() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        GUIMain dialog = new GUIMain();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
