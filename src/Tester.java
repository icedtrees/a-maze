
public class Tester {
	public static void main(String[] args) {
		Maze myMaze = new Maze(7, 2, 1);
		
		myMaze.genMazePrim();
		myMaze.drawToScreen();
		
		myMaze.genMazeDFS();
		myMaze.drawToScreen();
	}

}
