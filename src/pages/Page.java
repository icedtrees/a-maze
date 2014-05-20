package pages;

import javax.swing.JFrame;
import javax.swing.JPanel;


public abstract class Page extends JPanel {
    public enum results {
        
    };

	private static final long serialVersionUID = 1L;
	private final JFrame mainFrame;
	
	
    public Page(JFrame frame) {
        mainFrame = frame;
    }
    
    public void show() {
        this.setVisible(true);    
    }
    
    public void hide() {
        this.setVisible(false);        
    }
    
}
