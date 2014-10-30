package it.hackcaffebabe.jxswingplus.searchengine.model;

import javax.swing.Icon;

/**
 * This is the default implementation of
 * {@link it.hackcaffebabe.jxswingplus.searchengine.model.JXSearchEngine}.
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXDefaultSearchEngine implements JXSearchEngine
{
	public String name;
	public String url;
	public Icon favicon;

	/**
	 * Instance the default implementation of {@link JXSearchEngine}.<br>
	 * Check out the most used search engines icons in {@link JXSearchEngine}.
	 * @param name {@link String} the name of {@link JXSearchEngine}.
	 * @param url {@link String} the url of {@link JXSearchEngine}.
	 * @param icon {@link Icon} the icon of {@link JXSearchEngine}.
	 */
	public JXDefaultSearchEngine(String name, String url, Icon icon){
		this.setName( name );
		this.setUrl( url );
		this.setFavicon( icon );
	}

//==============================================================================
// SETTER
//==============================================================================
	/** @param name {@link String} the name of {@link JXSearchEngine} */
	public void setName(String name){
		this.name = name == null || name.isEmpty() ? "null" : name;
	}

	/** @param url {@link String} the url of {@link JXSearchEngine} */
	public void setUrl(String url){
		this.url = url == null || url.isEmpty() ? "null" : url;
	}

	/** @param favicon {@link Icon} the icon of {@link JXSearchEngine} */
	public void setFavicon(Icon favicon){
		this.favicon = favicon == null ? NO_SEARCH_ENGINE : favicon;
	}

//==============================================================================
// GETTER
//==============================================================================
	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public String getURL(){
		return this.url;
	}

	@Override
	public Icon getFavIcon(){
		return this.favicon;
	}

	@Override
	public String toString(){
		return getName();
	}
}
