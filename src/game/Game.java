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
    private static final String CUSTOM_PAGE = "custom";
    private static final String SETTINGS_PAGE = "settings";
    
    // Main window and layout
    private JFrame mainWindow;
    private JPanel mainPanel;
    private CardLayout layout;
    
    // All the pages
    private HomePage homePage;
    private MazePage mazePage;
    private InstructionsPage instructionsPage;
    private CustomPage customPage;
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
        Campaign campaign = new Campaign();
        
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
                } else if (result.equals(HomePage.Result.SHOW_CUSTOM)) {
                    currentPage = CUSTOM_PAGE;
                } else if (result.equals(HomePage.Result.QUIT_GAME)) {
                    currentPage = null;
                }
            } else if (currentPage.equals(MAZE_PAGE)) {
                mainWindow.setTitle(campaign.getLevelName());
                mazePage.setMazeSettings(campaign.getLevelSettings());
                MazePage.Result result = mazePage.run();
                if (result.equals(MazePage.Result.LOST_GAME)) {
                    currentPage = HOME_PAGE;
                } else if (result.equals(MazePage.Result.WON_GAME)) {
                    campaign.advance();
                }
            } else if (currentPage.equals(INSTRUCTIONS_PAGE)) {
                InstructionsPage.Result result = instructionsPage.run();
                if (result.equals(InstructionsPage.Result.RETURN_HOME)) {
                    currentPage = HOME_PAGE;
                }
            } else if (currentPage.equals(SETTINGS_PAGE)) {
                SettingsPage.Result result = settingsPage.run();
                mainWindow.setSize(settings.screenSize.width, settings.screenSize.height);
                if (result.equals(SettingsPage.Result.RETURN_HOME)) {
                    currentPage = HOME_PAGE;
                }
            } else if (currentPage.equals(CUSTOM_PAGE)) {
                CustomPage.Result result = customPage.run();
                if (result.equals(CustomPage.Result.RETURN_HOME)) {
                    currentPage = HOME_PAGE;
                }
            }
        }
        
        mainWindow.dispose();
    }
    
    private void initialiseGUI() {
        mainWindow = new JFrame();
        mainWindow.setSize(settings.screenSize.width, settings.screenSize.height);
        mainWindow.setTitle("A*maze-d yet?");
        //mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainPanel = new JPanel();

        layout = new CardLayout();
        mainPanel.setLayout(layout);
        
        // Create all the Pages
        homePage = new HomePage();
        mazePage = new MazePage();
        instructionsPage = new InstructionsPage();
        customPage = new CustomPage();
        settingsPage = new SettingsPage(settings);
        
        // Add all the Pages to the CardLayout
        
        mainPanel.add(homePage, HOME_PAGE);
        mainPanel.add(mazePage, MAZE_PAGE);
        mainPanel.add(instructionsPage, INSTRUCTIONS_PAGE);
        mainPanel.add(customPage, CUSTOM_PAGE);
        mainPanel.add(settingsPage, SETTINGS_PAGE);

        mainWindow.add(mainPanel);  
        
        mainWindow.setVisible(true);
    }
}
