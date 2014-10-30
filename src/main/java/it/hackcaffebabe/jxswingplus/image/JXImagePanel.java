package it.hackcaffebabe.jxswingplus.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * Simple Panel to view an Image given as {@link java.io.InputStream}.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class JXImagePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private BufferedImage buf;

	/**
	 * Instance a Panel to display an Image. Panel size will be the same of
     * loaded Image.
	 * @param file {@link InputStream} as image to view
	 * @throws IOException if method can not read image file.
	 */
	public JXImagePanel(InputStream file) throws IOException{
		super();
		this.buf = ImageIO.read( file );
		this.setSize( this.buf.getWidth(), this.buf.getHeight() );
	}

	@Override
	public void paint(Graphics g){
		g.drawImage( buf, 0, 0, null );
	}
}
