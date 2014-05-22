package pages;


import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RefactorPageTester implements Runnable {
	
    private JFrame mainWindow;
    private JPanel cardPanel;
    private HomePage home;
    private MazePage startMaze;
    
    // might be better to just use new CardLayout() every time?
    private static CardLayout cardLayout = new CardLayout();
    
    public static void main(String[] args) {
    	RefactorPageTester game = new RefactorPageTester();
    	game.run();
    	SwingUtilities.invokeLater(game);	
    }
    
    public RefactorPageTester() {
        mainWindow = new JFrame();
        
        mainWindow.setSize(400, 600);
        mainWindow.setMinimumSize(new Dimension(300, 400));
        mainWindow.setTitle("card layout panel");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        
        mainWindow.setTitle("MAIN MENU");
        home = new HomePage();
        cardPanel.add(home, "main menu panel");
        

        startMaze = new MazePage();
        cardPanel.add(startMaze, "maze panel");
        
        InstructionsPage instructions = new InstructionsPage();
        cardPanel.add(instructions, "instructions panel");
        
        SettingsPage settings = new SettingsPage();
        cardPanel.add(settings, "settings panel");
        
        HighScoresPage highScores = new HighScoresPage();
        cardPanel.add(highScores, "high scores panel");
        
        cardLayout.show(cardPanel, "main menu panel");
        mainWindow.add(cardPanel);
        mainWindow.setVisible(true);
        
    }
    
    public void run() {
        boolean running = true;
        
        while (running) {

            
            HomePage.Result result = home.run();
            System.out.println("homePage.result is " + result);
            if (result.equals(HomePage.Result.PLAY_GAME)) {
            	mainWindow.setTitle("MAZE");
            	cardLayout.show(cardPanel, "maze panel");
            	System.out.println("going to generate maze");
            	result = null;
            	home.setHomeResult(result);
            	System.out.println("result should be null");
            	startMaze.run();	
            } else if (result.equals(HomePage.Result.SHOW_INSTRUCTIONS)) {
            	mainWindow.setTitle("INSTRUCTIONS");
            	cardLayout.show(cardPanel, "instructions panel");
            } else if (result.equals(HomePage.Result.SHOW_SETTINGS)) {
            	mainWindow.setTitle("SETTINGS");
            	cardLayout.show(cardPanel, "settings panel");
            	// how to get the resolution selected by the radio button?
            } else if (result.equals(HomePage.Result.SHOW_HIGH_SCORES)) {
            	mainWindow.setTitle("HIGH SCORES");
            	cardLayout.show(cardPanel, "high scores panel");
            } else if (result.equals(HomePage.Result.QUIT_GAME)) {
            	running = false;
            	mainWindow.dispose();
            }
            
        }
    }
	
}
