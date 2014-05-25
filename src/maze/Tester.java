package maze;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.AbstractAction;

public class Tester {
    public static void main(String[] args) {        
        final JFrame f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        
        int mazeHeight = 15;
        int windowHeight = 500;
        
        final Maze maze1 = new Maze(mazeHeight, windowHeight, 0);
        c.gridx = 0;
        c.gridy = 0;
        f.add(maze1, c);
        
        final Maze maze2 = new Maze(mazeHeight, windowHeight, 0);
        c.gridx = 1;
        c.gridy = 0;
        f.add(maze2, c);
        
        final Maze maze3 = new Maze(mazeHeight, windowHeight, 0);
        c.gridx = 0;
        c.gridy = 1;
        f.add(maze3, c);
        
        final Maze maze4 = new Maze(mazeHeight, windowHeight, 0);
        c.gridx = 1;
        c.gridy = 1;
        f.add(maze4, c);
        
        f.pack();
        f.setVisible(true);
        
        /*
         * The main event loop which gets run every frame based on a frame-rate
         * in the Maze.FPS variable.
         * This runs on a Swing timer which runs on the event dispatch thread.
         */
        Timer actionLoop = new Timer(1000/Maze.FPS, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
        	public void actionPerformed(ActionEvent e) {
        		maze1.nextFrame();
        		maze2.nextFrame();
        		maze3.nextFrame();
        		maze4.nextFrame();
        		f.repaint();
        	}
        });
        actionLoop.start();
        
        /*
         * Generate two mazes side by side in parallel
         */
        Thread thread1 = new Thread() {
            public void run() {
            	maze1.genMazeDFSBranch(100, 200, 10);
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
            	maze2.genMazeDFSBranch(100, 0, 10);
            }
        };
        Thread thread3 = new Thread() {
            public void run() {
            	maze3.genMazeDFSBranch(5, 200, 10);
            }
        };
        Thread thread4 = new Thread() {
            public void run() {
            	maze4.genMazeDFSBranch(5, 0, 10);
            }
        };
        
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        /*
         * Now solve them together
         */
        thread1 = new Thread() {
            public void run() {
            	maze.autoplayer.DFSPlayer.play(maze1);
            }
        };
        thread2 = new Thread() {
            public void run() {
            	maze.autoplayer.DFSPlayer.play(maze2);
            }
        };
        thread3 = new Thread() {
            public void run() {
            	maze.autoplayer.DFSPlayer.play(maze3);
            }
        };
        thread4 = new Thread() {
            public void run() {
            	maze.autoplayer.DFSPlayer.play(maze4);
            }
        };
        
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    }

}
