package maze;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.swing.JFrame;

public class Tester {
    public static void main(String[] args) {
        Maze myMaze = new Maze(60, 610, 1);
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
        
//        while (true) {
//        	myMaze.movePlayerRandom();
//        	f.repaint();
//        	try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
        
        Set<Coordinate> seen = new HashSet<Coordinate>();
        Stack<Coordinate> s = new Stack<Coordinate>();
        s.add(new Coordinate(1, 0));
        while (!s.isEmpty()) {
        	Coordinate cur = s.pop();
        	
        	if (seen.contains(cur)) {
        		continue;
        	}
        	seen.add(cur);
        	myMaze.setPlayerPos(cur.getX(), cur.getY());
        	myMaze.setTile(cur.getX(), cur.getY(), Tile.EXPLORED);
        	f.repaint();
        	
        	for (Coordinate coord : cur.adj()) {
        		if (myMaze.isSpace(coord.getX(), coord.getY())) {
        			s.push(coord);
        		}
        	}
        	try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

}
