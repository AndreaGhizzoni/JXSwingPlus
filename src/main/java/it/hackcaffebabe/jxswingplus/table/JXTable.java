package it.hackcaffebabe.jxswingplus.table;


import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;

/**
 * This class provide a simplification of using {@link JTable}. You can use this
 * class as a classic {@link JTable} with the model
 * {@link it.hackcaffebabe.jxswingplus.table.model.JXTableModel} or, the
 * {@link it.hackcaffebabe.jxswingplus.table.model.JXObjectModel} of
 * {@link it.hackcaffebabe.jxswingplus.table.model.DisplayableObject}.
 *
 * There is also a method to enable a row sorter with {@link javax.swing.JTextField}
 * given to get the text to sort:
 * <pre>{@code
 * myTable.setRowSorter( myFantasticTextField )
 * }</pre>
 * Remember: when the model change:
 * <pre>{@code
 * myTable.refreshRowSorter();
 * }</pre>
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXTable extends JTable
{
	private static final long serialVersionUID = 1L;

    // "(?i)" this make regular expression case insensitive
    private static final String pattern = "(?i)%s";
	private TableRowSorter<TableModel> rowSorter;

	/** Instance an empty table. */
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

//==============================================================================
// METHOD
//==============================================================================
	/**
	 * This method refresh the sorter with new model of data.<br>
	 * Needs to be called when the model change.
	 */
	public void refreshRowSorter(){
		this.rowSorter = new TableRowSorter<TableModel>( getModel() );
		setRowSorter( this.rowSorter );
	}

//==============================================================================
// GETTER
//==============================================================================
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
	 * It's so usefully when the row of table are sorted to get the effective
     * position of selected row from the model of data.
	 * @return {@link Integer}the row selected from the model of table.
	 */
	public Integer getSelectedModelRow(){
		int modelRow = getSelectedRow();
		if(modelRow != -1)
			return convertRowIndexToModel( modelRow );
		else return -1;
	}

//==============================================================================
// SETTER
//==============================================================================
	/**
	 * Sets the Row filter bind with {@link JTextField} given.<br>
	 * At the JTextField given is bind a {@link DocumentListener} to catch every
     * changes of text.
	 * @param c {@link JTextComponent} to get the filter string.
	 * @throws IllegalArgumentException if text field given is null.
	 */
	public void setRowSorter(JTextComponent c ) throws IllegalArgumentException{
		if(c == null)
			throw new IllegalArgumentException( "Text field given can not be null" );
        c.getDocument().addDocumentListener(new SearchListener(c));

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

//==============================================================================
// INNER CLASS
//==============================================================================
    // document listener to attach on the field
    class SearchListener implements DocumentListener
    {
        private JTextComponent txt;
        public SearchListener( JTextComponent t ){
            this.txt = t;
        }

        /* this method is used for the Document Listener of userTextFilter */
        private void search(){
            try {
                String f = String.format(pattern, txt.getText());
                RowFilter<TableModel, Object> rf = RowFilter.regexFilter(f);
                rowSorter.setRowFilter( rf );
            } catch(java.util.regex.PatternSyntaxException ex) {
                //If current expression doesn't parse, don't update.
            }
        }
        @Override
        public void insertUpdate(DocumentEvent e) { search(); }

        @Override
        public void removeUpdate(DocumentEvent e) { search(); }

        @Override
        public void changedUpdate(DocumentEvent e) { search(); }
    }
}
