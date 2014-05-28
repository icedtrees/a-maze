package pages;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CustomPage extends Page implements ItemListener {
	public enum Result implements Page.Result {
        RETURN_HOME
        //APPLY_SETTINGS ?
    };
	private static final long serialVersionUID = 1L;
	private volatile Result result;
	
    private CardLayout sliderLayout;
    private CardLayout infoLayout;
    
    private JPanel sliderPanel;
    private JPanel infoPanel;
    
    private JLabel mazeDescription;
    private JLabel bootsDescription;
    private JLabel clocksDescription;
    private JLabel trailDescription;
    private JLabel fogDescription;
    private JLabel wallsDescription;
	
    private static final String MAZE = "Maze";
    private static final String BOOTS = "Boots";
    private static final String CLOCKS = "Clocks";
    private static final String EXPLORED_TRAIL = "Explored trail";
    private static final String FOG_OF_WAR = "Fog of war";
    private static final String SHIFTING_WALLS = "Shifting walls";
    
	private JCheckBox bootsBox;
	private JCheckBox clockBox;
	private JCheckBox trailBox;
	//fog of war should enable torches too
	private JCheckBox fogBox; 
	private JCheckBox shiftingWallsBox;
	
	
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
        final JSlider sizeSlider = Components.makeJSlider(5, 30, 11, 5, 1, 400);
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
		c.weighty = 0.08;
		add(sizeSliderPanel, c);
        
		//-----------------------------------------------------------slider for branching
        JPanel branchingSliderPanel = Components.makePanel();
        
        JLabel branchingSliderLabel = Components.makeText("Branching:    ", 20);
        branchingSliderPanel.add(branchingSliderLabel);
        
        final JSlider branchingSlider = Components.makeJSlider(1, 10, 8, 1, 1, 400);
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
		c.weighty = 0.08;
		add(branchingSliderPanel, c);
		
		//-----------------------------------------------------------slider for straightness
        JPanel straightnessSliderPanel = Components.makePanel();
        
        JLabel straightnessSliderLabel = Components.makeText("Straightness:", 20);
        straightnessSliderPanel.add(straightnessSliderLabel);
        
        final JSlider straightnessSlider = Components.makeJSlider(-10, 10, 0, 2, 1, 400);
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
		c.weighty = 0.08;
		add(straightnessSliderPanel, c);
		
		//----------------------------------------------------------slider for starting time
        JPanel timeSliderPanel = Components.makePanel();
        
        JLabel timeSliderLabel = Components.makeText("Starting time:", 20);
        timeSliderPanel.add(timeSliderLabel);
        
        final JSlider timeSlider = Components.makeJSlider(20, 300, 120, 20, 10, 400);
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
		c.weighty = 0.08;
		add(timeSliderPanel, c);
        
		//-----------------------------------------------------------panel to select features
		// should it be a translucent panel later?
		JPanel featuresPanel = Components.makePanel();
		featuresPanel.setLayout(new BorderLayout());
		
		JLabel featuresLabel = Components.makeText("Features", 20);
		featuresPanel.add(featuresLabel, BorderLayout.NORTH);
		featuresPanel.setMinimumSize(new Dimension(150, 300));
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.weighty = 10;
		c.weightx = 1;
		add(featuresPanel, c);
		
		
		JPanel checkBoxPanel = Components.makePanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		// make checkboxes
		bootsBox = Components.makeCheckBox(BOOTS);
		clockBox = Components.makeCheckBox(CLOCKS);
		trailBox = Components.makeCheckBox(EXPLORED_TRAIL);
		//fog of war should enable torches too
		fogBox = Components.makeCheckBox(FOG_OF_WAR); 
		shiftingWallsBox = Components.makeCheckBox(SHIFTING_WALLS);
		
		bootsBox.addItemListener(this);
		clockBox.addItemListener(this);
		trailBox.addItemListener(this);
		fogBox.addItemListener(this);
		shiftingWallsBox.addItemListener(this);
		
		bootsBox.setSelected(false); //true
		clockBox.setSelected(false); //true
		trailBox.setSelected(false); //true
		fogBox.setSelected(false);
		shiftingWallsBox.setSelected(false);
		
		// add checkboxes to featurePanel
		checkBoxPanel.add(bootsBox);
		checkBoxPanel.add(clockBox);
		checkBoxPanel.add(trailBox);
		checkBoxPanel.add(fogBox);
		checkBoxPanel.add(shiftingWallsBox);
		
		// have to figure out how to align checkboxes
		
		featuresPanel.add(checkBoxPanel, BorderLayout.CENTER);
		
		
		//-----------------------------------------------------------panel to show description
		JPanel descriptionPanel = Components.makePanel();
		descriptionPanel.setLayout(new GridBagLayout());
		GridBagConstraints dCon = new GridBagConstraints();
		dCon.fill = GridBagConstraints.HORIZONTAL;
		dCon.gridx = 0;
		dCon.gridy = 0;
		dCon.weighty = 1;
		dCon.weightx = 1;
		
		JLabel descriptionLabel = Components.makeText("Description", 20);
		descriptionPanel.add(descriptionLabel, dCon);
		
		sliderPanel = Components.makePanel();
		sliderLayout = new CardLayout();
		sliderPanel.setLayout(sliderLayout);
		final JSlider clockSlider = Components.makeJSlider(0, 100, 3, 10, 5, 100);
        clockSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = clockSlider.getValue();
        		System.out.println("clock slider's value is " + value);
        	}
        });
		final JSlider bootsSlider = Components.makeJSlider(0, 100, 3, 10, 5, 200);
		bootsSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = bootsSlider.getValue();
        		System.out.println("boots slider's value is " + value);
        	}
        });
		final JSlider fogSlider = Components.makeJSlider(0, 100, 0, 10, 5, 200);
		fogSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = fogSlider.getValue();
        		System.out.println("fog slider's value is " + value);
        	}
        });
		final JSlider wallsSlider = Components.makeJSlider(0, 100, 0, 10, 5, 200);
		clockSlider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent event) {
        		int value = wallsSlider.getValue();
        		System.out.println("wall slider's value is " + value);
        	}
        });
		JLabel blankLabel = Components.makeText("     ", 13);
		sliderPanel.add(clockSlider, CLOCKS);
		sliderPanel.add(bootsSlider, BOOTS);
		sliderPanel.add(fogSlider, FOG_OF_WAR);
		sliderPanel.add(wallsSlider, SHIFTING_WALLS);
		sliderPanel.add(blankLabel, MAZE);
		sliderPanel.add(blankLabel, EXPLORED_TRAIL);
		sliderLayout.show(sliderPanel, MAZE);
		
		dCon.gridy = 1;
		descriptionPanel.add(sliderPanel, dCon);
		
		
		infoPanel = Components.makePanel();
		infoLayout = new CardLayout();
		infoPanel.setLayout(infoLayout);
		mazeDescription = Components.makeText("maze description of sliders", 13);
		bootsDescription = Components.makeText("boots description of sliders", 13);
		clocksDescription = Components.makeText("clocks description of sliders", 13);
		trailDescription = Components.makeText("explored trail description", 13);
		fogDescription = Components.makeText("fog of war description of sliders", 13);
		wallsDescription = Components.makeText("shifting walls description of sliders", 13);
		infoPanel.add(mazeDescription, MAZE);
		infoPanel.add(bootsDescription, BOOTS);
		infoPanel.add(clocksDescription, CLOCKS);
		infoPanel.add(trailDescription, EXPLORED_TRAIL);
		infoPanel.add(fogDescription, FOG_OF_WAR);
		infoPanel.add(wallsDescription, SHIFTING_WALLS);
		infoLayout.show(infoPanel, MAZE);
		
		dCon.gridy = 2;
		descriptionPanel.add(infoPanel, dCon);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		c.weighty = 10;
		c.weightx = 1;
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
			sliderLayout.show(sliderPanel, BOOTS);
			infoLayout.show(infoPanel, BOOTS);
			
		} else if (source == clockBox) {
			System.out.print(CLOCKS);
			sliderLayout.show(sliderPanel, CLOCKS);
			infoLayout.show(infoPanel,CLOCKS);
			
		} else if (source == trailBox) {
			System.out.print(EXPLORED_TRAIL);
			infoLayout.show(infoPanel, EXPLORED_TRAIL);
			sliderLayout.show(sliderPanel, EXPLORED_TRAIL);
			
		} else if (source == fogBox) {
			System.out.print(FOG_OF_WAR);
			sliderLayout.show(sliderPanel, FOG_OF_WAR);
			infoLayout.show(infoPanel, FOG_OF_WAR);
			
		} else if (source == shiftingWallsBox) {
			System.out.print(SHIFTING_WALLS);
			sliderLayout.show(sliderPanel, SHIFTING_WALLS);
			infoLayout.show(infoPanel, SHIFTING_WALLS);
		}
		
		if (e.getStateChange() == ItemEvent.SELECTED) {
			System.out.println(" was selected");
			// do something
		} else if (e.getStateChange() == ItemEvent.DESELECTED) {
			System.out.println(" was deselected");
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
		c.gridy = 6;
		c.gridwidth = 2;
		c.weighty = 0.02;
		add(returnPanel, c);
	}
	
}
