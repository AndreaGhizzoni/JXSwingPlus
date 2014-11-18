package it.hackcaffebabe.jxswingplus.typeahead;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * This action will complete the keyword if is found. Use with:
 * <pre>{@code
 * JTextField textField = new JTextField();
 * JXAutoComplete autoComplete = new JXAutoComplete(textField, k );
 * String tab = "TAB";
 * String enter = "ENTER";
 * textField.getInputMap().put( KeyStroke.getKeyStroke( tab ), COMMIT_ACTION );
 * textField.getInputMap().put( KeyStroke.getKeyStroke( enter ), COMMIT_ACTION );
 * textField.getActionMap(COMMIT_ACTION, new CommitAction(autoComplete) );
 * }</pre>
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXCommitAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private JXAutoComplete autoComplete;

	public JXCommitAction(JXAutoComplete autocomplete){
		this.autoComplete = autocomplete;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(autoComplete.getMode() == Mode.COMPLETION) {
			int pos = autoComplete.getJTextFiled().getSelectionEnd();
			//autoComplete.getJTextFiled().setText(autoComplete.getJTextFiled().getText());
			autoComplete.getJTextFiled().setCaretPosition( pos );
			autoComplete.setMode( Mode.INSERT );
		} else {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		}
	}
}
