package it.hackcaffebabe.jxswingplus.timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.Timer;


/**
 * Simple timer that displays the time in a {@link JLabel}.
 * To use this kind of timer you need to initialize that with a time to start in milliseconds.
 * <pre>{@code
 * JXTimer myTimer = new JXTimer( 1000 * n );
 * }</pre>
 * Now let sets the {@link JLabel} ( or list ) that displays the time.
 * <pre>{@code
 * myTimer.setLabels( Arrays.asList( label1, label2 ) );
 * myTimer.addLabel( myBeautifullLabel );
 * }</pre>
 * Optionally you can set an {@link Runnable} event to run when the time is up:
 * <pre>{@code
 * myTimer.setActionWhenTimeIsUp( myFantasticEvent );
 * }</pre>
 * Now you can use the control method to start, stop and pause the timer:{@code
 * myTimer.start() // to start the count down and each second will be displayed the updated time.
 * myTimer.pause() // to pause the count down.
 * myTimer.stop()  // to stop and reset the count down.
 * }
 * While you using this method you probably need to know witch state the {@link JXTimer} is.
 * To control this you can use this method that returns a {@link JXTimerState}:
 * <pre>{@code
 * myTimer.getState()
 * }</pre>
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXTimer
{
	private static final int TIMER_PERIOD = 1000;

	private JXTimerState state = JXTimerState.STOPPED;
	private long currentTime = 0L;

	private Timer timer;
	private Runnable timeUpEvent;

	private List<JLabel> components = new ArrayList<JLabel>();

	/**
	 * Instance an empty timer.<br>
	 * Use: <br>
	 * 	-<code> setTimeToStart( 1000 * n )</code>, where n is the number of seconds, to set the start timer.<br>
	 * 	-<code> addLabel( JLabel label )</code> or <code>setLabels( JLabel label )</code> to set the {@link JLabel} witch display the timer.<br>
	 * 	-<code> setActionWhenTimeIsUp( Runnable event )</code> to set a {@link Runnable} event when time is up.<br>
	 * Finally use the control method to start, stop and pause the timer.<br>
	 */
	public JXTimer(){}

	/**
	 * Instance a timer with start time given.<br>
	 * Use: <br>
	 * 	-<code> addLabel( JLabel label )</code> or <code>setLabels( JLabel label )</code> to set the {@link JLabel} witch display the timer.<br>
	 * 	-<code> setActionWhenTimeIsUp( Runnable event )</code> to set a {@link Runnable} event when time is up.<br>
	 * @param startingTime {@link Long} time in milliseconds.
	 * @throws IllegalArgumentException {@link Exception} if some argument are null.
	 */
	public JXTimer(long startingTime) throws IllegalArgumentException{
		this.setStartingTime( startingTime );
	}

	/**
	 * Instance a Timer that display the countdown on a list of {@link JLabel}.
	 * 	-<code> setActionWhenTimeIsUp( Runnable event )</code> to set a {@link Runnable} event when time is up.<br>
	 * @param startingTime {@link Long} time in milliseconds.
	 * @param components {@link List} as list to display the countdown.
	 * @throws IllegalArgumentException {@link Exception} if some argument are null.
	 */
	public JXTimer(long startingTime, List<JLabel> components) throws IllegalArgumentException{
		this.setStartingTime( startingTime );
		this.setLabels( components );
		this.displayFormattedTime( JXTimerUtils.getFormattedTime( this.currentTime ) );
	}

	/**
	 * JTime display the countdown on a Swing Component with a specific event on time's up.
	 * @param timeToStart {@link Long} to start in milliseconds.
	 * @param components {@link List}as list to display the countdown.
	 * @param event {@link Runnable} event to execute when time is up.
	 * @throws IllegalArgumentException {@link Exception} if some argument are null.
	 */
	public JXTimer(long timeToStart, List<JLabel> components, Runnable event) throws IllegalArgumentException{
		this.setStartingTime( timeToStart );
		this.setLabels( components );
		this.displayFormattedTime( JXTimerUtils.getFormattedTime( this.currentTime ) );
		this.setActionWhenTimeIsUp( event );
	}

//====================================================================================================//
// METHOD
//====================================================================================================//
	/* This method display the formatted time on the components of the list */
	private void displayFormattedTime(String formattedTime){
		for(JLabel l: this.components)
			if(l != null)
				l.setText( formattedTime );
	}

	/**
	 * This method starts the countdown
	 * @throws IllegalStateException {@link Exception} if timer is still running
	 */
	public void start() throws IllegalStateException{
		if(this.state == JXTimerState.RUNNING)
			throw new IllegalStateException( "You can not start the timer because is still running." );

		this.timer = new Timer( JXTimer.TIMER_PERIOD, new TimerActionListener() );
		this.timer.setRepeats( true );
		this.timer.start();

		this.state = JXTimerState.RUNNING;
	}

	/**
	 * This method stop and restart the countdown
	 * @throws IllegalStateException if timer is not running 
	 */
	public void stop() throws IllegalStateException{
		if(this.state != JXTimerState.RUNNING)
			throw new IllegalStateException( "You can not stop the timer because is not running." );

		this.timer.stop();
		this.timer = null;// necessary because timer.stop() means pause the timer.
		this.currentTime = 0L;
		this.displayFormattedTime( "00:00:00" );

		this.state = JXTimerState.STOPPED;
	}

	/**
	 * This method pause the countdown
	 * @throws IllegalStateException if timer is not running 
	 */
	public void pause() throws IllegalStateException{
		if(this.state != JXTimerState.RUNNING)
			throw new IllegalStateException( "You can not pause the timer because is not running." );

		this.timer.stop();
		this.state = JXTimerState.PAUSED;
	}

//====================================================================================================//
// SETTER
//====================================================================================================//
	/**
	 * This method adds a {@link JLabel} to display the countdown.
	 * @param label {@link JLabel} that will be added.
	 * @throws IllegalArgumentException {@link Exception} if label given is null.
	 * @throws IllegalStateException {@link Exception} if times is still running.
	 */
	public void addLabel(JLabel label) throws IllegalArgumentException, IllegalStateException{
		if(this.state != JXTimerState.STOPPED)
			throw new IllegalStateException(
					"You can not add label to display the countdown while the timer is still running." );

		if(label == null)
			throw new IllegalArgumentException( "Label can not be null." );

		this.components.add( label );
	}

	/**
	 * This method set the component/s to display the countdown
	 * @param labels {@link List} as list {@link JLabel}.
	 * @throws IllegalArgumentException {@link Exception} if argument given is null or list size = 0.
	 * @throws IllegalStateException {@link Exception} if times is still running.
	 */
	public void setLabels(List<JLabel> labels) throws IllegalArgumentException, IllegalStateException{
		if(this.state != JXTimerState.STOPPED)
			throw new IllegalStateException(
					"You can not add label to display the countdown while the timer is still running." );

		if(labels == null || labels.size() == 0)
			throw new IllegalArgumentException( "List of Labels can not be null or empty list." );

		this.components = labels;
	}

	/**
	 * This method change the time to start the countdown
	 * @param time {@link Long} to start
	 * @throws IllegalArgumentException if the countdown is still running.
	 * @throws IllegalStateException if times is still running.
	 */
	public void setStartingTime(long time) throws IllegalArgumentException, IllegalStateException{
		if(this.state != JXTimerState.STOPPED)
			throw new IllegalStateException(
					"You can not change the time to start while the countdown is still running." );

		if(time < 0)
			throw new IllegalArgumentException( "Time can not be less of zero." );

		this.currentTime = time;
		this.displayFormattedTime( JXTimerUtils.getFormattedTime( this.currentTime ) );
	}

	/**
	 * This method set the action to execute when timer is up.<br>
	 * Set this to null to cancel the event.
	 * @param event {@link Runnable} to run when time is up. 
	 */
	public void setActionWhenTimeIsUp(Runnable event){
		this.timeUpEvent = event;
	}

//====================================================================================================//
// GETTER
//====================================================================================================//
	/**
	 * Returns the list of JLabel witch displays the JXTimer.
	 * @return {@link List} of JLabel. If there is no labels the list will be void.
	 */
	public List<JLabel> getLabels(){
		return this.components;
	}

	/**
	 * This method returns the state of timer.<br>
	 * @see JXTimerState for more information.<br>
	 * @return {@link JXTimerState} the state of timer.
	 */
	public JXTimerState getState(){
		return this.state;
	}

//====================================================================================================//
// INNER CLASS
//====================================================================================================//
	/* class that describe the action of timer each second. */
	private class TimerActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			//if countdown returns true, it's means that time is up
			if(countdown()) {
				timer.stop();
				timer = null;// necessary because timer.stop() means pause the timer.
				currentTime = 0L;

				if(timeUpEvent != null)
					timeUpEvent.run();

				state = JXTimerState.STOPPED;
			} else {
				displayFormattedTime( JXTimerUtils.getFormattedTime( currentTime ) );
			}
		}

		/* this method create the countdown, necessary to the timer. */
		private boolean countdown(){
			String formatted = JXTimerUtils.getFormattedTime( currentTime );

			StringTokenizer token = new StringTokenizer( formatted, ":" );
			Integer h = Integer.parseInt( token.nextToken() );
			Integer m = Integer.parseInt( token.nextToken() );
			Integer s = Integer.parseInt( token.nextToken() );

			if(s != 0) {
				s--;
			} else {
				if(m != 0) {
					m--;
					s = 59;
				} else {
					if(h != 0) {
						h--;
						m = 59;
						s = 59;
					} else {
						return true;
					}
				}
			}

			Long seconds = JXTimerUtils.getMillisecondFromSeconds( s );
			Long minutes = JXTimerUtils.getMillisecondFromMinutes( m );
			Long hours = JXTimerUtils.getMillisecondFromHours( h );

			currentTime = seconds + minutes + hours;

			return false;
		}
	}
}
