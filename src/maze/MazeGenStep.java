package maze;

public class MazeGenStep implements Comparable<MazeGenStep> {
    private Tile newTile;
    private Tile wallTile;
    private double weight;
    private int dijkstraWeight;
    
    public MazeGenStep(Tile newNewTile, Tile newWallTile) {
        this(newNewTile, newWallTile, 0, 0);
    }
    public MazeGenStep(Tile newNewTile, int dijkstraWeight) {
        this(newNewTile, null, 0, dijkstraWeight);
    }
    public MazeGenStep(Tile newNewTile, Tile newWallTile, double newWeight) {
        this(newNewTile, newWallTile, newWeight, 0);
    }
    public MazeGenStep(Tile newNewTile, Tile newWallTile, double newWeight, int dijkstraWeight) {
    	newTile = newNewTile;
        wallTile = newWallTile;
        weight = newWeight;
        this.dijkstraWeight = dijkstraWeight;
    }
    
    public Tile getNewTile() {
        return newTile;
    }
    public Tile getWallTile() {
        return wallTile;
    }
    public double getWeight() {
    	return weight;
    }
    public int getDijkstraWeight() {
    	return dijkstraWeight;
    }
    
    public void setValues(int value) {
        newTile.setValue(value);
        wallTile.setValue(value);
    }
    public boolean isValidMove() {
        if (newTile.getValue() != Tile.WALL) {
            return false;
        }
        return true;
    }
	@Override
	public int compareTo(MazeGenStep o) {
		return Double.compare(this.dijkstraWeight, o.dijkstraWeight);
	}
}
