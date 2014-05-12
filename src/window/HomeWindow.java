import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeWindow extends Window {
    public HomeWindow() {
    	super(400, 600);
    	JFrame homeWindow = getWindow();
        homeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeWindow.setTitle("Menu/home");
        homeWindow.setLayout(new GridLayout(6, 1));
        
        JLabel blank = new JLabel("", JLabel.CENTER);;
        homeWindow.add(blank);
        
        JLabel titleLabel = new JLabel("MAZE", JLabel.CENTER);
        homeWindow.add(titleLabel);
        
        addStartButton(homeWindow);
        addInstructionsButton(homeWindow);
        addHighScoresButton(homeWindow);
        addExitButton(homeWindow);
    }
    
    private void addStartButton(JFrame homeWindow) {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new FlowLayout());
        
        JButton startMaze = new JButton("Start maze");
		startMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// some action to generate maze and display a MazeWindow
				System.out.println("hello");
			}
		});
		
		startPanel.add(startMaze);
		homeWindow.add(startPanel);
    }
    
	private void addInstructionsButton(JFrame homeWindow) {
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.setLayout(new FlowLayout());

		JButton instructions = new JButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// some action to show instructions screen for each feature
				System.out.println("show instructions");
			}
		});
		instructionsPanel.add(instructions);
		homeWindow.add(instructionsPanel);
	}
	
	private void addHighScoresButton(JFrame homeWindow) {
		JPanel scoresPanel = new JPanel();
		scoresPanel.setLayout(new FlowLayout());

		JButton scores = new JButton("High Scores");
		scores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// some action to show high scores screen
				System.out.println("show high scores");
			}
		});
		scoresPanel.add(scores);
		homeWindow.add(scoresPanel);
	}
	
	private void addExitButton(JFrame homeWindow) {
		JPanel exitPanel = new JPanel();
		exitPanel.setLayout(new FlowLayout());

		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// exit from game
			}
		});
		exitPanel.add(exit);
		homeWindow.add(exitPanel);
	}
    
    public Game.Command waitCommand() {
        return Game.Command.QUIT;
    }
}
