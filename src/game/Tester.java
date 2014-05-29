package game;

import java.util.Arrays;

import pages.MazePage;
import maze.MazeSettings;
import maze.modification.ClockMod;
import maze.modification.Modification;

/**
 * @author icedtrees
 *
 */
public class Tester {

    /**
     * @param args
     */
    public static void main(String[] args) {
        MazeSettings s = new MazeSettings(false, true, 9, 8, 5, 20, -1, Arrays.asList(new Modification[] {
                new ClockMod(3)
        }));
        MazePage p = new MazePage();
        p.setMazeSettings(s);
        p.run();

    }

}
