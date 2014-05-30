package pages;

import game.Campaign;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import maze.MazeSettings;
import maze.modification.*;

public class CustomPage extends Page implements ItemListener {
	public enum Result implements Page.Result {
	    PLAY_CUSTOM_GAME,
	    RETURN_HOME
    };
	private static final long serialVersionUID = 1L;
	private volatile Result result;
	
    private CardLayout descriptionLayout;
    
    private JPanel descriptionPanel;
    
    private JPanel mazePanel;
    private JPanel multiplayerPanel;
    private JPanel bootsPanel;
    private JPanel clocksPanel;
    private JPanel trailPanel;
    private JPanel fogPanel;
    private JPanel wallsPanel;
    private JPanel hintsPanel;
	
    private static final String MAZE = "Maze";
    private static final String MULTIPLAYER = "Multiplayer";
    private static final String BOOTS = "Boots";
    private static final String CLOCKS = "Clocks";
    private static final String EXPLORED_TRAIL = "Explored trail";
    private static final String FOG_OF_WAR = "Fog of war";
    private static final String SHIFTING_WALLS = "Shifting walls";
    private static final String HINTS = "Hints";
    
    private JSlider sizeSlider;
    private JSlider branchingSlider;
    private JSlider straightnessSlider;
    private JSlider timeSlider;
    
    private JSlider clockSlider;
    private JSlider bootsSlider;
    private JSlider fogSlider;
    private JSlider wallsSlider;
    
    private JCheckBox multiplayerBox;
	private JCheckBox bootsBox;
	private JCheckBox clockBox;
	private JCheckBox trailBox;
	private JCheckBox fogBox; 
	private JCheckBox shiftingWallsBox;
	private JCheckBox hintsBox;
	private MazeSettings mazeSettings;
	
	public CustomPage() {
		super();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel titleLabel = Components.makeTitle("Custom Maze");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
        add(titleLabel, c);
        
        //-----------------------------------------------------------slider for maze size
        JPanel sizeSliderPanel = Components.makePanel();
        JLabel sizeSliderLabel = Components.makeText("Maze Size:    ", 17);
        sizeSliderPanel.add(sizeSliderLabel);
        sizeSlider = Components.makeJSlider(5, 30, 9, 5, 1, 400);
        sizeSliderPanel.add(sizeSlider);
        
        c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.ipady = 0;
		c.weightx = 10;
		c.weighty = 0.08;
		add(sizeSliderPanel, c);
        
		//-----------------------------------------------------------slider for branching
        JPanel branchingSliderPanel = Components.makePanel();
        JLabel branchingSliderLabel = Components.makeText("Branching:    ", 17);
        branchingSliderPanel.add(branchingSliderLabel);
        branchingSlider = Components.makeJSlider(1, 10, 8, 1, 1, 400);
        branchingSliderPanel.add(branchingSlider);
        
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 10;
		c.weighty = 0.08;
		add(branchingSliderPanel, c);
		
		//-----------------------------------------------------------slider for straightness
        JPanel straightnessSliderPanel = Components.makePanel();
        JLabel straightnessSliderLabel = Components.makeText("Straightness:", 17);
        straightnessSliderPanel.add(straightnessSliderLabel);
        straightnessSlider = Components.makeJSlider(-10, 10, 0, 2, 1, 400);
        straightnessSliderPanel.add(straightnessSlider);
        
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weightx = 10;
		c.weighty = 0.08;
		add(straightnessSliderPanel, c);
		
		//----------------------------------------------------------slider for starting time
        JPanel timeSliderPanel = Components.makePanel();
        JLabel timeSliderLabel = Components.makeText("Starting time:", 17);
        timeSliderPanel.add(timeSliderLabel);
        timeSlider = Components.makeJSlider(20, 180, 40, 20, 10, 400);
        timeSliderPanel.add(timeSlider);
        
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.weightx = 10;
		c.weighty = 0.08;
		add(timeSliderPanel, c);
        
		//-----------------------------------------------------------panel to select features
		JLabel featuresLabel = Components.makeText("Features", 20);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.weighty = 0.01;
		c.weightx = 1;
		add(featuresLabel, c);
		
		JPanel featuresPanel = Components.makePanel();
		featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.PAGE_AXIS));
		featuresPanel.setMinimumSize(new Dimension(150, 200));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 10;
		c.gridy = 6;
		add(featuresPanel, c);
		
		JPanel checkBoxPanel = Components.makePanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		checkBoxPanel.setAlignmentX(CENTER_ALIGNMENT);
		// make checkboxes
		multiplayerBox = Components.makeCheckBox(MULTIPLAYER);
		bootsBox = Components.makeCheckBox(BOOTS);
		clockBox = Components.makeCheckBox(CLOCKS);
		trailBox = Components.makeCheckBox(EXPLORED_TRAIL);
		//fog of war should enable torches too
		fogBox = Components.makeCheckBox(FOG_OF_WAR); 
		shiftingWallsBox = Components.makeCheckBox(SHIFTING_WALLS);
		hintsBox = Components.makeCheckBox(HINTS);
		
		multiplayerBox.addItemListener(this);
		bootsBox.addItemListener(this);
		clockBox.addItemListener(this);
		trailBox.addItemListener(this);
		fogBox.addItemListener(this);
		shiftingWallsBox.addItemListener(this);
		hintsBox.addItemListener(this);
		
		multiplayerBox.setSelected(false);
		bootsBox.setSelected(false); //true
		clockBox.setSelected(false); //true
		trailBox.setSelected(false); //true
		fogBox.setSelected(false);
		shiftingWallsBox.setSelected(false);
		hintsBox.setSelected(false);
		
		// add checkboxes to featurePanel
		checkBoxPanel.add(multiplayerBox);
		checkBoxPanel.add(bootsBox);
		checkBoxPanel.add(clockBox);
		checkBoxPanel.add(trailBox);
		checkBoxPanel.add(fogBox);
		checkBoxPanel.add(shiftingWallsBox);
		checkBoxPanel.add(hintsBox);
		
		featuresPanel.add(checkBoxPanel);
		
		//-----------------------------------------------------------panel to show description
		JLabel descriptionLabel = Components.makeText("Description", 20);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		c.weighty = 0.01;
		c.weightx = 1;
		add(descriptionLabel, c);

		clockSlider = Components.makeJSlider(0, 20, 5, 5, 1, 400);
        clockSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = clockSlider.getValue();
        		if (value == 0) {
        			clockBox.setSelected(false);
        		} else {
        			clockBox.setSelected(true);
        		}
        	}
        });
		bootsSlider = Components.makeJSlider(0, 20, 5, 5, 1, 400);
		bootsSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = bootsSlider.getValue();
        		if (value == 0) {
        			bootsBox.setSelected(false);
        		} else {
        			bootsBox.setSelected(true);
        		}
        	}
        });
		fogSlider = Components.makeJSlider(0, 20, 5, 5, 1, 400);
		fogSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = fogSlider.getValue();
        		if (value == 0) {
        			fogBox.setSelected(false);
        		} else {
        			fogBox.setSelected(true);
        		}
        	}
        });
		wallsSlider = Components.makeJSlider(0, 20, 5, 5, 1, 400);
		wallsSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = wallsSlider.getValue();
        		if (value == 0) {
        			shiftingWallsBox.setSelected(false);
        		} else {
        			shiftingWallsBox.setSelected(true);
        		}
        	}
        });
		
		descriptionPanel = Components.makePanel();
		descriptionLayout = new CardLayout();
		descriptionPanel.setLayout(descriptionLayout);

		String html1 = "<html><body style='width: 300px'>";
		JLabel mazeDescription = Components.makeText(html1 + "Maze size: Controls the size of the maze.<br>"
				+ "Branching: Controls the amount of branching.<br>"
				+ "Straightness: Controls the straightness of the maze. A negative value will create very squiggly mazes.<br>"
				+ "Starting time: Controls how long you have to finish the maze.</html>", 15);
		JLabel playerDescription = Components.makeText(html1 + "Multiplayer is with two players "
				+ "playing cooperatively. If multiplayer is off, the player mode is single"
				+ "player. This is either on or off.</html>", 15);
		JLabel bootsDescription = Components.makeText(html1 + "Boots increase your movement speed. "
				+ "Move the slider to select the number of boots in the maze.</html>", 15);
		JLabel clocksDescription = Components.makeText(html1 + "Clocks give you extra time. "
				+ "Move the slider to select the number of clocks in the maze.</html>", 15);
		JLabel trailDescription = Components.makeText(html1 + "The explored trail shows the path "
				+ "you've travelled on. This is either on or off. </html>", 15);
		JLabel fogDescription = Components.makeText(html1 + "The fog of war limits your visibility. "
				+ "Move the slider to select the number of torches in the maze.</html>", 15);
		JLabel wallsDescription = Components.makeText(html1 + "Walls will shift after the player has"
				+ " moved a certain number of steps. Move the slider to select the number"
				+ " of (pairs of) walls to shift each time.</html>", 15);
		JLabel hintsDescription = Components.makeText(html1 + "The next _ steps of the correct path will be shown "
				+ "[when you press H?]. Move the slider to select the number of starting hints.</html>", 15);
		
		GridBagConstraints dCon = new GridBagConstraints();
		dCon.fill = GridBagConstraints.NONE;
		dCon.gridx = 0;
		dCon.gridy = 0;
		dCon.weighty = 1;
		dCon.weightx = 1;
		
		mazePanel = Components.makePanel();
		mazePanel.setLayout(new GridBagLayout());
		mazePanel.add(mazeDescription, dCon);
		
		multiplayerPanel = Components.makePanel();
		multiplayerPanel.setLayout(new GridBagLayout());
		multiplayerPanel.add(playerDescription, dCon);
		
		bootsPanel = Components.makePanel();
		bootsPanel.setLayout(new GridBagLayout());
		bootsPanel.add(bootsSlider, dCon);
		dCon.fill = GridBagConstraints.BOTH;
		dCon.gridy = 1;
		bootsPanel.add(bootsDescription, dCon);
		
		dCon.fill = GridBagConstraints.NONE;
		dCon.gridy = 0;
		clocksPanel = Components.makePanel();
		clocksPanel.setLayout(new GridBagLayout());
		clocksPanel.add(clockSlider, dCon);
		dCon.fill = GridBagConstraints.BOTH;
		dCon.gridy = 1;
		clocksPanel.add(clocksDescription, dCon);
		
		dCon.fill = GridBagConstraints.BOTH;
		dCon.gridy = 0;
		trailPanel = Components.makePanel();
		trailPanel.setLayout(new GridBagLayout());
		trailPanel.add(trailDescription, dCon);
		
		dCon.fill = GridBagConstraints.NONE;
		dCon.gridy = 0;
		fogPanel = Components.makePanel();
		fogPanel.setLayout(new GridBagLayout());
		fogPanel.add(fogSlider, dCon);
		dCon.fill = GridBagConstraints.BOTH;
		dCon.gridy = 1;
		fogPanel.add(fogDescription, dCon);
		
		dCon.fill = GridBagConstraints.NONE;
		dCon.gridy = 0;
		wallsPanel = Components.makePanel();
		wallsPanel.setLayout(new GridBagLayout());
		wallsPanel.add(wallsSlider, dCon);
		dCon.fill = GridBagConstraints.BOTH;
		dCon.gridy = 1;
		wallsPanel.add(wallsDescription, dCon);
		
		dCon.fill = GridBagConstraints.BOTH;
		dCon.gridy = 0;
		hintsPanel = Components.makePanel();
		hintsPanel.setLayout(new GridBagLayout());
		hintsPanel.add(hintsDescription, dCon);
		
		descriptionPanel.add(mazePanel, MAZE);
		descriptionPanel.add(multiplayerPanel, MULTIPLAYER);
		descriptionPanel.add(bootsPanel, BOOTS);
		descriptionPanel.add(clocksPanel, CLOCKS);
		descriptionPanel.add(trailPanel, EXPLORED_TRAIL);
		descriptionPanel.add(fogPanel, FOG_OF_WAR);
		descriptionPanel.add(wallsPanel, SHIFTING_WALLS);
		descriptionPanel.add(hintsPanel, HINTS);
		descriptionLayout.show(descriptionPanel, MAZE);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 10;
		add(descriptionPanel, c);
        
        addReturnButton();
        result = null;
	}

	public MazeSettings getCustomSettings() {
	    return mazeSettings;
	}
	
	private void storeCustomSettings() {
		boolean multiplayer = multiplayerBox.isSelected();
		boolean trail = trailBox.isSelected();
		int mazeSize = sizeSlider.getValue();
		int branching = branchingSlider.getValue();
		int straightness = straightnessSlider.getValue();
		int startingTime = timeSlider.getValue();
//		long seed; // haven't done yet, i just put in -1
		System.out.println(multiplayer);
		System.out.println(trail);
		System.out.println(mazeSize);
		System.out.println(branching);
		System.out.println(straightness);
		System.out.println(startingTime);
		
		ArrayList<Modification> modifications = new ArrayList<Modification>();

		if (bootsBox.isSelected()) {
			modifications.add(new SpeedMod(bootsSlider.getValue()));
			System.out.println(bootsSlider.getValue());
		}
		if (clockBox.isSelected()) {
			modifications.add(new ClockMod(clockSlider.getValue()));
			System.out.println(clockSlider.getValue());
		}
		if (fogBox.isSelected()) {
			if (multiplayer == false) {
				modifications.add(new FogMod(Campaign.SINGLEPLAYER_DEFAULT_VISION, fogSlider.getValue()));
			} else {
				modifications.add(new FogMod(Campaign.MULTIPLAYER_DEFAULT_VISION, fogSlider.getValue()));
			}
			System.out.println(fogSlider.getValue());
		}
		if (shiftingWallsBox.isSelected()) {
			modifications.add(new ShiftingWallsMod(wallsSlider.getValue(), 10));
			System.out.println(wallsSlider.getValue());
		}
		
		MazeSettings mazeSettings = new MazeSettings(multiplayer, trail, mazeSize, branching, straightness,
				 startingTime, false, -1, modifications);
		
		this.mazeSettings = mazeSettings;
	}
	
	@Override
	public CustomPage.Result run() {
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
	
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		
		if (source == multiplayerBox) {
			System.out.print(MULTIPLAYER);
			descriptionLayout.show(descriptionPanel, MULTIPLAYER);
			
		} else if (source == bootsBox) {
			System.out.print(BOOTS);
			descriptionLayout.show(descriptionPanel, BOOTS);
			
		} else if (source == clockBox) {
			System.out.print(CLOCKS);
			descriptionLayout.show(descriptionPanel, CLOCKS);
			
		} else if (source == trailBox) {
			System.out.print(EXPLORED_TRAIL);
			descriptionLayout.show(descriptionPanel, EXPLORED_TRAIL);
			
		} else if (source == fogBox) {
			System.out.print(FOG_OF_WAR);
			descriptionLayout.show(descriptionPanel, FOG_OF_WAR);
			
		} else if (source == shiftingWallsBox) {
			System.out.print(SHIFTING_WALLS);
			descriptionLayout.show(descriptionPanel, SHIFTING_WALLS);
			
		} else if (source == hintsBox) {
			System.out.print(HINTS);
			descriptionLayout.show(descriptionPanel, HINTS);
		}
		
		if (e.getStateChange() == ItemEvent.SELECTED) {
			System.out.println(" was selected");
			if (source == bootsBox) {
				bootsSlider.setValue(3);
			} else if (source == clockBox) {
				clockSlider.setValue(3);
			}
		} else if (e.getStateChange() == ItemEvent.DESELECTED) {
			System.out.println(" was deselected");
			if (source == bootsBox) {
				bootsSlider.setValue(0);
			} else if (source == clockBox) {
				clockSlider.setValue(0);
			} else if (source == fogBox) {
				fogSlider.setValue(0);
			} else if (source == shiftingWallsBox) {
				wallsSlider.setValue(0);
			}
		}
	}
	
	private void addReturnButton() {
        JPanel returnPanel = Components.makePanel();
        returnPanel.setLayout(new FlowLayout());
		
        JButton returnButton = Components.makeButton("return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("return to main menu");
            	result = Result.RETURN_HOME;
            }
        });
		
        JButton playButton = Components.makeButton("Play custom game");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("play custom game");
                storeCustomSettings();
            	result = Result.PLAY_CUSTOM_GAME;
            }
        });
        returnPanel.add(playButton);
        
		returnPanel.add(returnButton);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		c.weighty = 0.02;
		add(returnPanel, c);
	}
	
}
