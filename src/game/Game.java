package game;

import java.awt.CardLayout;
import javax.swing.*;

import pages.*;

/**
 * @author icedtrees
 *
 */
public class Game {
    
    private JFrame mainWindow;
    
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
    
    public Game() {
         mainWindow = new JFrame();
         CardLayout gameLayout = new CardLayout();
         gameLayout.addLayoutComponent(new HomePage(), "home");
    }
    
    public void run() {
        boolean running = true;
        
        while (running) {
            mainWindow.setVisible(true);
            HomePage home = new HomePage(mainWindow);
            HomePage.Result result = home.run();
            if (result.equals(HomePage.Result.PLAY_GAME)) {
                
            } else if (result.equals(HomePage.Result.SHOW_INSTRUCTIONS)) {
                
            } else if (result.equals(HomePage.Result.SHOW_HIGH_SCORES)) {
                
            } else if (result.equals(HomePage.Result.QUIT_GAME)) {
                running = false;
            }
        }
    }

}
