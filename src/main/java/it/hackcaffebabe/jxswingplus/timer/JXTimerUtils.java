package it.hackcaffebabe.jxswingplus.timer;

/**
 * This class provide the common operation on time usefully for {@link JXTimer}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXTimerUtils
{
	/**
	 * This method returns a formatted time with pattern hh:mm:ss.<br>
	 * @param millisecond {@link Long} millisecond representing a time.
	 * @return {@link String} formatted as hh:mm:ss
	 * @throws IllegalArgumentException if millisecond is less than zero.
	 */
	public static String getFormattedTime(long millisecond) throws IllegalArgumentException{
		Long s = JXTimerUtils.getSecondsFromMillisecond( millisecond );
		Long seconds = s % 60;
		Long m = s / 60;
		Long minutes = m % 60;
		Long hours = m / 60;

		return String.format( "%2s%s%2s%s%2s", 
				(hours.toString().length() == 1 ? "0" + hours.toString() : hours.toString()), ":", 
				(minutes.toString().length() == 1 ? "0" + minutes.toString() : minutes.toString()), ":", 
				(seconds.toString().length() == 1 ? "0" + seconds.toString() : seconds.toString()) );
	}

	/**
	 * This method returns the number of Seconds in a long time format.<br>
	 * @param milliseconds {@link Long} long number representing a time
	 * @return {@link Long} number of Seconds from millisecond given.
	 * @throws IllegalArgumentException {@link Exception} if argument given is less of zero 
	 */
	public static long getSecondsFromMillisecond(long milliseconds) throws IllegalArgumentException{
		if(milliseconds < 0)
			throw new IllegalArgumentException( "Milliseconds given can not be less of zero." );

		return milliseconds / 1000;
	}

	/**
	 * This method returns the number of Minutes in a long time format.<br>
	 * @param milliseconds {@link Long} in long format.
	 * @return {@link Long} number of Minutes in a millisecond given.
	 * @throws IllegalArgumentException {@link Exception} if argument given is less of zero.
	 */
	public static long getMinutesFromMilliseconds(long milliseconds) throws IllegalArgumentException{
		if(milliseconds < 0)
			throw new IllegalArgumentException( "Milliseconds given can not be less of zero." );

		return JXTimerUtils.getSecondsFromMillisecond( milliseconds ) / 60;
	}

	/**
	 * This method returns the number of hours in a long time format.<br>
	 * @param milliseconds {@link Long} in long format.
	 * @return {@link Long} number of Hours in a millisecond given.
	 * @throws IllegalArgumentException {@link Exception} if argument given is less of zero.
	 */
	public static long getHoursFromMilliseconds(long milliseconds) throws IllegalArgumentException{
		if(milliseconds < 0)
			throw new IllegalArgumentException( "Milliseconds given can not be less of zero." );

		return JXTimerUtils.getSecondsFromMillisecond( milliseconds ) / 60 * 60;
	}

	/**
	 * This method converts the seconds in milliseconds.<br>
	 * @param seconds {@link Integer} to convert in millisecond.
	 * @return {@link Long} number representing the milliseconds of seconds given.
	 * @throws IllegalArgumentException {@link Exception} if argument given is less of zero.
	 */
	public static long getMillisecondFromSeconds(int seconds) throws IllegalArgumentException{
		if(seconds < 0)
			throw new IllegalArgumentException( "Second given can not be less of zero." );

		return seconds * 1000;
	}

	/**
	 * This method converts the minutes in milliseconds.<br>
	 * @param minutes {@link Integer} to convert in millisecond.
	 * @return {@link Long} number representing the milliseconds of minutes given.
	 * @throws IllegalArgumentException {@link Exception} if argument given is less of zero.
	 */
	public static long getMillisecondFromMinutes(int minutes) throws IllegalArgumentException{
		if(minutes < 0)
			throw new IllegalArgumentException( "Minutes given can not be less of zero." );

		return minutes * 1000 * 60;
	}

	/**
	 * This method converts the hours in milliseconds.<br>
	 * @param hours {@link Integer} to convert in milliseconds.
	 * @return {@link Long} number representing the milliseconds of hours given.
	 * @throws IllegalArgumentException {@link Exception} if argument given is less of zero.
	 */
	public static long getMillisecondFromHours(int hours) throws IllegalArgumentException{
		if(hours < 0)
			throw new IllegalArgumentException( "Hours given can not be less of zero." );

		return hours * 1000 * 60 * 60;
	}
}
