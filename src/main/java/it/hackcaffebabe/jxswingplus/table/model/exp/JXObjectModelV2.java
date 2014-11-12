package it.hackcaffebabe.jxswingplus.table.model.exp;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.Vector;

/**
 * This class is a simplified table model to manage heterogeneous data type.
 * This model can manage objects that implements
 * {@link JXDisplayable}.
 * The simplest way to use this model is to instance an empty model of some kind
 * of your model class data:
 * <pre>{@code
 * JXObjectModel<MyDisplayableObject> model = new JXObjectModel();
 * }</pre>
 * At this point you can use the following method to set the column table and
 * set witch one is editable or not:
 * <pre>{@code
 * String[] cn = {"foo", "bar", "eggs"};
 * model.setColumnNames(cn);
 * model.setColumnNotEditable(0,1);
 * }</pre>
 * The other two constructions functions can simplify this process:
 * <pre>{@code
 * String[] cn = {"foo", "bar", "eggs"};
 * JXObjectModel<MyDisplayableObject> model = new JXObjectModel(cn, 0, 1);
 * }</pre>
 * The model is ready to add all the object you want by:
 * <pre>{@code
 * MyDisplayableObject o = new MyDisplayableObject();
 * model.addObject(o);
 * }</pre>
 * Also there are a list of method to get or remove objects from the model. *
 * Now is ready to be displayed into a
 * {@link it.hackcaffebabe.jxswingplus.table.JXTable} as a common data model.
 *
 * @param <T>
 */
public class JXObjectModelV2<T extends JXDisplayable> extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;

	private Vector<String> colNames = new Vector<String>();
	private Vector<Integer> colNotEdit = new Vector<Integer>();
	private Vector<T> objects = new Vector<T>();

	/**
	 * Instance the model with the column names and a list of not editable
	 * columns as index.
	 * @param colNames {@link String} array string of column names.
	 * @param colNotEditable {@Integer} list of index of column not editable
	 * @throws IllegalArgumentException if arguments given are null or empty array.
	 */
	public JXObjectModelV2( String[] colNames, Integer...colNotEditable ) throws IllegalArgumentException{
		this.setColumnNames(colNames);
		this.setColumnNotEditable(colNotEditable);
	}

	/**
	 * Instance the model with column names and leave all columns editable.
	 * @param colNames {@link String} array string of column names.
	 * @throws IllegalArgumentException if argument given is null or empty.
	 */
    public JXObjectModelV2( String[] colNames ) throws IllegalArgumentException{
		this(colNames, null);
	}

	/** Instance a empty model */
	public JXObjectModelV2(){}

//==============================================================================
// METHOD
//==============================================================================
	/**
	 * This method add an object to the model.
	 * @param obj the object to add.
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public void addObject(T obj) throws IllegalArgumentException{
		if(obj == null)
			throw new IllegalArgumentException( "Object to display can not be null." );

		this.objects.add(obj);
		fireTableDataChanged();
	}

//==============================================================================
// SETTER
//==============================================================================
	/**
	 * This method set the column names for this model.
	 * @param colNames {@link String} array string of column names.
	 * @throws IllegalArgumentException if argument is null or empty array.
	 */
	public void setColumnNames( String... colNames ) throws IllegalArgumentException{
		if(colNames == null || colNames.length == 0 )
			throw new IllegalArgumentException("Column Names can not be null.");

		this.colNames.clear();
		Collections.addAll(this.colNames, colNames);
	}

	/**
	 * This method se the column not editable by given a list of column indexes.
	 * @param colNotEditable {@link Integer} list of indexes.
	 * @throws IllegalArgumentException if argument is null or empty array.
	 */
	public void setColumnNotEditable( Integer... colNotEditable ) throws IllegalArgumentException{
		if(colNotEditable != null){
			if( colNotEditable.length == 0 )
				throw new IllegalArgumentException("Column not-editable can not be void.");

			for( Integer aColNotEditable : colNotEditable) {
				if (aColNotEditable < 0 || aColNotEditable >= this.colNames.size())
					throw new IllegalArgumentException("Column not-editable are " +
							"incorrect. It's must be in range 0-"
							+ (this.colNames.size() - 1));
			}

			this.colNotEdit.clear();
			Collections.addAll(this.colNotEdit, colNotEditable);
		}else{
			this.colNotEdit.clear();
		}
	}

	/** This method removes all the object in the model. */
	public void removeAll(){
		if(this.objects.isEmpty())
			return;

		this.objects.clear();
		fireTableDataChanged();
	}

	/**
	 * This method remove the specific object from the model if exists.
	 * @param index {@link Integer} the model index of the object.
	 * @throws IndexOutOfBoundsException if index < 0 or >= getObjects().size()
	 */
	public void removeObject(int index) throws IndexOutOfBoundsException{
		if(index < 0 || index >= this.objects.size())
			throw new IndexOutOfBoundsException( "Index must in range 0-" +
					this.objects.size() );

		this.objects.remove( index );
		fireTableDataChanged();
	}

	/**
	 * This method remove the specific object from the model if exists.
	 * @param obj Object to remove if exists.
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public void removeObject(T obj) throws IllegalArgumentException{
		if(obj == null)
			throw new IllegalArgumentException( "Object to remove can not be null." );

		int i = this.objects.indexOf(obj);
		if(i != -1) {
			this.removeObject(i);
			fireTableDataChanged();
		}
	}

//==============================================================================
// GETTER
//==============================================================================
	/**
	 * This method returns a specific object from his model index.
	 * @param row {@link Integer} the model row of the table.
	 * @return the object from the model.
	 * @throws IndexOutOfBoundsException if row is < 0 or >= getObjects().size().
	 */
	public T getObject(int row) throws IndexOutOfBoundsException{
		if(row < 0 || row > this.objects.size())
			throw new IllegalArgumentException( "Row given is out of range 0-" +
					(this.objects.size()-1) );

		return this.objects.get(row);
	}

	/** @return {@link Vector} the objects in the model. */
	public Vector<T> getObjects(){
		return this.objects;
	}

//==============================================================================
// OVERRIDE
//==============================================================================
	@Override
	public int getColumnCount(){
		return this.colNames.size();
	}

	@Override
	public int getRowCount(){
		return objects.size();
	}

	@Override
	public String getColumnName(int col){
		if(col < 0 || col >= this.colNames.size() )
			return "";
		else return this.colNames.get(col);
	}

	@Override
	public Object getValueAt(int row, int col) throws IllegalArgumentException{
		if( row >= this.objects.size() )
			throw new IllegalArgumentException( "Row can not be greater than row " +
					"count." );

        Object[] dr = this.objects.get( row ).getDisplayRaw();
        if(col > dr.length)
			throw new IllegalArgumentException( "Column can not be greater than " +
					"column count." );
        else return dr[col];
	}

	@Override
	public void setValueAt(Object value, int row, int col){
		T r = this.objects.get(row);
		if(col >= r.getDisplayRaw().length)
			return;
		r.getDisplayRaw()[col] = value;
		fireTableCellUpdated( row, col );
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c){
		if(this.objects.isEmpty())
			return JXDisplayable.class;
		else
			return getValueAt( 0, c ).getClass();
	}

	@Override
	public boolean isCellEditable(int row, int col){
		return !this.colNotEdit.contains(col);
	}
}