package it.hackcaffebabe.jxswingplus.table;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


/**
 * Class to manage the widths of columns in a table.<br>
 * To adjust all the columns widths just call <code>adjustColumns()</code> each time models change.<br>
 * Finally various properties control how the width of the column is calculated.
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXTableColumnAdjuster implements PropertyChangeListener, TableModelListener
{
	private JTable table;
	private int spacing;
	private boolean isColumnHeaderIncluded;
	private boolean isColumnDataIncluded;
	private boolean isOnlyAdjustLarger;
//	private boolean isDynamicAdjustment; // maybe fix this feature instead to remove it
 	private Map<TableColumn, Integer> columnSizes = new HashMap<TableColumn, Integer>();// used into restoreColumns()

	/**
	 * Specify the table and use default spacing.
	 * @param table {@link JTable} the table.
	 */
	public JXTableColumnAdjuster(JTable table){
		this( table, 18 );
	}

	/**
	 * Specify the table and spacing.
	 * @param table {@link JTable} the table.
	 * @param spacing {@link Integer} the spacing.
	 */
	public JXTableColumnAdjuster(JTable table, int spacing){
		this.table = table;
		this.spacing = spacing;
		setColumnHeaderIncluded( true );
		setColumnDataIncluded( true );
		setOnlyAdjustLarger( true );
//		setDynamicAdjustment( false );
	}

//====================================================================================================//
// METHOD
//====================================================================================================//
	/**
	 * Adjust the widths of all the columns in the table
	 */
	public void adjustColumns(){
		for(int i = 0; i < table.getColumnModel().getColumnCount(); i++)
			adjustColumn( i );
	}

	/**
	 * Adjust the width of the specified column in the table
	 * @param column {@link Integer} the index of column.
	 */
	public void adjustColumn(final int column){
		TableColumn tableColumn = table.getColumnModel().getColumn( column );

		if(!tableColumn.getResizable())
			return;

		int columnHeaderWidth = getColumnHeaderWidth( column );
		int columnDataWidth = getMaxColumnDataWidth( column );
		int preferredWidth = Math.max( columnHeaderWidth, columnDataWidth );

		updateTableColumn( column, preferredWidth );
	}

	/* Update the TableColumn with the newly calculated width. */
	private void updateTableColumn(int column, int width){
		final TableColumn tableColumn = table.getColumnModel().getColumn( column );

		if(!tableColumn.getResizable())
			return;

		width += spacing;

		//  Don't shrink the column width
		if(isOnlyAdjustLarger)
			width = Math.max( width, tableColumn.getPreferredWidth() );

		columnSizes.put( tableColumn, new Integer( tableColumn.getWidth() ) );
		table.getTableHeader().setResizingColumn( tableColumn );
		tableColumn.setWidth( width );
	}

	/**
	 * Restore the widths of the columns in the table to its previous width
	 */
	public void restoreColumns(){
		for(int i = 0; i < table.getColumnModel().getColumnCount(); i++)
			restoreColumn( i );
	}

	/* Restore the width of the specified column to its previous width */
	private void restoreColumn(int column){
		TableColumn tableColumn = table.getColumnModel().getColumn( column );
		Integer width = columnSizes.get( tableColumn );

		if(width != null) {
			table.getTableHeader().setResizingColumn( tableColumn );
			tableColumn.setWidth( width.intValue() );
		}
	}

//====================================================================================================//
// GETTER
//====================================================================================================//
	/* Calculated the width based on the column name. */
	private int getColumnHeaderWidth(int column){
		if(!isColumnHeaderIncluded)
			return 0;

		TableColumn tableColumn = table.getColumnModel().getColumn( column );
		Object value = tableColumn.getHeaderValue();
		TableCellRenderer renderer = tableColumn.getHeaderRenderer();

		if(renderer == null)
			renderer = table.getTableHeader().getDefaultRenderer();

		Component c = renderer.getTableCellRendererComponent( table, value, false, false, -1, column );
		return c.getPreferredSize().width;
	}

	/* Calculate the width based on the widest cell renderer for the given column. */
	private int getMaxColumnDataWidth(int column){
		if(!isColumnDataIncluded)
			return 0;

		int preferredWidth = 0;
		int maxWidth = table.getColumnModel().getColumn( column ).getMaxWidth();

		for(int row = 0; row < table.getRowCount(); row++) {
			preferredWidth = Math.max( preferredWidth, getCellDataWidth( row, column ) );
//			//  We've exceeded the maximum width, no need to check other rows
			if(preferredWidth >= maxWidth)
				break;
		}

		return preferredWidth;
	}

	/* Get the preferred width for the specified cell. */
	private int getCellDataWidth(int row, int column){
		// Invoke the renderer for the cell to calculate the preferred width

		TableCellRenderer cellRenderer = table.getCellRenderer( row, column );
		Component c = table.prepareRenderer( cellRenderer, row, column );
		return c.getPreferredSize().width + table.getIntercellSpacing().width;
	}

//====================================================================================================//
// SETTER
//====================================================================================================//
	/**
	 * Indicates whether to include the header in the width calculation.
	 * @param isColumnHeaderIncluded {@link Boolean}
	 */
	public void setColumnHeaderIncluded(boolean isColumnHeaderIncluded){
		this.isColumnHeaderIncluded = isColumnHeaderIncluded;
	}

	/**
	 * Indicates whether to include the model data in the width calculation
	 * @param isColumnDataIncluded {@link Boolean}
	 */
	public void setColumnDataIncluded(boolean isColumnDataIncluded){
		this.isColumnDataIncluded = isColumnDataIncluded;
	}

	/**
	 * Indicates whether columns can only be increased in size
	 * @param isOnlyAdjustLarger {@link Boolean} 
	 */
	public void setOnlyAdjustLarger(boolean isOnlyAdjustLarger){
		this.isOnlyAdjustLarger = isOnlyAdjustLarger;
	}

//	/**
//	 * Indicate whether changes to the model should cause the width to be dynamically recalculated.
//	 * @param isDynamicAdjustment {@link Boolean}
//	 */
//	public void setDynamicAdjustment(boolean isDynamicAdjustment){
//		//  May need to add or remove the TableModelListener when changed
//
//		if(this.isDynamicAdjustment != isDynamicAdjustment) {
//			if(isDynamicAdjustment) {
//				table.addPropertyChangeListener( this );
//				table.getModel().addTableModelListener( this );
//			}
//			else {
//				table.removePropertyChangeListener( this );
//				table.getModel().removeTableModelListener( this );
//			}
//		}
//
//		this.isDynamicAdjustment = isDynamicAdjustment;
//	}

//====================================================================================================//
// OVERRIDE
//====================================================================================================//
	@Override
	public void propertyChange(PropertyChangeEvent e){
		//  When the TableModel changes we need to update the listeners
		//  and column widths

		if("model".equals( e.getPropertyName() )) {
			TableModel model = (TableModel) e.getOldValue();
			model.removeTableModelListener( this );

			model = (TableModel) e.getNewValue();
			model.addTableModelListener( this );
			adjustColumns();
		}
	}

	@Override
	public void tableChanged(TableModelEvent e){
		if(!isColumnDataIncluded)
			return;

		//  A cell has been updated
		if(e.getType() == TableModelEvent.UPDATE) {
			int column = table.convertColumnIndexToView( e.getColumn() );

			//  Only need to worry about an increase in width for this cell
			if(isOnlyAdjustLarger) {
				int row = e.getFirstRow();
				TableColumn tableColumn = table.getColumnModel().getColumn( column );

				if(tableColumn.getResizable())
					updateTableColumn( column, getCellDataWidth( row, column ) );
			}
			else {
				// Could be an increase of decrease so check all rows
				adjustColumn( column );
			}
		}
		else {
			// The update affected more than one column so adjust all columns
			adjustColumns();
		}
	}
}
