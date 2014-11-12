package it.hackcaffebabe.jxswingplus.tableObj;

import it.hackcaffebabe.jxswingplus.table.JXTable;
import it.hackcaffebabe.jxswingplus.table.model.JXObjectModel;
import it.hackcaffebabe.jxswingplus.table.model.JXDisplayable;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Simple Class to test {@link it.hackcaffebabe.jxswingplus.table.JXTable} and
 * {@link it.hackcaffebabe.jxswingplus.table.model.JXObjectModel}
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestJXTableWithObjectModel extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final Dimension size = new Dimension( 790, 490 );

	private JPanel contentPane = new JPanel();
	private JXObjectModel<MyDisplayableObject> model;
	private JTextField txtSelectedRow;

	/** Create the frame. */
	public TestJXTableWithObjectModel(){
		super( "TestV2 JXTable with JXObjectModel" );
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
				"[336.00,grow][36.00]" ) );

		JXTable table = new JXTable( computeModel() );
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(
				new TableSelectionListener(table));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView( table );
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

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ClearAllActionListener());
		contentPane.add(btnClear, "cell 1 1" );

		setContentPane( contentPane );
	}

	/* build the appropriate model */
	private JXObjectModel<MyDisplayableObject> computeModel(){
		this.model = new JXObjectModel<>();
		this.model.setColumnNames( "foo", "bar", "eggs" );
		this.model.setColumnNotEditable( 1 );
		return this.model;
	}

	/* build up new example object */
	private MyDisplayableObject buildExampleObject() {
		float a = rand(10.0f, 100.0f);
		float b = rand(10.0f, 100.0f);
		int c = rand(10, 100);
		return new MyDisplayableObject(a,b,c);
	}

	/* returns random number */
    private int rand(int a, int b){
		return new Random( System.currentTimeMillis() ).nextInt( b ) + a;
	}

	/* returns random number */
	private float rand(float a, float b){
		return (new Random( System.currentTimeMillis() ).nextFloat() * b) + a;
	}

	/* return the point when the frame need to be painted to make in the center
	 * of the screen */
	private Point center( int w, int h ){
		int wt = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
		int ht = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
		return new Point( wt - (w/ 2), ht - (h/2) );
	}

//==============================================================================
// INNER CLASS
//==============================================================================
	/* Simple Object for example purpose */
	private class MyDisplayableObject implements JXDisplayable
	{
		float a,b; int c;
		public MyDisplayableObject( float a, float b, int c ){
			this.a = a;
			this.b = b;
			this.c = c;
		}

		@Override
		public Object[] getDisplayRaw() {
			return new Object[]{this.a, this.b, this.c};
		}
	}

	/* Inner class that describe the event of row selection from the Table. */
	private class TableSelectionListener implements ListSelectionListener
	{
		private JXTable t;
		public TableSelectionListener(JXTable t){ this.t = t; }
		@Override
		public void valueChanged(ListSelectionEvent e){
			int viewRow = t.getSelectedModelRow();
			if(viewRow != -1)
				txtSelectedRow.setText( String.valueOf( viewRow ) );
		}
	}

	/* Event handle of button btnClear. */
	private class ClearAllActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			if(model.getRowCount() != 0)
				model.removeAll();
			txtSelectedRow.setText("");
		}
	}

	/* Event handle of button btnAddRow. */
	private class AddRowActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
            model.addObject( buildExampleObject() );
        }
	}

	/* Event handle of button btnDelRow. */
	private class DelRowActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			String text = txtSelectedRow.getText();
			if(text != null && !text.isEmpty()) {
				model.removeObject( Integer.parseInt( text ) );
				txtSelectedRow.setText( "" );
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
					TestJXTableWithObjectModel frame = new TestJXTableWithObjectModel();
					frame.setVisible( true );
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} );
	}
}
