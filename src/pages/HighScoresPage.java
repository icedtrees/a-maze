package pages;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighScoresPage extends Page {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	
	public HighScoresPage(JFrame frame) {
		super();
		
		mainPanel = new JPanel(new GridLayout(2,1));
		
		JLabel text = new JLabel("display high scores", JLabel.CENTER);;
        mainPanel.add(text);
	}
	
	public JPanel getHighScoresPanel() {
		return mainPanel;
	}

	@Override
	public Result run() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
