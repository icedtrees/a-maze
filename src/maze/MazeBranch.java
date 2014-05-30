package maze;

import java.util.Stack;

import maze.Direction;

/**
 * Represents a DFS'ing branch during the generation of the maze
 * @author Leo
 *
 */
public class MazeBranch {
	private Stack<Step> s;
	private int id;
	private boolean branchDue;
	
	public MazeBranch(Coord firstCell, Direction dir, int id) {
		this(new Step(firstCell, dir, 0), id);
	}
	public MazeBranch(Step firstStep, int id) {
		s = new Stack<Step>();
		s.push(firstStep);
		this.id = id;
		branchDue = false;
	}
	public boolean isEmpty() {
		return s.isEmpty();
	}
	public Step pop() {
		return s.pop();
	}
	public Step peek() {
		return s.peek();
	}
	public void push(Step newStep) {
		s.push(newStep);
	}
	public int getID() {
		return id;
	}
	public boolean branchDue() {
		return branchDue;
	}
	public void setBranchDue(boolean due) {
		branchDue = due;
	}
}
