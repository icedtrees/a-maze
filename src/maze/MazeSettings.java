package maze;

import java.util.*;

import maze.modification.Modification;

public class MazeSettings {
	private boolean multiplayer;
	private boolean trail;
	private int mazeSize;
	private int branching;
	private int straightness;
	private int startingTime;
	private int startingHints;
	private List<Modification> modifications;
	private long seed;
	
	public static final int MIN_MAZE_SIZE = 5;
	public static final int MAX_MAZE_SIZE = 40;
	public static final int MIN_BRANCHING = 1;
	public static final int MAX_BRANCHING = 10;
	public static final int MIN_STRAIGHTNESS = -10;
	public static final int MAX_STRAIGHTNESS = 10;
	public static final int MIN_STARTING_TIME = 10;
	public static final int MAX_STARTING_TIME = 300;
	public static final int MIN_STARTING_HINTS = 0;
	
	public static final List<Modification> NO_MODIFICATIONS = Collections.emptyList();
	
    public MazeSettings(boolean multiplayer, boolean trail, int mazeSize,
    		int branching, int straightness, int startingTime,
            int startingHints, long seed, Collection<Modification> modifications) {
        if ((mazeSize < MIN_MAZE_SIZE || mazeSize > MAX_MAZE_SIZE)
                || (branching < MIN_BRANCHING || branching > MAX_BRANCHING)
                || (straightness < MIN_STRAIGHTNESS || straightness > MAX_STRAIGHTNESS)
                || (startingTime < MIN_STARTING_TIME || startingTime > MAX_STARTING_TIME)
                || startingHints < MIN_STARTING_HINTS) {
            throw new IllegalArgumentException("Maze parameters are incorrect");
        }

        if (modifications == null) {
            throw new NullPointerException();
        }

        this.multiplayer = multiplayer;
        this.trail = trail;
        this.mazeSize = mazeSize;
        this.branching = branching;
        this.straightness = straightness;
        this.startingTime = startingTime;
        this.startingHints = startingHints;
        this.modifications = new ArrayList<Modification>(modifications);
        if (seed == -1) {
        	this.seed = System.nanoTime();
        } else {
        	this.seed = seed;
        }
    }
	
	public MazeSettings() {
	    this(false, true, MIN_MAZE_SIZE, MIN_BRANCHING, MIN_STRAIGHTNESS, MIN_STARTING_TIME, MIN_STARTING_HINTS, System.nanoTime(), NO_MODIFICATIONS);
	}

    public boolean getMultiplayer() {
        return multiplayer;
    }
    
    public boolean getTrail() {
    	return trail;
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
    
    public int getStartingHints() {
    	return startingHints;
    }

    public List<Modification> getModifications() {
        return modifications;
    }
    
    public long getSeed() {
    	return seed;
    }

    public void setMultiplayer(boolean multiplayer) {
        this.multiplayer = multiplayer;
    }
    
    public void setTrail(boolean trail) {
    	this.trail = trail;
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
    
    public void setStartingHints(int startingHints) {
    	this.startingHints = startingHints;
    }

    public void setModifications(List<Modification> modifications) {
        this.modifications = modifications;
    }
    
    public void setSeed(long seed) {
    	this.seed = seed;
    }
}
