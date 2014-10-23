package it.hackcaffebabe.jxswingplus.typeahead;

/**
 * Enumerator that represents the modality of {@link JXAutocomplete}.  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public enum Mode
{
	/** Modality of {@link JXAutocomplete} until keyword is found */
	INSERT, 
	/** Modality of {@link JXAutocomplete} when keyword is found and you can complete with {@link CommitAction} */
	COMPLETION
}
