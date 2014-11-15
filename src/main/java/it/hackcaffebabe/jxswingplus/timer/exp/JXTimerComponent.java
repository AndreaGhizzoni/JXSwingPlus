package it.hackcaffebabe.jxswingplus.timer.exp;

import javax.swing.*;

public class JXTimerComponent extends JLabel {
    private long milliseconds;

    public JXTimerComponent(){
        super();
        this.setTime(0L);
    }

    /* package method used in JXTimer */
    void setTime(long milliseconds){

    }

    @Override
    public void setText(String text){}

    @Override
    public String getText(){
        return "";//TODO return the formatted time string
    }
}
