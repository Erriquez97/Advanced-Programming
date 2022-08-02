package com.TicTacToe.MouseListener;

import com.TicTacToe.TTTBoard;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

public class MouseListener extends MouseAdapter {

    private TTTBoard board;

    public MouseListener(TTTBoard board) {
        this.board = board;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == board.getCells()[i][j].getButtonO() && board.getCells()[i][j].getButtonO().isEnabled()) {
                    try {
                        board.getCells()[i][j].setState("O");
                        // so after changed the state of the cell clicked, if there is not a winner that cell will be
                        // colored with the color associated to the player
                        if (board.getWinner() == false) {
                            board.getCells()[i][j].getButtonO().setBackground(Color.CYAN);
                        }
                        board.getCells()[i][j].getButtonX().setEnabled(false);
                        board.getCells()[i][j].getButtonX().setText("");
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                } else if (e.getSource() == board.getCells()[i][j].getButtonX() && board.getCells()[i][j].getButtonX().isEnabled()) {
                    try {
                        board.getCells()[i][j].setState("X");
                        // so after changed the state of the cell clicked, if there is not a winner that cell will be
                        // colored with the color associated to the player
                        if (board.getWinner() == false) {
                            board.getCells()[i][j].getButtonX().setBackground(Color.yellow);
                        }
                        //disable the button not clicked of the cell
                        board.getCells()[i][j].getButtonO().setEnabled(false);
                        // set the text of the button not clicked to empty
                        board.getCells()[i][j].getButtonO().setText("");
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}