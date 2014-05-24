package game;

import java.awt.*;
import javax.swing.*;
import pages.*;

/**
 * @author icedtrees
 *
 */
public class Game {
    // CardLayout keys can only be strings, so we define constant strings for the CardLayout
    private static final String HOME_PAGE = "home";
    private static final String MAZE_PAGE = "maze";
    private static final String INSTRUCTIONS_PAGE = "instructions";
    private static final String HIGH_SCORES_PAGE = "highscores";
    private static final String SETTINGS_PAGE = "settings";
    
    // Main window and layout
    private JFrame mainWindow;
    private JPanel mainPanel;
    private CardLayout layout;
    
    // All the pages
    private HomePage homePage;
    private MazePage mazePage;
    private InstructionsPage instructionsPage;
    private HighScoresPage highScoresPage;
    private SettingsPage settingsPage;
    
    // Current page
    String currentPage;
    
    // Settings
    private Settings settings;
    
    public static void main(String[] args) {
    	Game game = new Game();
        game.run();
    }
    
    public Game() {
        settings = new Settings();
        initialiseGUI();
    }
    
    public void run() {
        // The starting page is the Home Page
        currentPage = HOME_PAGE;
        
        while (currentPage != null) {
            layout.show(mainPanel, currentPage);
       
            if (currentPage.equals(HOME_PAGE)) {
                HomePage.Result result = homePage.run();
                if (result.equals(HomePage.Result.PLAY_GAME)) {
                    currentPage = MAZE_PAGE;
                } else if (result.equals(HomePage.Result.SHOW_INSTRUCTIONS)) {
                    currentPage = INSTRUCTIONS_PAGE;
                } else if (result.equals(HomePage.Result.SHOW_SETTINGS)) {
                    currentPage = SETTINGS_PAGE;
                } else if (result.equals(HomePage.Result.SHOW_HIGH_SCORES)) {
                    currentPage = HIGH_SCORES_PAGE;
                } else if (result.equals(HomePage.Result.QUIT_GAME)) {
                    currentPage = null;
                }
            } else if (currentPage.equals(MAZE_PAGE)) {
                
            } else if (currentPage.equals(INSTRUCTIONS_PAGE)) {
                
            } else if (currentPage.equals(SETTINGS_PAGE)) {
                
            } else if (currentPage.equals(HIGH_SCORES_PAGE)) {
                
            }
        }
    }
    
    private void initialiseGUI() {
        mainWindow = new JFrame();
        mainWindow.setSize(settings.getScreenDimension());
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setTitle("GAME's MAIN MENU");
        mainWindow.setResizable(false);
        
        mainPanel = new JPanel();
        layout = new CardLayout();
        mainPanel.setLayout(layout);
        
        // Create all the Pages
        homePage = new HomePage();
        mazePage = new MazePage();
        instructionsPage = new InstructionsPage();
        highScoresPage = new HighScoresPage();
        settingsPage = new SettingsPage();
        
        // Add all the Pages to the CardLayout
        mainPanel.add(homePage, HOME_PAGE);
        mainPanel.add(mazePage, MAZE_PAGE);
        mainPanel.add(instructionsPage, INSTRUCTIONS_PAGE);
        mainPanel.add(highScoresPage, HIGH_SCORES_PAGE);
        mainPanel.add(settingsPage, SETTINGS_PAGE);
        
        mainWindow.add(mainPanel);  
        mainWindow.setVisible(true);
    }
}
