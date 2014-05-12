package maze;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Maze {
    private final static boolean DEBUGGING = false;
    
    private Tile[][] tiles;
    private int width;
    private int height;
    private int complexity;
    
    public Maze(int newWidth, int newHeight, int newComplexity) {
        width = newWidth;
        height = newHeight;
        tiles = new Tile[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tiles[col][row] = new Tile(Tile.WALL, col, row);
            }
        }
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row > 1) {
                    tiles[col][row].setNorth(tiles[col][row-1]);
                }
                if (col < width - 2) {
                    tiles[col][row].setEast(tiles[col+1][row]);
                }
                if (row < height - 2) {
                    tiles[col][row].setSouth(tiles[col][row+1]);
                }
                if (col > 1) {
                    tiles[col][row].setWest(tiles[col-1][row]);
                }
            }
        }
        complexity = newComplexity;
        
        genMaze();
    }
    
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getComplexity() {
        return complexity;
    }
    public Tile getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        } else {
            return null;
        }
    }
    
    public String toString() {
        String[] asciiOutput = new String[]{" ", "#"};
        String mazeString = "";
        
//        mazeString += "+";
//        for (int i = 0; i < width; i++) {
//            mazeString += "-";
//        }
//        mazeString += "+\n";
        for (int row = 0; row < height; row++) {
//            mazeString += "|";
            for (int col = 0; col < width; col++) {
                mazeString += asciiOutput[tiles[col][row].getValue()];
            }
//            mazeString += "|\n";
            mazeString += "\n";
        }
//        mazeString += "+";
//        for (int i = 0; i < width; i++) {
//            mazeString += "-";
//        }
//        mazeString += "+";
        
        return mazeString;
    }
    public void drawToScreen() {
        JFrame f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GridBagConstraints c = new GridBagConstraints();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (tiles[col][row].getValue() == Tile.WALL) {
                    c.gridx = col;
                    c.gridy = row;
                    JPanel wall = new JPanel();
                    wall.setBackground(Color.BLACK);
                    f.add(wall, c);
                }
            }
        }
        
        f.pack();
        f.setVisible(true);
    }
    
    private void genMaze() {
        Random rand = new Random(System.nanoTime());
        
        Stack<MazeGenStep> s = new Stack<MazeGenStep>();
        s.add(new MazeGenStep(tiles[1][1], tiles[1][0]));
        
        while (!s.empty()) {
            MazeGenStep curStep = s.pop();
            if (!curStep.isValidMove()) {
                continue;
            }
            curStep.setValues(Tile.SPACE);
            
            List<MazeGenStep> possibleMoves = new ArrayList<MazeGenStep>();
            Tile curTile = curStep.getNewTile();
            debug("Currently at " + curTile.toString());
            Tile curMove;
            curMove = curTile.getNorth(2);
            if (curMove != null) {
                debug("    Considering move north to " + curMove.toString());
                possibleMoves.add(new MazeGenStep(curMove, curTile.getNorth()));
            }
            curMove = curTile.getEast(2);
            if (curMove != null) {
                debug("    Considering move east to " + curMove.toString());
                possibleMoves.add(new MazeGenStep(curMove, curTile.getEast()));
            }
            curMove = curTile.getSouth(2);
            if (curMove != null) {
                debug("    Considering move south to " + curMove.toString());
                possibleMoves.add(new MazeGenStep(curMove, curTile.getSouth()));
            }
            curMove = curTile.getWest(2);
            if (curMove != null) {
                debug("    Considering move west to " + curMove.toString());
                possibleMoves.add(new MazeGenStep(curMove, curTile.getWest()));
            }
            
            Collections.shuffle(possibleMoves, rand);
            for (MazeGenStep nextMove : possibleMoves) {
                s.add(nextMove);
            }
        }
    }
    
    private void debug(String message) {
        if (Maze.DEBUGGING) {
            System.out.println("> " + message);
        }
    }
}
