package window;

import game.Game;

/**
 * @author icedtrees
 *
 */
public class HomeWindow extends Window {
    public HomeWindow() {
        super(400, 600);
    }
    
    public Game.Command waitCommand() {
        return Game.Command.QUIT;
    }
}
