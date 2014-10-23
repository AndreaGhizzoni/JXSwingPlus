package it.hackcaffebabe.jxswingplus.searchengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;


/**
 * This is the model to carry all the {@link JXSearchEngine} objects.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXSearchBarModel extends AbstractListModel<JXSearchEngine> implements MutableComboBoxModel<JXSearchEngine>
{
	private static final long serialVersionUID = 1L;
	private ArrayList<JXSearchEngine> list = new ArrayList<JXSearchEngine>();
	private int selectedItemIndex = -1;

	/**
	 * Instance an empty model for {@link JXSearchBar}.
	 */
	public JXSearchBarModel(){
		fireContentsChanged( this, 0, this.list.size() );
	}

	/**
	 * Instance a model whit a list of {@link JXSearchEngine} given.
	 * @param items {@link JXSearchEngine}
	 */
	public JXSearchBarModel(List<JXSearchEngine> items){
		if(items != null) {
			this.list.addAll( items );
			fireContentsChanged( this, 0, this.list.size() );
		}
	}

	/**
	 * Instance a model whit an array of {@link JXSearchEngine} given.
	 * @param items array of {@link JXSearchEngine}.
	 */
	public JXSearchBarModel(final JXSearchEngine[] items){
		if(items != null) {
			for(JXSearchEngine s: items)
				list.add( s );
			fireContentsChanged( this, 0, this.list.size() );
		}
	}

	/**
	 * Instance a model whit a {@link Vector} of {@link JXSearchEngine} given.
	 * @param items a {@link Vector} of {@link JXSearchEngine}
	 */
	public JXSearchBarModel(Vector<JXSearchEngine> items){
		this( new ArrayList<JXSearchEngine>( items ) );
	}

	@Override
	public void setSelectedItem(Object anItem){
		if(anItem != null && anItem instanceof JXSearchEngine) {
			int i = this.list.indexOf( anItem );
			if(i != -1)
				this.selectedItemIndex = i;
		}
	}

	@Override
	public Object getSelectedItem(){
		if(selectedItemIndex == -1)
			return null;
		else return this.list.get( selectedItemIndex );
	}

	@Override
	public int getSize(){
		return this.list.size();
	}

	@Override
	public JXSearchEngine getElementAt(int index){
		if(index >= 0 && index < this.list.size())
			return this.list.get( index );
		else return null;
	}

	@Override
	public void addElement(JXSearchEngine item){
		if(item != null) {
			this.list.add( item );
			fireContentsChanged( this, this.list.indexOf( list ), this.list.indexOf( list ) );
		}
	}

	@Override
	public void removeElement(Object obj){
		if(obj != null && obj instanceof JXDefaultSearchEngine && this.list.contains( obj )) {
			this.list.remove( obj );
			fireContentsChanged( this, 0, this.list.size() );
		}
	}

	@Override
	public void insertElementAt(JXSearchEngine item, int index){
		if(item != null && index >= 0 && index < this.list.size()) {
			this.list.add( index, item );
			fireContentsChanged( this, 0, this.list.size() );
		}
	}

	@Override
	public void removeElementAt(int index){
		if(index >= 0 && index < this.list.size()) {
			this.list.remove( index );
			fireContentsChanged( this, 0, this.list.size() );
		}
	}

	@Override
	public String toString(){
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < getSize(); i++) {
			b.append( getElementAt( i ) + " " );
		}
		return b.toString();
	}
}
