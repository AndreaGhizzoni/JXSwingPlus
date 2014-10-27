package it.hackcaffebabe.jxswingplus.searchengine.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/* Utility class for Search Bar UI */
class Utility
{
    /* simple void action used in JXSearchBar constructor */
	static final AbstractAction VOID_ACTION = new AbstractAction(){
		private static final long serialVersionUID = 1L;
		@Override
		public void actionPerformed(ActionEvent e){}
	};

    /* method used in TriangleArrowButton */
    static Icon makeRolloverIcon(Icon srcIcon){
        float[] f = new float[] { 1.2f, 1.2f, 1.2f, 1.0f };
        float[] e = new float[] { 0f, 0f, 0f, 0f };
		RescaleOp op = new RescaleOp( f, e, null );

        int iWith = srcIcon.getIconWidth();
        int iHeight = srcIcon.getIconHeight();
        int c = BufferedImage.TYPE_INT_ARGB ;
		BufferedImage img = new BufferedImage( iWith, iHeight, c );

		Graphics g = img.getGraphics();
		srcIcon.paintIcon( null, g, 0, 0 );
		g.dispose();
		return new ImageIcon( op.filter( img, null ) );
	}
}
