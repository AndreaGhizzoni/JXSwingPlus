package it.hackcaffebabe.jxswingplus.table.standard;

import it.hackcaffebabe.jxswingplus.table.JXTable;
import it.hackcaffebabe.jxswingplus.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jxswingplus.table.model.JXTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * TODO there must be some bug with Adjuster
 *
 * Simple Class to test {@link it.hackcaffebabe.jxswingplus.table.JXTable} and
 * {@link it.hackcaffebabe.jxswingplus.table.model.JXTableModel}.
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestJXTableModelWithColumnAdjuster extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final Dimension size = new Dimension( 790, 490 );

	private JPanel contentPane = new JPanel();
	private JXTable table;
    private JXTableColumnAdjuster adjuster;

    private Random random = new Random(System.currentTimeMillis());

	/** Create the frame. */
	public TestJXTableModelWithColumnAdjuster(){
		super( "Test JXTable with JXTableModel and RowSorter" );
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
		setLocation( (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.size.width / 2), 
				(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.size.height / 2) );

		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new MigLayout( "", "[118.00][142.00,grow][grow]", "[336.00,grow][36.00]" ) );

		this.table = new JXTable( this.getModel() );
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// selection mode

        this.adjuster = new JXTableColumnAdjuster(this.table);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView( this.table );
		contentPane.add( scrollPane, "cell 0 0 3 1,grow" );

        JButton btnAdjust = new JButton("Adjust Columns");
        btnAdjust.addActionListener(new AdjustActionListener());
        contentPane.add(btnAdjust, "cell 0 1 2 1,growx");

		setContentPane( contentPane );
	}

//==============================================================================
// GETTER
//==============================================================================
    /* random number generator */
    private float rand(float a, float b){
		return (this.random.nextFloat()*b) + a;
	}

    /* This method initialize the contact table model. */
    private JXTableModel getModel(){
        //column names
        String[] c = {"asd", "foo", "bar"};
        //data model
        int rows = 10;
        Object[][] data = new Object[rows][c.length];

        for(int i=0; i<rows; i++){
            for(int j=0; j<c.length; j++){
                float tmp = rand(10.0f,100.0f);
                data[i][j] = tmp;
            }
        }

        JXTableModel model = new JXTableModel();
        model.setData(c,null,data);
        return model;
    }

//==============================================================================
// INNER CLASS
//==============================================================================
    /* action listener from button bntAdjust */
    class AdjustActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            adjuster.adjustColumns();
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
					TestJXTableModelWithColumnAdjuster frame = new TestJXTableModelWithColumnAdjuster();
					frame.setVisible( true );
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		} );
	}
}
