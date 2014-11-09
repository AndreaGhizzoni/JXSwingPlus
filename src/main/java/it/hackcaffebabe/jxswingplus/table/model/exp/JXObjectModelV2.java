package it.hackcaffebabe.jxswingplus.table.model.exp;

import it.hackcaffebabe.jxswingplus.table.model.JXObjectModel;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.Vector;

public class JXObjectModelV2<T extends Displayable> extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;

	private Vector<String> colNames = new Vector<String>();
	private Vector<Integer> colNotEdit = new Vector<Integer>();
	private Vector<T> objects = new Vector<T>();

	public JXObjectModelV2( String[] colNames, Integer...colNotEditable ) throws IllegalArgumentException{
		this.setColumnNames(colNames);
		this.setColumnNotEditable(colNotEditable);
	}

    public JXObjectModelV2( String[] colNames ) throws IllegalArgumentException{
		this(colNames, null);
	}

//==============================================================================
// METHOD
//==============================================================================
	public void addObject(T obj) throws IllegalArgumentException{
		if(obj == null)
			throw new IllegalArgumentException( "Object to display can not be null." );

		this.objects.add(obj);
		fireTableDataChanged();
	}

//==============================================================================
// SETTER
//==============================================================================
	public void setColumnNames( String[] colNames ) throws IllegalArgumentException{
		if(colNames == null || colNames.length == 0 )
			throw new IllegalArgumentException("Column Names can not be null.");

		this.colNames.clear();
		Collections.addAll(this.colNames, colNames);
	}

	public void setColumnNotEditable( Integer... colNotEditable ) throws IllegalArgumentException{
		if(colNotEditable != null){
			if( colNotEditable.length == 0 )
				throw new IllegalArgumentException("Column not-editable can not be void.");

			for( Integer aColNotEditable : colNotEditable) {
				if (aColNotEditable < 0 || aColNotEditable >= this.colNotEdit.size())
					throw new IllegalArgumentException("Column not-editable are incorrect. It's must be in range 0-"
							+ (this.colNotEdit.size() - 1));
			}

			this.colNotEdit.clear();
			Collections.addAll(this.colNotEdit, colNotEditable);
		}else{
			this.colNotEdit.clear();
		}
	}

	public void removeAll(){
		if(this.objects.isEmpty())
			return;

		this.objects.clear();
		fireTableDataChanged();
	}

	public void removeObject(int index) throws IndexOutOfBoundsException{
		if(index < 0 || index >= this.objects.size())
			throw new IndexOutOfBoundsException( "Index must in range 0-" +
					this.objects.size() );

		this.objects.remove( index );
		fireTableDataChanged();
	}

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
	public T getObject(int row) throws IndexOutOfBoundsException{
		if(row < 0 || row > this.objects.size())
			throw new IllegalArgumentException( "Row given is out of range 0-" +
					this.objects.size() );

		return this.objects.get(row);
	}

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
			return Displayable.class;
		else
			return getValueAt( 0, c ).getClass();
	}

	@Override
	public boolean isCellEditable(int row, int col){
		return !this.colNotEdit.contains(col);
	}
}