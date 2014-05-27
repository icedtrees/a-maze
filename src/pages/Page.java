package pages;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public abstract class Page extends JPanel {
	
    public Page() {
        this.setFocusable(true);
        this.setBackground(Color.YELLOW);
    }
    
    private static final long serialVersionUID = 1L;
    
    public interface Result{};
    
    public abstract Result run();
    
    @Override
    public void paintComponent(Graphics g) {
    	BufferedImage image = null;
    	try {
    		image = ImageIO.read(new File("src/catsBackground.png"));
    	} catch (IOException E) {
    		
    	}
    	super.paintComponent(g);
    	g.drawImage(image, 0, 0, null);
	}

}
