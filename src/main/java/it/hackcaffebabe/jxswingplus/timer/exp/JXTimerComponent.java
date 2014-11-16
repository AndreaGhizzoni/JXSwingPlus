package it.hackcaffebabe.jxswingplus.timer.exp;

import javax.swing.*;
import static it.hackcaffebabe.jxswingplus.timer.exp.JXTimerUtils.*;

public class JXTimerComponent extends JLabel {
    private long milliseconds;
    private String formattedTime;

    public JXTimerComponent(){
        super();
        this.setTime(0L);
        this.formattedTime = "--:--:--";
    }

    /* package method used in JXTimer */
    void setTime(long milliseconds){
        this.milliseconds = milliseconds;
        this.formattedTime = getFormattedTime(this.milliseconds);
        super.setText( this.formattedTime );
    }

    @Override
    public void setText(String text){}

    @Override
    public String getText(){
        return this.formattedTime;
    }
}
