package window;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InstructionsWindow extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;

	public InstructionsWindow(JFrame frame) {
		super(frame);
		
		mainPanel = new JPanel(new GridLayout(2, 1));
		
		JLabel text = new JLabel("will show instructions here", JLabel.CENTER);;
        mainPanel.add(text);
	}

	public JPanel getInstructionsPanel() {
		return mainPanel;
	}
	
}
