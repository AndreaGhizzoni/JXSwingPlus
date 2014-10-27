package it.hackcaffebabe.jxswingplus.table;

import it.hackcaffebabe.jxswingplus.table.JXTable;
import it.hackcaffebabe.jxswingplus.table.model.JXTableModel;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;


/**
 * Simple Class to test {@link JXTable} and {@link JXTableModel}.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestJXTable extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final Dimension size = new Dimension( 790, 490 );

	private JPanel contentPane = new JPanel();
	private JXTable table;
	private JButton btnAddRow;
	private JTextField txtSelectedRow;
	private JButton btnDelRow;
	private JTextField txtFilter;

	/**
	 * Create the frame.
	 */
	public TestJXTable(){
		super( "Test JXTable with JXTableModel" );
		this.initGUI();

		this.table.setRowSorter( this.txtFilter );// sorter feature!
	}

//====================================================================================================//
// METHOD
//====================================================================================================//
	/* Initialize all components */
	private void initGUI(){
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setSize( this.size );
		setMinimumSize( this.size );
		setLocation( (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.size.width / 2), 
				(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.size.height / 2) );

		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new MigLayout( "", "[118.00][142.00,grow][grow]", "[336.00,grow][36.00][59.00]" ) );

		this.table = new JXTable( this.getEmptyTableModel() );
		this.table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );// selection mode
		this.table.getSelectionModel().addListSelectionListener( new TableSelectionListener() );// selection row event

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView( this.table );
		contentPane.add( scrollPane, "cell 0 0 3 1,grow" );

		this.btnAddRow = new JButton( "Add Row" );
		this.btnAddRow.addActionListener( new AddRowActionListener() );
		contentPane.add( btnAddRow, "flowx,cell 0 2 3 1" );

		contentPane.add( new JLabel( "String Filter" ), "cell 0 1,alignx right" );

		this.txtFilter = new JTextField();
		this.txtFilter.setColumns( 10 );
		contentPane.add( this.txtFilter, "cell 1 1 2 1,growx" );

		this.txtSelectedRow = new JTextField();
		this.txtSelectedRow.setColumns( 3 );
		this.txtSelectedRow.setEditable( false );
		contentPane.add( txtSelectedRow, "cell 1 2 2 1" );

		this.btnDelRow = new JButton( "Del Row" );
		this.btnDelRow.addActionListener( new DelRowActionListener() );
		contentPane.add( btnDelRow, "cell 1 2 2 1" );

		setContentPane( contentPane );
	}

//====================================================================================================//
// GETTER
//====================================================================================================//
	/* This method initialize the contact table model. */
	private JXTableModel getEmptyTableModel(){
		JXTableModel model = new JXTableModel();

		// array of column names
		String[] columnNames = new String[] { "Column0", "Column1", "Column2", "Column3" };
		// list of not editable column index
		List<Integer> columnNotEditable = Arrays.asList( 0, 1, 2, 3 );

		model.setColumnNames( columnNames, columnNotEditable );
		model.setData( new Object[][] {} );

		return model;
	}

//====================================================================================================//
// INNER CLASS
//====================================================================================================//
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
			model.addRow( new Object[] { (model.getRowCount() + 1) + ") This", "is", "a new", "row!" } );

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

//====================================================================================================//
// MAIN
//====================================================================================================//
	/* Launch the application. */
	public static void main(String[] args){
		EventQueue.invokeLater( new Runnable(){
			public void run(){
				try {
					TestJXTable frame = new TestJXTable();
					frame.setVisible( true );
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		} );
	}
}
