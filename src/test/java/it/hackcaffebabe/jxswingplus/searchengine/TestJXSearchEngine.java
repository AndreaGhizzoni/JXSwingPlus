package it.hackcaffebabe.jxswingplus.searchengine;

import it.hackcaffebabe.jxswingplus.searchengine.model.JXDefaultSearchEngine;
import it.hackcaffebabe.jxswingplus.searchengine.model.JXSearchEngine;
import it.hackcaffebabe.jxswingplus.searchengine.ui.JXSearchBar;
import it.hackcaffebabe.jxswingplus.searchengine.ui.JXDefaultSearchBarModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Test {@link it.hackcaffebabe.jxswingplus.searchengine.ui.JXSearchBar} and
 * {@link it.hackcaffebabe.jxswingplus.searchengine.ui.JXDefaultSearchBarModel}
 *
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestJXSearchEngine extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JXSearchBar comboBox;

	public TestJXSearchEngine(){
        super("SearchBarLayoutComboBox");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        Dimension d = new Dimension( 400, 100 );
        setSize(d);
        setMinimumSize(d);

		JPanel p = new JPanel( new GridLayout(1,2,5,5) );
		p.add( new JLabel( "SearchBar JComboBox" ) );

		JXDefaultSearchBarModel model = new JXDefaultSearchBarModel();
		model.addElement( new JXDefaultSearchEngine( "Amazon", "http://www.amazon.co.uk/", JXSearchEngine.AMAZON_ICON ) );
		model.addElement( new JXDefaultSearchEngine( "Bing", "http://www.bing.com/", JXSearchEngine.BING_ICON ) );
		model.addElement( new JXDefaultSearchEngine( "DuckDuckGo", "http://duckduckgo.com/",JXSearchEngine.DUCKDUCKGO_ICON ) );
		model.addElement( new JXDefaultSearchEngine( "Github", "http://www.github.com/", JXSearchEngine.GITHUB_ICON ) );
		model.addElement( new JXDefaultSearchEngine( "Google", "http://www.google.com/", JXSearchEngine.GOOGLE_ICON ) );
		model.addElement( new JXDefaultSearchEngine( "Wikipedia", "http://www.wikipedia.com/",JXSearchEngine.WIKIPEDIA_ICON ) );
		model.addElement( new JXDefaultSearchEngine( "Youtube", "http://www.youtube.com/", JXSearchEngine.YOUTUBE_ICON ) );

		this.comboBox = new JXSearchBar( model, new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println( "combobox.getSelectedItem() : "+ comboBox.getSelectedItem());
				System.out.println( "((SearchBarComboBoxModel)combobox.getModel()).getSelectedItem() : " + comboBox.getModel().getSelectedItem() );
				System.out.println( "comboBox.getInsertedQuerySearch() : " + comboBox.getQueryString() );
			}
		} );

		p.add(this.comboBox);

		add(p, BorderLayout.NORTH);
	}

	public static void main(String[] args){
		EventQueue.invokeLater( new Runnable(){
			@Override
			public void run(){
                TestJXSearchEngine frame = new TestJXSearchEngine();
				frame.pack();
				frame.setLocationRelativeTo( null );
				frame.setVisible( true );
			}
		} );
	}
}