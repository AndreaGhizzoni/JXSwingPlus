package it.hackcaffebabe.jxswingplus.tree;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;


/**
 * Tree render to add different icons at tree leaf and node.
 *   
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXTreeRenderer extends DefaultTreeCellRenderer
{
	private static final long serialVersionUID = 1L;
	private String leafIcoPath;
	private String nodeIcoPath;

	/**
	 * A custom JTree render. This render can display two different icons for leaf and node of tree.
	 * @param leafIcoPath {@link String} relative path of leaf icon.
	 * @param nodeIcoPath {@link String} relative path of node icon.
	 */
	public JXTreeRenderer(String leafIcoPath, String nodeIcoPath){
		this.leafIcoPath = leafIcoPath;
		this.nodeIcoPath = nodeIcoPath;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
			boolean leaf, int row, boolean hasFocus){
		super.getTreeCellRendererComponent( tree, value, sel, expanded, leaf, row, hasFocus );

		if(leaf)//leaf icon
			this.setIcon( new ImageIcon( this.leafIcoPath ) );
		else
		//node icon			
		this.setIcon( new ImageIcon( this.nodeIcoPath ) );

		return this;
	}
}
