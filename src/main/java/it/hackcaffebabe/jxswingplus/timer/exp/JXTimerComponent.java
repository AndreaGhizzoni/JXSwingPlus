package it.hackcaffebabe.jxswingplus.timer.exp;

import javax.swing.*;
import static it.hackcaffebabe.jxswingplus.timer.exp.JXTimerUtils.*;

/**
 * //TODO add doc
 */
public class JXTimerComponent extends JLabel {
    private String formattedTime;

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
