package pages;

import game.Game;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends Page {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	
    public HomePage(JFrame frame) {
    	super(frame);
        mainPanel = new JPanel(new GridLayout(6, 1));
        
        JLabel blank = new JLabel("", JLabel.CENTER);
        mainPanel.add(blank);
        
        JLabel titleLabel = new JLabel("MAZE", JLabel.CENTER);
        mainPanel.add(titleLabel);
        
    }
    
    public JPanel getHomePanel() {
    	return mainPanel;
    }
	
    public Game.Command waitCommand() {
        return Game.Command.QUIT;
    }
}
