package game;

import game.Campaign.CampaignType;

import java.awt.*;

import javax.swing.*;

import maze.MazeSettings;
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
    private static final String TRANSITION_PAGE = "transition";
    
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
    private TransitionPage transitionPage;
    
    // Current page
    String currentPage;
    
    // Settings
    public static Settings settings = new Settings();
    
    public static void main(String[] args) {
    	Game game = new Game();
        game.run();
    }
    
    /**
     * Constructs a new game object and initialises the GUI components of the
     * entire program.
     */
    public Game() {
        initialiseGUI();
    }
    
    /**
     * Begins execution of the game.
     */
    public void run() {
        // The starting page is the Home Page
        currentPage = HOME_PAGE;
        TransitionPage currentTransition = null;
        Campaign singleCampaign = new Campaign(CampaignType.SINGLEPLAYER);
        Campaign multiCampaign = new Campaign(CampaignType.MULTIPLAYER);
        Campaign currentCampaign = null;
        MazeSettings customSettings = null;
        
        
        while (currentPage != null) {
            layout.show(mainPanel, currentPage);
       
            if (currentPage.equals(HOME_PAGE)) {
                HomePage.Result result = homePage.run();
                if (result.equals(HomePage.Result.PLAY_GAME_SINGLEPLAYER)) {
                    transitionPage.setText("Level " + singleCampaign.getCurrentLevel() + ": " + singleCampaign.getLevelName());
                    currentTransition = transitionPage;
                    currentCampaign = singleCampaign;
                    currentPage = MAZE_PAGE;
                } else if (result.equals(HomePage.Result.PLAY_GAME_MULTIPLAYER)) {
                    transitionPage.setText("Level " + multiCampaign.getCurrentLevel() + ": " + multiCampaign.getLevelName());
                    currentTransition = transitionPage;
                    currentCampaign = multiCampaign;
                    currentPage = MAZE_PAGE;                    
                } else if (result.equals(HomePage.Result.SHOW_INSTRUCTIONS)) {
                    currentPage = INSTRUCTIONS_PAGE;
                } else if (result.equals(HomePage.Result.SHOW_SETTINGS)) {
                    currentPage = SETTINGS_PAGE;
                } else if (result.equals(HomePage.Result.PLAY_CUSTOM)) {
                    currentPage = CUSTOM_PAGE;
                } else if (result.equals(HomePage.Result.QUIT_GAME)) {
                    currentPage = null;
                }
            } else if (currentPage.equals(MAZE_PAGE)) {
                if (customSettings == null) {
                    // Campaign mode
                    mazePage.setMazeSettings(currentCampaign.getLevelSettings());
                    MazePage.Result result = mazePage.run();
                    if (result.equals(MazePage.Result.LOST_GAME)) {
                        transitionPage.setText("Better luck next time!");
                        currentTransition = transitionPage;
                        currentPage = HOME_PAGE;
                    } else if (result.equals(MazePage.Result.WON_GAME)) {
                        if (currentCampaign.getCurrentLevel() == Campaign.SINGLEPLAYER_NUM_LEVELS) {
                            transitionPage.setText("Congratulations, you finished every level!");
                            currentTransition = transitionPage;
                            currentPage = HOME_PAGE;
                        } else {
                            if (currentCampaign.getCurrentLevel() == 1) {
                                if (currentCampaign == singleCampaign) {
                                    homePage.setSingleButtonText("Continue (Solo)");
                                } else if (currentCampaign == multiCampaign) {
                                    homePage.setMultiButtonText("Continue (Co-op)");
                                }
                            }
                            currentCampaign.advance();
                            transitionPage.setText("Level " + currentCampaign.getCurrentLevel() + ": " + currentCampaign.getLevelName());
                            currentTransition = transitionPage;
                        }
                    } else if (result.equals(MazePage.Result.RETURN_HOME)) {
                        currentPage = HOME_PAGE;
                    }
                } else {
                    // Custom game mode
                    mazePage.setMazeSettings(customSettings);
                    MazePage.Result result = mazePage.run();
                    if (result.equals(MazePage.Result.WON_GAME)) {
                        transitionPage.setText("Well done! Was that level too easy for you?");
                        currentTransition = transitionPage;     
                    } else if (result.equals(MazePage.Result.LOST_GAME)){
                        transitionPage.setText("Maybe you made that game a bit too hard.");
                        currentTransition = transitionPage;
                    } else if (result.equals(MazePage.Result.RETURN_HOME)) {
                        currentPage = HOME_PAGE;
                    }
                    customSettings = null;
                    currentPage = HOME_PAGE;
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
                } else if (result.equals(CustomPage.Result.PLAY_CUSTOM_GAME)) {
                    customSettings = customPage.getCustomSettings();
                    currentPage = MAZE_PAGE;
                }
            }
            
            // Show the transition page if needed
            if (currentTransition != null) {
                layout.show(mainPanel, TRANSITION_PAGE);
                transitionPage.run();
                currentTransition = null;
            }
        }
        
        mainWindow.dispose();
    }
    
    private void initialiseGUI() {
        mainWindow = new JFrame();
        mainWindow.setSize(settings.screenSize.width, settings.screenSize.height);
        mainWindow.setTitle("A*maze-d");
        mainWindow.setResizable(false);
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
        transitionPage = new TransitionPage();
        
        // Add all the Pages to the CardLayout
        
        mainPanel.add(homePage, HOME_PAGE);
        mainPanel.add(mazePage, MAZE_PAGE);
        mainPanel.add(instructionsPage, INSTRUCTIONS_PAGE);
        mainPanel.add(customPage, CUSTOM_PAGE);
        mainPanel.add(settingsPage, SETTINGS_PAGE);
        mainPanel.add(transitionPage, TRANSITION_PAGE);

        mainWindow.add(mainPanel);  
        
        mainWindow.setVisible(true);
    }
}
