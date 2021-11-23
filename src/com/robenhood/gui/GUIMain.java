package com.robenhood.gui;

import com.robenhood.model.Model;
import com.robenhood.model.Portfolio;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.*;

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
    private JList list1;
    private JButton updateList;

    public GUIMain() {
        setTitle("RoBen Hood");
        setContentPane(contentPane);
        Model userModel = new Model();
        setModal(true);
        getRootPane().setDefaultButton(buttonQuit);
        ArrayList <String> ListData = new ArrayList<String>();
        ArrayList <String> TradeData = new ArrayList<String>();
        ListData.add("Profile 1");
        ListData.add("Profile 2");
        ListData.add("Profile 3");
        profileUpdate(ListData);
        TradeData.add("Trade1   Value1  Date1   Complete");
        TradeData.add("Trade2   Value2  Date2   Canceled");
        TradeData.add("Trade3   Value3  Date3   Incomplete");
        tradeUpdate(TradeData);
        profileLabel.setText(ListData.get(0));
        tradesList.setListData(TradeData.toArray());

        profileList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selection = profileList.getSelectedValue().toString();
                //userModel.setCurrentPortfolio(selection);
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

    private void profileUpdate(ArrayList <String> ListData){
        profileList.setListData(ListData.toArray());
    }
    private void onQuit() {
        // add your code here if necessary
        dispose();
    }
    private void tickChange(){
        currentTicker.setText(tickerTextField.getText());
    }

    private void tradeUpdate(ArrayList <String> TradeData){
        tradesList.setListData(TradeData.toArray());
    }

    public static void main(String[] args) {
        GUIMain dialog = new GUIMain();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
