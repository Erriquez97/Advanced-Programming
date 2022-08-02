package com.TicTacToe.Gui;

import com.TicTacToe.MouseListener.MouseListener;
import com.TicTacToe.TTTBoard;
import com.TicTacToe.Beans.TTTCell;
import com.TicTacToe.Beans.TTTController;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {

    public GridPanel(TTTController controller, TTTBoard board, MouseListener mouseListener) {

        this.setLayout(new GridLayout(3, 3, 0, 0));
        this.setMaximumSize(new Dimension(350,350));
        this.setBackground(new Color(65, 91, 255));
        //this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(65, 91, 255)));

        // it creates the 9 cells and to each of them add the change listeners and to the 2 buttons of each cell add the mouse listener
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.getCells()[i][j] = new TTTCell();
                board.getCells()[i][j].addPropertyChangeListener(controller);
                board.getCells()[i][j].addPropertyChangeListener(board);
                board.getCells()[i][j].addVetoableChangelistener(controller);
                board.getCells()[i][j].getButtonX().addMouseListener(mouseListener);
                board.getCells()[i][j].getButtonO().addMouseListener(mouseListener);
                this.add(board.getCells()[i][j]);
            }
        }
    }
}