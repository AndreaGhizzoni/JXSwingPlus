package it.hackcaffebabe.jxswingplus.statusbar;

import java.awt.*;
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
		setLocation(center(this.size.width, this.size.height));

        JXStatusBar statusBar = new JXStatusBar(this);
		statusBar.setTitle("title");
		statusBar.setStatus("status");
		statusBar.setTextFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		add(statusBar, BorderLayout.SOUTH );
	}

	/* return the point when the frame need to be painted to make in the center
	 * of the screen */
	private Point center( int w, int h ){
		int wt = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
		int ht = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
		return new Point( wt - (w/ 2), ht - (h/2) );
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
