package gui.view;

import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import gui.BackgroundPanel;
import gui.BackgroundPanel.View;
import utils.Background;

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

	/** The layout. */
	protected final SpringLayout layout;

	/** The back button. */
	protected final JButton backButton;

	/** The back button listener. */
	private ActionListener backButtonListener;

	/**
	 * Instantiates a new abstract view.
	 *
	 * @param parent     the parent
	 * @param background the background
	 */
	public AbstractView(BackgroundPanel parent) {
		this.parent = parent;
		this.background = Background.load("Black screen.png");
		this.parent.setBackground(this.background);
		this.layout = new SpringLayout();
		this.setLayout(this.layout);
		this.setOpaque(false);
		this.backButton = new JButton("Zurück");
	}

	/**
	 * Adds the back button.
	 *
	 * @param distance the distance
	 */
	protected final void addBackButton(int distance) {
		this.addBackButton(View.DEFAULT, this.NORTH_WEST, distance);
	}

	/**
	 * Adds the back button.
	 */
	protected final void addBackButton() {
		this.addBackButton(View.DEFAULT, this.NORTH_WEST, 10);
	}

	protected final void addBackButton(View view) {
		this.addBackButton(view, this.NORTH_WEST, 10);
	}

	/**
	 * Adds the back button.
	 *
	 * @param backview the backview
	 * @param corner   the corner
	 * @param distance the distance
	 */
	protected final void addBackButton(View backview, int corner, int distance) {
		this.changeBackview(backview);
		this.layoutButton(corner, distance);
		this.add(this.backButton);
	}

	/** The north. */
	private final int NORTH = 1 << 0;
	/** The east. */
	private final int EAST = 1 << 1;
	/** The south. */
	private final int SOUTH = 1 << 2;
	/** The west. */
	private final int WEST = 1 << 3;

	/** The north west. */
	protected final int NORTH_WEST = this.NORTH | this.WEST;
	/** The north east. */
	protected final int NORTH_EAST = this.NORTH | this.EAST;
	/** The south east. */
	protected final int SOUTH_EAST = this.SOUTH | this.EAST;
	/** The south west. */
	protected final int SOUTH_WEST = this.SOUTH | this.WEST;

	/**
	 * Layout button.
	 *
	 * @param corner   the corner
	 * @param distance the distance
	 */
	protected final void layoutButton(int corner, int distance) {
		this.layout.removeLayoutComponent(this.backButton);
		if ((corner & this.NORTH) == this.NORTH) {
			this.layout.putConstraint(SpringLayout.NORTH, this.backButton, distance, SpringLayout.NORTH, this);
		}
		if ((corner & this.EAST) == this.EAST) {
			this.layout.putConstraint(SpringLayout.EAST, this.backButton, distance, SpringLayout.EAST, this);
		}
		if ((corner & this.SOUTH) == this.SOUTH) {
			this.layout.putConstraint(SpringLayout.SOUTH, this.backButton, distance, SpringLayout.SOUTH, this);
		}
		if ((corner & this.WEST) == this.WEST) {
			this.layout.putConstraint(SpringLayout.WEST, this.backButton, distance, SpringLayout.WEST, this);
		}
	}

	/**
	 * Change backview.
	 *
	 * @param backview the backview
	 */
	protected final void changeBackview(View backview) {
		this.changeBackListener(e -> this.parent.changeView(backview));
	}

	/**
	 * Change back listener.
	 *
	 * @param listener the listener
	 */
	protected final void changeBackListener(ActionListener listener) {
		if (this.backButtonListener != null) {
			this.backButton.removeActionListener(this.backButtonListener);
		}
		this.backButtonListener = listener;
		this.backButton.addActionListener(this.backButtonListener);
	}

}