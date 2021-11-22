package com.robenhood.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class GUIMain extends JDialog {
    private JPanel contentPane;
    private JButton buttonQuit;
    private JTabbedPane tabbedPane1;
    private JTextField tickerTextField;
    private JList profileList;
    private JPanel labelPanel;
    private JLabel profileLabel;
    private JLabel currentTicker;
    private JButton changeTicker;
    private JList tradesList;
    private JButton updateList;

    public GUIMain() {
        setTitle("RoBen Hood");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonQuit);
        String [] ListData = {"Profile 1", "Profile 2", "Profile 3"};
        listUpdate(ListData);
        profileLabel.setText(ListData[0]);
        tradesList.setListData(ListData);

        profileList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selection = profileList.getSelectedValue().toString();
                profileLabel.setText(selection);
            }
        });

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
        changeTicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tickChange();
            }
        });
    }

    private void listUpdate(String [] ListData){
        profileList.setListData(ListData);
    }
    private void onQuit() {
        // add your code here if necessary
        dispose();
    }
    private void tickChange(){
        currentTicker.setText(tickerTextField.getText());
    }

    public static void main(String[] args) {
        GUIMain dialog = new GUIMain();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
