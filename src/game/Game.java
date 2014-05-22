package game;

import java.awt.*;
import javax.swing.*;
import pages.*;

/**
 * @author icedtrees
 *
 */
public class Game implements Runnable {
    // CardLayout keys can only be strings, so we define constant strings for the CardLayout
    private static final String HOME_PAGE = "home";
    private static final String MAZE_PAGE = "maze";
    private static final String INSTRUCTIONS_PAGE = "instructions";
    private static final String HIGH_SCORES_PAGE = "highscores";
    private static final String SETTINGS_PAGE = "settings";
    
    // Main window and layout
    private JFrame mainWindow;
    private CardLayout layout;
    
    // All the pages
    private HomePage homePage;
    private MazePage mazePage;
    private InstructionsPage instructionsPage;
    private HighScoresPage highScoresPage;
    private SettingsPage settingsPage;
    
    String currentPage;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
    
    public Game() {
        mainWindow = new JFrame();
        layout = new CardLayout();
        mainWindow.setLayout(layout);
        
        // Create all the Pages
        homePage = new HomePage();
        mazePage = new MazePage();
        instructionsPage = new InstructionsPage();
        highScoresPage = new HighScoresPage();
        settingsPage = new SettingsPage();
        
        // Add all the Pages to the CardLayout
        layout.addLayoutComponent(homePage, HOME_PAGE);
        layout.addLayoutComponent(mazePage, MAZE_PAGE);
        layout.addLayoutComponent(instructionsPage, INSTRUCTIONS_PAGE);
        layout.addLayoutComponent(highScoresPage, HIGH_SCORES_PAGE);
        layout.addLayoutComponent(settingsPage, SETTINGS_PAGE);
        
    }
    
    public void run() {
        // The starting page is the Home Page
        currentPage = HOME_PAGE;
        mainWindow.setVisible(true);
        
        while (currentPage != null) {
            layout.show(mainWindow, currentPage);
       
            if (currentPage.equals(HOME_PAGE)) {
                HomePage.Result result = homePage.run();
                if (result.equals(HomePage.Result.PLAY_GAME)) {
                    currentPage = MAZE_PAGE;
                } else if (result.equals(HomePage.Result.SHOW_INSTRUCTIONS)) {
                    currentPage = INSTRUCTIONS_PAGE;
                } else if (result.equals(HomePage.Result.SHOW_HIGH_SCORES)) {
                    currentPage = HIGH_SCORES_PAGE;
                } else if (result.equals(HomePage.Result.SHOW_SETTINGS)) {
                    currentPage = SETTINGS_PAGE;
                } else if (result.equals(HomePage.Result.QUIT_GAME)) {
                    currentPage = null;
                }
            } else if (currentPage.equals(MAZE_PAGE)) {
                
            } else if (currentPage.equals(INSTRUCTIONS_PAGE)) {
                
            } else if (currentPage.equals(HIGH_SCORES_PAGE)) {
                
            } else if (currentPage.equals(SETTINGS_PAGE)) {
                
            }
        }
    }
}
