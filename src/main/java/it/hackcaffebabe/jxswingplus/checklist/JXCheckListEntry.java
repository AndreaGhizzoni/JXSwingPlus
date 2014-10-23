package it.hackcaffebabe.jxswingplus.checklist;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * Default element for {@link JXCheckList}.<br>
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXCheckListEntry <T> extends JPanel
{
	private static final long serialVersionUID = 1L;
	private T userObj;
	private JCheckBox chbCheck;
	private boolean showRightIcon;
	private JLabel lblRightIcon = new JLabel();

	/**
	 * Instance an entry of {@link JXCheckList} for given type of object.
	 * @param obj T of Object wants to be displayed into {@link JXCheckList}
	 * @param showRightIcon {@link Boolean} if you want to display a icon right of this element.<br>
	 * This icon indicates if the elements is checked or not.
	 */
	public JXCheckListEntry(T obj, boolean showRightIcon){
		super( new BorderLayout() );
		setBorder( new EmptyBorder( 1, 1, 1, 1 ) );

		this.userObj = obj;
		this.chbCheck = new JCheckBox( obj == null ? "null" : obj.toString() );
		add( this.chbCheck, BorderLayout.WEST );

		this.showRightIcon = showRightIcon;
		setRightIcon( JXCheckListCellRenderer.DEFAULT_CANCEL_ICO );
		add( this.lblRightIcon, BorderLayout.EAST );
	}

	/**
	 * Set one {@link ActionListener} to perform when this entry is selected.
	 * @param l {@link ActionListener} to perform. It can not be null.
	 */
	public void addActionListener(ActionListener l){
		this.chbCheck.addActionListener( l );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/* Package method. Used only by the render */
	void setRightIcon(Icon i){
		if(showRightIcon) {
			if(i == null) {
				lblRightIcon.setIcon( null );
			} else {
				lblRightIcon.setIcon( i );
			}
		}
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the user object displayed as a check entry.
	 * @return T user object.
	 */
	public T getUserObject(){
		return this.userObj;
	}

	/* Package method. Used only by the render */
	JCheckBox getCheckbox(){
		return this.chbCheck;
	}
}
