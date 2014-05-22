package pages;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InstructionsPage extends Page {
	public enum Result implements Page.Result {
        RETURN_HOME
    };
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;

	public InstructionsPage(JFrame frame) {
		super();
		
		mainPanel = new JPanel(new GridLayout(2, 1));
		
		JLabel text = new JLabel("will show instructions here", JLabel.CENTER);;
        mainPanel.add(text);
	}

	public JPanel getInstructionsPanel() {
		return mainPanel;
	}

	@Override
	public pages.Page.Result run() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
