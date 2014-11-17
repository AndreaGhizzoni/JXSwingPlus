package it.hackcaffebabe.jxswingplus.timer.exp;

import javax.swing.*;
import static it.hackcaffebabe.jxswingplus.timer.exp.JXTimerUtils.*;

/**
 * This is a swing component to display the timer from
 * {@link it.hackcaffebabe.jxswingplus.timer.exp.JXTimer}. This is used as a
 * simple JLabel, but, instead to use <code>setText("sometext")</code> there is
 * a package method to display the time on this component.
 */
public class JXTimerComponent extends JLabel
{
    private String formattedTime;

    /**
     * Instance the JXTimerComponent. By default this will display the string
     * "00:00:00" if time to start of JXTimer is not set, otherwise the time to
     * start with the same pattern.
     */
    public JXTimerComponent(){
        super();
        this.setTime(0L);
        this.formattedTime = "00:00:00";
    }

    /* package method used in JXTimer */
    void setTime(long milliseconds){
        this.formattedTime = getFormattedTime(milliseconds);
        super.setText( this.formattedTime );
    }

    @Override
    public void setText(String text){}

    @Override
    public String getText(){
        return this.formattedTime;
    }
}
