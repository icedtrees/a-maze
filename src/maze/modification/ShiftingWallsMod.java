package maze.modification;

import java.util.Random;

import maze.Maze;

public class ShiftingWallsMod implements Modification{
	private int numWalls;
	private int stepsBeforeShift;
	
	public ShiftingWallsMod(int numWalls, int stepsBeforeShift) {
		this.numWalls = numWalls;
		this.stepsBeforeShift = stepsBeforeShift;
	}
	
	@Override
	public void apply(Maze maze, Random rand) {
		maze.setShiftingWalls(true, numWalls, stepsBeforeShift);
	}
}
