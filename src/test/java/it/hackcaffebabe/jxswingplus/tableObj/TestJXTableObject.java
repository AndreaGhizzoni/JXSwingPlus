package it.hackcaffebabe.jxswingplus.tableObj;

import it.hackcaffebabe.jxswingplus.table.JXTable;
import it.hackcaffebabe.jxswingplus.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jxswingplus.table.model.JXObjectModel;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;


/**
 * Simple Class to test {@link JXTable} and {@link JXObjectModel} representing {@link MySimpleObject}.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestJXTableObject extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final Dimension size = new Dimension( 790, 490 );

	private JPanel contentPane = new JPanel();
	private JXTable table;
	private JXTableColumnAdjuster tableColumnAdjuster;
	private JButton btnAddRow;
	private JButton btnDelRow;
	private JButton btnClear;

	private JTextField txtSelectedRow;
	private JTextField txtFilter;

	/**
	 * Create the frame.
	 */
	public TestJXTableObject(){
		super( "TestV2 JXTable with JXObjectModel" );
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

		ArrayList<MySimpleObject> l = new ArrayList<MySimpleObject>();
		for(int i = 0; i < 10; i++) {
			l.add( new MySimpleObject( 1, "asd", 1.0f ) );
		}
		this.table = new JXTable( new JXObjectModel<MySimpleObject>( l ) );
		this.table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );// selection mode
		this.table.getSelectionModel().addListSelectionListener( new TableSelectionListener() );// selection row event
		this.tableColumnAdjuster = new JXTableColumnAdjuster( this.table );
		this.table.refreshRowSorter();
		this.tableColumnAdjuster.adjustColumns();

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
		contentPane.add( this.btnDelRow, "cell 1 2 2 1" );

		this.btnClear = new JButton( "Clear" );
		this.btnClear.addActionListener( new ClearAllActionListener() );
		contentPane.add( this.btnClear, "cell 1 2" );

		setContentPane( contentPane );
	}

//====================================================================================================//
// GETTER
//====================================================================================================//
	private int rand(int a, int b){
		return new Random( System.currentTimeMillis() ).nextInt( b ) + a;
	}

	private float rand(float a, float b){
		return (new Random( System.currentTimeMillis() ).nextFloat() * b) + a;
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

	/* Event handle of button btnClear. */
	private class ClearAllActionListener implements ActionListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e){
			JXObjectModel<MySimpleObject> model = (JXObjectModel<MySimpleObject>) table.getModel();
			if(model.getRowCount() != 0)
				model.removeAll();
		}
	}

	/* Event handle of button btnAddRow. */
	private class AddRowActionListener implements ActionListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e){
			JXObjectModel<MySimpleObject> model = ((JXObjectModel<MySimpleObject>) table.getModel());
			if(model.getRowCount() == 0) {
				JXObjectModel<MySimpleObject> n = new JXObjectModel<MySimpleObject>();
				n.addObject( new MySimpleObject( rand( 10, 100 ), "This is a new row!", rand( 10.0f, 1000.0f ) ) );
				table.setModel( n );
			} else {
				model.addObject( new MySimpleObject( rand( 10, 100 ), "This is a new row!", rand( 10.0f, 1000.0f ) ) );
			}
			table.refreshRowSorter();// refresh the Sorter
			tableColumnAdjuster.adjustColumns();
		}
	}

	/* Event handle of button btnDelRow. */
	private class DelRowActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			String text = txtSelectedRow.getText();
			if(text != null && !text.isEmpty()) {
				@SuppressWarnings("unchecked")
				JXObjectModel<MySimpleObject> model = ((JXObjectModel<MySimpleObject>) table.getModel());
				model.removeObject( Integer.parseInt( text ) );

				txtSelectedRow.setText( "" );

				table.refreshRowSorter();// refresh the Sorter
				tableColumnAdjuster.adjustColumns();
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
					TestJXTableObject frame = new TestJXTableObject();
					frame.setVisible( true );
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} );
	}
}