package com.TicTacToe.Beans;

import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.io.Serializable;

public class TTTCell extends JPanel implements Serializable {

    private JButton buttonX;
    private JButton buttonO;
    private String state;
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private VetoableChangeSupport vetos = new VetoableChangeSupport(this);
    private GridBagConstraints gbc = new GridBagConstraints();


    public TTTCell() {
        this.setVisible(true);
        this.setLayout(new GridBagLayout());
        // row and column for the buttonX
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.setBackground(new Color(65, 91, 255));
        this.state = "Initial";
        this.setPreferredSize(new Dimension(105, 105));
        this.buttonX = new JButton("X");
        this.buttonX.setBackground(Color.lightGray);
        this.buttonX.setFont(new Font("Arial Black", Font.PLAIN, 40));
        this.buttonX.setPreferredSize(new Dimension(this.getPreferredSize().width, this.getPreferredSize().height / 2));
        this.buttonX.setFocusPainted(false);
        this.buttonX.setBorderPainted(false);
        this.add(this.buttonX, gbc);

        // row and column for the buttonO
        gbc.gridx = 0;
        gbc.gridy = 1;
        // it adds a little padding between the 2 buttons
        gbc.insets = new Insets(2, 0, 0, 0);
        this.buttonO = new JButton("O");
        this.buttonO.setFont(new Font("Arial Black", Font.PLAIN, 40));
        this.buttonO.setBackground(Color.lightGray);
        this.buttonO.setPreferredSize(new Dimension(this.getPreferredSize().width, this.getPreferredSize().height / 2));
        this.buttonO.setFocusPainted(false);
        this.buttonO.setBorderPainted(false);
        this.add(this.buttonO, gbc);

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }

    public void addVetoableChangelistener(VetoableChangeListener listener) {
        vetos.addVetoableChangeListener(listener);
    }

    public void removeVetoableChangelistener(VetoableChangeListener listener) {
        vetos.removeVetoableChangeListener(listener);
    }

    public JButton getButtonX() {
        return buttonX;
    }

    public void setButtonX(JButton buttonX) {
        this.buttonX = buttonX;
    }

    public JButton getButtonO() {
        return buttonO;
    }

    public void setButtonO(JButton buttonO) {
        this.buttonO = buttonO;
    }

    public String getState() {
        return state;
    }

    // function that sets the state of the cell, that can be initial, X or O. If the change is allowed from the vetoes then
    // the change is made and it will communicate to the listeners that the change of the state has been done.
    public void setState(String newState) throws PropertyVetoException {
        String oldState = state;
        try {
            this.vetos.fireVetoableChange("state", oldState, newState);
            state = newState;
            this.changes.firePropertyChange("state", oldState, newState);
        } catch (PropertyVetoException e) {
            throw e;
        }
    }
}