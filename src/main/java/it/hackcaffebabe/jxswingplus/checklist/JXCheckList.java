package it.hackcaffebabe.jxswingplus.checklist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;


/**
 * This class describe a common list witch each elements can be checked as a common {@link JCheckBox}.<br>
 * JXCheckList needs to know the type of its elements by:<br>
 * <pre>{@code
 * JXCheckList<Integer> lstCheck = new JXCheckList<>();
 * }
 * </pre>
 * To add elements is the same as common {@link JList} instead of the elements MUST be of type {@link JXCheckListEntry}<br>
 * You can simply create the appropriate model using:<br>
 * <pre>{@code
 * List<Integer> list = Arrays.asList( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 );
 * DefaultListModel<JXCheckListEntry<Integer>> model = JXCheckBoxList.convertToModel( list, true );
 * lstCheck.setModel( model );
 * }</pre>
 * It's simply to add an {@link ActionListener} when user click on element to check it:
 * <pre>{@code
 * ActionListener evt = new ActionListener(){
 * 		public void actionPerformed(ActionEvent e){
 * 			System.out.println( lstCheck.getCheckedIdexes() );
 * 		}
 * };
 * DefaultListModel<JXCheckListEntry<Integer>> model = new DefaultListModel<>();
 * for(int i = 0; i < 100; i++) {
 * 		JXCheckListEntry<Integer> xentry = new JXCheckListEntry<Integer>( i, false );
 * 		xentry.addActionListener( evt );
 * 		model.addElement( xentry );
 * }
 * lstCheck.setModel( model );
 * }</pre>
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXCheckList <T> extends JList<JXCheckListEntry<T>>
{
	private static final long serialVersionUID = 1L;
	private List<Integer> lstSelectedIndex = new ArrayList<Integer>();
	private List<T> lstSelectedObjects = new ArrayList<T>();

	/**
	 * Create a {@link JList} of {@link JXCheckListEntry}.<br>
	 * This constructor set the appropriate render for JXCheckListEntry, ListSelectionMode on SINGLE_SELECTION and
	 * Mouse-Keyboard listener to check the JCheckBox.
	 */
	public JXCheckList(){
		super();
		setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		setCellRenderer( new JXCheckListCellRenderer() );
		initMouseListener();
		initKeyboardListener();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* Initialize the Mouse
	 * this will not override by user declaration as: "list.addMouseListener( new MouseAdapter(){ ... } )" */
	private void initMouseListener(){
		addMouseListener( new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				if(SwingUtilities.isLeftMouseButton( e )) {
					pressJCheckBox( e );
				}
			}
		} );
	}

	/* this initialize the keyboard listener to pressJCheckbox on space and enter typed. */
	private void initKeyboardListener(){
		addKeyListener( new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e){}

			@Override
			public void keyReleased(KeyEvent e){}

			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
					pressJCheckBox( e );
				}
			}
		} );
	}

	/* perform the check event */
	@SuppressWarnings("unchecked")
	private void pressJCheckBox(InputEvent e){
		int index = -1;
		if(e instanceof MouseEvent)
			index = locationToIndex( ((MouseEvent) e).getPoint() );

		if(e instanceof KeyEvent)
			index = this.getSelectedIndex();

		if(index != -1) {
			Object obj = getModel().getElementAt( index );
			if(obj instanceof JXCheckListEntry) {
				JXCheckListEntry<T> entry = (JXCheckListEntry<T>) obj;
				if(entry.getCheckbox().isSelected()) {
					lstSelectedIndex.remove( lstSelectedIndex.indexOf( index ) );
					lstSelectedObjects.remove( entry.getUserObject() );
				} else {
					lstSelectedIndex.add( index );
					lstSelectedObjects.add( entry.getUserObject() );
				}
				entry.getCheckbox().setSelected( !entry.getCheckbox().isSelected() );
				repaint();
				if(entry.getCheckbox().getActionListeners().length != 0) {
					ActionEvent evt = new ActionEvent( entry.getCheckbox(), 0, entry.getCheckbox().getActionCommand() );
					entry.getCheckbox().getActionListeners()[0].actionPerformed( evt );
				}
			}
		}
	}

	/**
	 * This method converts a list of user data in an appropriate {@link DefaultListModel} of {@link JXCheckListEntry}
	 * that represents the list given to be displayed on {@link JXCheckList}
	 * @param objects {@link List} of T objects user.
	 * @param showRightIcon {@link Boolean} if you want to display a icon right of this element.<br>
	 * This icon indicates if the elements is checked or not.
	 * @return {@link DefaultListModel} of T type.
	 */
	public static <T> DefaultListModel<JXCheckListEntry<T>> convertToModel(List<T> objects, boolean showRightIcon){
		DefaultListModel<JXCheckListEntry<T>> model = new DefaultListModel<JXCheckListEntry<T>>();
		if(objects != null) {
			for(T t: objects) {
				model.addElement( new JXCheckListEntry<T>( t, showRightIcon ) );
			}
		}
		return model;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * This method returns a list of index that correspond at the checked elements.
	 * @return {@link List} of checked index
	 */
	public List<Integer> getCheckedIdexes(){
		return this.lstSelectedIndex;
	}

	/**
	 * This method returns a list of selected objects.
	 * @return {@link List} of T selected objects.
	 */
	public List<T> getCheckedObjects(){
		return this.lstSelectedObjects;
	}
}
