package game;
import javax.swing.JFrame;

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
    }
    
    public void run() {
        boolean running = true;
        
        while (running) {
            mainWindow.setVisible(true);
            Command command = homePage.waitCommand();
            if (command.equals(Command.PLAY_GAME)) {
                mazePage.show();
                homePage.hide();
            } else if (command.equals(Command.INSTRUCTIONS)) {
                homePage.hide();

            } else if (command.equals(Command.QUIT)) {
                running = false;
            }
        }
    }

}
