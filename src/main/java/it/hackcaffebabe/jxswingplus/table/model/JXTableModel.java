package it.hackcaffebabe.jxswingplus.table.model;

import it.hackcaffebabe.jxswingplus.table.JXTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;


/**
 * This is a simple model that manage the insertion or delete of rows.<br>
 * To initialize the model you can use:
 * <pre>
 * myTabelModel.setData( columnNames, columNotEditableList, dataOfModel );
 * </pre>
 * 
 * You can use the below method to add or delete rows dynamically:{@code
 * myTableModel.addRow( myObjectRow ) // to add a new row as an array of {@link Object}
 * myTabelModel.removeRow( indexOfSelectedRow ) // to remove a single row from the table
 * }
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	private String[] columnsNames = new String[] {};
	private Object[][] data = new Object[][] { {} };
	private List<Integer> nonEditableColumns;

	/**
	 * Instance a simple model with empty columns names and data.
	 * 
	 * To set the columns names use <code>setColumnNames</code> method.
	 * Use this method to set the columns not editable by specify the index of the columns. 
	 * NB: First column is 0.
	 * 
	 * To set the data of the table use <code>setData</code> method.
	 * NB: If some data is NOT a String, you need to use {@link JXTable} and set the {@link TableCellEditor} 
	 * with <code>setCellEditor</code> method.
	 */
	public JXTableModel(){}

	/**
	 * Instance a Table model with column ( editable or not ) and data given.
	 * @param data {@link Object} matrix of data to display
	 * @param columnNames {@link String} array of column names to display
	 * @param columnNotEditable {@link List} list of non-editable column indexes.
	 * @throws IllegalArgumentException if argument given are null or empty.
	 */
	public JXTableModel(Object[][] data, String[] columnNames, List<Integer> columnNotEditable)
			throws IllegalArgumentException{
		this.setData( columnNames, columnNotEditable, data );
	}

//====================================================================================================//
// METHOD
//====================================================================================================//	
	/**
	 * This method a row at the bottom of {@link JTable}.
	 * @param row {@link Object} as array to add at the bottom of {@link JTable}.
	 * @throws IllegalArgumentException {@link Exception} if row is null or length = 0.
	 */
	public void addRow(Object[] row) throws IllegalArgumentException{
		if(row == null || row.length == 0)
			throw new IllegalArgumentException( "Row to add can not be null or void." );

		Object[][] newData = new Object[this.data.length + 1][this.columnsNames.length];
		System.arraycopy( this.data, 0, newData, 0, this.data.length );

		for(int i = 0; i < row.length; i++)
			newData[newData.length - 1][i] = row[i];

		this.data = newData;

		fireTableDataChanged();//update render of JTable
	}

	/**
	 * This method remove a row with specific index.
	 * @param index {@link Integer} of row. First index start at 0.
	 * @throws IllegalArgumentException {@link Exception} if index is not in range 0-this.data.lenght.
	 * @throws IllegalStateException if there aren't rows in table.
	 */
	public void removeRow(int index) throws IllegalArgumentException, IllegalStateException{
		if(index < 0 || index > this.data.length)
			throw new IllegalArgumentException( "Index of column can not be < 0 or > of table dimension." );

		if(this.data.length == 0)
			throw new IllegalStateException( "There aren't rows in table." );

//		index--;//for human indexing 

		Object[][] newData = new Object[this.data.length - 1][this.columnsNames.length];

		boolean deleted = false;
		for(int i = 0; i < this.data.length; i++) {
			if(i == index)
				deleted = true;
			else if(deleted)
				newData[i - 1] = this.data[i];
			else newData[i] = this.data[i];
		}

		this.data = newData;

		fireTableDataChanged();
	}

//====================================================================================================//
// SETTER
//====================================================================================================//	
	/**
	 * This method set column names, with editable column or not, and the data.
	 * @param names {@link String} as array of string to display the name of columns.
	 * @param columnNotEditable {@link ArrayList} list of index of non-editable columns. First column is 0.
	 * Set this at null if you want to make all table editable.
	 * @param data {@link Object} as matrix of Object of data table.
	 * @throws IllegalArgumentException if vectors are null, or list contains indexes out of bound of array given.
	 */
	public void setData(String[] names, List<Integer> columnNotEditable, Object[][] data)
			throws IllegalArgumentException{
		this.setColumnNames( names, columnNotEditable );
		this.setData( data );
	}

	/**
	 * Set column names of Table Model with, if is necessary, list of columns not editable.
	 * @param names {@link String} as array of string to display the name of columns.
	 * @param columnsNotEditable {@link ArrayList} as list of index of non-editable columns. First column is 0.
	 * Set this at null if you want to make all table editable.
	 * @throws IllegalArgumentException if column names is null, size == 0 or 
	 * index of columns non-editable are zero or greater than size of column names.
	 */
	public void setColumnNames(String[] names, List<Integer> columnsNotEditable) throws IllegalArgumentException{
		if(columnsNames == null || names.length == 0)
			throw new IllegalArgumentException( "Columns Names can not be null or void." );

		this.columnsNames = names;
		this.setColumnNonEditable( columnsNotEditable );
	}

	/**
	 * Set the column non-editable by specify a list of column index.
	 * Set null if you want to make all table editable.
	 * @param columnsNotEditable {@link ArrayList} as list of index of non-editable columns. First column is 0.
	 * @throws IllegalArgumentException if size of columnsNotEditable is 0 or 
	 * index of columns non-editable are zero or greater column's names size.
	 */
	public void setColumnNonEditable(List<Integer> columnsNotEditable) throws IllegalArgumentException{
		if(columnsNotEditable != null) {
			if(columnsNotEditable.size() == 0)
				throw new IllegalArgumentException( "Argument given can not be null or size == 0." );

			Collections.sort( columnsNotEditable );
			for(int i = 0; i < columnsNotEditable.size(); i++)
				if(columnsNotEditable.get( i ) < 0 || columnsNotEditable.get( i ) > this.columnsNames.length)
					throw new IllegalArgumentException( "Column non-editable are incorrect. It's must be in range 0-"
							+ (this.columnsNames.length - 1) );

			this.nonEditableColumns = columnsNotEditable;
		}
	}

	/**
	 * Set data of Table Model. 
	 * @param data {@link Object} as matrix of Object of data table
	 * @throws IllegalArgumentException if argument is null.
	 */
	public void setData(Object[][] data) throws IllegalArgumentException{
		if(data == null)
			throw new IllegalArgumentException( "Data can not be null." );

		this.data = data;
		fireTableDataChanged();
	}

//====================================================================================================//
// GETTER
//====================================================================================================//	
	/**
	 * Returns the list of index of not editable column.
	 * @return {@link ArrayList} of Integer index.
	 */
	public List<Integer> getNonEditableColumns(){
		return this.nonEditableColumns;
	}

	/**
	 * Returns the data matrix of JXTable.
	 * @return {@link Object} matrix data of {@link JTable}.
	 */
	public Object[][] getData(){
		return this.data;
	}

	/**
	 * Return the array of column names of {@link JTable} 
	 * @return {@link String} the array of column names of {@link JTable}
	 */
	public String[] getColumnNames(){
		return this.columnsNames;
	}

	/**
	 * Returns specific row with index given. First row is 0.
	 * If there aren't rows, returns null.
	 * @param index {@link Integer} of row to get.
	 * @return {@link ArrayList} of String represents the table row.
	 * @throws IllegalArgumentException if index is not in range [0-<code>getRowCount()</code>]
	 */
	public ArrayList<String> getRow(int index) throws IllegalArgumentException{
		if(index < 0 || index > this.data.length)
			throw new IllegalArgumentException( "Index is out of range 0-getRowCount()." );

		if(this.data.length == 0)
			return null;

		ArrayList<String> lineToReturn = new ArrayList<String>();
		for(int i = 0; i < getColumnCount(); i++)
			lineToReturn.add( (String) getValueAt( index, i ) );

		return lineToReturn;
	}

//====================================================================================================//
// OVERRIDE
//====================================================================================================//	
	@Override
	public int getColumnCount(){
		return columnsNames.length;
	}

	@Override
	public int getRowCount(){
		return data.length;
	}

	@Override
	public String getColumnName(int col) throws IllegalArgumentException{
		if(col > this.columnsNames.length)
			throw new IllegalArgumentException( "Colum can not be less of zero." );

		return columnsNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) throws IllegalArgumentException{
		if(col > this.columnsNames.length || row > this.data.length)
			throw new IllegalArgumentException( "Row or Column can not be less of zero." );

		return data[row][col];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c){
		if(this.data.length == 0)
			return Object.class;
		else return getValueAt( 0, c ).getClass();
	}

	@Override
	public boolean isCellEditable(int row, int col){
		if(this.nonEditableColumns.contains( col ))
			return false;
		else return true;
	}

	@Override
	public void setValueAt(Object value, int row, int col){
		data[row][col] = value;
		fireTableCellUpdated( row, col );
	}
}