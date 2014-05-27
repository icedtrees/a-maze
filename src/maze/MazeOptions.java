package maze;

public class MazeOptions {
	private int numPlayers;
	private int[] playerTimer;
	private boolean[] playerFinished;
	
	public MazeOptions(int numPlayers, int timer) {
		this.numPlayers = numPlayers;
		this.playerTimer = new int[]{timer, timer};
		this.playerFinished = new boolean[]{false, false};
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	public int getTimer(int playerNum) {
		return playerTimer[playerNum - 1];
	}
	public void setTimer(int playerNum, int timer) {
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
