package maze;

public class MazeStats {
	private int numPlayers;
	private double[] playerTimer;
	private boolean[] playerFinished;
	
	public MazeStats(int numPlayers, double timer) {
		this.numPlayers = numPlayers;
		this.playerTimer = new double[] {timer, timer};
		this.playerFinished = new boolean[] {false, false};
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	public double getTimer(int playerNum) {
		return playerTimer[playerNum - 1];
	}
	public void setTimer(int playerNum, double timer) {
		playerTimer[playerNum - 1] = timer;
	}
	public void setTimerRelative(int playerNum, int timer) {
		playerTimer[playerNum - 1] += timer;
	}
	public boolean playerFinished(int playerNum) {
		return playerFinished[playerNum - 1];
	}
	public void setPlayerFinished(int playerNum, boolean finished) {
		playerFinished[playerNum - 1] = finished;
	}
}
