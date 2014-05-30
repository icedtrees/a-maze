package pages;

import game.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
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
    private JTextArea title;
    
    public TransitionPage() {
        super();
        this.setSize(Game.settings.screenSize.width, Game.settings.screenSize.height);
        title = new JTextArea();
        title.setSize(this.getSize());
        Font font = title.getFont();
        title.setMargin(new Insets(100, 100, 100, 100));
        title.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);
        title.setOpaque(false);
        title.setFont(new Font(font.getName(), Font.PLAIN, 40));
        title.setLineWrap(true);
        title.setWrapStyleWord(true);
        
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
