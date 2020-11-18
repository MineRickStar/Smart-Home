package gui.view.test;

import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Class JFrameFrame.
 */
public class JFrameFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6247969473008053153L;

	/** The periods. */
	private ArrayList<JButton> periods;

	/** The times. */
	private ArrayList<JButton> times;

	/**
	 * J frame.
	 */
	public JFrameFrame() {
		this.setDefaultCloseOperation(2);
//		this.setMinimumSize(new Dimension(600, 600));
		this.setContentPane(this.panel());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Panel.
	 *
	 * @return the j panel
	 */
	private JPanel panel() {

		this.periods = new ArrayList<>();
		this.periods.add(new JButton("Jeden Tag"));
		this.periods.add(new JButton("Jeden 2 Tag"));
		this.periods.add(new JButton("Jedes Wochenende"));
		this.periods.add(new JButton("Jede Woche"));
		this.periods.add(new JButton("Jeden Monat"));

		this.times = new ArrayList<>();
		this.times.add(new JButton("17:00"));
		this.times.add(new JButton("18:00"));
		this.times.add(new JButton("19:00"));
		this.times.add(new JButton("20:00"));

		JPanel datesAndTimes = new JPanel();
		GroupLayout layout = new GroupLayout(datesAndTimes);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		datesAndTimes.setLayout(layout);
//		datesAndTimes.setOpaque(false);

		ParallelGroup trailingGroup = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
		this.periods.forEach(period -> trailingGroup.addComponent(period));

		ParallelGroup leadingGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		this.times.forEach(leadingGroup::addComponent);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(trailingGroup)
				.addGroup(leadingGroup));

		layout.linkSize(this.periods.toArray(JButton[]::new));
		layout.linkSize(this.times.toArray(JButton[]::new));

		SequentialGroup sGroup = layout.createSequentialGroup();

		for (int i = 0, n = Math.max(this.periods.size(), this.times.size()); i < n; i++) {
			ParallelGroup group = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
			if (this.periods.size() > i) {
				sGroup.addGroup(group.addComponent(this.periods.get(i)));
			}
			if (this.times.size() > i) {
				sGroup.addGroup(group.addComponent(this.times.get(i)));
			}
		}

		layout.setVerticalGroup(sGroup);
		return datesAndTimes;

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		new JFrameFrame();
	}

}
