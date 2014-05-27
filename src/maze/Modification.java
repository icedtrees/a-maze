package maze;

import java.util.Random;

public interface Modification {
	public void apply(Maze maze, Random rand);
}
