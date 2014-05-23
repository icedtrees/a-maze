package pages;


import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RefactorPageTester implements Runnable {
	// CardLayout keys can only be strings, so we define constant strings for the CardLayout
    private static final String HOME_PAGE = "home";
    private static final String MAZE_PAGE = "maze";
    private static final String INSTRUCTIONS_PAGE = "instructions";
    private static final String HIGH_SCORES_PAGE = "highscores";
    private static final String SETTINGS_PAGE = "settings";
	
    private JFrame mainWindow;
    private JPanel cardPanel;
    private CardLayout layout;
    
    private HomePage homePage;
    private MazePage mazePage;
    private InstructionsPage instructionsPage;
    private HighScoresPage highScoresPage;
    private SettingsPage settingsPage;
    
    String currentPage;
    
    public static void main(String[] args) {
    	RefactorPageTester game = new RefactorPageTester();
    	game.run();
    	SwingUtilities.invokeLater(game);	
    }
    
    public RefactorPageTester() {
        mainWindow = new JFrame();
        mainWindow.setSize(800, 600);
        mainWindow.setMinimumSize(new Dimension(400, 300));
        mainWindow.setTitle("card layout panel");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);
        
        homePage = new HomePage();
        mazePage = new MazePage();
        instructionsPage = new InstructionsPage();
        settingsPage = new SettingsPage();
        highScoresPage = new HighScoresPage();
        
        cardPanel.add(homePage, HOME_PAGE);
        cardPanel.add(mazePage, MAZE_PAGE);
        cardPanel.add(instructionsPage, INSTRUCTIONS_PAGE);
        cardPanel.add(settingsPage, SETTINGS_PAGE);
        cardPanel.add(highScoresPage, HIGH_SCORES_PAGE);
        
        mainWindow.add(cardPanel);
    }
    
    public void run() {
        currentPage = HOME_PAGE;
        mainWindow.setVisible(true);
        
        while (currentPage != null) {
        	layout.show(cardPanel, currentPage);
        	System.out.println("currentPage is " + currentPage);
        	
            if (currentPage.equals(HOME_PAGE)) {
            	mainWindow.setTitle("MAIN MENU");
	            HomePage.Result result = homePage.run();
	            System.out.println("homePage.result is " + result);
	            if (result.equals(HomePage.Result.PLAY_GAME)) {
	            	currentPage = MAZE_PAGE;
	            	System.out.println("going to generate maze");
	            	homePage.setHomeResult(null);
	            } else if (result.equals(HomePage.Result.SHOW_INSTRUCTIONS)) {
	            	currentPage = INSTRUCTIONS_PAGE;
	            	result = null;
	            	homePage.setHomeResult(result);
	            } else if (result.equals(HomePage.Result.SHOW_SETTINGS)) {
	            	currentPage = SETTINGS_PAGE;
	            	homePage.setHomeResult(null);
	            } else if (result.equals(HomePage.Result.SHOW_HIGH_SCORES)) {
	            	currentPage = HIGH_SCORES_PAGE;
	            	homePage.setHomeResult(null);
	            } else if (result.equals(HomePage.Result.QUIT_GAME)) {
	            	mainWindow.dispose();
	            	currentPage = null;
	            }
	            
            } else if (currentPage.equals(MAZE_PAGE)) {
            	mainWindow.setTitle("MAZE");
            	MazePage.Result result = mazePage.run();
	            System.out.println("mazePage.result is " + result);
	            if (result.equals(MazePage.Result.RETURN_HOME)) {
	            	currentPage = HOME_PAGE;
	            	mainWindow.setTitle("MAZE");
	            	layout.show(cardPanel, MAZE_PAGE);
	            	System.out.println("going to generate maze");
	            	mazePage.setMazeResult(null);
	            	// should i have one for PLAYING_GAME?
	            	//result = null;
	            	//mazePage.setPageResult(result);
	            }
	            
            } else if (currentPage.equals(INSTRUCTIONS_PAGE)) {
            	mainWindow.setTitle("INSTRUCTIONS");
            	InstructionsPage.Result result = instructionsPage.run();
	            System.out.println("instructionsPage.result is " + result);
	            if (result.equals(InstructionsPage.Result.RETURN_HOME)) {
	            	currentPage = HOME_PAGE;
	            	System.out.println("going to go back to home");
	            	instructionsPage.setInstructionsResult(null);
	            }
	            
            } else if (currentPage.equals(SETTINGS_PAGE)) {
            	mainWindow.setTitle("SETTINGS");
            	SettingsPage.Result result = settingsPage.run();
	            System.out.println("settingsPage.result is " + result);
	            if (result.equals(SettingsPage.Result.RETURN_HOME)) {
	            	currentPage = HOME_PAGE;
	            	System.out.println("going to go back to home");
	            	settingsPage.setSettingsResult(null);
	            } else if (result.equals(SettingsPage.Result.RESOLUTION_ONE)) {
	            	System.out.println("changing resolution");
	            	mainWindow.setSize(1000, 800);
	            	settingsPage.setSettingsResult(null);
	            } else if (result.equals(SettingsPage.Result.DEFAULTRESOLUTION)) {
	            	System.out.println("changing resolution");
	            	mainWindow.setSize(800, 600);
	            	settingsPage.setSettingsResult(null);
	            } else if (result.equals(SettingsPage.Result.RESOLUTION_TWO)) {
	            	System.out.println("changing resolution");
	            	mainWindow.setSize(600, 400);
	            	settingsPage.setSettingsResult(null);
	            } else if (result.equals(SettingsPage.Result.RESOLUTION_THREE)) {
	            	System.out.println("changing resolution");
	            	mainWindow.setSize(400, 300);
	            	settingsPage.setSettingsResult(null);
	            }
	            
            } else if (currentPage.equals(HIGH_SCORES_PAGE)) {
            	mainWindow.setTitle("HIGH SCORES");
            	HighScoresPage.Result result = highScoresPage.run();
	            System.out.println("highScoresPage.result is " + result);
	            if (result.equals(HighScoresPage.Result.RETURN_HOME)) {
	            	currentPage = HOME_PAGE;
	            	System.out.println("going to go back to home");
	            	highScoresPage.setHighScoresResult(null);
	            }
            	
            }
            
        }
    }
	
}
