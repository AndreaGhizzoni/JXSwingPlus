package it.hackcaffebabe.jxswingplus.table.model;

import java.util.*;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * This is a simple model that manage the insertion or delete of rows. To
 * initialize the model you can use:
 * <pre>{@code
 * myTableModel.setData( columnNames, columnNotEditableList, dataOfModel );
 * }</pre>
 * You can use the below method to add or delete rows dynamically:
 * <pre>{@code
 * // to add a new row as an array of {@link java.lang.Object}
 * myTableModel.addRow( myObjectRow )
 *
 * // to remove a single row from the table
 * myTableModel.removeRow( indexOfSelectedRow )
 * }</pre>
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
    private Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    private Vector<String> colsN = new Vector<String>();
    private Vector<Integer> nEditColumns = new Vector<Integer>();

    /**
     * Instance a Table model from data given and his column names.
     * @param data {@link Object} as matrix of Objects for table data.
     * @param colName {@link String} array of columns names to display as table
     *                              header.
     * @throws IllegalArgumentException if arguments given are null or empty.
     */
    public JXTableModel(Object[][] data, String[] colName) throws IllegalArgumentException{
        this.setData(data);
        this.setColumnNames(colName, null);
    }

    /**
	 * Instance a Table model with column ( editable or not ) and data given.
	 * @param data {@link Object} matrix of data to display
	 * @param colNames {@link String} array of column names to display as header
     *                               table.
	 * @param colNotEditable {@link List} list of non-editable column indexes.
	 * @throws IllegalArgumentException if arguments given are null or empty.
	 */
	public JXTableModel(Object[][] data, String[] colNames, List<Integer> colNotEditable) throws IllegalArgumentException{
		this.setData( colNames, colNotEditable, data );
	}

    /**
	 * Instance a simple model with empty columns names and data. To set the
     * columns names use <code>setColumnNames</code> method. Use this method
     * to set the columns not editable by specify the index of the columns.
     * First column is 0. To set the data of the table use <code>setData</code>
     * method. If some data is NOT a String, you need to use
     * {@link it.hackcaffebabe.jxswingplus.table.JXTable} and set the
     * {@link javax.swing.table.TableCellEditor} with <code>setCellEditor</code>
     * method.
	 */
	public JXTableModel(){}

//==============================================================================
// METHOD
//==============================================================================
	/**
	 * This method add row at the bottom of {@link JTable}.
	 * @param row {@link Object} as array to add at the bottom of {@link JTable}.
	 * @throws IllegalArgumentException if row is null or length = 0.
	 */
	public void addRow(Object[] row) throws IllegalArgumentException{
        Vector<Object> r = new Vector<Object>();
        Collections.addAll(r, row);
        this.addRow(r);
	}

    /**
     * This method add a row at the bottom {@link JTable}
     * @param row {@link Vector} of {@link Object}.
     * @throws IllegalArgumentException if row is null or size == 0.
     */
    public void addRow(Vector<Object> row) throws IllegalArgumentException{
        if(row == null || row.size() == 0)
            throw new IllegalArgumentException( "Row to add can not be null or void." );

        this.data.add( row );
        fireTableDataChanged();//update the render
    }

	/**
	 * This method remove a row with specific index.
	 * @param index {@link Integer} of row. First index start at 0.
	 * @throws IllegalArgumentException if index is out of bound of model.
	 */
	public void removeRow(int index) throws IllegalArgumentException{
        if(this.data.size() == 0)
            return;// nothing to do.

		if(index < 0 || index >= this.data.size())
			throw new IllegalArgumentException( "Index of column can not be < 0 or > of table dimension." );

        this.data.remove(index);

		fireTableDataChanged(); // update the render
	}

//==============================================================================
// SETTER
//==============================================================================
	/**
	 * This method set column names, with editable column or not, and the data.
	 * @param names {@link String} as array of string to display the name of
     *                            columns.
	 * @param colNotEditable {@link ArrayList} list of index of non-editable
     *                                           columns. First column is 0.
	 * Set this at null if you want to make all table editable.
	 * @param data {@link Object} as matrix of Object of data table.
	 * @throws IllegalArgumentException if vectors are null, or list contains
     * indexes out of bound of array given.
	 */
	public void setData(String[] names, List<Integer> colNotEditable, Object[][] data) throws IllegalArgumentException{
		this.setColumnNames( names, colNotEditable );
		this.setData( data );
	}

	/**
	 * Set column names of Table Model with, if is necessary, list of columns
     * not editable.
	 * @param names {@link String} as array of string to display the name of
     *                            columns.
	 * @param colNotEditable {@link ArrayList} as list of index of
     *                                            non-editable columns.
     *                                            First column is 0.
	 * Set this at null if you want to make all table editable.
	 * @throws IllegalArgumentException if column names is null, size == 0 or 
	 * index of columns non-editable are zero or greater than size of column
     * names.
	 */
	public void setColumnNames(String[] names, List<Integer> colNotEditable) throws IllegalArgumentException{
		if(names == null || names.length == 0)
			throw new IllegalArgumentException( "Columns Names can not be null or empty." );

        this.colsN.clear();
        Collections.addAll(this.colsN, names);

		this.setColumnNotEditable(colNotEditable);
	}

	/**
	 * Set the column non-editable by specify a list of column index. Set null
     * if you want to make all table editable.
	 * @param colsNotEditable {@link ArrayList} as list of index of
     *                                            non-editable columns.
     *                                            First column is 0.
	 * @throws IllegalArgumentException if size of colsNotEditable is 0 or
	 * index of columns non-editable are zero or greater column's names size.
	 */
	public void setColumnNotEditable(List<Integer> colsNotEditable) throws IllegalArgumentException{
		if(colsNotEditable != null) {
			if(colsNotEditable.size() == 0)
				throw new IllegalArgumentException( "Argument given can not be null or size == 0." );

			Collections.sort( colsNotEditable );
            int tmp;
			for(int i = 0; i < colsNotEditable.size(); i++) {
                tmp = colsNotEditable.get(i);
                if ( tmp < 0 || tmp >= this.colsN.size() )
                    throw new IllegalArgumentException("Column not-editable are incorrect. It's must be in range 0-"
                            + (this.colsN.size()-1));
            }

			this.nEditColumns.clear();
            this.nEditColumns.addAll(colsNotEditable);
		}else{
            this.nEditColumns.clear();
        }
	}

    /**
     * Set the column not-editable by specify the indexes of columns.
     * @param cols {@link Integer} the indexes of not-editable columns.
     */
    public void setColumnNotEditable(Integer... cols) throws IllegalArgumentException{
        this.setColumnNotEditable(Arrays.asList(cols));
    }

	/**
	 * Replace the data of Table Model. Pass null to clear the data in Table
     * Model.
	 * @param data {@link Object} as matrix of Object of data table.
	 */
	public void setData(Object[][] data){
        this.data.clear();

		if(data != null) {
            Vector<Object> tmp;
            for (Object[] i : data) {
                tmp = new Vector<Object>();
                Collections.addAll(tmp, i);
                this.data.add(tmp);
            }
        }

		fireTableDataChanged();
	}

    /**
     * This method add all the data give into Table Model.
     * @param data {@link Object} as matrix of Object of data table
     * @throws IllegalArgumentException if argument is null
     */
    public void addData(Object[][] data) throws IllegalArgumentException{
        if(data == null)
			throw new IllegalArgumentException( "Data can not be null." );

        Vector<Object> tmp;
        for(Object[] i: data) {
            tmp = new Vector<Object>();
            Collections.addAll(tmp, i);
            this.data.add(tmp);
        }

		fireTableDataChanged();
    }

//==============================================================================
// GETTER
//==============================================================================
	/**
	 * Returns the list of index of not editable column.
	 * @return {@link Vector} of Integer index.
	 */
	public Vector<Integer> getNonEditableColumns(){
		return this.nEditColumns;
	}

	/**
	 * Returns the data matrix of JXTable.
	 * @return {@link Vector} matrix data of {@link JTable}.
	 */
	public Vector<Vector<Object>> getData(){
		return this.data;
	}

	/**
	 * Return the array of column names of {@link JTable} 
	 * @return {@link Vector} the array of column names of {@link JTable}
	 */
	public Vector<String> getColumnNames(){
		return this.colsN;
	}

	/**
	 * Returns specific row with index given. First row is 0. If there aren't
     * rows, returns null.
	 * @param index {@link Integer} of row to get.
	 * @return {@link Vector} of object represents the table row.
	 * @throws IllegalArgumentException if index is not in range
     * [0-<code>getRowCount()</code>]
	 */
	public Vector<Object> getRow(int index) throws IllegalArgumentException{
		if(index < 0 || index > this.data.size())
			throw new IllegalArgumentException( "Index is out of range 0-getRowCount()." );

		if(this.data.size() == 0)
			return null;

        return this.data.get(index);
	}

//==============================================================================
// OVERRIDE
//==============================================================================
	@Override
	public int getColumnCount(){
        return this.colsN.size();
	}

	@Override
	public int getRowCount(){
        return this.data.size();
	}

	@Override
	public String getColumnName(int col) throws IllegalArgumentException{
		if(col >= this.colsN.size())
			throw new IllegalArgumentException( "Column can not be less of zero." );

        return this.colsN.get(col);
	}

	@Override
	public Object getValueAt(int row, int col) throws IllegalArgumentException{
		if(col >= this.colsN.size() || row >= this.data.size() )
			throw new IllegalArgumentException( "Row or Column can not be greater than row count or column count." );

        return this.data.get(row).get(col);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c){
		if(this.data.size() == 0)
			return Object.class;
		else return getValueAt( 0, c ).getClass();
	}

	@Override
	public boolean isCellEditable(int row, int col){
        return !this.colsN.contains(col);
	}

	@Override
	public void setValueAt(Object value, int row, int col){
        this.data.get(row).set(col,value);
        fireTableDataChanged();
	}
}