package com.TicTacToe;

import com.TicTacToe.Beans.TTTCell;
import com.TicTacToe.Beans.TTTController;
import com.TicTacToe.Gui.FooterPanel;
import com.TicTacToe.Gui.GridPanel;
import com.TicTacToe.MouseListener.MouseListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;


public class TTTBoard extends JFrame implements PropertyChangeListener {

    private TTTController controller = new TTTController();
    private GridPanel gridPanel;
    private FooterPanel footerPanel;
    private GridBagConstraints gbc = new GridBagConstraints();
    private TTTCell[][] cells = new TTTCell[3][3];
    private boolean winner;
    private MouseListener mouseListener;


    public TTTBoard() throws HeadlessException {

        this.setTitle("Tic Tac Toe");
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(500, 600);
        this.setLocation(460, 80);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(65, 91, 255)));
        this.winner = false;
        this.mouseListener= new MouseListener(this);
        //it creates an invisible space between the top border of the frame and the gridPanel
        this.add(Box.createRigidArea(new Dimension(this.getSize().width, 30)));
        this.gridPanel = new GridPanel(this.controller, this, this.mouseListener);

        this.add(gridPanel);
        //it creates an invisible space between the gridPanel and the footerPanel
        this.add(Box.createRigidArea(new Dimension(this.getSize().width, 30)));
        this.footerPanel = new FooterPanel(this.controller,this);
        this.footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(this.footerPanel);
        this.controller.setText("START GAME");

    }

    //It gets notified that the state of the cell has been modified and it checks if there is a tris or if the grid got
    //full without a winner, so in the last case there is a tie
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() == "X") {
            if (this.checkTrisHorizontal() >= 0 || this.checkTrisVertical() >= 0 || this.checkTrisDiagonal() >= 0) {
                this.winner = true;

                this.disableButtons();
                this.controller.setFont(new Font("Cambria Math", Font.PLAIN, 23));
                this.controller.setText("THE WINNER IS: X");
                if (this.checkTrisHorizontal() == 0) {
                    this.setRowWinning("X", 0);
                }
                if (this.checkTrisHorizontal() == 1) {
                    this.setRowWinning("X", 1);
                }
                if (this.checkTrisHorizontal() == 2) {
                    this.setRowWinning("X", 2);
                }
                if (this.checkTrisVertical() == 0) {
                    this.setColumnWinning("X", 0);
                }
                if (this.checkTrisVertical() == 1) {
                    this.setColumnWinning("X", 1);
                }
                if (this.checkTrisVertical() == 2) {
                    this.setColumnWinning("X", 2);
                }
                if (this.checkTrisDiagonal() == 1) {
                    this.setDiagonalWinning("X", 1);
                }
                if (this.checkTrisDiagonal() == 2) {
                    this.setDiagonalWinning("X", 2);
                }
            }
        } if (evt.getNewValue() == "O") {
            if (this.checkTrisHorizontal() >= 0 || this.checkTrisVertical() >= 0 || this.checkTrisDiagonal() >= 0) {
                this.winner = true;
                this.disableButtons();
                this.controller.setFont(new Font("Cambria Math", Font.PLAIN, 23));
                this.controller.setText("THE WINNER IS: 0");
                if (this.checkTrisHorizontal() == 0) {
                    this.setRowWinning("O", 0);
                }
                if (this.checkTrisHorizontal() == 1) {
                    this.setRowWinning("O", 1);
                }
                if (this.checkTrisHorizontal() == 2) {
                    this.setRowWinning("O", 2);
                }
                if (this.checkTrisVertical() == 0) {
                    this.setColumnWinning("O", 0);
                }
                if (this.checkTrisVertical() == 1) {
                    this.setColumnWinning("O", 1);
                }
                if (this.checkTrisVertical() == 2) {
                    this.setColumnWinning("O", 2);
                }
                if (this.checkTrisDiagonal() == 1) {
                    this.setDiagonalWinning("O", 1);
                }
                if (this.checkTrisDiagonal() == 2) {
                    this.setDiagonalWinning("O", 2);
                }
            }
        }if (this.checkFullGrid() && this.winner==false) {
           this.disableButtons();
            this.controller.setFont(new Font("Cambria Math", Font.PLAIN, 34));
            this.controller.setText("IT'S A TIE");
        }
    }
    // It colours the row in green if there is a tris in that row
    public void setRowWinning(String winner, int row) {
        if (winner == "X") {
            for (int i = 0; i < 3; i++) {
                cells[row][i].getButtonX().setBackground(Color.green);
            }
        }
        if (winner == "O") {
            for (int i = 0; i < 3; i++) {
                cells[row][i].getButtonO().setBackground(Color.green);
            }
        }
    }
    // It colours the column in green if there is a tris in that column
    public void setColumnWinning(String winner, int column) {
        if (winner == "X") {
            for (int i = 0; i < 3; i++) {
                cells[i][column].getButtonX().setBackground(Color.green);
            }
        }
        if (winner == "O") {
            for (int i = 0; i < 3; i++) {
                cells[i][column].getButtonO().setBackground(Color.green);
            }
        }
    }
    // It colours the diagonal in green if there is a tris in that diagonal
    public void setDiagonalWinning(String winner, int diagonal) {
        if (winner == "X" && diagonal == 1) {
            for (int i = 0; i < 3; i++) {
                cells[i][i].getButtonX().setBackground(Color.green);
            }
        }
        if (winner == "X" && diagonal == 2) {
                cells[0][2].getButtonX().setBackground(Color.green);
                cells[1][1].getButtonX().setBackground(Color.green);
                cells[2][0].getButtonX().setBackground(Color.green);

        }
        if (winner == "O" && diagonal == 1) {
            for (int i = 0; i < 3; i++) {
                cells[i][i].getButtonO().setBackground(Color.green);
            }
        }
        if (winner == "O" && diagonal == 2) {
                cells[0][2].getButtonO().setBackground(Color.green);
                cells[1][1].getButtonO().setBackground(Color.green);
                cells[2][0].getButtonO().setBackground(Color.green);
        }
    }

    // it disables the buttons in case there is a winner or it's tie
    public void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].getButtonO().setEnabled(false);
                cells[i][j].getButtonX().setEnabled(false);
            }
        }
    }

    // it resets all the values needed to restart a new match
    public void reset() {
        this.winner = false;
        this.controller.setInitialGame(true);
        this.controller.setFont(new Font("Cambria Math", Font.PLAIN, 25));
        this.controller.setText("START GAME");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    cells[i][j].setState("Initial");
                    cells[i][j].getButtonX().setEnabled(true);
                    cells[i][j].getButtonO().setEnabled(true);
                    cells[i][j].getButtonX().setBackground(Color.lightGray);
                    cells[i][j].getButtonO().setBackground(Color.lightGray);
                    cells[i][j].getButtonX().setText("X");
                    cells[i][j].getButtonO().setText("O");
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // it checks if there is a horizontal tris, so if the state of the cells are different from initial and they have the
    // same state then there is a tris in a certain row. It returns the row where there is th tris
    public int checkTrisHorizontal() {
        for (int i = 0; i < 3; i++) {
            if (cells[i][0].getState() != "Initial" && cells[i][0].getState() == cells[i][1].getState() && cells[i][0].getState() == cells[i][2].getState())
                return i;
        }
        return -1;
    }
    // it checks if there is a vertical tris, so if the state of the cells are different from initial and they have the
    // same state then there is a tris in a certain column. It returns the columns where there is th tris
    public int checkTrisVertical() {
        for (int j = 0; j < 3; j++) {
            if (cells[0][j].getState() != "Initial" && cells[0][j].getState() == cells[1][j].getState() && cells[0][j].getState() == cells[2][j].getState())
                return j;
        }
        return -1;
    }
    // it checks if there is a diagonal tris, so if the state of the cells are different from initial and they have the
    // same state then there is a tris in a certain diagonal, it returns 1 if the diagonal is from the first cell in top
    // left to cell in bottom right. It returns 2 if the diagonal is from the cell top right to the cell bottom left
    public int checkTrisDiagonal() {
        if (cells[0][0].getState() != "Initial" && cells[0][0].getState() == cells[1][1].getState() && cells[0][0].getState() == cells[2][2].getState()) {
            return 1;
        }
        if (cells[0][2].getState() != "Initial" && cells[0][2].getState() == cells[1][1].getState() && cells[0][2].getState() == cells[2][0].getState()) {
            return 2;
        }
        return -1;
    }

    // it checks if the grid is full
    public boolean checkFullGrid() {
        int count=0;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(cells[i][j].getState()!= "Initial")
                    count++;
            }
        }
        if(count==9){
            return true;
        }else
            return false;
    }

    public TTTCell[][] getCells(){
        return cells;
    }

    public boolean getWinner(){
        return winner;
    }


}
