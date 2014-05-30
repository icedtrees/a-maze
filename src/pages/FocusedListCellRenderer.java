package pages;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class FocusedListCellRenderer implements ListCellRenderer {
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
	    JLabel renderer = (DefaultListCellRenderer) defaultRenderer.getListCellRendererComponent(list, value, 
	    		index, isSelected, cellHasFocus);
	    renderer.setForeground(Color.WHITE);
	    renderer.setOpaque(false);
	    if (cellHasFocus) {
	    	renderer.setForeground(Color.LIGHT_GRAY);
	    }
	    
	    return renderer;
	  }
}
