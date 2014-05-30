package pages;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * A page which stores and displays instructions on how to play the game. These
 * are displayed as expandable buttons, each button describing a particular
 * feature and how it functions. 
 */
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
    private static final String HINTS = "Hints";
    
//    private JList<String> selectionList; // JDK 7 version
     private JList selectionList;
    private JPanel showDescription;
    private CardLayout showLayout;

	public InstructionsPage() {
		super();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel titleLabel = Components.makeTitle("Instructions");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.gridwidth = 2;
		c.weighty = 0.15;
        add(titleLabel, c);
        
        
        JPanel selectionPanel = Components.makePanel();
        selectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints selectCon = new GridBagConstraints();
        selectCon.fill = GridBagConstraints.BOTH; 
        selectCon.gridx = 0;
        selectCon.gridy = 0;
        selectCon.weighty = 0.05;
        
        JLabel selectionTitle = Components.makeText("Select to find out more:", 28);
        selectionPanel.add(selectionTitle, selectCon);
        
//        DefaultListModel<String> selectionListModel = new DefaultListModel<String>(); // JDK 7 version
        DefaultListModel selectionListModel = new DefaultListModel();
        selectionListModel.addElement(SINGLE_PLAYER);
        selectionListModel.addElement(MULTI_PLAYER);
        selectionListModel.addElement(BOOTS);
        selectionListModel.addElement(CLOCKS);
        selectionListModel.addElement(EXPLORED_TRAIL);
        selectionListModel.addElement(FOG_OF_WAR);
        selectionListModel.addElement(SHIFTING_WALLS);
        selectionListModel.addElement(HINTS);
        
//        selectionList = new JList<String>(selectionListModel); // JDK 7 version
        selectionList = new JList(selectionListModel);
        selectionList.setOpaque(false);
        ListCellRenderer renderer = new FocusedListCellRenderer();
        selectionList.setCellRenderer(renderer);
        selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Font font = selectionList.getFont();
        selectionList.setFont(new Font(font.getName(), Font.PLAIN, 22));
        selectionList.setSelectedIndex(0);
        selectionList.setForeground(Color.WHITE);
        selectionList.addListSelectionListener(this);
        selectionList.setVisibleRowCount(5);
        selectCon.fill = GridBagConstraints.BOTH;
        selectCon.gridx = 0;
        selectCon.gridy = 1;
        selectCon.weighty = 0.25;
        selectionPanel.add(selectionList, selectCon);
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 70;
		c.gridwidth = 1;
		c.weighty = 0.5;
		add(selectionPanel, c);
		
		JPanel descriptionPanel = Components.makePanel();
        c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 0;
		c.weighty = 0.5;
		add(descriptionPanel, c);
		
		showDescription = Components.makePanel();
		showLayout = new CardLayout();
		showDescription.setLayout(showLayout);
		
        String html1 = "<html><body style='width: 300px'>";
		
        JPanel singlePlayerPanel = makeDescription(html1 + "Single Player: <br>Use the arrow keys to move. Your goal is to help cat find his fish before he collapses from hunger.</html>", "img/arrowKeys.png");		
        JPanel multiPlayerPanel = makeDescription(html1 + "Multi Player: <br>Player 1: Use the WASD keys to move. "
        		+ "<br> Player 2:   Use arrows to move.<br><br>Both cats must find their favourite coloured fish before they collapse from hunger! Once you get to your fish your timer will stop counting down.<br><br>Cats cannot walk past each other.</html>", "img/WASDarrowKeys.png");
        // need to find images for the rest of these, preferably of our actual maze so it's clear what the feature is
		JPanel bootsPanel = makeDescription(html1 + "Boots: <br>When cat picks up a pair of these boots, he can run faster!</html>", "img/boots.png");
		JPanel clocksPanel = makeDescription(html1 + "Clocks: <br>Cat is able to turn back time with one of these clocks.<br>This means that cat has more time to find his fish.</html>", "img/clocks.png");
		JPanel trailPanel = makeDescription(html1 + "Explored trail: <br>Cat leaves colourful footprints to help him remember where he's been.</html>", "img/trail.png");
		JPanel fogPanel = makeDescription(html1 + "Fog of war: <br>It's nighttime and cat can't see very far."
				+ "Fortunately cat can pick up torches to increase his field of view.</html>", "img/fog.png");
		//fill in the blanks
		JPanel wallsPanel = makeDescription(html1 + "Shifting walls: <br>Is this Hogwarts? Every few steps that cat takes, "
				+ "a bunch of walls will shift and move around, confuddling poor cat.</html>", "img/walls.png");
		JPanel hintsPanel = makeDescription(html1 + "Hints: <br>"
				+ "When hints are on you get 5 hints. The first hint highlights 100% of the path. The next hint highlights 80%, then 60%, 40%, 20% and then cat is left all alone.</html>", "img/hints.png");
		
		showDescription.add(singlePlayerPanel, SINGLE_PLAYER);
		showDescription.add(multiPlayerPanel, MULTI_PLAYER);
		showDescription.add(bootsPanel, BOOTS);
		showDescription.add(clocksPanel, CLOCKS);
		showDescription.add(trailPanel, EXPLORED_TRAIL);
		showDescription.add(fogPanel, FOG_OF_WAR);
		showDescription.add(wallsPanel, SHIFTING_WALLS);
		showDescription.add(hintsPanel, HINTS);
		
		descriptionPanel.add(showDescription);
        
        addReturnButton();
        result = null;
	}

	/**
	 * Runs the page.
	 */
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

	private JPanel makeDescription(String description, String filename) {
		JPanel featurePanel = Components.makePanel();
		featurePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		JLabel featureDescription = Components.makeText(description, 18);
		ImageIcon feature = new ImageIcon(filename);
		JLabel featureLabel = new JLabel(feature, JLabel.CENTER);
		featureLabel.setOpaque(false);
		featureLabel.setLayout(new BorderLayout());
		featurePanel.add(featureLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		featurePanel.add(featureDescription, c);
		return featurePanel;
	}
	
	private void addReturnButton() {
        JPanel returnPanel = Components.makePanel();
        returnPanel.setLayout(new FlowLayout());
		
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
		c.weighty = 0.05;
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
				showLayout.show(showDescription, BOOTS);
			} else if (selectionList.getSelectedIndex() == 3) {
				System.out.println("clocks selected");
				showLayout.show(showDescription, CLOCKS);
			} else if (selectionList.getSelectedIndex() == 4) {
				System.out.println("trail selected");
				showLayout.show(showDescription, EXPLORED_TRAIL);
			} else if (selectionList.getSelectedIndex() == 5) {
				System.out.println("fog selected");
				showLayout.show(showDescription, FOG_OF_WAR);
			} else if (selectionList.getSelectedIndex() == 6) {
				System.out.println("shifting walls selected");
				showLayout.show(showDescription, SHIFTING_WALLS);
			} else if (selectionList.getSelectedIndex() == 7) {
				System.out.println("hints selected");
				showLayout.show(showDescription, HINTS);
			}
		}
	}
	
}
