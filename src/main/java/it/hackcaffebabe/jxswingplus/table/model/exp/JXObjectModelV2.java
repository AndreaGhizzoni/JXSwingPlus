package it.hackcaffebabe.jxswingplus.table.model.exp;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.Vector;

public class JXObjectModelV2<T extends Displayable> extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;

	private Vector<String> colNames = new Vector<String>();
	private Vector<T> objects = new Vector<T>();

	public JXObjectModelV2( String[] colNames ) throws IllegalArgumentException{
		this.setColumnNames(colNames);
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

//	public void removeAll(){
//		if(this.data.isEmpty())
//			return;
//
//		this.data.clear();
//		fireTableDataChanged();
//	}

//	public void removeObject(int index) throws IndexOutOfBoundsException{
//		if(index < 0 || index > this.data.size())
//			throw new IndexOutOfBoundsException( "Index must in range 0-" + this.data.size() );
//
//		this.data.remove( index );
//		fireTableDataChanged();
//	}

//	public void removeObject(T obj) throws IllegalArgumentException{
//		if(obj == null)
//			throw new IllegalArgumentException( "Object to get can not be null." );
//
//		int i = this.data.indexOf( obj );
//		if(i != -1) {
//			this.removeObject(i);
//			fireTableDataChanged();
//		}
//	}

//==============================================================================
// GETTER
//==============================================================================
	public T getObject(int row) throws IndexOutOfBoundsException{
		if(row < 0 || row > this.objects.size())
			throw new IllegalArgumentException( "Row given is out of range 0-" +
					this.objects.size() );

		return this.objects.get(row);
	}

//	public List<T> getObjects(){
//		return this.data;
//	}

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
		return false; // TODO think how to do this
//		return !this.data.get(row).getColumnNotEditable().contains(col);
	}
}