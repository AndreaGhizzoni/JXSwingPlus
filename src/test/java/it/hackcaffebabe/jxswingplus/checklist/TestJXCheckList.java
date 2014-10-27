package it.hackcaffebabe.jxswingplus.checklist;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Test {@link JXCheckList}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestJXCheckList extends JFrame
{
	private static final long serialVersionUID = 1L;

	/** Create the frame */
	public TestJXCheckList(){
		super( "Test JXCheckList" );
		this.initGUI();
	}

//==============================================================================
// METHOD
//==============================================================================
	/* Initialize all components */
	private void initGUI(){
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setBounds( 100, 100, 450, 300 );
		getContentPane().setLayout( new MigLayout( "", "[grow][grow]", "[grow][grow]" ) );

		final JXCheckList<Integer> lstCheck = new JXCheckList<Integer>();

		//=== SECOND WAY
//		List<Integer> lst = Arrays.asList( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 );
//		DefaultListModel<JXCheckListEntry<Integer>> model = JXCheckList.convertToModel( lst, true );
//		lstCheck.setModel( model );

//		//=== FIRST WAY
		ActionListener evt = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println( lstCheck.getCheckedIdexes() );
				System.out.println( lstCheck.getCheckedObjects().toString() );
			}
		};
		DefaultListModel<JXCheckListEntry<Integer>> model = new DefaultListModel<JXCheckListEntry<Integer>>();
		for(int i = 0; i < 100; i++) {
			JXCheckListEntry<Integer> xentry = new JXCheckListEntry<Integer>( i, false );
			xentry.addActionListener( evt );
			model.addElement( xentry );
		}
		lstCheck.setModel( model );

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView( lstCheck );
		getContentPane().add( scrollPane, "cell 0 0 2 1,grow" );
	}

//===========================================================================================
// MAIN
//===========================================================================================
	public static void main(String[] args){
		EventQueue.invokeLater( new Runnable(){
			public void run(){
				new TestJXCheckList().setVisible( true );
			}
		} );
	}
}
