
/**
 * @author icedtrees
 *
 */
public class Game {
    public enum Command {
        PLAY_GAME,
        QUIT
    }
    
    private HomeWindow homeWindow;
    private MazeWindow mazeWindow;

    public Game() {
        homeWindow = new HomeWindow();
        homeWindow.hide();
    }
    
    public void run() {
        homeWindow.show();
        Command command = homeWindow.waitCommand();
        // do something
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
    

}
