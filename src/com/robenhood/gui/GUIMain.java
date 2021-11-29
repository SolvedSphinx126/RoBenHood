package com.robenhood.gui;

import com.robenhood.model.API;
import com.robenhood.model.Crypto;
import com.robenhood.model.Model;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.OffsetDateTime;

/**
 * Class to handle the displaying of a portfolio
 * @author Jeremiah Rhoton
 * @author Nathan Wilson
 */
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
    private JTextField cryptoTickerText;
    private JButton getCryptoPriceButton;
    private JTextField cryptoPriceTextArea;
    private JButton loadPortfoliosButton;
    private JTextField addMoneyAmount;
    private JButton confirmAddMoneyButton;
    private JTextField portfolioBalanceText;
    private JTextField portfolioValueText;
    private JButton saveButton;
    private JButton selectOrderButton;
    private JButton cancelOrderButton;
    private JTextArea selectedOrderText;

    private Model model;

    /**
     * Creates a new GUI given a model and a title
     * @param aModel The model to be displayed
     * @param title The title of the window
     */
    public GUIMain(Model aModel, String title) {
        model = aModel;

        this.setTitle(title);
        this.setContentPane(contentPane);
        this.getRootPane().setDefaultButton(buttonQuit);

        initContents();

        this.pack();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Initializes the contents of the window
     */
    private void initContents() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onQuit();
            }
        });

        buttonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onQuit();
            }
        });

        selectOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedOrderText.setText(ordersList.getSelectedValue().toString());
            }
        });

        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedOrderText.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please select an order to cancel");
                } else {
                    // Extract creation date from selected order
                    int beginIndex = selectedOrderText.getText().indexOf("CreateTime: ") + ("CreateTime: ".length());
                    int endIndex = selectedOrderText.getText().indexOf(",", beginIndex);
                    String date = selectedOrderText.getText().substring(beginIndex, endIndex);
                    System.out.println("Removing date: " + date + "\n   Parsed to: " + OffsetDateTime.parse(date));
                    model.cancelOrder(OffsetDateTime.parse(date));
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        confirmAddMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPort.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please select a portfolio to add money to");
                } else {
                    model.incrementCurrentPortfolioBalance(Double.parseDouble(addMoneyAmount.getText()));
                    update();
                }
            }
        });

        loadPortfoliosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePortfolioList();
            }
        });

        updateOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPort.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please select a portfolio to update orders of");
                } else {
                    update();
                    updateOrdersList();
                }
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
                update();
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
                    JOptionPane.showMessageDialog(contentPane, "Order successfully added!");
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
                } else if (cryptoTickerText.getText().equals("")) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter the crypto ticker for the name");
                } else {
                    cryptoPriceTextArea.setText("$" + String.format("%f", API.getCryptoValue(OffsetDateTime.now(), cryptoTickerText.getText())));
                }
            }
        });
    }

    /**
     * Updates the GUI's data
     */
    private void update() {
        if (selectedPort.getText().equals("")) {
            updatePortfolioList();
        } else {
            model.update();
            updatePortfolioList();
            updateAssetList();
            updateOrdersList();
            updateTransactionsList();
            updateBalance();
            updateValue();
        }
    }

    /**
     * Function to run before exiting the view
     */
    private void onQuit() {
        save();
        dispose();
    }

    /**
     * Function to save the model's data before closing
     */
    private void save() {
        update();
        if (!selectedPort.getText().equals("")) {
            model.saveCurrentPortfolio();
        }
    }

    /**
     * Updates the portfolio's balance viewer
     */
    private void updateBalance() {
        portfolioBalanceText.setText("$" + model.getCurrentPortfolioBalance());
    }

    /**
     * Updates the portfolio's value viewer
     */
    private void updateValue() {
        portfolioValueText.setText("$" + model.getCurrentPortfolioTotalValue());
    }

    /**
     * Updates the viewer of the orders
     */
    private void updateOrdersList() {
        ordersList.setListData(model.getCurrentPortfolioOrders().toArray());
    }

    /**
     * Updates the viewer of transactions
     */
    private void updateTransactionsList() {
        completedTransactionsList.setListData(model.getCurrentPortfolioCompletedTransactions().toArray());
        expiredTransactionsList.setListData(model.getCurrentPortfolioExpiredTransactions().toArray());
    }

    /**
     * Updates the viewer of transactions
     */
    private void updateAssetList() {
        assetList.setListData(model.getCurrentPortfolioAssets().toArray());
    }

    /**
     * Updates the viewer of portfolio list
     */
    private void updatePortfolioList() {
        profileList.setListData(model.getPotentialPortfolios().toArray());
    }
}
