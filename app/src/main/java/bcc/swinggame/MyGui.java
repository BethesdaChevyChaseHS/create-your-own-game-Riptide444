package bcc.swinggame;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyGui {
    private JLabel currentPlayer;

    public MyGui(Game g) {
        // create frame
        JFrame frame = new JFrame("Java In a Row");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        currentPlayer = new JLabel("Current Player: " + (g.getCurrentPlayer() == Color.RED ? "Red" : "Yellow"));
        currentPlayer.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        p.add(currentPlayer);
        Board board = new Board();
        board.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!g.isGameOver()) {
                    int updateResult = board.updateBoard(g.getCurrentPlayer(), e.getX());
                    if (updateResult == 0) {
                        g.nextPlayer();
                        currentPlayer.setText("Current Player: " + (g.getCurrentPlayer() == Color.RED ? "Red" : "Yellow"));
                    } else if (updateResult == 1) {
                        g.gameOver();
                        currentPlayer.setText((g.getCurrentPlayer() == Color.RED ? "Red" : "Yellow") + " won!! Congrats!");
                    } else if (updateResult == 2) {
                        g.gameOver();
                        currentPlayer.setText("You had a draw! Bummer! Play again.");
                    }
                }
            }
        });
        p.add(board);
        frame.add(p);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
