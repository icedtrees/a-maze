package maze;

public class MazeGenStep {
    private Tile newTile;
    private Tile wallTile;
    public MazeGenStep(Tile newNewTile, Tile newWallTile) {
        newTile = newNewTile;
        wallTile = newWallTile;
    }
    
    public Tile getNewTile() {
        return newTile;
    }
    public Tile getWallTile() {
        return wallTile;
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
}
