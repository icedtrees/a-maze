package window;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowTester {

    /**
     * @param args
     */
	private static JFrame frame;
	private static JPanel cardPanel;
	private static CardLayout cardLayout = new CardLayout();
    public static void main(String[] args) {
        frame = new JFrame();
        frame.setSize(400, 600);
        frame.setTitle("card layout panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        
        // buttons for home's JPanel here
    	HomeWindow home = new HomeWindow(frame);
        addStartButton(home.getHomePanel());
        addInstructionsButton(home.getHomePanel());
        addHighScoresButton(home.getHomePanel());
        addExitButton(home.getHomePanel());	
    	cardPanel.add(home.getHomePanel(), "main menu panel");
        
    	// buttons for startMaze's JPanel here
    	MazeWindow startMaze = new MazeWindow(frame);
    	cardPanel.add(startMaze.getMazePanel(), "maze panel");
    	
    	// buttons for instructions' JPanel here
    	InstructionsWindow instructions = new InstructionsWindow(frame);
    	addReturnButton(instructions.getInstructionsPanel());
    	cardPanel.add(instructions.getInstructionsPanel(), "instruction panel");
    	
    	// buttons for highScores' JPanel here
        HighScoresWindow highScores = new HighScoresWindow(frame);
        addReturnButton(highScores.getHighScoresPanel());
        cardPanel.add(highScores.getHighScoresPanel(), "high scores panel");

        frame.add(cardPanel);
    	frame.setVisible(true);
    }

    private static void addStartButton(JPanel mainPanel) {
    	System.out.println("have added start maze panel");
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new FlowLayout());
        
        JButton startMaze = new JButton("Start maze");
		startMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// some action to generate maze and display a MazeWindow
				System.out.println("hello");
				frame.setTitle("MAZE");
				cardLayout.show(cardPanel, "maze panel");
			}
		});
		
		startPanel.add(startMaze);
		mainPanel.add(startPanel);
    }
    
	private static void addInstructionsButton(final JPanel mainPanel) {
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.setLayout(new FlowLayout());

		JButton instructions = new JButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// some action to show instructions screen for each feature
				System.out.println("show instructions");
				frame.setTitle("INSTRUCTIONS");
				cardLayout.show(cardPanel, "instruction panel");
			}
		});
		instructionsPanel.add(instructions);
		mainPanel.add(instructionsPanel);
	}
	
	private static void addHighScoresButton(JPanel mainPanel) {
		JPanel scoresPanel = new JPanel();
		scoresPanel.setLayout(new FlowLayout());

		JButton scores = new JButton("High Scores");
		scores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("show high scores");
				frame.setTitle("HIGH SCORES");
				cardLayout.show(cardPanel, "high scores panel");
			}
		});
		scoresPanel.add(scores);
		mainPanel.add(scoresPanel);
	}
    
	private static void addExitButton(JPanel mainPanel) {
		JPanel exitPanel = new JPanel();
		exitPanel.setLayout(new FlowLayout());

		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		exitPanel.add(exit);
		mainPanel.add(exitPanel);
	}
	
	private static void addReturnButton(JPanel mainPanel) {
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new FlowLayout());
		
        JButton returnBut = new JButton("return");
        returnBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("return to main menu");
            	frame.setTitle("MAIN MENU");
                cardLayout.show(cardPanel, "main menu panel");
            }
        });
		
		returnPanel.add(returnBut);
		mainPanel.add(returnPanel);
	}
	
}
