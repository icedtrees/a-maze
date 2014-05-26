package game;

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
    
    private ScreenSize screenSize;
    
    public Settings() {
        this.screenSize = ScreenSize.SMALL;
    }
    
    public ScreenSize getScreenSize() {
        return screenSize;
    }
    
    public void setScreenSize(ScreenSize newSize) {
        screenSize = newSize;
    }
    
}
