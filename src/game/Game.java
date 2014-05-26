package game;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

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
    public static Settings settings = new Settings();
    
    public static void main(String[] args) {
    	Game game = new Game();
        game.run();
    }
    
    public Game() {
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
                MazePage.Result result = mazePage.run();
                if (result.equals(MazePage.Result.RETURN_HOME)) {
                    currentPage = HOME_PAGE;
                }
            } else if (currentPage.equals(INSTRUCTIONS_PAGE)) {
                InstructionsPage.Result result = instructionsPage.run();
                if (result.equals(InstructionsPage.Result.RETURN_HOME)) {
                    currentPage = HOME_PAGE;
                }
            } else if (currentPage.equals(SETTINGS_PAGE)) {
                SettingsPage.Result result = settingsPage.run();
                if (result.equals(SettingsPage.Result.RETURN_HOME)) {
                    currentPage = HOME_PAGE;
                }
            } else if (currentPage.equals(HIGH_SCORES_PAGE)) {
                HighScoresPage.Result result = highScoresPage.run();
                if (result.equals(HighScoresPage.Result.RETURN_HOME)) {
                    currentPage = HOME_PAGE;
                }
            }
        }
        
        mainWindow.dispose();
    }
    
    private void initialiseGUI() {
        mainWindow = new JFrame();
        mainWindow.setSize(settings.getScreenDimension());
        mainWindow.setTitle("GAME's MAIN MENU");
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
        
        

        // Adds image to imagePanel. 
        // OverlayLayout allows components to be overlayed
        // and thus we can use imagePanel as a background
        JLabel label = new JLabel();
        label.setLayout(new BorderLayout());
        URL hello;
		try {
			hello = new URL("http://www.vippetfoods.com.au/Uploads/cat%20for%20cats%20page.png");
			ImageIcon image = new ImageIcon(hello);
			label.setIcon(image);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        JPanel imagePanel = new JPanel(new BorderLayout());
        JPanel bothPanel = new JPanel(new BorderLayout());
        OverlayLayout overlay = new OverlayLayout(bothPanel);
        bothPanel.setLayout(overlay);
        imagePanel.add(label);
        imagePanel.setBackground(Color.BLACK);
        bothPanel.add(mainPanel);
        mainPanel.setOpaque(false);
        bothPanel.add(imagePanel);
        mainWindow.add(bothPanel);  
        
        mainWindow.setVisible(true);
    }
}
