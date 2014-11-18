package it.hackcaffebabe.jxswingplus.typeahead;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;


/**
 * This action will complete the keyword if is found.
 * Use with:
 * <pre>{@code
 * textField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "commit");
 * textField.getActionMap().put("commit", new CommitAction(jxAutocompleteDocumentListener, keywordList) );
 * }</pre>
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class CommitAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private JXAutoComplete autocomplete;

	public CommitAction(JXAutoComplete autocomplete){
		this.autocomplete = autocomplete;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(autocomplete.getMode() == Mode.COMPLETION) {
			int pos = autocomplete.getJTextFiled().getSelectionEnd();
			StringBuffer sb = new StringBuffer( autocomplete.getJTextFiled().getText() );
			autocomplete.getJTextFiled().setText( sb.toString() );
			autocomplete.getJTextFiled().setCaretPosition( pos );
			autocomplete.setMode( Mode.INSERT );
		} else {
//			autocomplete.getJTextFiled().replaceSelection( "\t" );
//			autocomplete.setMode( Mode.INSERT );
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		}
	}
}
