package it.hackcaffebabe.jxswingplus.table.render;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


/**
 * This render gets the stripe effect on a {@link JTable}.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXStripedTableRender implements TableCellRenderer
{
	protected TableCellRenderer targetRenderer;
	protected Color evenBack;
	protected Color evenFore;
	protected Color oddBack;
	protected Color oddFore;

	/**
	 * Create the stripe render for a {@link JTable}.
	 * @param render {@link TableCellRenderer} from the table to use.
	 * @param evenBack {@link Color}
	 * @param evenFore {@link Color}
	 * @param oddBack {@link Color}
	 * @param oddFore {@link Color}
	 */
	public JXStripedTableRender(TableCellRenderer render, Color evenBack, Color evenFore, Color oddBack, Color oddFore){
		this.targetRenderer = render;
		this.evenBack = evenBack;
		this.evenFore = evenFore;
		this.oddBack = oddBack;
		this.oddFore = oddFore;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column){

		TableCellRenderer renderer = targetRenderer;
		if(renderer == null) {
			// get the default renderer from the table
			renderer = table.getDefaultRenderer( table.getColumnClass( column ) );
		}

		Component comp = renderer.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );

		// apply the stripe effect
		if(isSelected == false && hasFocus == false) {
			if((row & 1) == 0) {
				comp.setBackground( evenBack != null ? evenBack : table.getBackground() );
				comp.setForeground( evenFore != null ? evenFore : table.getForeground() );
			} else {
				comp.setBackground( oddBack != null ? oddBack : table.getBackground() );
				comp.setForeground( oddFore != null ? oddFore : table.getForeground() );
			}
		}

		return comp;
	}

	/**
	 * Install the {@link JXStripedTableRender} to the given table column index.
	 * @param table {@link JTable} to get the column.
	 * @param columnIndex {@link Integer} the index of column.
	 * @param evenBack {@link Color}
	 * @param evenFore {@link Color}
	 * @param oddBack {@link Color}
	 * @param oddFore {@link Color}
	 */
	public static void installInColumn(JTable table, int columnIndex, Color evenBack, Color evenFore, Color oddBack,
			Color oddFore){
		TableColumn tc = table.getColumnModel().getColumn( columnIndex );
		TableCellRenderer renderer = tc.getCellRenderer();
		tc.setCellRenderer( new JXStripedTableRender( renderer, evenBack, evenFore, oddBack, oddFore ) );
	}

	/**
	 * Install the {@link JXStripedTableRender} to all the columns on the table.
	 * @param table {@link JTable} to install in all the columns.
	 * @param evenBack {@link Color}
	 * @param evenFore {@link Color}
	 * @param oddBack {@link Color}
	 * @param oddFore {@link Color}
	 */
	public static void installInTable(JTable table, Color evenBack, Color evenFore, Color oddBack, Color oddFore){
		JXStripedTableRender sharedInstance = null;
		int columns = table.getColumnCount();
		for(int i = 0; i < columns; i++) {
			TableColumn tc = table.getColumnModel().getColumn( i );
			TableCellRenderer targetRenderer = tc.getCellRenderer();
			if(targetRenderer != null) {
				// this column add a specific renderer
				tc.setCellRenderer( new JXStripedTableRender( targetRenderer, evenBack, evenFore, oddBack, oddFore ) );
			} else {
				// this column uses a class renderer - use a shared renderer
				if(sharedInstance == null) {
					sharedInstance = new JXStripedTableRender( null, evenBack, evenFore, oddBack, oddFore );
				}
				tc.setCellRenderer( sharedInstance );
			}
		}
	}

}
