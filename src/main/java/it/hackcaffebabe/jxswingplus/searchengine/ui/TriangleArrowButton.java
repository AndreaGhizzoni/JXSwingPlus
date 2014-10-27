package it.hackcaffebabe.jxswingplus.searchengine.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/* class used  */
class TriangleArrowButton extends JButton
{
	private static final long serialVersionUID = 1L;
	private transient Icon triangleIcon = new TriangleIcon();

	@Override
	public void setIcon(Icon favicon){
		super.setIcon( favicon );
		if(favicon != null)
			setRolloverIcon( Utility.makeRolloverIcon(favicon) );
	}

	@Override
	protected void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        ButtonModel bm = getModel();
        boolean isArmedAndRollover = bm.isArmed() && bm.isRollover() ;
        boolean isRolloverEnableAndHasFocus = isRolloverEnabled() && hasFocus();
		if(isArmedAndRollover && isRolloverEnableAndHasFocus ) {
			g2.setColor( new Color( 220, 220, 220 ) );
		} else {
			g2.setColor( getBackground() );
		}

		Rectangle r = getBounds();
		r.grow( 1, 1 );
		g2.fill( r );
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF );
		g2.setColor( getBackground() );
		super.paintComponent( g );

		Insets i = getInsets();
		int x = r.width - i.right - triangleIcon.getIconWidth() - 2;
		int y = i.top + (r.height - i.top - i.bottom - triangleIcon.getIconHeight()) / 2;
		triangleIcon.paintIcon( this, g, x, y );
	}

	@Override
	public Dimension getPreferredSize(){
		Insets i = getInsets();
		Icon favicon = getIcon();
		int fw = (favicon != null) ? favicon.getIconWidth() : 16;
		int w = fw + triangleIcon.getIconWidth() + i.left + i.right;
		return new Dimension( w, w );
	}

	@Override
	public void setBorder(Border border){
		if(border instanceof CompoundBorder) {
			super.setBorder( border );
		}
	}

    /* little icon triangle for the button */
	static class TriangleIcon implements Icon
	{
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y){
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint( Color.GRAY );
			g2.translate( x, y );
			g2.drawLine( 2, 3, 6, 3 );
			g2.drawLine( 3, 4, 5, 4 );
			g2.drawLine( 4, 5, 4, 5 );
			g2.translate( -x, -y );
		}

		@Override
		public int getIconWidth(){ return 9; }

		@Override
		public int getIconHeight(){ return 9; }
	}
}
