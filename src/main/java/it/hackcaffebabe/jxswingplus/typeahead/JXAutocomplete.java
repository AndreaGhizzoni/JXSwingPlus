package it.hackcaffebabe.jxswingplus.typeahead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;


/**
 * This is a {@link DocumentListener} that provide the typeahead on a {@link JTextField}.
 * How to use:
 * <pre>{@code
 * JTextField textField = new JTextField();
 * JXAutocomplete autoComplete = new JXAutocomplete(textField, keywords);
 * }</pre>
 * Where "keywords" is a {@link List} of {@link String} to help the user while is typing on {@link JTextField}.
 * You can use {@link CommitAction} to complete the word that this class suggest you:
 * <pre>{@code
 * textField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
 * textField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION );
 * }</pre>
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.5
 */
public class JXAutocomplete implements DocumentListener
{
	private JTextField textField;
	private List<String> keywords;
	private Mode mode = Mode.INSERT;
	private static int MIN_CHAR_TO_START = 1;

	/**
	 * Create a new Document listener that helps user to search a word while typing.
	 * @param textField {@link JTextField} the field to insert.
	 * @param keywords {@link List} of keywords to view while typing.
	 * @throws IllegalArgumentException textFiled is null.
	 */
	public JXAutocomplete(JTextField textField, List<String> keywords) throws IllegalArgumentException{
		this.setJTextField( textField );
		textField.getDocument().addDocumentListener( this );
		textField.setFocusTraversalKeysEnabled( false );// Without this, cursor always leaves text field
		this.setKeywords( keywords );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the Keywords to search while typing
	 * @param listOfKeywords {@link List} of keywords.
	 */
	public void setKeywords(List<String> listOfKeywords){
		if(listOfKeywords == null)
			this.keywords = new ArrayList<String>();
		else this.keywords = listOfKeywords;

		if(!this.keywords.isEmpty())
			Collections.sort( this.keywords );//sort for later binary search
	}

	/**
	 * Set the mode of auto complete.
	 * @param m {@link Mode}
	 */
	public void setMode(Mode m){
		if(m == null)
			this.mode = Mode.INSERT;
		else this.mode = m;
	}

	/**
	 * This method set the minimum characters to start the search into keywords list.
	 * @param i {@link Integer} minimum characters.
	 */
	public void setMinimumCharToStartSearch(int i){
		if(i > 0)
			MIN_CHAR_TO_START = i;
	}

	/* Set the JTextFiled */
	private void setJTextField(JTextField f) throws IllegalArgumentException{
		if(f == null)
			throw new IllegalArgumentException( "JTextField not to be null." );
		this.textField = f;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link JTextField} where the user will insert the text. */
	public JTextField getJTextFiled(){
		return this.textField;
	}

	/** @return {@link List} of Strings that help the user to write his text. */
	public final List<String> getKeywords(){
		return this.keywords;
	}

	/** @return {@link Mode} the modality of auto completion. */
	public Mode getMode(){
		return this.mode;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public void changedUpdate(DocumentEvent ev){}

	@Override
	public void removeUpdate(DocumentEvent ev){}

	@Override
	public void insertUpdate(DocumentEvent ev){
		if(ev.getLength() != 1 || this.keywords.isEmpty())
			return;

		int offest = ev.getOffset();
		String content = null;
		try {
			content = textField.getText( 0, offest + 1 );
		} catch(BadLocationException e) {
			e.printStackTrace();
		}

		// Find where the word starts
		int wordStartIndex;
		for(wordStartIndex = offest; wordStartIndex >= 0; wordStartIndex--) {
			if(!Character.isLetter( content.charAt( wordStartIndex ) )) {
				break;
			}
		}

		if(offest - wordStartIndex < MIN_CHAR_TO_START)// number of char to start the search.
			return;

		String prefix = content.substring( wordStartIndex + 1 );
		int indexOfFirstKeyFound = Collections.binarySearch( this.keywords, prefix );
		if(indexOfFirstKeyFound < 0 && -indexOfFirstKeyFound <= keywords.size()) {
			String match = keywords.get( -indexOfFirstKeyFound - 1 );
			if(match.startsWith( prefix )) { // A completion is found
				String completion = match.substring( offest - wordStartIndex );
				SwingUtilities.invokeLater( new CompletionTask( completion, offest + 1 ) );
			}
		} else {
			setMode( Mode.INSERT );// Nothing found
		}
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* 
	 * We cannot modify Document from within insertUpdate() function, 
	 * so we submit a task that does the change later.
	 * To avoid: java.lang.IllegalStateException: Attempt to mutate in notification 
	 */
	private class CompletionTask implements Runnable
	{
		private String completion;
		private int position;

		public CompletionTask(String completion, int position){
			this.completion = completion;
			this.position = position;
		}

		public void run(){
			StringBuffer sb = new StringBuffer( textField.getText() );
			sb.insert( position, completion );
			textField.setText( sb.toString() );
			textField.setCaretPosition( position + completion.length() );
			textField.moveCaretPosition( position );
			setMode( Mode.COMPLETION );
		}
	}
}
