package maze;

import java.util.Stack;

public class BranchCounter {
	private Coordinate coord;
	private int branchCount;
	private Stack<Coordinate> pathSince;
	
	public BranchCounter(Coordinate coord, int branchCount) {
		this.coord = coord;
		this.branchCount = branchCount;
		pathSince = new Stack<Coordinate>();
	}
	
	public Coordinate getCoord() {
		return coord;
	}
	public int getBranchCount() {
		return branchCount;
	}
	public boolean pathEmpty() {
		return pathSince.isEmpty();
	}
	public Coordinate popPath() {
		return pathSince.pop();
	}
	public void pushPath(Coordinate newCoord) {
		pathSince.push(newCoord);
	}
	public void decBranchCount() {
		branchCount--;
	}
}
