package it.hackcaffebabe.jxswingplus.table.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * This is a simplified table model to manage heterogeneous data types.
 * All objects saved into the model must extends
 * {@link it.hackcaffebabe.jxswingplus.table.model.DisplayableObject}.
 *   
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXObjectModel<T extends DisplayableObject> extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	private List<T> data = new ArrayList<T>();

	/**
	 * Instance a model to show a set of objects of the same class T.
	 * @param data {@link List} of object to display into a table.
	 * @throws IllegalArgumentException if data is null or empty.
	 */
	public JXObjectModel(List<T> data) throws IllegalArgumentException{
		this.addObjects( data );
	}

	/** Instance an empty model to show a set of objects of the same class T. */
	public JXObjectModel(){
		fireTableDataChanged();
	}

//==============================================================================
// METHOD
//==============================================================================
	/**
	 * Append the object given at the end of table.
	 * @param obj of class T object to append.
	 * @throws IllegalArgumentException if object given is null.
	 */
	public void addObject(T obj) throws IllegalArgumentException{
		if(obj == null)
			throw new IllegalArgumentException( "Object to display can not be null." );

		this.data.add( obj );
		fireTableDataChanged();
	}

	/**
	 * Add all objects from the list to model.
	 * @param data {@link List} of objects to add.
	 * @throws IllegalArgumentException if list of given is null or empty.
	 */
	public void addObjects(List<T> data) throws IllegalArgumentException{
		if(data == null || data.isEmpty())
			throw new IllegalArgumentException( "List of objects to display can not be null or empty." );

		this.data.addAll( data );
		fireTableDataChanged();
	}

	/** Remove all objects in the model. */
	public void removeAll(){
		if(this.data.isEmpty())
			return;

		this.data.clear();
		fireTableDataChanged();
	}

	/**
	 * Remove object at specified index.
	 * @param index int the index of object to remove.
	 * @throws IndexOutOfBoundsException if index is out of range 0-table rows.
	 */
	public void removeObject(int index) throws IndexOutOfBoundsException{
		if(index < 0 || index > this.data.size())
			throw new IndexOutOfBoundsException( "Index must in range 0-" + this.data.size() );

		this.data.remove( index );
		fireTableDataChanged();
	}

	/**
	 * Remove the object from the model. If object there is into the model will
	 * be removed, otherwise nothing.
	 * @param obj T the object to remove.
	 * @throws IllegalArgumentException if argument is null is null.
	 */
	public void removeObject(T obj) throws IllegalArgumentException{
		if(obj == null)
			throw new IllegalArgumentException( "Object to get can not be null." );

		int i = this.data.indexOf( obj );
		if(i != -1) {
			this.removeObject(i);
			fireTableDataChanged();
		}
	}

//==============================================================================
// GETTER
//==============================================================================
	/**
	 * Return the object with his index from the model.
	 * @param row int the index of row.
	 * @return object from the model.
	 * @throws IndexOutOfBoundsException if index is out of range 0-model's row.
	 */
	public T getObject(int row) throws IndexOutOfBoundsException{
		if(row < 0 || row > this.data.size())
			throw new IllegalArgumentException( "Row given is out of range 0-" + this.data.size() );

		return this.data.get( row );
	}

	/**
	 * Returns the list of objects in the table.
	 * @return {@link List} of objects.
	 */
	public List<T> getObjects(){
		return this.data;
	}

//==============================================================================
// OVERRIDE
//==============================================================================
	@Override
	public int getColumnCount(){
		if(this.data.isEmpty())
			return 0;
		else return this.data.get( 0 ).getColumnNames().size();
	}

	@Override
	public int getRowCount(){
		return data.size();
	}

	@Override
	public String getColumnName(int col){
		if(this.data.size() == 0)
			return "";
		else return this.data.get( 0 ).getColumnNames().get( col );
	}

	@Override
	public Object getValueAt(int row, int col) throws IllegalArgumentException{
		if( row >= this.data.size() )
			throw new IllegalArgumentException( "Row can not be greater than row count." );

        Object[] dr = this.data.get( row ).getDisplayRow();
        if(col > dr.length)
			throw new IllegalArgumentException( "Column can not be greater than column count." );
        else return dr[col];
	}

	@Override
	public void setValueAt(Object value, int row, int col){
		this.data.get( row ).getDisplayRow()[col] = value;
		fireTableCellUpdated( row, col );
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c){
		if(this.data.isEmpty())
			return DisplayableObject.class;
		else
			return getValueAt( 0, c ).getClass();
	}

	@Override
	public boolean isCellEditable(int row, int col){
//		if(this.data.isEmpty())
//			return true;
//		else return !this.data.get(row).getColumnNotEditable().contains(col);
		return !this.data.get(row).getColumnNotEditable().contains(col);
	}
}