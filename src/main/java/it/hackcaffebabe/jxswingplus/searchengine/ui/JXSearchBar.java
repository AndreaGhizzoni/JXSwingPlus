package it.hackcaffebabe.jxswingplus.searchengine.ui;

import it.hackcaffebabe.jxswingplus.searchengine.model.JXSearchEngine;
import it.hackcaffebabe.jxswingplus.searchengine.model.JXDefaultSearchEngine;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.UIManager;

/**
 * Simple search bar UI component. This support the model
 * {@link it.hackcaffebabe.jxswingplus.searchengine.ui.JXDefaultSearchBarModel}
 * that contains object of type
 * {@link it.hackcaffebabe.jxswingplus.searchengine.model.JXSearchEngine}.
 * You can use
 * {@link it.hackcaffebabe.jxswingplus.searchengine.model.JXDefaultSearchEngine}
 * or implements {@link it.hackcaffebabe.jxswingplus.searchengine.model.JXSearchEngine}
 * to create your own Engine class. Here there is a simple use:
 * <pre>{@code
 * ActionListener l = new AbstractAction(){
 *    public void actionPerformed(ActionEvent e){
 *       System.out.println("Somethings has been searched.")
 *    }
 * }
 * JXSearchBar searchBar = new JXSearchBar(l);
 * searchBar.setModel(model); //assuming model is a JXDefaultSearchBarModel
 * }</pre>
 *
 * @see it.hackcaffebabe.jxswingplus.searchengine.ui.JXDefaultSearchBarModel
 * @see it.hackcaffebabe.jxswingplus.searchengine.model.JXSearchEngine
 * @see it.hackcaffebabe.jxswingplus.searchengine.model.JXDefaultSearchEngine
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXSearchBar extends JComboBox<JXSearchEngine>
{
	private static final long serialVersionUID = 1L;
	private AbstractAction searchAction;

	/**
	 * Instance an empty search bar whit his action to start to search.
     * if search Action is null, it will be set to a void action.
     * The model used in this constructor is {@link JXDefaultSearchBarModel}.
	 * @param searchAction {@link AbstractAction} this action will call on
     *                                           button search.
	 */
	public JXSearchBar(AbstractAction searchAction){
		super();
		setModel( new JXDefaultSearchBarModel() );
		this.searchAction = searchAction == null ? Utility.VOID_ACTION : searchAction;
		init();
	}

	/**
	 * Instance a search bar with a model and a search action given. if model is
     * null, it will be set to {@link JXDefaultSearchEngine} and if search
     * Action is null, it will be set to a void action.
     * The model used in this constructor is {@link JXDefaultSearchBarModel}.
	 * @param aModel {@link ComboBoxModel} of {@link JXSearchEngine}
	 * @param searchAction {@link AbstractAction} this action will call on
     *                                           button search.
	 */
	public JXSearchBar(ComboBoxModel<JXSearchEngine> aModel, AbstractAction searchAction){
		super();
		setModel( aModel == null ? new JXDefaultSearchBarModel() : aModel );
		this.searchAction = searchAction == null ? Utility.VOID_ACTION : searchAction;
		init();
	}

	/**
	 * Instance a search bar with an array of {@link JXDefaultSearchEngine} and
     * the search action. The model used in this constructor is
     * {@link JXDefaultSearchBarModel}.
	 * @param items array of {@link JXSearchEngine}
	 * @param searchAction {@link AbstractAction} this action will call on
     *                                           button search.
	 */
	public JXSearchBar(final JXSearchEngine[] items, AbstractAction searchAction){
		this( new JXDefaultSearchBarModel( items ), searchAction );
	}

	/**
	 * Instance a search bar with a List of {@link JXSearchEngine} and the
     * search action. The model used in this constructor is
     * {@link JXDefaultSearchBarModel}.
	 * @param items {@link List} of {@link JXSearchEngine}
	 * @param searchAction {@link AbstractAction} this action will call on
     *                                           button search.
	 */
	public JXSearchBar(List<JXSearchEngine> items, AbstractAction searchAction){
		this( new JXDefaultSearchBarModel( items ), searchAction );
	}

//==============================================================================
// METHOD
//==============================================================================
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

//==============================================================================
// SETTER
//==============================================================================
	@Override
	public void setModel(ComboBoxModel<JXSearchEngine> model){
		if(model != null) {
			super.setModel( model );
			init();
		}
	}

	@Override
	protected void processFocusEvent(java.awt.event.FocusEvent e){}

//==============================================================================
// GETTER
//==============================================================================
	/**
	 * This method returns the current {@link JXSearchEngine} selected.
	 * Use this is equal to call:
     * <pre>{@code (SearchEngine) getModel().getSelectedItem();}</pre>
	 * @return {@link JXSearchEngine} the search engine selected
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
}
