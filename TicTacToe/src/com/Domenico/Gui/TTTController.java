package com.Domenico.Gui;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;

public class TTTController extends JLabel implements PropertyChangeListener, Serializable, VetoableChangeListener {

    private boolean activePlayer0;
    private boolean initialGame;

    public TTTController() {

        this.setVisible(true);
        this.setFont(new Font("Cambria Math", Font.PLAIN, 25));
        this.setOpaque(true);
        this.setBackground(Color.ORANGE);
        this.setMinimumSize(new Dimension(230,75));
        this.setPreferredSize(new Dimension(230, 75));
        this.setMaximumSize(new Dimension(230,75));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        this.initialGame = true;
        this.setHorizontalAlignment(SwingConstants.CENTER);

    }

    public void setInitialGame(boolean initialGame) {
        this.initialGame = initialGame;
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if (initialGame != true && ((activePlayer0 == true && evt.getNewValue() == "X") || (activePlayer0 == false && evt.getNewValue() == "O"))) {
            JOptionPane.showMessageDialog(null, "Not your turn");
            throw new PropertyVetoException("Not your turn", evt);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        initialGame = false;
        if (evt.getNewValue() == "X") {
            activePlayer0 = true;
            this.setFont(new Font("Cambria Math", Font.PLAIN, 27));
            this.setText("NEXT MOVE : O");
        }
        if (evt.getNewValue() == "O") {
            activePlayer0 = false;
            this.setFont(new Font("Cambria Math", Font.PLAIN, 27));
            this.setText("NEXT MOVE : X");
        }
    }
}
