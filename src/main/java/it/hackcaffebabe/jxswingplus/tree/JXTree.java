package it.hackcaffebabe.jxswingplus.tree;

import javax.swing.JTree;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


/**
 * Simple mutable tree that can adds new node dynamically.
 * To do that use:{@code
 * myTree.addNode( myDefaultMutableTreeNode ) // to add given node at the root of Tree.
 * }
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXTree extends JTree
{
	private static final long serialVersionUID = 1L;

	/**
	 * Instance a mutable tree that is more useful than a common {@link JTree}.<br>
	 * @param model {@link TreeModel} of data to display.
	 */
	public JXTree(TreeModel model){
		super( model );
	}

	/**
	 * Add new node at the root of Tree.<br>
	 * @param node {@link DefaultMutableTreeNode} to add.
	 * @throws IllegalArgumentException {@link Exception} if node is null.
	 */
	public void addNode(DefaultMutableTreeNode node) throws IllegalArgumentException{
		if(node == null)
			throw new IllegalArgumentException( "Node to add can not be null." );

		DefaultTreeModel model = (DefaultTreeModel) this.getModel();

		TreePath path = this.getNextMatch( model.getRoot().toString(), 0, Position.Bias.Forward );
		MutableTreeNode lastNode = (MutableTreeNode) path.getLastPathComponent();
		model.insertNodeInto( node, lastNode, lastNode.getChildCount() );
	}
}
