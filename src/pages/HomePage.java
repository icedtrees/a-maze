package pages;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends Page {
	public enum Result implements Page.Result {
        PLAY_GAME,
        SHOW_INSTRUCTIONS,
        SHOW_HIGH_SCORES,
        QUIT_GAME,
    };
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
    private Result result;
	
    public HomePage(JFrame frame) {
    	super(frame);
        mainPanel = new JPanel(new GridLayout(6, 1));
        
        JLabel blank = new JLabel("", JLabel.CENTER);
        mainPanel.add(blank);
        
        JLabel titleLabel = new JLabel("MAZE", JLabel.CENTER);
        mainPanel.add(titleLabel);
        
        result = null;
    }
    
    public Page.Result run() {
        // Wait until the user presses the button, and then return the result
        while (result == null);
        return result;
    }
    
    public JPanel getHomePanel() {
        return mainPanel;
    }
}
