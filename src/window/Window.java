package window;

import javax.swing.JFrame;


/**
 * @author icedtrees
 *
 */

public abstract class Window {
	private final JFrame window;
	
	
    public Window(int width, int height) {
        window = new JFrame();
        window.setSize(width, height);
    }
    
    
    public void show() {
        window.setVisible(true);    
    }
    
    public void hide() {
        window.setVisible(false);        
    }
}
