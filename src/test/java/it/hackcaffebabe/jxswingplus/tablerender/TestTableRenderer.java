package it.hackcaffebabe.jxswingplus.tablerender;

import it.hackcaffebabe.jxswingplus.table.JXTable;
import it.hackcaffebabe.jxswingplus.table.model.JXTableModel;
import it.hackcaffebabe.jxswingplus.table.render.JXStripedTableRender;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;


/**
 * Simple Class to test all the renders
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestTableRenderer extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final Dimension size = new Dimension( 790, 490 );

	private JPanel contentPane = new JPanel();
	private JXTable table;

	/**
	 * Create the frame.
	 */
	public TestTableRenderer(){
		super( "Test JTable Renderer" );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setSize( this.size );
		setMinimumSize( this.size );
		setLocation( (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.size.width / 2),
				(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.size.height / 2) );
		this.initGUI();
		this.populate();
		this.initRenderer();
	}

//====================================================================================================//
// METHOD
//====================================================================================================//
	/* Initialize all components */
	private void initGUI(){
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new MigLayout( "", "[118.00][142.00,grow][grow]", "[336.00,grow][]" ) );

		this.table = new JXTable( getEmptyTableModel() );
		this.table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );// selection mode

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView( this.table );
		contentPane.add( scrollPane, "cell 0 0 3 1,grow" );

		JButton btnAdd = new JButton( "add" );
		btnAdd.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				JXTableModel model = (JXTableModel) table.getModel();
				model.addRow( new Object[] { (model.getRowCount() + 1) + ") This", "is", "a new", "row!" } );
			}
		} );
		contentPane.add( btnAdd, "cell 0 1" );

		setContentPane( contentPane );
	}

	/* NB: if model change you need to re install the renderer. */
	private void initRenderer(){
		JXStripedTableRender.installInTable( table, Color.LIGHT_GRAY, Color.WHITE, null, null );
	}

//====================================================================================================//
// GETTER
//====================================================================================================//
	/* This method populate the table */
	private void populate(){
		JXTableModel model = (JXTableModel) table.getModel();
		for(int i = 0; i < 10; i++) {
			model.addRow( new Object[] { (model.getRowCount() + 1) + ") This", "is", "a new", "row!" } );
		}
	}

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
// MAIN
//====================================================================================================//
	/* Launch the application. */
	public static void main(String[] args){
		EventQueue.invokeLater( new Runnable(){
			public void run(){
				try {
					TestTableRenderer frame = new TestTableRenderer();
					frame.setVisible( true );
					frame.pack();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} );
	}
}
