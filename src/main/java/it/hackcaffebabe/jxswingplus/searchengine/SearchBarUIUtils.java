package it.hackcaffebabe.jxswingplus.searchengine;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;


class SearchBarUIUtils
{
	static final AbstractAction VOID_ACTION = new AbstractAction(){
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e){}
	};

	static Icon makeRolloverIcon(Icon srcIcon){
		RescaleOp op = new RescaleOp( new float[] { 1.2f, 1.2f, 1.2f, 1.0f }, new float[] { 0f, 0f, 0f, 0f }, null );
		BufferedImage img = new BufferedImage( srcIcon.getIconWidth(), srcIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB );
		Graphics g = img.getGraphics();
		srcIcon.paintIcon( null, g, 0, 0 );
		g.dispose();
		return new ImageIcon( op.filter( img, null ) );
	}
}
