package game;
import window.HomeWindow;
import window.MazeWindow;


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
    
    private HomeWindow homeWindow;
    private MazeWindow mazeWindow;
    
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
    
    public Game() {
        homeWindow = new HomeWindow();
        homeWindow.hide();
    }
    
    public void run() {
        currentWindow = homeWindow;
        boolean running = true;
        
        while (running) {
            homeWindow.show();
            Command command = homeWindow.waitCommand();
            if (command == PLAY_GAME) {
                mazeWindow.show();
                homeWindow.hide();
            } else if (command == INSTRUCTIONS) {
                homeWindow.hide();
                Command result = instructionsWindow.run();
                
            } else if (Command == QUIT) {
                running = false;
            }
        }
    }

}
