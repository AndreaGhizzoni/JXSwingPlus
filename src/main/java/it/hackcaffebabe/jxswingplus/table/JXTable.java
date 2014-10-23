package it.hackcaffebabe.jxswingplus.table;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 * This class provide a simplification of using {@link JTable}.<br>
 * You may use a method to enable a row sorter with {@link JTextField} given to get the text to sort:
 * <pre>
 * myTable.setRowSorter( myFantasticTextField );
 * </pre>
 * myTable.refreshRowSorter() (When the model change you need to refresh the sorter with new model)
 * 
 * You may need to get the index of selected row, so you can use:<br>
 * 	-<code> getSelectedViewRow()</code>: that returns the selected row of view of the table.<br>
 * 	-<code> getSelectedModelRow()</code>: that returns the selected row of model of the table.<br>
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXTable extends JTable
{
	private static final long serialVersionUID = 1L;

	private TableRowSorter<TableModel> rowSorter;
	private JTextField userTextFilter;

	/**
	 * Instance an empty table.
	 */
	public JXTable(){
		super();
	}

	/**
	 * Instance a JXTable with modal given.
	 * @param model {@link TableModel} to set on JTable.
	 */
	public JXTable(TableModel model){
		super( model );
	}

//====================================================================================================//
// METHOD
//====================================================================================================//	
	/* this method is used for the Document Listener of userTextFilter */
	private void search(){
		try {
			// "(?i)" this make regular expression case insensitive
			RowFilter<TableModel, Object> rf = RowFilter.regexFilter( "(?i)" + userTextFilter.getText() );
			rowSorter.setRowFilter( rf );
		} catch(java.util.regex.PatternSyntaxException ex) {
			//If current expression doesn't parse, don't update.
		}
	}

	/**
	 * This method refresh the sorter with new model of data.<br>
	 * Needs to be called when the model change.
	 */
	public void refreshRowSorter(){
		this.rowSorter = new TableRowSorter<TableModel>( getModel() );
		setRowSorter( this.rowSorter );
	}

//====================================================================================================//
// GETTER
//====================================================================================================//	
	/**
	 * This method returns the row selected from the view of table.<br>
	 * It's equivalent as <code>getSelectedRow()</code> of {@link JTable}
	 * @return {@link Integer} the row selected from the view of table.
	 */
	public Integer getSelectedViewRow(){
		return getSelectedRow();
	}

	/**
	 * This method returns the row selected from the model of table.<br>
	 * It's so usefully when the row of table are sorted to get the effective position of selected row from the model of data.
	 * @return {@link Integer}the row selected from the model of table.
	 */
	public Integer getSelectedModelRow(){
		int modelRow = getSelectedRow();
		if(modelRow != -1)
			return convertRowIndexToModel( modelRow );
		else return -1;
	}

//====================================================================================================//
// SETTER
//====================================================================================================//	
	/**
	 * Sets the Row filter binded with {@link JTextField} given.<br>
	 * At the JTextField given is binded a {@link DocumentListener} to catch every changes of text.
	 * @param field {@link JTextField} to get the filter string.
	 * @throws IllegalArgumentException if text field given is null.
	 */
	public void setRowSorter(JTextField field) throws IllegalArgumentException{
		if(field == null)
			throw new IllegalArgumentException( "Text field given can not be null" );

		this.userTextFilter = field;
		this.userTextFilter.getDocument().addDocumentListener( new DocumentListener(){
			public void changedUpdate(DocumentEvent e){
				search();
			}

			public void insertUpdate(DocumentEvent e){
				search();
			}

			public void removeUpdate(DocumentEvent e){
				search();
			}
		} );

		// in most cases when row sorter is activated there are already data into table model.
		refreshRowSorter();
	}

	/**
	 * This method set new CellEditor for specific column.
	 * @param column {@link Integer} to set new CellEditor.
	 * @param editor {@link TableCellEditor} to set of column given.
	 * @throws ArrayIndexOutOfBoundsException if column is out of range.
	 */
	public void setCellEditorInColumn(int column, TableCellEditor editor) throws IllegalArgumentException{
		getColumnModel().getColumn( column ).setCellEditor( editor );
	}
}
