package gui.view;

import java.awt.Image;

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

	/** The backview. */
	private View backview;

	/**
	 * Instantiates a new abstract view.
	 *
	 * @param parent     the parent
	 * @param background the background
	 */
	public AbstractView(BackgroundPanel parent, String background) {
		this.parent = parent;
//		this.background = Background.load(background);
		this.background = Background.load("Black screen.png");
		this.parent.setBackground(this.background);
		this.layout = new SpringLayout();
		this.setLayout(this.layout);
		this.setOpaque(false);
		this.backButton = new JButton("Zurück");
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
	 * Adds the back button.
	 *
	 * @param backview the backview
	 * @param corner   the corner
	 * @param distance the distance
	 */
	protected final void addBackButton(View backview, int corner, int distance) {
		this.backview = backview;
		this.backButton.addActionListener(e -> this.parent.changeView(this.backview));
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
		this.add(this.backButton);
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
		this.addBackButton(View.DEFAULT, this.NORTH_WEST, 70);
	}

	/**
	 * Change backview.
	 *
	 * @param backview the backview
	 */
	protected final void changeBackview(View backview) {
		this.backview = backview;
	}

}
