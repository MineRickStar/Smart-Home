package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JPanel;

import gui.view.ViewDefault;
import gui.view.ViewTaskAdd;
import gui.view.ViewTasks;

/**
 * The Class BackgroundPanel.
 *
 * @author MineRickStar
 */
public class BackgroundPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2238992011815695912L;

	/** The current background. */
	private Image currentBackground;

	/**
	 * Instantiates a new background panel.
	 */
	public BackgroundPanel() {
		this.setLayout(new GridLayout(1, 1));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Change view.
	 *
	 * @param view the view
	 */
	public void changeView(View view) {
		this.removeAll();
		switch (view) {
		case ADD_TASK:
			this.add(new ViewTaskAdd(this));
			break;
		case DEFAULT:
			this.add(new ViewDefault(this));
			break;
		case VIEW_TASKS:
			this.add(new ViewTasks(this));
		}
		this.revalidate();
		this.repaint();
	}

	/**
	 * The Enum View.
	 */
	public enum View {

		/** The add task. */
		ADD_TASK,
		/** The default. */
		DEFAULT,
		/** The view tasks. */
		VIEW_TASKS;
	}

	public void setBackground(Image background) {
		this.currentBackground = background;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.currentBackground, 0, 0, null);
	}
}