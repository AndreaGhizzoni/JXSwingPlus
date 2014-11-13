package it.hackcaffebabe.jxswingplus.searchengine.ui;

import it.hackcaffebabe.jxswingplus.searchengine.model.JXSearchEngine;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/* UI for JXSearchBar */
class SearchBarUI extends javax.swing.plaf.basic.BasicComboBoxUI
{
	protected PopupMenuListener popupMenuListener;
	protected Action loupeAction;
	protected JButton loupeButton;

	public SearchBarUI(AbstractAction c){
		super();
		if(c == null)
			loupeAction = Utility.VOID_ACTION;
		else loupeAction = c;
	}

	@Override
	protected void installDefaults(){
		super.installDefaults();
		comboBox.putClientProperty( "JComboBox.isTableCellEditor", Boolean.TRUE );
	}

	@Override
	protected void installListeners(){
		super.installListeners();
		if((popupMenuListener = createPopupMenuListener()) != null) {
			comboBox.addPopupMenuListener( popupMenuListener );
		}
	}

	@Override
	protected void uninstallListeners(){
		super.installListeners();
		if(popupMenuListener != null) {
			comboBox.removePopupMenuListener( popupMenuListener );
		}
	}

	protected PopupMenuListener createPopupMenuListener(){
		if(popupMenuListener == null) {
			popupMenuListener = new PopupMenuListener(){

				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e){
					final Object obj = listBox.getSelectedValue();
					if(obj != null && obj instanceof JXSearchEngine)
						arrowButton.setIcon( ((JXSearchEngine) obj).getFavIcon() );

					EventQueue.invokeLater( new Runnable(){
						@Override
						public void run(){
							comboBox.getModel().setSelectedItem(obj);
						}
					} );
				}

				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e){}

				@Override
				public void popupMenuCanceled(PopupMenuEvent e){}
			};
		}
		return popupMenuListener;
	}

	//NullPointerException at BasicComboBoxUI#isNavigationKey(int keyCode, int modifiers)
	@Override
	protected KeyListener createKeyListener(){
		if(keyListener == null) {
			keyListener = new KeyAdapter(){};
		}
		return keyListener;
	}

	@Override
	protected void configureEditor(){
		// Should be in the same state as the combo box
		editor.setEnabled( comboBox.isEnabled() );
		editor.setFocusable( comboBox.isFocusable() );
		editor.setFont( comboBox.getFont() );
		if(editor instanceof JComponent) {
			((JComponent) editor).setInheritsPopupMenu( true );
		}
		comboBox.configureEditor( comboBox.getEditor(), comboBox.getSelectedItem() );
		editor.addPropertyChangeListener( propertyChangeListener );

        JComponent edit = ((JComponent) editor);
//      edit.setBorder( BorderFactory.createLineBorder(Color.BLACK,1));
		edit.getActionMap().put( "loupe", loupeAction );
		InputMap im = edit.getInputMap( JComponent.WHEN_FOCUSED );
		im.put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ), "loupe" );
	}

	@Override
	public void configureArrowButton(){
		super.configureArrowButton();
		if(arrowButton != null) {
			arrowButton.setBackground( UIManager.getColor( "Panel.background" ) );
			arrowButton.setHorizontalAlignment( SwingConstants.LEFT );
			arrowButton.setOpaque( true );
			arrowButton.setFocusPainted( false );
			arrowButton.setContentAreaFilled( false );
			arrowButton.setBorder( BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder( 0, 0, 0, 0,
                    new Color( 127, 157, 185 ) ),
					BorderFactory.createEmptyBorder( 1, 1, 1, 1 ) )
            );
		}
	}

	@Override
	protected void installComponents(){
		arrowButton = new TriangleArrowButton();
        configureArrowButton();
		comboBox.add( arrowButton );

		loupeButton = createLoupeButton();
        configureLoupeButton();
		comboBox.add( loupeButton );

		addEditor();
		comboBox.add( currentValuePane );
	}

	@Override
	protected void uninstallComponents(){
		if(loupeButton != null) {
			unconfigureLoupeButton();
		}
		loupeButton = null;
		super.uninstallComponents();
	}

	protected JButton createLoupeButton(){
		JButton button = new JButton( loupeAction );
        URL u = getClass().getClassLoader().getResource( "searchengine/loupe.png" );
		ImageIcon loupe = new ImageIcon( u );
		button.setIcon( loupe );
		button.setRolloverIcon(Utility.makeRolloverIcon(loupe));
		return button;
	}

	protected void configureLoupeButton(){
		if(loupeButton != null) {
			loupeButton.setName( "ComboBox.loupeButton" );
//          loupeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			loupeButton.setEnabled( comboBox.isEnabled() );
			loupeButton.setFocusable( comboBox.isFocusable() );
			loupeButton.setOpaque( false );
			loupeButton.setRequestFocusEnabled( false );
			loupeButton.setFocusPainted( false );
			loupeButton.resetKeyboardActions();
			loupeButton.setInheritsPopupMenu( true );
		}
	}

	public void unconfigureLoupeButton(){
		if(loupeButton != null) {
			loupeButton.setAction( null );
		}
	}

	@Override
	protected ListCellRenderer<Object> createRenderer(){
		return new DefaultListCellRenderer(){
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList< ? > list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus){
				JLabel l = (JLabel) super.getListCellRendererComponent( list,
						value, index, isSelected, cellHasFocus );
				if(value instanceof JXSearchEngine) {
					JXSearchEngine se = (JXSearchEngine) value;
					l.setIcon( se.getFavIcon() );
				}
				return l;
			}
		};
	}

	@Override
	protected LayoutManager createLayoutManager(){
		return new LayoutManager(){
			@Override
			public void addLayoutComponent(String name, Component comp){}

			@Override
			public void removeLayoutComponent(Component comp){}

			@Override
			public Dimension preferredLayoutSize(Container parent){
				return parent.getPreferredSize();
			}

			@Override
			public Dimension minimumLayoutSize(Container parent){
				return parent.getMinimumSize();
			}

			@Override
			public void layoutContainer(Container parent){
				if(!(parent instanceof JComboBox))
					return;
				JXSearchBar cb = (JXSearchBar) parent;
				int width = cb.getWidth();
				int height = cb.getHeight();
				Insets insets = cb.getInsets();
				int buttonHeight = height - insets.top - insets.bottom;

                //set layout of arrow button
				int buttonWidth;
				JButton arrowButton = (JButton) cb.getComponent( 0 );
				if(arrowButton != null) {
					Insets arrowInsets = arrowButton.getInsets();
					int alr = arrowInsets.left + arrowInsets.right;
					buttonWidth = arrowButton.getPreferredSize().width + alr ;
					arrowButton.setBounds( insets.left, insets.top, buttonWidth,
							buttonHeight );
				}

                //set layout of search button
				for(Component c: cb.getComponents()) {
					if("ComboBox.loupeButton".equals( c.getName() )) {
						loupeButton = (JButton) c;
                        loupeButton.setBounds(width - insets.right - buttonHeight,
								insets.top, buttonHeight, buttonHeight);
						break;
					}
				}

                //set layout of text edit
				JTextField editor = (JTextField) cb.getEditor().getEditorComponent();
				if(editor != null) {
					editor.setBounds( insets.left+buttonHeight, insets.top,
                            width-insets.left-insets.right-buttonHeight-buttonHeight-5,
                            height-insets.top-insets.bottom );
				}
			}
		};
	}
}
