package it.hackcaffebabe.jxswingplus.searchengine;

import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * Default interface that you must implement to use {@link JXSearchBar} with {@link JXSearchBarModel}.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public interface JXSearchEngine
{
	/** This is the default {@link Icon} if you don't have one */
	public static final Icon NO_SEARCH_ENGINE = new ImageIcon( JXSearchEngine.class.getClassLoader().getResource(
			"searchengine/no_se.png" ) );

	/** This is the default {@link Icon} for google search engine */
	public static final Icon GOOGLE_ICON = new ImageIcon( JXSearchEngine.class.getClassLoader().getResource(
			"searchengine/16google.png" ) );

	/** This is the default {@link Icon} for bing search engine */
	public static final Icon BING_ICON = new ImageIcon( JXSearchEngine.class.getClassLoader().getResource(
			"searchengine/16bing.png" ) );

	/** This is the default {@link Icon} for amazon search engine */
	public static final Icon AMAZON_ICON = new ImageIcon( JXSearchEngine.class.getClassLoader().getResource(
			"searchengine/16amazon.png" ) );

	/** This is the default {@link Icon} for duckduckgo search engine */
	public static final Icon DUCKDUCKGO_ICON = new ImageIcon( JXSearchEngine.class.getClassLoader().getResource(
			"searchengine/16duckduckgo.png" ) );

	/** This is the default {@link Icon} for github search engine */
	public static final Icon GITHUB_ICON = new ImageIcon( JXSearchEngine.class.getClassLoader().getResource(
			"searchengine/16github.png" ) );

	/** This is the default {@link Icon} for wikipedia search engine */
	public static final Icon WIKIPEDIA_ICON = new ImageIcon( JXSearchEngine.class.getClassLoader().getResource(
			"searchengine/16wikipedia.png" ) );

	/** This is the default {@link Icon} for youtube search engine */
	public static final Icon YOUTUBE_ICON = new ImageIcon( JXSearchEngine.class.getClassLoader().getResource(
			"searchengine/16youtube.png" ) );

	/** @return {@link String} the name of {@link JXSearchEngine} */
	public String getName();

	/** @return {@link String} the url of {@link JXSearchEngine} */
	public String getURL();

	/** @return {@link Icon} the icon of {@link JXSearchEngine} */
	public Icon getFavIcon();
}
