package bcc.swinggame;
import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private String[][] boardGrid;
    private int pieces;

    public Board() {
        boardGrid = new String[6][7];
        pieces = 0;
    }

    // use mouse position to find desired column
    public int getCol(int x) {
        int size = Math.min(getWidth() - 4, getHeight() - 4) / 7;
        int startX = (getWidth() - (size * 7)) / 2;

        if(x < startX || x > (getWidth() - startX)) {
            return -1;
        }

        return (x - startX) / size;
    }

    // place a new item in on the board
    public int updateBoard(Color c, int x) {
        int col = getCol(x);
        System.out.println(col);
        if(col < 0) {
            return -1;
        }
        int y = 5;
        while(boardGrid[y][col] != null) {
            y--;
            if(y < 0) {
                return -1;
            }
        }
        boardGrid[y][col] = c.toString();
        System.out.println("board col " + col + " updated to " + c.toString() + " at y = " + y);
        pieces++;
        repaint();
        if(checkWin(c.toString(), col, y)) {
            return 1;
        } else if(pieces >= 42) {
            return 2;
        }
        return 0;
    }

    public boolean checkWin(String target, int x, int y) {
        System.out.println(boardGrid[y][x]);
        for(int dx = -1; dx <= 1; dx++) {
            for(int dy = -1; dy <= 1; dy++) {
                if(!(dy == 0 && dx == 0)) {
                    int totalConsec = checkBoardHelper(target, x+dx, y+dy, dx, dy) + checkBoardHelper(target,x-dx,y-dy,-1*dx, -1*dy) + 1;
                    System.out.println("Direction dx=" + dx + " dy=" + dy + " count=" + totalConsec);
                    if(totalConsec >= 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int checkBoardHelper(String target, int x, int y, int dx, int dy) {
//        System.out.println("x: " + x + " y: " + y);
        if ((x) < 0 || (x) >= 7 || (y) < 0 || (y) >= 6) {
            return 0;
        }
        if (boardGrid[y][x] == null || !boardGrid[y][x].equals(target)) {
            return 0;
        }
//        System.out.println(" value: " + boardGrid[y][x]);
        return 1 + checkBoardHelper(target, x + dx, y + dy, dx, dy);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400,400);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int size = Math.min(getWidth() - 4, getHeight() - 4) / 7;

        int yOffset = (getHeight() - (size * 6)) / 2;
        int xOffset = (getWidth() - (size * 7)) / 2;

        // Draw the grid
        int y = yOffset;
        for (int row = 0; row < 6; row++) {
            int x = xOffset;
            for (int col = 0; col < 7; col++) {
                g2d.drawRect(x, y, size, size);

                // Draw piece if present
                if (boardGrid[row][col] != null) {
                    if (boardGrid[row][col].equals(Color.RED.toString())) {
                        g2d.setColor(Color.RED);
                    } else {
                        g2d.setColor(Color.YELLOW);
                    }
                    int ovalPadding = size / 10;
                    g2d.fillOval(x + ovalPadding, y + ovalPadding, size - 2 * ovalPadding, size - 2 * ovalPadding);
                    g2d.setColor(Color.BLACK); // reset color for next rect
                }
                x += size;
            }
            y += size;
        }
        g2d.dispose();
    }
}
