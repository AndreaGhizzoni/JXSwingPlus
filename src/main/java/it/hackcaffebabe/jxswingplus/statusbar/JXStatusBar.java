package it.hackcaffebabe.jxswingplus.statusbar;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;


/**
 * Create an horizontal status panel to put on the bottom or on top of the {@link JFrame} using:
 * <pre>{@code
 * JFrame frame = new JFrame();
 * ... 
 * JXStatusBar emptyStatusBar = new JXStatusBar( frame );
 * JXStatusBar statusBarWithString = new JXStatusBar( frame, "MyProgram", "Start", 16 );
 * frame.add(s, BorderLayout.SOUTH);
 * }</pre>
 * It's possible to change the {@link Font} and the text displayed by:<br>
 * <pre>{@code
 * statusBarWithString.setTextFont( new Font( Font.MONOSPACED, Font.PLAIN, 11 ) );
 * statusBarWithString.setTitle( "title" );
 * statusBarWithString.setStatus( "status" );
 * }</pre>
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXStatusBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JFrame content;

	private JLabel lblStatus = new JLabel();
	private JLabel lblTitle = new JLabel();

	/**
	 * Instance an empty status bar.
	 * @param content {@link JFrame} the content that JXStatusBar is display.
	 * @throws NullPointerException if content is null.
	 */
	public JXStatusBar(JFrame content) throws NullPointerException{
		this( content, "", "", 16 );
	}

	/**
	 * Create an horizontal status panel.
	 * @param content {@link JFrame} the content that JXStatusBar is display.
	 * @param status {@link String} that is displayed on the left of the status bar. Pass null to hidden it.
	 * @param title {@link String} that is displayed on the tight of the status bar. Pass null to hidden it.
	 * @param height {@link Integer} height must be in rage (16, Integer.MAX_VALUE) otherwise it will be set to 16
	 * @throws NullPointerException if content is null.
	 */
	public JXStatusBar(JFrame content, String status, String title, int height) throws NullPointerException{
		super();
		this.setContent( content );
		setBorder( new BevelBorder( BevelBorder.RAISED ) );
		setPreferredSize( new Dimension( this.content.getWidth(), (height > Integer.MAX_VALUE || height < 16 ? 16
				: height) ) );
		setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		this.init();
		this.setStatus( status );
		this.setTitle( title );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* Initialize all components */
	private void init(){
		this.lblTitle.setHorizontalAlignment( SwingConstants.LEFT );
		this.lblTitle.setPreferredSize( new Dimension( getPreferredSize().width / 3, getPreferredSize().height ) );
		add( this.lblTitle );

//		add( Box.createHorizontalGlue() );// separate on the left the label status and on the fight label title. 
		add( new JSeparator( JSeparator.VERTICAL ) );

		this.lblStatus.setHorizontalAlignment( SwingConstants.RIGHT );
		this.lblStatus.setPreferredSize( new Dimension( getPreferredSize().width / 2, getPreferredSize().height ) );
		add( this.lblStatus );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * This method set the font of the two label.
	 * @param f {@link Font}. If font is null nothing change.
	 */
	public void setTextFont(Font f){
		if(f == null)
			return;
		this.lblStatus.setFont( f );
		this.lblTitle.setFont( f );
	}

	/**
	 * Set the title of status bar. Pass null to doesn't display title label.
	 * @param title {@link String}.
	 */
	public void setTitle(String title){
		if(title == null)
			this.lblTitle.setText( "" );
		else this.lblTitle.setText( title );
	}

	/**
	 * Set the status label of status bar. Pass null to doesn't display status label.
	 * @param status {@link JXStatusBar}.
	 */
	public void setStatus(String status){
		if(status == null)
			this.lblStatus.setText( "" );
		else this.lblStatus.setText( status );
	}

	/* set the content */
	private void setContent(JFrame content) throws NullPointerException{
		if(content == null)
			throw new NullPointerException( "Content can not be null." );
		this.content = content;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link String} represent the title displayed on status bar */
	public String getTitle(){
		return this.lblTitle.getText();
	}

	/** @return {@link String} represents the status displayed on status bar */
	public String getStatus(){
		return this.lblStatus.getText();
	}
}
