package maze;

public class Tester {
    public static void main(String[] args) {
        Maze myMaze = new Maze(81, 51, 1);
        System.out.println(myMaze.toString());
        myMaze.drawToScreen();
    }

}
