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
            if (Command == PLAY_GAME) {
                mazeWindow.show();
                homeWindow.hide();
                currentWindow = mazeWindow;
            } else if (Command == INSTRUCTIONS) {
                currentWindow = instructionsWindow;
                homeWindow.hide();
                instructionsWindow.show()
            } else if (Command == QUIT) {
                running = false;
            }
        }
    }

}
