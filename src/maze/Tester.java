package maze;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class Tester {
    public static void main(String[] args) {
        Maze myMaze = new Maze(107, 52, 1);
        myMaze.genMazeDFS();
        

        JFrame f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        f.add(myMaze, c);
        
        f.pack();
        f.setVisible(true);
    }

}
