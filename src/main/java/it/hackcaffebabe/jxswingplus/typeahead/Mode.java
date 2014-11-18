package it.hackcaffebabe.jxswingplus.typeahead;

/**
 * Enumerator that represents the modality of
 * {@link it.hackcaffebabe.jxswingplus.typeahead.JXAutoComplete}.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
enum Mode
{
	/** Modality of {@link JXAutoComplete} until keyword is found */
	INSERT, 
	/**
	 * Modality of {@link JXAutoComplete} when keyword is found and you can
	 * complete with {@link JXCommitAction}
	 */
	COMPLETION
}
