package it.hackcaffebabe.jxswingplus.searchengine.model;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.net.URL;


/**
 * Default interface that you must implement to use
 * {@link it.hackcaffebabe.jxswingplus.searchengine.ui.JXSearchBar} with
 * {@link it.hackcaffebabe.jxswingplus.searchengine.ui.JXDefaultSearchBarModel}.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public interface JXSearchEngine
{
    //resource
    ClassLoader cl = JXSearchEngine.class.getClassLoader();
    URL res_no_se   = cl.getResource("searchengine/no_se.png");
    URL res_google  = cl.getResource("searchengine/16google.png");
    URL res_bing    = cl.getResource("searchengine/16bing.png");
    URL res_amazon  = cl.getResource("searchengine/16amazon.png");
    URL res_duck    = cl.getResource("searchengine/16duckduckgo.png");
    URL res_github  = cl.getResource("searchengine/16github.png");
    URL res_wiki    = cl.getResource("searchengine/16wikipedia.png");
    URL res_youtube = cl.getResource("searchengine/16youtube.png");


	/** This is the default {@link Icon} if you don't have one */
	public static final Icon NO_SEARCH_ENGINE = new ImageIcon( res_no_se );

	/** This is the default {@link Icon} for google search engine */
	public static final Icon GOOGLE_ICON = new ImageIcon( res_google );

	/** This is the default {@link Icon} for bing search engine */
	public static final Icon BING_ICON = new ImageIcon( res_bing );

	/** This is the default {@link Icon} for amazon search engine */
	public static final Icon AMAZON_ICON = new ImageIcon( res_amazon );

	/** This is the default {@link Icon} for duckduckgo search engine */
	public static final Icon DUCKDUCKGO_ICON = new ImageIcon( res_duck );

	/** This is the default {@link Icon} for github search engine */
	public static final Icon GITHUB_ICON = new ImageIcon( res_github );

	/** This is the default {@link Icon} for wikipedia search engine */
	public static final Icon WIKIPEDIA_ICON = new ImageIcon( res_wiki );

	/** This is the default {@link Icon} for youtube search engine */
	public static final Icon YOUTUBE_ICON = new ImageIcon( res_youtube );

	/** @return {@link String} the name of {@link JXSearchEngine} */
	public String getName();

	/** @return {@link String} the url of {@link JXSearchEngine} */
	public String getURL();

	/** @return {@link Icon} the icon of {@link JXSearchEngine} */
	public Icon getFavIcon();
}
