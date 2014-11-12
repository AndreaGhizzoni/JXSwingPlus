package it.hackcaffebabe.jxswingplus.table.standard;

import it.hackcaffebabe.jxswingplus.table.JXTable;
import it.hackcaffebabe.jxswingplus.table.model.JXTableModel;
import it.hackcaffebabe.jxswingplus.table.render.JXStripedTableRender;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;


/**
 * Simple Class to test {@link it.hackcaffebabe.jxswingplus.table.JXTable} and
 * {@link it.hackcaffebabe.jxswingplus.table.model.JXTableModel} with
 * {@link it.hackcaffebabe.jxswingplus.table.render.JXStripedTableRender}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestJXTableModelWithStripeRender extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final Dimension size = new Dimension( 790, 490 );

	private JPanel contentPane = new JPanel();
	private JXTable table;
    private JTextField txtSelectedRow;

	/** Create the frame. */
	public TestJXTableModelWithStripeRender(){
		super( "Test JXTable with JXTableModel" );
		this.initGUI();
	}

//==============================================================================
// METHOD
//==============================================================================
	/* Initialize all components */
	private void initGUI(){
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setSize( this.size );
		setMinimumSize( this.size );
		setLocation( center( this.size.width, this.size.height ));

		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new MigLayout( "", "[118.00][142.00,grow][grow]",
				"[336.00,grow][59.00]" ) );

		this.table = new JXTable( this.getEmptyTableModel() );
		this.table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.table.getSelectionModel().addListSelectionListener(
				new TableSelectionListener() );
		JXStripedTableRender.installInTable(this.table, Color.DARK_GRAY, Color.CYAN,
				Color.BLUE, Color.LIGHT_GRAY);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView( this.table );
		contentPane.add( scrollPane, "cell 0 0 3 1,grow" );

        JButton btnAddRow = new JButton("Add Row");
		btnAddRow.addActionListener(new AddRowActionListener());
		contentPane.add(btnAddRow, "flowx,cell 0 1 3 1" );

		this.txtSelectedRow = new JTextField();
		this.txtSelectedRow.setColumns( 3 );
		this.txtSelectedRow.setEditable( false );
		contentPane.add( txtSelectedRow, "cell 1 1 2 1" );

        JButton btnDelRow = new JButton("Del Row");
		btnDelRow.addActionListener(new DelRowActionListener());
		contentPane.add(btnDelRow, "cell 1 1 2 1" );

		setContentPane( contentPane );
	}

	/* return the point when the frame need to be painted to make in the center
	 * of the screen */
	private Point center( int w, int h ){
		int wt = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
		int ht = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
		return new Point( wt - (w/ 2), ht - (h/2) );
	}

//==============================================================================
// GETTER
//==============================================================================
	/* This method initialize the contact table model. */
	private JXTableModel getEmptyTableModel(){
		// array of column names
		String[] columnNames = new String[] { "Column0", "Column1", "Column2",
				"Column3" };
		// list of not editable column index
		List<Integer> columnNotEditable = Arrays.asList( 0, 1, 2, 3 );

        JXTableModel model = new JXTableModel();
		model.setColumnNames( columnNames, columnNotEditable );
		model.setData( new Object[][] {} );

		return model;
	}

//==============================================================================
// INNER CLASS
//==============================================================================
	/* Inner class that describe the event of row selection from the Table. */
	private class TableSelectionListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e){
			int viewRow = table.getSelectedModelRow();
			if(viewRow != -1)
				txtSelectedRow.setText( String.valueOf( viewRow ) );
		}
	}

	/* Event handle of button btnAddRow. */
	private class AddRowActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			JXTableModel model = (JXTableModel) table.getModel();
			model.addRow( new Object[] { (model.getRowCount() + 1) + ") This",
					"is", "a new", "row!" } );

			table.refreshRowSorter();// refresh the Sorter
		}
	}

	/* Event handle of button btnDelRow. */
	private class DelRowActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			String text = txtSelectedRow.getText();
			if(text != null && !text.isEmpty()) {
				((JXTableModel) table.getModel()).removeRow( Integer.parseInt( text ) );
				txtSelectedRow.setText( "" );

				table.refreshRowSorter();// refresh the Sorter
			}
		}
	}

//==============================================================================
// MAIN
//==============================================================================
	/* Launch the application. */
	public static void main(String[] args){
		EventQueue.invokeLater( new Runnable(){
			public void run(){
				try {
					TestJXTableModelWithStripeRender frame =
							new TestJXTableModelWithStripeRender();
					frame.setVisible( true );
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		} );
	}
}
