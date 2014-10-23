package it.hackcaffebabe.jxswingplus.checklist;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


/**
 * This is the render for {@link JXCheckList}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXCheckListCellRenderer extends DefaultListCellRenderer
{
	private static final long serialVersionUID = 1L;

	static final Icon DEFAULT_ACCEPT_ICO = new ImageIcon( JXCheckListCellRenderer.class.getClassLoader().getResource( "checklist/accept.png" ) );
	static final Icon DEFAULT_CANCEL_ICO = new ImageIcon( JXCheckListCellRenderer.class.getClassLoader().getResource( "checklist/cancel.png" ) );

	@Override
	public Component getListCellRendererComponent(JList< ? > list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		if(value instanceof JXCheckListEntry) {
			JXCheckListEntry< ? > entry = (JXCheckListEntry< ? >) value;
			entry.setEnabled( isSelected );
			if(entry.getCheckbox().isSelected()) {
				entry.setRightIcon( DEFAULT_ACCEPT_ICO );
			} else {
				entry.setRightIcon( DEFAULT_CANCEL_ICO );
			}
			//smoothing check box border
			entry.setBorder( isSelected ? UIManager.getBorder( "List.focusCellHighlightBorder" ) : new EmptyBorder( 1, 1, 1, 1 ) );
			//set the appropriate background, foreground and fort of check list entry
			entry.setBackground( isSelected ? list.getSelectionBackground() : list.getBackground() );
			entry.setForeground( isSelected ? list.getSelectionForeground() : list.getForeground() );
			entry.setFont( getFont() );

			//set the appropriate background, foreground and fort of check box into list entry.
			entry.getCheckbox().setBackground( isSelected ? list.getSelectionBackground() : list.getBackground() );
			entry.getCheckbox().setForeground( isSelected ? list.getSelectionForeground() : list.getForeground() );
			entry.getCheckbox().setFont( getFont() );
			entry.getCheckbox().setFocusPainted( false );
			entry.getCheckbox().setBorderPainted( false );

			return entry;
		} else {
			return super.getListCellRendererComponent( list, value.getClass().getName(), index, isSelected, cellHasFocus );
		}
	}
}
