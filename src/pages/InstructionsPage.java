package pages;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class InstructionsPage extends Page implements ListSelectionListener{
	public enum Result implements Page.Result {
        RETURN_HOME
    };
	private static final long serialVersionUID = 1L;
	private volatile Result result;
	
    private static final String SINGLE_PLAYER = "Single Player";
    private static final String MULTI_PLAYER = "Multi Player";
    private static final String BOOTS = "Boots";
    private static final String CLOCKS = "Clocks";
    private static final String EXPLORED_TRAIL = "Explored trail";
    private static final String FOG_OF_WAR = "Fog of war";
    private static final String SHIFTING_WALLS = "Shifting walls";
    
    private JList<String> selectionList;
    private JPanel showDescription;
    private CardLayout showLayout;

	public InstructionsPage() {
		super();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel titleLabel = Components.makeTitle("Instructions");
		c.fill = GridBagConstraints.HORIZONTAL; //not sure what this does
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.gridwidth = 2;
		c.weighty = 0.25;
        add(titleLabel, c);
        
        
        JPanel selectionPanel = Components.makePanel();
        selectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints selectCon = new GridBagConstraints();
        selectCon.fill = GridBagConstraints.HORIZONTAL; //not sure what this does
        selectCon.gridx = 0;
        selectCon.gridy = 0;
        selectCon.weighty = 0.05;
        
        JLabel selectionTitle = Components.makeText("Select to find out more:", 28);
        selectionPanel.add(selectionTitle, selectCon);
        
        DefaultListModel<String> selectionListModel = new DefaultListModel<String>();
        selectionListModel.addElement(SINGLE_PLAYER);
        selectionListModel.addElement(MULTI_PLAYER);
        selectionListModel.addElement(BOOTS);
        selectionListModel.addElement(CLOCKS);
        selectionListModel.addElement(EXPLORED_TRAIL);
        selectionListModel.addElement(FOG_OF_WAR);
        selectionListModel.addElement(SHIFTING_WALLS);
        
        selectionList = new JList<String>(selectionListModel);
        selectionList.setOpaque(false);
        ((javax.swing.DefaultListCellRenderer)selectionList.getCellRenderer()).setOpaque(false);
        selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Font font = selectionList.getFont();
        selectionList.setFont(new Font(font.getName(), Font.PLAIN, 22));
        selectionList.setSelectedIndex(0);
        selectionList.setForeground(Color.WHITE);
        selectionList.addListSelectionListener(this);
        selectionList.setVisibleRowCount(7);
        selectCon.fill = GridBagConstraints.BOTH; //not sure what this does
        selectCon.gridx = 0;
        selectCon.gridy = 1;
        selectCon.weighty = 0.25;
        selectionPanel.add(selectionList, selectCon);
        
        
        c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 70;
		c.gridwidth = 1;
		c.weighty = 0.75;
		add(selectionPanel, c);
		
		JPanel descriptionPanel = Components.makePanel();
		descriptionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 50));
		//borderlayout?
        c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 0;
		c.weighty = 0.75;
		add(descriptionPanel, c);
		
		showDescription = Components.makePanel();
		showLayout = new CardLayout();
		showDescription.setLayout(showLayout);
		
        JPanel singlePlayerPanel = Components.makePanel();
        singlePlayerPanel.setLayout(new GridLayout(2, 0));
        JLabel instructions1 = Components.makeText("Single Player: Use the arrow keys to move.", 20);
		ImageIcon singlePlayer = new ImageIcon("src/arrowKeys.png");
		JLabel singlePlayerLabel = new JLabel(singlePlayer, JLabel.CENTER);
		singlePlayerLabel.setOpaque(false);
		singlePlayerLabel.setLayout(new BorderLayout());
		singlePlayerPanel.add(singlePlayerLabel);
		singlePlayerPanel.add(instructions1);
		
        JPanel multiPlayerPanel = Components.makePanel();
        multiPlayerPanel.setLayout(new GridLayout(2, 0));
        // order of players might be switched around.
        JLabel instructions2 = Components.makeText("<html>Multi Player: <br>Player 1: Player 2: <br> Use the WASD keys to move. Use WASD to move.</html>", 20);
		ImageIcon multiPlayer = new ImageIcon("src/WASDarrowKeys.png");
		JLabel multiPlayerLabel = new JLabel(multiPlayer, JLabel.CENTER);
		multiPlayerLabel.setOpaque(false);
		multiPlayerLabel.setLayout(new BorderLayout());
		multiPlayerPanel.add(multiPlayerLabel);
		multiPlayerPanel.add(instructions2);
		
		showDescription.add(singlePlayerPanel, SINGLE_PLAYER);
		showDescription.add(multiPlayerPanel, MULTI_PLAYER);
		descriptionPanel.add(showDescription);
		
        
        addReturnButton();
        result = null;
	}

	public InstructionsPage.Result run() {
	    result = null;
    	while (result == null) {
    		// will need to modify this busy block to thread.notify and thread.wait?
    		try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		return result;
	}

	private void addReturnButton() {
        JPanel returnPanel = Components.makePanel();
        returnPanel.setLayout(new FlowLayout());
        returnPanel.setOpaque(false);
		
        JButton returnBut = Components.makeButton("return");
        returnBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	result = Result.RETURN_HOME;
            }
        });
		
		returnPanel.add(returnBut);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH; //not sure what this does
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weighty = 0.10;
		add(returnPanel, c);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			if (selectionList.getSelectedIndex() == 0) {
				System.out.println("single player selected");
				showLayout.show(showDescription, SINGLE_PLAYER);
			} else if (selectionList.getSelectedIndex() == 1) {
				System.out.println("multi player selected");
				showLayout.show(showDescription, MULTI_PLAYER);
			} else if (selectionList.getSelectedIndex() == 2) {
				System.out.println("boots selected");
			} else if (selectionList.getSelectedIndex() == 3) {
				System.out.println("clocks selected");
			} else if (selectionList.getSelectedIndex() == 4) {
				System.out.println("trail selected");
			} else if (selectionList.getSelectedIndex() == 5) {
				System.out.println("fog selected");
			} else if (selectionList.getSelectedIndex() == 6) {
				System.out.println("shifting walls selected");
			}
		}
		
	}
	
}
