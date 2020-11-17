package start;

import gui.SmartHomeFrame;

/**
 * The Class SmartHome.
 *
 * @author MineRickStar
 */
public class SmartHome {

	/**
	 * Instantiates a new smart home.
	 */
	private SmartHome() {
		new SmartHomeFrame();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		new SmartHome();
	}

}