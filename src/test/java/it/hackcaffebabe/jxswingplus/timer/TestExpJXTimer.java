package it.hackcaffebabe.jxswingplus.timer;

import it.hackcaffebabe.jxswingplus.timer.exp.JXTimerComponent;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import it.hackcaffebabe.jxswingplus.timer.exp.JXTimer;


/**
 * Simple class to test //TODO add link to class
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestExpJXTimer extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final Dimension size = new Dimension( 790, 490 );
	private JXTimer timer = new JXTimer();

	private JPanel contentPane = new JPanel();
	private JPanel pnlTimer = new JPanel();
	private final JTextField txtTimeToStart = new JTextField();
	private final JButton btnSetTimeToStart = new JButton( "set Time to Start" );
	private final JButton btnAddTimer = new JButton( "Add Timer" );
	private final JButton btnStart = new JButton( "Start" );
	private final JButton btnPause = new JButton( "Pause" );
	private final JButton btnStop = new JButton( "Stop" );

	/** Create the frame. */
	public TestExpJXTimer(){
		super( "Test M2_JXTimer" );
		this.initGUI();

		// set the event when time is up
		timer.setActionWhenTimeIsUp( new MyTimeUpEvent() );
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

		contentPane.setLayout( new MigLayout( "", "[grow]", "[354.00,grow][61.00]" ) );

		this.pnlTimer.setBorder( new TitledBorder( null, "Timers",
				TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		this.pnlTimer.setLayout( new GridLayout( 10, 5, 10, 10 ) );
		contentPane.add( pnlTimer, "cell 0 0,grow" );

		JPanel pnlOption = new JPanel();
		pnlOption.setBorder( new TitledBorder( null, "Options",
				TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		pnlOption.setLayout( new MigLayout( "", "[128.00][253.00][][grow]", "[]" ) );

		this.btnAddTimer.addActionListener( new AddTimerActionListener() );
		pnlOption.add( this.btnAddTimer, "cell 0 0" );

		this.txtTimeToStart.setEnabled( false );
		pnlOption.add( this.txtTimeToStart, "flowx,cell 1 0,growx" );

		pnlOption.add( new JLabel( "ms" ), "cell 1 0" );

		this.btnSetTimeToStart.addActionListener( new SetTimeToStartActionListener() );
		this.btnSetTimeToStart.setEnabled( false );
		pnlOption.add( this.btnSetTimeToStart, "cell 1 0" );

		this.btnStart.setEnabled( false );
		this.btnStart.addActionListener( new StartActionListener() );
		pnlOption.add( this.btnStart, "flowx,cell 3 0,growx" );

		this.btnPause.setEnabled( false );
		this.btnPause.addActionListener( new PauseActionListener() );
		pnlOption.add( this.btnPause, "cell 3 0,growx" );

		this.btnStop.setEnabled( false );
		this.btnStop.addActionListener( new StopActionListener() );
		pnlOption.add( this.btnStop, "cell 3 0,growx" );

		contentPane.add( pnlOption, "cell 0 1,grow" );
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
// INNER CLASS
//==============================================================================
	/* Event handle of button AddTimer. */
	private class AddTimerActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			JXTimerComponent l = new JXTimerComponent();
			l.setBorder( LineBorder.createGrayLineBorder() );

			pnlTimer.add( l );
			timer.addClock( l );
			pnlTimer.revalidate();

			btnSetTimeToStart.setEnabled( true );
			txtTimeToStart.setEnabled( true );
		}
	}

	/* Simple event when time is up */
	private class MyTimeUpEvent implements Runnable
	{
		@Override
		public void run(){
			JOptionPane.showMessageDialog( pnlTimer, "Time is up!", "Time up!",
					JOptionPane.INFORMATION_MESSAGE );

			btnStart.setEnabled( false );
			btnPause.setEnabled( false );
			btnStop.setEnabled( false );
			txtTimeToStart.setEnabled( true );
			btnSetTimeToStart.setEnabled( true );
		}
	}

	/* Event handle of button btnSetTimeToStart. */
	private class SetTimeToStartActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			Long i = null;
			try {
				String time = txtTimeToStart.getText();
				i = Long.parseLong( time );

				timer.setStartingTime( i );

				btnAddTimer.setEnabled( false );
				btnStart.setEnabled( true );
				txtTimeToStart.setEnabled( false );
				btnSetTimeToStart.setEnabled( false );

			}
			catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog( pnlTimer, i + " is not a valid " +
								"number. Pleas insert a millisecond as a number.",
                                "Error",JOptionPane.ERROR_MESSAGE );
			}
		}
	}

	/* Event handle of button btnStart. */
	private class StartActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			try {
				timer.start();

				btnStart.setEnabled( false );
				btnPause.setEnabled( true );
				btnStop.setEnabled( true );
			}
			catch(IllegalStateException e1) {
				e1.printStackTrace();
			}
		}
	}

	/* Event handle of button btnPause. */
	private class PauseActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			timer.pause();

			btnStart.setEnabled( true );
			btnPause.setEnabled( false );
		}
	}

	/* Event handle of button btnStop. */
	private class StopActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			timer.stop();

			btnStart.setEnabled( false );
			btnPause.setEnabled( false );
			btnStop.setEnabled( false );
			txtTimeToStart.setEnabled( true );
			btnSetTimeToStart.setEnabled( true );
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
					new TestExpJXTimer().setVisible( true );
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		} );
	}
}
