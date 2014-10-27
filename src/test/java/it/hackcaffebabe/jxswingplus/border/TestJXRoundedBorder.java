package it.hackcaffebabe.jxswingplus.border;

import it.hackcaffebabe.jxswingplus.border.JXRoundedBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;


/**
 * Test {@link JXRoundedBorder}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestJXRoundedBorder extends JFrame
{
	private static final long serialVersionUID = 1L;

	public TestJXRoundedBorder(){
        super("RoundedTextField");
        Dimension d = new Dimension( 400, 100 );
        setPreferredSize(d);
        setMinimumSize(d);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel content = new JPanel(new BorderLayout());

		JTextField textRounded = new JTextField( 20 );
		textRounded.setBorder(new JXRoundedBorder());
		textRounded.setText("HI! im a rounded JTextField");

		content.add(makeTitlePanel(textRounded, "setBorder(new RoundedCornerBorder())"));

		getContentPane().add(content);
	}

	private JComponent makeTitlePanel(JComponent cmp, String title){
        JPanel p = new JPanel();
        p.add(cmp);
		p.setBorder( BorderFactory.createTitledBorder( title ) );
		return p;
	}

	public static void main(String[] args){
		EventQueue.invokeLater( new Runnable(){
			@Override
			public void run(){
                TestJXRoundedBorder frame = new TestJXRoundedBorder();
                frame.pack();
                frame.setLocationRelativeTo( null );
                frame.setVisible( true );
			}
		} );
	}
}
