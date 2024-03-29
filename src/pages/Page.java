package pages;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * An abstract class to store information about a particular 'page', that is
 * it is a JPanel which spans the entire window, which can be hidden and focused
 * on in order to navigate between different pages in the program.
 */
public abstract class Page extends JPanel {
	BufferedImage bgImage = null;
	
    public Page() {
        this.setFocusable(true);
        this.setBackground(new Color(50, 50, 50));
        try {
    		bgImage = ImageIO.read(new File("img/largeMazeBackground.png"));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    private static final long serialVersionUID = 1L;
    
    public interface Result{};
    
    public abstract Result run();
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(bgImage, 0, 0, null);
	}

}
