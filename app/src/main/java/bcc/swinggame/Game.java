package bcc.swinggame;
import java.awt.Color;

public class Game {
    private Color currentPlayer;
    private boolean gameOver;

    public Game() {
        currentPlayer = Color.RED;
        gameOver = false;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public void nextPlayer() {
        if(currentPlayer == Color.RED) {
            currentPlayer = Color.YELLOW;
        } else {
            currentPlayer = Color.RED;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void gameOver() {
        gameOver = true;
    }
}
