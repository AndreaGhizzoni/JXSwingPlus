package it.hackcaffebabe.jxswingplus.typeahead;

/**
 * Enumerator that represents the modality of {@link JXAutoComplete}.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public enum Mode
{
	/** Modality of {@link JXAutoComplete} until keyword is found */
	INSERT, 
	/** Modality of {@link JXAutoComplete} when keyword is found and you can complete with {@link CommitAction} */
	COMPLETION
}
