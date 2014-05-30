package pages;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
    private JPanel bootsPanel;
    private JPanel clocksPanel;
    private JPanel trailPanel;
    private JPanel fogPanel;
    private JPanel wallsPanel;
    private JPanel hintsPanel;
	
    private static final String MAZE = "Maze";
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
    
	private JCheckBox bootsBox;
	private JCheckBox clockBox;
	private JCheckBox trailBox;
	private JCheckBox fogBox; 
	private JCheckBox shiftingWallsBox;
	private JCheckBox hintsBox;
	
	public CustomPage() {
		super();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel titleLabel = Components.makeTitle("Custom Maze");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.ipady = 20;
        add(titleLabel, c);
        
        // can i just add a slider, or does it need to be in a jpanel?
        JPanel sizeSliderPanel = Components.makePanel();
        
        JLabel sizeSliderLabel = Components.makeText("Maze Size:    ", 20);
        sizeSliderPanel.add(sizeSliderLabel);
        
//        final JTextArea sizeValue = new JTextArea(1, 1);
//        sizeC.gridx = 3;
//        sizeC.gridy = 0;
//        sizeSliderPanel.add(sizeValue, sizeC);
        
      //-----------------------------------------------------------slider for maze size
        sizeSlider = Components.makeJSlider(5, 40, 11, 5, 1, 400);
        sizeSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = sizeSlider.getValue();
        		// supposed to be an odd number...
        		System.out.println("Size slider's value is " + value);
//        		sizeValue.setText(String.valueOf(value));
        	}
        });
        
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
        
        JLabel branchingSliderLabel = Components.makeText("Branching:    ", 20);
        branchingSliderPanel.add(branchingSliderLabel);
        
        branchingSlider = Components.makeJSlider(1, 10, 8, 1, 1, 400);
        branchingSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = branchingSlider.getValue();
        		System.out.println("Branching slider's value is " + value);
        	}
        });
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
        
        JLabel straightnessSliderLabel = Components.makeText("Straightness:", 20);
        straightnessSliderPanel.add(straightnessSliderLabel);
        
        straightnessSlider = Components.makeJSlider(-10, 10, 0, 2, 1, 400);
        straightnessSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = straightnessSlider.getValue();
        		System.out.println("Straightness slider's value is " + value);
        	}
        });
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
        
        JLabel timeSliderLabel = Components.makeText("Starting time:", 20);
        timeSliderPanel.add(timeSliderLabel);
        
        timeSlider = Components.makeJSlider(20, 300, 120, 20, 10, 400);
        timeSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = timeSlider.getValue();
        		System.out.println("Time slider's value is " + value);
        	}
        });
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
		c.weighty = 3;
		c.weightx = 1;
		add(featuresLabel, c);
		
		JPanel featuresPanel = Components.makePanel();
		featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.PAGE_AXIS));
		featuresPanel.setMinimumSize(new Dimension(150, 300));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 5;
		c.gridy = 6;
		add(featuresPanel, c);
		
		JPanel checkBoxPanel = Components.makePanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		checkBoxPanel.setAlignmentX(CENTER_ALIGNMENT);
		// make checkboxes
		bootsBox = Components.makeCheckBox(BOOTS);
		clockBox = Components.makeCheckBox(CLOCKS);
		trailBox = Components.makeCheckBox(EXPLORED_TRAIL);
		//fog of war should enable torches too
		fogBox = Components.makeCheckBox(FOG_OF_WAR); 
		shiftingWallsBox = Components.makeCheckBox(SHIFTING_WALLS);
		hintsBox = Components.makeCheckBox(HINTS);
		
		bootsBox.addItemListener(this);
		clockBox.addItemListener(this);
		trailBox.addItemListener(this);
		fogBox.addItemListener(this);
		shiftingWallsBox.addItemListener(this);
		hintsBox.addItemListener(this);
		
		bootsBox.setSelected(false); //true
		clockBox.setSelected(false); //true
		trailBox.setSelected(false); //true
		fogBox.setSelected(false);
		shiftingWallsBox.setSelected(false);
		hintsBox.setSelected(false);
		
		// add checkboxes to featurePanel
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
		c.weighty = 3;
		c.weightx = 1;
		add(descriptionLabel, c);

		clockSlider = Components.makeJSlider(0, 100, 3, 10, 5, 400);
        clockSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = clockSlider.getValue();
        		System.out.println("clock slider's value is " + value);
        		if (value == 0) {
        			clockBox.setSelected(false);
        		} else {
        			clockBox.setSelected(true);
        		}
        	}
        });
		bootsSlider = Components.makeJSlider(0, 100, 3, 10, 5, 400);
		bootsSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = bootsSlider.getValue();
        		System.out.println("boots slider's value is " + value);
        		if (value == 0) {
        			bootsBox.setSelected(false);
        		} else {
        			bootsBox.setSelected(true);
        		}
        	}
        });
		fogSlider = Components.makeJSlider(0, 100, 0, 10, 5, 400);
		fogSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = fogSlider.getValue();
        		System.out.println("fog slider's value is " + value);
        		if (value == 0) {
        			fogBox.setSelected(false);
        		} else {
        			fogBox.setSelected(true);
        		}
        	}
        });
		wallsSlider = Components.makeJSlider(0, 20, 0, 5, 1, 400);
		wallsSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = wallsSlider.getValue();
        		System.out.println("wall slider's value is " + value);
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
				+ "Branching: Controls _____________<br>"
				+ "Straightness: Controls ___________<br>"
				+ "Starting time: Controls how long you have to finish the maze.</html>", 15);
		JLabel bootsDescription = Components.makeText(html1 + "Boots increase your movement speed. "
				+ "Move the slider to select the frequency of boots in the maze.</html>", 15);
		JLabel clocksDescription = Components.makeText(html1 + "Clocks give you extra time. "
				+ "Move the slider to select the frequency of clocks in the maze.</html>", 15);
		JLabel trailDescription = Components.makeText(html1 + "The explored trail shows the path "
				+ "you've travelled on. This is either on or off. </html>", 15);
		JLabel fogDescription = Components.makeText(html1 + "The fog of war limits your visibility. "
				+ "Move the slider to select the frequency of torches.</html>", 15);
		JLabel wallsDescription = Components.makeText(html1 + "Walls will shift after the player has"
				+ " moved a certain number of steps. Move the slider to select the percentage"
				+ " of walls to shift each time.</html>", 15);
		JLabel hintsDescription = Components.makeText(html1 + "The next _ steps of the correct path will be shown "
				+ "[when you press H?]. This is either on or off.</html>", 15);
		
		GridBagConstraints dCon = new GridBagConstraints();
		dCon.fill = GridBagConstraints.NONE;
		dCon.gridx = 0;
		dCon.gridy = 0;
		dCon.weighty = 1;
		dCon.weightx = 1;
		
		mazePanel = Components.makePanel();
		mazePanel.setLayout(new GridBagLayout());
		mazePanel.add(mazeDescription, dCon);
		
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
		c.weighty = 5;
		add(descriptionPanel, c);
        
        addReturnButton();
        result = null;
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
		if (source == bootsBox) {
			System.out.print(BOOTS);
			//show description for boots
			// set settings to pass into maze
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
		
        JButton returnBut = Components.makeButton("return");
        returnBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("return to main menu");
            	result = Result.RETURN_HOME;
            }
        });
		
		returnPanel.add(returnBut);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		c.weighty = 0.02;
		add(returnPanel, c);
	}
	
}
