package it.hackcaffebabe.jxswingplus.searchengine;

import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.UIManager;


/**
 * Simple search bar UI component. This support his model {@link JXSearchBarModel} that 
 * contains object of type {@link JXSearchEngine}. You can use {@link JXDefaultSearchEngine}
 * or develop your custom class that implements {@link JXSearchEngine}.
 *  
 * @see JXSearchBarModel
 * @see JXSearchEngine
 * @see JXDefaultSearchEngine
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXSearchBar extends JComboBox<JXSearchEngine>
{
	private static final long serialVersionUID = 1L;
	private AbstractAction searchAction;

	/**
	 * Instance an empty search bar whit his action to start to search.
	 * @param searchAction {@link AbstractAction} this action will call on button search.
	 */
	public JXSearchBar(AbstractAction searchAction){
		super();
		setModel( new JXSearchBarModel() );
		this.searchAction = searchAction == null ? SearchBarUIUtils.VOID_ACTION : searchAction;
		init();
	}

	/**
	 * Instance a search bar with a model and a search action given.
	 * @param aModel {@link ComboBoxModel} of {@link JXSearchEngine}
	 * @param searchAction {@link AbstractAction} this action will call on button search.
	 */
	public JXSearchBar(ComboBoxModel<JXSearchEngine> aModel, AbstractAction searchAction){
		super();
		setModel( aModel == null ? new JXSearchBarModel() : aModel );
		this.searchAction = searchAction == null ? SearchBarUIUtils.VOID_ACTION : searchAction;
		init();
	}

	/**
	 * Instance a search bar with an array of {@link JXDefaultSearchEngine} and the search action.
	 * @param items array of {@link JXSearchEngine}
	 * @param searchAction {@link AbstractAction} this action will call on button search.
	 */
	public JXSearchBar(final JXSearchEngine[] items, AbstractAction searchAction){
		this( new JXSearchBarModel( items ), searchAction );
	}

	/**
	 * Instance a search bar with a vector of {@link JXSearchEngine} and the search action.
	 * @param items {@link Vector} of {@link JXSearchEngine}
	 * @param searchAction {@link AbstractAction} this action will call on button search.
	 */
	public JXSearchBar(java.util.Vector<JXSearchEngine> items, AbstractAction searchAction){
		this( new JXSearchBarModel( items ), searchAction );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* for each constructor initialize the UI */
	private void init(){
		installAncestorListener();
		updateUI();
	}

	@Override
	public void updateUI(){
		setUI( new SearchBarUI( this.searchAction ) );
		UIManager.put( "ComboBox.font", getFont() ); //XXX: ???
		JButton arrowButton = (JButton) getComponent( 0 );
		if(getModel().getSize() != 0) {
			JXSearchEngine s = getModel().getElementAt( 0 );
			arrowButton.setIcon( s.getFavIcon() );
			getModel().setSelectedItem( s );
			getEditor().setItem( "" );
		}
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * This method returns the current {@link JXSearchEngine} selected.<br>
	 * Use this is equal as : <code>(SearchEngine) getModel().getSelectedItem();</code>
	 * @return {@link JXSearchEngine}
	 */
	public JXSearchEngine getSelectedSearchEngine(){
		return (JXSearchEngine) getModel().getSelectedItem();
	}

	/**
	 * This method returns the string inserted by the user as query string.
	 * @return {@link String}
	 */
	public String getQueryString(){
		return (String) getEditor().getItem();
	}

	@Override
	public SearchBarUI getUI(){
		return (SearchBarUI) ui;
	}

//===========================================================================================
// SETTER
//===========================================================================================
	@Override
	public void setModel(ComboBoxModel<JXSearchEngine> model){
		if(model != null) {
			super.setModel( model );
			init();
		}
	}

	@Override
	protected void processFocusEvent(java.awt.event.FocusEvent e){}
}
