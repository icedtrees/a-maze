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
	
	public HighScoresPage() {
		super();
		
		setLayout(new GridLayout(2,1));
		
		JLabel text = new JLabel("display high scores", JLabel.CENTER);;
        add(text);
	}

	@Override
	public Result run() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
