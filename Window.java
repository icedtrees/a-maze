import javax.swing.JFrame;


/**
 * @author icedtrees
 *
 */

public abstract class Window {
	private JFrame window;
	
	
    public Window(int width, int height) {
        window = new JFrame();
        window.setSize(width, height);
    }
    
    public JFrame getWindow() {
    	return window;
    }
    
    public void show() {
        window.setVisible(true);    
    }
    
    public void hide() {
        window.setVisible(false);        
    }
}
