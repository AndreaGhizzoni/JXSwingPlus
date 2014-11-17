package it.hackcaffebabe.jxswingplus.timer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple timer that displays the time in one or more
 * {@link JXTimerComponent}. To use this
 * kind of timer you need to initialize that with a time to start in milliseconds:
 * <pre>{@code
 * JXTimer myTimer = new JXTimer( 1000 * n );
 * }</pre>
 * Now let sets the {@link JXTimerComponent}
 * that displays the time.
 * <pre>{@code
 * myTimer.setClocks( Arrays.asList( cmp1, cmp2 ) );
 * myTimer.addClock( cmp1 );
 * }</pre>
 * Optionally you can set an {@link java.lang.Runnable} event to run when the
 * time is up:
 * <pre>{@code
 * myTimer.setActionWhenTimeIsUp( myFantasticEvent );
 * }</pre>
 * Note that there is a constructor method to specify all the previous parameter.
 * Now you can use the control method to start, stop and pause the timer:
 * <pre>{@code
 * myTimer.start() // to start the count down.
 * myTimer.pause() // to pause the count down.
 * myTimer.stop()  // to stop and reset the count down.
 * }</pre>
 * While you using this method you probably need to know witch state the
 * {@link JXTimer} is. To control this you
 * can use this method that returns a
 * {@link it.hackcaffebabe.jxswingplus.timer.JXTimerState}:
 * <pre>{@code
 * myTimer.getState()
 * }</pre>
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class JXTimer
{
	private Timer timer;
	private TimerActionListener timerActionListener = new TimerActionListener();
	private static final int TIMER_PERIOD = 1000;// 1000 milliseconds

	private JXTimerState state = JXTimerState.STOPPED;
	private List<JXTimerComponent> components = new ArrayList<>();

	/**
	 * Instance a JXTimer with time to start, time up event and list of
	 * JXTimerComponents to display the timer.
	 * @param msToStart long time to start in milliseconds.
	 * @param eventTimeUp {@link Runnable} the event when time is up.
	 * @param comp array of {@link JXTimerComponent}.
	 */
    public JXTimer( long msToStart, Runnable eventTimeUp, JXTimerComponent... comp ){
		this.setStartingTime(msToStart);
		this.setActionWhenTimeIsUp(eventTimeUp);
		this.setClocks(Arrays.asList(comp));
	}

	/**
	 * Instance a JXTimer with time to start.
	 * @param msToStart long the time to start in milliseconds.
	 */
	public JXTimer( long msToStart ){
		this(msToStart, null, new JXTimerComponent[]{});
	}

	/**
	 * Instance a JXTimer with time to start and the time up event.
	 * @param msToStart long the time to start in milliseconds.
	 * @param eventTimeUp {@link Runnable} the event when time is up.
	 */
	public JXTimer( long msToStart, Runnable eventTimeUp ){
		this(msToStart,eventTimeUp, new JXTimerComponent[]{});
	}

	/**
	 * Instance an empty JXTimer. To start this timer needs to be set the
	 * starting time.
	 */
	public JXTimer(){}

//==============================================================================
// METHOD
//==============================================================================
	/**
	 * This method starts the countdown
	 * @throws IllegalStateException {@link Exception} if timer is still running
	 */
	public void start() throws IllegalStateException{
		if(this.state == JXTimerState.RUNNING)
			throw new IllegalStateException( "You can not start the timer " +
					"because is still running." );

		this.timer = new Timer( JXTimer.TIMER_PERIOD, this.timerActionListener );
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
			throw new IllegalStateException( "You can not stop the timer because " +
					"is not running." );

		this.timer.stop();
		this.timer = null;// necessary because timer.stop() means pause the timer.
		this.timerActionListener.setCurrentTime(0L);

		this.state = JXTimerState.STOPPED;
	}

	/**
	 * This method pause the countdown
	 * @throws IllegalStateException if timer is not running
	 */
	public void pause() throws IllegalStateException{
		if(this.state != JXTimerState.RUNNING)
			throw new IllegalStateException( "You can not pause the timer " +
					"because is not running." );

		this.timer.stop();
		this.state = JXTimerState.PAUSED;
	}

//==============================================================================
// SETTER
//==============================================================================
	/**
	 * This method adds a {@link JXTimerComponent} to display the countdown.
	 * @param tComp {@link JXTimerComponent} that will be added.
	 * @throws IllegalArgumentException if timer component given is null.
	 * @throws IllegalStateException if times is running.
	 */
	public void addClock(JXTimerComponent tComp) throws IllegalArgumentException,
			IllegalStateException{
		if(this.state != JXTimerState.STOPPED)
			throw new IllegalStateException("You can not add timer component to " +
					"display the countdown while the timer is still running." );
		if(tComp == null)
			throw new IllegalArgumentException( "Timer component can not be null." );

		tComp.setTime(this.timerActionListener.currentTime);
		this.components.add( tComp );
	}

	/**
	 * This method set the component/s to display the countdown
	 * @param tComp {@link List} as list {@link javax.swing.JLabel}.
	 * @throws IllegalArgumentException if at least one object in the list is null.
	 * @throws IllegalStateException if times is still running.
	 */
	public void setClocks(List<JXTimerComponent> tComp) throws IllegalArgumentException,
			IllegalStateException{
		if(this.state != JXTimerState.STOPPED)
			throw new IllegalStateException( "You can not add timer component to display " +
					"the countdown while the timer is still running." );
		if(tComp == null || tComp.size() == 0)
			return;
		for(JXTimerComponent l : tComp)
			this.addClock(l);
	}

	/**
	 * This method change the time to start the countdown
	 * @param time {@link Long} to start
	 * @throws IllegalArgumentException if the countdown is still running.
	 * @throws IllegalStateException if times is still running.
	 */
	public void setStartingTime(long time) throws IllegalArgumentException,
			IllegalStateException{
		if(this.state != JXTimerState.STOPPED)
			throw new IllegalStateException("You can not change the time to " +
					"start while the countdown is still running." );
		if(time <= 0)
			return;
        this.timerActionListener.setCurrentTime(time);
	}

	/**
	 * This method set the action to execute when timer is up. Set this to null
	 * to cancel the event.
	 * @param event {@link Runnable} to run when time is up.
	 */
	public void setActionWhenTimeIsUp(Runnable event){
        this.timerActionListener.timeUpEvent = event;
	}

//==============================================================================
// GETTER
//==============================================================================
	/** @return {@link List} of JXTimerComponent added to display the timer. */
	public List<JXTimerComponent> getClocks(){
		return this.components;
	}

	/** @return {@link JXTimerState} the state of timer */
	public JXTimerState getState(){
		return this.state;
	}

//==============================================================================
// INNER CLASS
//==============================================================================
	/* class that describe the action of timer each second. */
	private class TimerActionListener implements ActionListener
	{
		private long currentTime = 0L;
		private Runnable timeUpEvent;

		void setCurrentTime(long currentTime){
			this.currentTime = currentTime;
			this.update();
		}

		void update(){
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
                    for( JXTimerComponent i : components )
                        i.setTime(currentTime);
				}
			});
		}

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
				this.update();
			}
		}

		/* this method create the countdown, necessary to the timer. */
		boolean countdown(){
			this.currentTime -= TIMER_PERIOD;
			// c = 0 if currentTime == 0, c < 0 if currentTime < 0
			int c = Long.compare(this.currentTime, 0L);
			return ( c < 0 );
		}
	}
}
