package pages;

import java.awt.event.*;
import javax.swing.*;

/**
 * @author icedtrees
 *
 */
public class TransitionPage extends Page {
    private static final long serialVersionUID = 1L;

    public enum Result implements Page.Result {
        CONTINUE
    }
    
    private Result result;
    private JLabel title;
    
    public TransitionPage() {
        super();
        title = Components.makeTitle("");
        JButton continueButton = Components.makeButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = Result.CONTINUE;
            }
        });
        add(title);
        add(continueButton);
    }

    public void setText(String newText) {
        title.setText(newText);
    }
    
    public TransitionPage.Result run() {
        result = null;
        while (result == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

}
