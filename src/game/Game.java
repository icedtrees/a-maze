package game;
import javax.swing.JFrame;

import pages.HomePage;
import pages.MazePage;


/**
 * @author icedtrees
 *
 */
public class Game {
    public enum Command {
        PLAY_GAME,
        INSTRUCTIONS,
        QUIT
    }
    
    private HomePage homePage;
    private MazePage mazePage;
    
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
    
    public Game() {
        JFrame mainWindow = new JFrame();
        homePage = new HomePage(mainWindow);
        homePage.hide();
    }
    
    public void run() {
        boolean running = true;
        
        while (running) {
            homePage.show();
            Command command = homePage.waitCommand();
            if (command == Command.PLAY_GAME) {
                mazePage.show();
                homePage.hide();
            } else if (command == Command.INSTRUCTIONS) {
                homePage.hide();

            } else if (command == Command.QUIT) {
                running = false;
            }
        }
    }

}
