package game;

import maze.MazeSettings;

/**
 * @author icedtrees
 *
 */
public class Settings {
    public enum ScreenSize {
        SMALL(800, 600),
        MEDIUM(1280, 720),
        LARGE(1600, 900);
        
        public final int width;
        public final int height;
        
        private ScreenSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
    
    public ScreenSize screenSize;
    public int FPS;
    public MazeSettings mazeStats;
    
    public Settings() {
        this.screenSize = ScreenSize.SMALL;
        this.FPS = 50;
        this.mazeStats = new MazeSettings();
    }
    
}
