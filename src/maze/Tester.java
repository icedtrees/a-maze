package maze;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.AbstractAction;

public class Tester {
    public static void main(String[] args) {
        final Maze myMaze = new Maze(15, 610, 1);
        System.out.println(myMaze.getMazeWidth() + " " + myMaze.getMazeHeight());
        
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
         * This runs on a Swing timer which runs on the event dispatch thread.
         */
        Timer actionLoop = new Timer(1000/Maze.FPS, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
        	public void actionPerformed(ActionEvent e) {
        		myMaze.nextFrame();
        		maze2.nextFrame();
        		f.repaint();
        	}
        });
        actionLoop.start();
        
        /*
         * Generate two mazes side by side in parallel
         */
        Thread thread1 = new Thread() {
            public void run() {
            	myMaze.genMazeDFS(10);
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
            	maze2.genMazeDFSBranch(5, 0, 10);
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
//            	maze.autoplayer.DFSPlayer.play(myMaze);
            }
        };
        
        thread2 = new Thread() {
            public void run() {
//            	maze.autoplayer.DFSPlayer.play(maze2);
            }
        };
        
        thread1.start();
        thread2.start();
        try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    }

}
