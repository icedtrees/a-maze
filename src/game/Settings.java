package game;

import java.awt.Dimension;

/**
 * @author icedtrees
 *
 */
public class Settings {
    private int screenWidth;
    private int screenHeight;
    
    public Settings() {
        screenWidth = 800;
        screenHeight = 600;
    }
    
    public Dimension getScreenDimension() {
        return new Dimension(screenWidth, screenHeight);
    }
    
    public void setScreenDimension(Dimension newDimension) {
        screenWidth = newDimension.width;
        screenHeight = newDimension.height;
    }
    
    public void setScreenWidth(int newWidth) {
        screenWidth = newWidth;
    }
    
    public void setScreenHeight(int newHeight)  {
        screenHeight = newHeight;
    }
    
    public int getScreenWidth() {
        return screenWidth;
    }
    
    public int getScreenHeight() {
        return screenHeight;
    }
    
}
