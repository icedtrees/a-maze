package maze;

import java.util.*;

import maze.modification.Modification;

public class MazeSettings {
	private boolean multiplayer;
	private int mazeSize;
	private int branching;
	private int straightness;
	private int startingTime;
	private Collection<Modification> modifications;
	
	public static final boolean MAZE_SIZE_ODD = true;
	public static final int MIN_MAZE_SIZE = 11;
	public static final int MAX_MAZE_SIZE = 55;
	public static final int MIN_BRANCHING = 1;
	public static final int MAX_BRANCHING = 10;
	public static final int MIN_STRAIGHTNESS = -10;
	public static final int MAX_STRAIGHTNESS = 10;
	public static final int MIN_STARTING_TIME = 10;
	public static final int MAX_STARTING_TIME = 300;
	
	public static final List<Modification> NO_MODIFICATIONS = Collections.emptyList();
	
    public MazeSettings(boolean multiplayer, int mazeSize, int branching,
            int straightness, int startingTime,
            Collection<Modification> modifications) {
        if ((MAZE_SIZE_ODD && mazeSize % 2 == 0)
                || (mazeSize < MIN_MAZE_SIZE || mazeSize > MAX_MAZE_SIZE)
                || (branching < MIN_BRANCHING || branching > MAX_BRANCHING)
                || (straightness < MIN_STRAIGHTNESS || straightness > MAX_STRAIGHTNESS)
                || (startingTime < MIN_STARTING_TIME || startingTime > MAX_STARTING_TIME)) {
            throw new IllegalArgumentException("Maze parameters are incorrect");
        }

        if (modifications == null) {
            throw new NullPointerException();
        }

        this.multiplayer = multiplayer;
        this.mazeSize = mazeSize;
        this.branching = branching;
        this.straightness = straightness;
        this.startingTime = startingTime;
        this.modifications = new ArrayList<Modification>(modifications);
    }
	
	public MazeSettings() {
	    this(false, MIN_MAZE_SIZE, MIN_BRANCHING, MIN_STRAIGHTNESS, MIN_STARTING_TIME, NO_MODIFICATIONS);
	}

    public boolean getMultiplayer() {
        return multiplayer;
    }

    public int getMazeSize() {
        return mazeSize;
    }

    public int getBranching() {
        return branching;
    }

    public int getStraightness() {
        return straightness;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public Collection<Modification> getModifications() {
        return modifications;
    }

    public void setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;
    }

    public void setMazeSize(int mazeSize) {
        this.mazeSize = mazeSize;
    }

    public void setBranching(int branching) {
        this.branching = branching;
    }

    public void setStraightness(int straightness) {
        this.straightness = straightness;
    }

    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }

    public void setModifications(Collection<Modification> modifications) {
        this.modifications = modifications;
    }

}
