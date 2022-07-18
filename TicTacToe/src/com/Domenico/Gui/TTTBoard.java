package com.Domenico.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;


public class TTTBoard extends JFrame implements PropertyChangeListener {

    private TTTController controller = new TTTController();
    private JButton buttonRestart = new JButton();
    private GridPanel gridPanel;
    private FooterPanel footerPanel;
    private GridBagConstraints gbc = new GridBagConstraints();
    private TTTCell[][] cells = new TTTCell[3][3];
    private boolean winner;


    public TTTBoard() throws HeadlessException {

        this.setTitle("Tic Tac Toe");
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
        this.setLayout(new GridBagLayout());
        this.setSize(600, 600);
        this.setLocation(460, 80);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(65, 91, 255)));

        this.gridPanel = new GridPanel(this.cells, this.controller, this);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(10, 0, 0, 0);
        this.add(gridPanel, gbc);
        this.winner = false;

        this.footerPanel = new FooterPanel(this.controller, this.buttonRestart);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.buttonRestart.addMouseListener(new MouseListener());
        this.controller.setText("START GAME");
        this.add(this.footerPanel, gbc);
    }

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
                    System.out.println("entra");
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
        }if (this.checkFullGrid()==9 && this.winner==false) {
           this.disableButtons();
            this.controller.setFont(new Font("Cambria Math", Font.PLAIN, 34));
            this.controller.setText("IT'S A TIE");
        }
    }

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

    public void setDiagonalWinning(String winner, int diagonal) {
        if (winner == "X" && diagonal == 1) {
            for (int i = 0; i < 3; i++) {
                cells[i][i].getButtonX().setBackground(Color.green);
            }
        }
        if (winner == "X" && diagonal == 2) {
            for (int i = 0; i < 3; i++) {
                cells[0][2].getButtonX().setBackground(Color.green);
                cells[1][1].getButtonX().setBackground(Color.green);
                cells[2][0].getButtonX().setBackground(Color.green);
            }
        }
        if (winner == "O" && diagonal == 1) {
            for (int i = 0; i < 3; i++) {
                cells[i][i].getButtonO().setBackground(Color.green);
            }
        }
        if (winner == "O" && diagonal == 2) {
            for (int i = 0; i < 3; i++) {
                cells[0][2].getButtonO().setBackground(Color.green);
                cells[1][1].getButtonO().setBackground(Color.green);
                cells[2][0].getButtonO().setBackground(Color.green);
            }
        }
    }

    public void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].getButtonO().setEnabled(false);
                cells[i][j].getButtonX().setEnabled(false);
            }
        }
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    this.winner = false;
                    this.controller.setInitialGame(true);
                    this.controller.setFont(new Font("Cambria Math", Font.PLAIN, 25));
                    this.controller.setText("START GAME");
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


    public int checkTrisHorizontal() {
        for (int i = 0; i < 3; i++) {
            if (cells[i][0].getState() != "Initial" && cells[i][0].getState() == cells[i][1].getState() && cells[i][0].getState() == cells[i][2].getState())
                return i;
        }
        return -1;
    }

    public int checkTrisVertical() {
        for (int j = 0; j < 3; j++) {
            if (cells[0][j].getState() != "Initial" && cells[0][j].getState() == cells[1][j].getState() && cells[0][j].getState() == cells[2][j].getState())
                return j;
        }
        return -1;
    }

    public int checkTrisDiagonal() {
        if (cells[0][0].getState() != "Initial" && cells[0][0].getState() == cells[1][1].getState() && cells[0][0].getState() == cells[2][2].getState()) {
            return 1;
        }
        if (cells[0][2].getState() != "Initial" && cells[0][2].getState() == cells[1][1].getState() && cells[0][2].getState() == cells[2][0].getState()) {
            return 2;
        }
        return -1;
    }

    public int checkFullGrid() {
        int count=0;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(cells[i][j].getState()!= "Initial")
                    count++;
            }
        }
        return count;
    }


    public class GridPanel extends JPanel {

        public GridPanel(TTTCell[][] celle, TTTController controlla, TTTBoard board) {

            this.setLayout(new GridLayout(3, 3, 0, 0));
            this.setPreferredSize(new Dimension(350, 350));
            this.setBackground(new Color(65, 91, 255));
            this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(65, 91, 255)));

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    celle[i][j] = new TTTCell();
                    celle[i][j].addPropertyChangeListener(controlla);
                    celle[i][j].addPropertyChangeListener(board);
                    celle[i][j].addVetoableChangelistener(controlla);
                    celle[i][j].getButtonX().addMouseListener(new MouseListener());
                    celle[i][j].getButtonO().addMouseListener(new MouseListener());
                    celle[i][j].setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(65, 91, 255)));
                    this.add(celle[i][j]);
                }
            }
        }
    }

    public class FooterPanel extends JPanel {

        public FooterPanel(TTTController tttController, JButton button) {

            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setBackground(Color.white);
            this.setPreferredSize(new Dimension(500, 120));
            this.add(Box.createRigidArea(new Dimension(30, 120)));
            this.add(tttController);
            this.add(Box.createRigidArea(new Dimension(50, 120)));
            button.setBackground(Color.red);
            button.setBorder((BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2), BorderFactory.
                    createEmptyBorder(20, 30, 20, 30))));
            button.setFocusPainted(false);
            button.setFont(new Font("Cambria Math", Font.PLAIN, 25));
            button.setText("RESTART");
            this.add(button);
        }
    }


    public class MouseListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (e.getSource() == cells[i][j].getButtonO() && cells[i][j].getButtonO().isEnabled()) {
                        try {
                            cells[i][j].setState("O");
                            if (winner == false) {
                                cells[i][j].getButtonO().setBackground(Color.CYAN);
                            }
                            cells[i][j].getButtonX().setEnabled(false);
                            cells[i][j].getButtonX().setText("");
                        } catch (PropertyVetoException ex) {
                            ex.printStackTrace();
                        }

                    } else if (e.getSource() == cells[i][j].getButtonX() && cells[i][j].getButtonX().isEnabled()) {
                        try {
                            cells[i][j].setState("X");
                            if (winner == false) {
                                cells[i][j].getButtonX().setBackground(Color.yellow);
                            }
                            cells[i][j].getButtonO().setEnabled(false);
                            cells[i][j].getButtonO().setText("");
                        } catch (PropertyVetoException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            }
            if (e.getSource() == buttonRestart) {
                reset();
            }
        }
    }
}
