package it.hackcaffebabe.jxswingplus.typeahead;

import it.hackcaffebabe.jxswingplus.typeahead.CommitAction;
import it.hackcaffebabe.jxswingplus.typeahead.JXAutocomplete;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;


/**
 * Simple class to Test {@link JXAutocomplete}.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestJXAutocomplete
{
	private static final String COMMIT_ACTION = "commit";

	public static void main(String[] args){
		JFrame f = new JFrame( "autocomplete Test" );
		f.setPreferredSize( new Dimension( 300, 130 ) );
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		f.setLocation( 500, 500 );
		JPanel container = new JPanel( new GridLayout( 4, 1, 5, 5 ) );
		container.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );

		container.add( new JLabel( "JTextField 1:" ) );

		JTextField textField = new JTextField();
		// Our words to complete
		ArrayList<String> keywords = new ArrayList<String>( 5 );
		keywords.add( "Example" );
		keywords.add( "Autocomplete" );
		keywords.add( "stackabuse" );
		keywords.add( "java" );
		keywords.add( "jarjar" );
		JXAutocomplete autoComplete = new JXAutocomplete( textField, keywords );

		// Maps the tab key to the commit action, 
		// which finishes the auto complete when given a suggestion
		textField.getInputMap().put( KeyStroke.getKeyStroke( "TAB" ), COMMIT_ACTION );
		textField.getInputMap().put( KeyStroke.getKeyStroke( "ENTER" ), COMMIT_ACTION );
		textField.getActionMap().put( COMMIT_ACTION, new CommitAction( autoComplete ) );

		container.add( textField );
		container.add( new JButton( "asd" ) );
		f.add( container );
		f.pack();
		f.setVisible( true );
	}
}
