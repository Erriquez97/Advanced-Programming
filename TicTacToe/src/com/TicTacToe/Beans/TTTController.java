package com.TicTacToe.Beans;

import javax.swing.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;

public class TTTController extends JLabel implements PropertyChangeListener, Serializable, VetoableChangeListener {

    private boolean activePlayer0;
    // The variable initialGame is needed to know if the game is just started or not, because if the game is just started,
    // both the players are allowed to make the first move
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


    // it checks if the setState of the cell is allowed. In case it's PlayerO's turn and there is an attempt to set the
    // new state of the cell equal to X it throws a propertyVetoException(dually in case it's PlayerX's turn)
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if (initialGame != true && ((activePlayer0 == true && evt.getNewValue() == "X") || (activePlayer0 == false && evt.getNewValue() == "O"))) {
            JOptionPane.showMessageDialog(null, "Not your turn");
            throw new PropertyVetoException("Not your turn", evt);
        }
    }

    // Once the change of the cell's state is done, the following instructions will be executed:
    // it will be set to false the variable initial game and in case the new cell's state is X the activePlayer will be O,
    // So the variable activePlayer0 is set to true and the controller will display that the next move is O (dually in case the cell's state is O)
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
