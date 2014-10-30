package it.hackcaffebabe.jxswingplus.statusbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Test {@link it.hackcaffebabe.jxswingplus.statusbar.JXStatusBar}
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestJXStatusBar extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final Dimension size = new Dimension( 790, 490 );

    /** Create the frame. */
	public TestJXStatusBar(){
		super( "Test JXStatusBar" );
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

        JXStatusBar statusBar = new JXStatusBar(this);
		statusBar.setTitle("title");
		statusBar.setStatus("status");
		statusBar.setTextFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		add(statusBar, BorderLayout.SOUTH );
	}
	
//==============================================================================
// MAIN
//==============================================================================
	public static void main(String...args){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				new TestJXStatusBar().setVisible( true );
			}
		} );
	}
}
