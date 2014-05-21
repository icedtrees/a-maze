package maze;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Tester {
    public static void main(String[] args) {
        final Maze myMaze = new Maze(15, 610, 1);
        
        final JFrame f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        f.add(myMaze, c);
        
        final Maze maze2 = new Maze(15, 610, 1);
        c.gridx = 1;
        f.add(maze2, c);
        
        f.pack();
        f.setVisible(true);
        
        /*
         * The main event loop which gets run every frame based on a frame-rate
         * in the Maze.FPS variable.
         */
        Timer actionLoop = new Timer();
        actionLoop.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run() {
        		myMaze.nextFrame();
        		maze2.nextFrame();
        		f.repaint();
        	}
        }, 1000/Maze.FPS, 1000/Maze.FPS);
        
        /*
         * Generate two mazes side by side in parallel
         */
        Thread thread1 = new Thread() {
            public void run() {
            	myMaze.genMazeDFS();
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
            	maze2.genMazeDFSBranch(5);
            }
        };
        
        thread1.start();
        thread2.start();
        try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        /*
         * Now solve them together
         */
        thread1 = new Thread() {
            public void run() {
            	maze.autoplayer.DFSPlayer.play(myMaze);
            }
        };
        
        thread2 = new Thread() {
            public void run() {
            	maze.autoplayer.DFSPlayer.play(maze2);
            }
        };
        
        thread1.start();
        thread2.start();
        try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

}
