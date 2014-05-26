package game;

import java.awt.Dimension;

/**
 * @author icedtrees
 *
 */
public class Settings {
    private enum ScreenSize {
        SMALL(800, 600),
        MEDIUM(1280, 720),
        LARGE(1600, 900);
        
        private int width;
        private int height;
        
        private ScreenSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
    
    private ScreenSize screenSize;
    
    public Settings() {
        this.screenSize = ScreenSize.SMALL;
    }
    
    public Dimension getScreenDimension() {
        return new Dimension(screenSize.width, screenSize.height);
    }
    
}
