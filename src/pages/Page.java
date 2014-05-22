package pages;

import javax.swing.JPanel;


public abstract class Page extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public interface Result{};
    
    public abstract Result run();
    
}
