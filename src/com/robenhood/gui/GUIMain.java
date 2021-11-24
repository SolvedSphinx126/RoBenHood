package com.robenhood.gui;

import com.robenhood.model.API;
import com.robenhood.model.Crypto;
import com.robenhood.model.Model;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.OffsetDateTime;
import java.util.Spliterator;

public class GUIMain extends JFrame {
    private JPanel contentPane;
    private JButton buttonQuit;
    private JTabbedPane tabbedPane1;
    private JList profileList;
    private JPanel labelPanel;
    private JLabel selectedPort;
    private JButton newPortButton;
    private JButton deletePortButton;
    private JTextField newPortField;
    private JList assetList;
    private JButton selectPortButton;
    private JTextField deletePortName;
    private JList ordersList;
    private JComboBox orderTypeBox;
    private JTextField orderCryptoName;
    private JTextField orderCryptoTicker;
    private JCheckBox buyBooleanBox;
    private JTextField orderPriceTextField;
    private JTextField orderAmountText;
    private JComboBox orderExpireBox;
    private JButton addOrderButton;
    private JButton updateOrdersButton;
    private JList completedTransactionsList;
    private JList expiredTransactionsList;
    private JButton updateTransactionsButton;
    private JButton updateButton;
    private JTextField cryptoNameText;
    private JTextField crytpoTickerText;
    private JButton getCryptoPriceButton;
    private JTextArea cryptoPriceTextArea;
    private JButton updateList;

    private Model model;

    public GUIMain(Model aModel, String title) {
        model = aModel;

        this.setTitle(title);
        this.setContentPane(contentPane);
        this.getRootPane().setDefaultButton(buttonQuit);

        initContents();

        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initContents() {
        buttonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onQuit();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        newPortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Test for empty text first
                if (!newPortField.getText().equals("")) {
                    model.createNewPortfolio(newPortField.getText());
                    update();
                }
            }
        });

        selectPortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPort.setText(profileList.getSelectedValue().toString());
                model.setCurrentPortfolio(selectedPort.getText());
            }
        });

        deletePortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!deletePortName.getText().equals("")) {
                    // Cannot delete the selected portfolio
                    if (selectedPort.getText().equals(deletePortName.getText())) {
                        JOptionPane.showMessageDialog(contentPane, "Cannot delete the currently selected portfolio \"" + deletePortName.getText() + "\"");
                    } else {
                        model.deletePortfolio(deletePortName.getText());
                        JOptionPane.showMessageDialog(contentPane, "Successfully deleted portfolio \"" + deletePortName.getText() + "\"");
                        update();
                    }
                }
            }
        });

        // Adds an order, builds off of all the entered data
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPort.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please select a portfolio to add the order to");
                } else if (orderTypeBox.getSelectedItem().toString().equals("Limit Trade")) {
                    if (orderPriceTextField.getText().equals("")) {
                        JOptionPane.showMessageDialog(contentPane, "Please enter a price");
                    }
                } else if (orderCryptoName.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter a crypto name");
                } else if (orderCryptoTicker.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter a crypto ticker");
                } else if (orderPriceTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter a price");
                } else if (orderAmountText.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter an amount, can be set to 0 for market trades");
                } else {
                    OffsetDateTime time = OffsetDateTime.now();
                    switch (orderExpireBox.getSelectedItem().toString()) {
                        case "1 (day)":
                            time = time.plusDays(1);
                            break;
                        case "2 (days)":
                            time = time.plusDays(2);
                            break;
                        case "3 (days)":
                            time = time.plusDays(3);
                            break;
                        case "4 (days)":
                            time = time.plusDays(4);
                            break;
                        case "5 (days)":
                            time = time.plusDays(5);
                            break;
                        case "6 (days)":
                            time = time.plusDays(6);
                            break;
                        case "7 (days)":
                            time = time.plusDays(7);
                            break;
                        case "2 (weeks)":
                            time = time.plusWeeks(2);
                            break;
                        case "3 (weeks)":
                            time = time.plusWeeks(3);
                            break;
                        case "1 (month)":
                            time = time.plusWeeks(4);
                            break;
                    }
                    model.addOrder(orderTypeBox.getSelectedItem().toString(), new Crypto(orderCryptoName.getText(), orderCryptoTicker.getText()), buyBooleanBox.isSelected(),
                            Double.parseDouble(orderPriceTextField.getText()), time, Double.parseDouble(orderAmountText.getText()));
                }
            }
        });

        updateTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPort.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please select a portfolio for which to update transactions");
                } else {
                    updateTransactionsList();
                }
            }
        });

        getCryptoPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cryptoNameText.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter a crypto name");
                } else if (crytpoTickerText.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter the crypto ticker for the name");
                } else {
                    cryptoPriceTextArea.setText("$" + API.getCryptoValue(OffsetDateTime.now(), crytpoTickerText.getText()));
                }
            }
        });
    }

    private void update() {
        model.update();
        updatePortfolioList();
        updateAssetList();
    }

    private void onQuit() {
        dispose();
    }

    private void updateTransactionsList() {
        completedTransactionsList.setListData(model.getCurrentPortfolioCompletedTransactions().toArray());
        expiredTransactionsList.setListData(model.getCurrentPortfolioExpiredTransactions().toArray());
    }

    private void updateAssetList() {
        assetList.setListData(model.getCurrentPortfolioAssets().toArray());
    }

    private void updatePortfolioList() {
        profileList.setListData(model.getPotentialPortfolios().toArray());
    }
}
