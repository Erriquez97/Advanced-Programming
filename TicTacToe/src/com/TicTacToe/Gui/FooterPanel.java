package com.TicTacToe.Gui;

import com.TicTacToe.TTTBoard;
import com.TicTacToe.Beans.TTTController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FooterPanel extends JPanel {

    private JButton buttonRestart = new JButton();

    public FooterPanel(TTTController tttController, TTTBoard board) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(450, 120));
        this.add(tttController);
        // it creates an invisible box to outdistance the controller and the buttonRestart
        this.add(Box.createRigidArea(new Dimension(30, 120)));
        buttonRestart.setBackground(Color.red);
        buttonRestart.setBorder((BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2), BorderFactory.
                createEmptyBorder(20, 30, 20, 30))));
        buttonRestart.setFocusPainted(false);
        buttonRestart.setFont(new Font("Cambria Math", Font.PLAIN, 25));
        buttonRestart.setText("RESTART");
        this.add( buttonRestart);

        // Button's mouseListener that reset the game if the buttonRestart is clicked
        this.buttonRestart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getSource() == buttonRestart) {
                    board.reset();
                }
            }
        });
    }
}