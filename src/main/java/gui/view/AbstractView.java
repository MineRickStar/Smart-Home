package gui.view;

import java.awt.Image;

import javax.swing.JPanel;

import gui.BackgroundPanel;

/**
 * The Class AbstractView.
 *
 * @author MineRickStar
 */
public abstract class AbstractView extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6407992880759081955L;

	/** The parent. */
	protected final BackgroundPanel parent;

	/** The background. */
	protected final Image background;

	/**
	 * Instantiates a new abstract view.
	 *
	 * @param parent     the parent
	 * @param background the background
	 */
	public AbstractView(BackgroundPanel parent, Image background) {
		this.parent = parent;
		this.background = background;
		this.parent.setBackground(background);
		this.setOpaque(false);
	}

}
