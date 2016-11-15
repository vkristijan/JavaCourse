package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * {@link JLabel} that displays the current time in it. The time is displayed in
 * the format yyyy/MM/dd HH:mm:ss.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class ClockLabel extends JLabel {
	/**
	 * Default serial version ID number used for serialization.
	 */
	private static final long serialVersionUID = -5973136029215117297L;
	
	/**
	 * The timer used to count the time between time refresh.
	 */
	private Timer clock;

	/**
	 * Creates a new {@link ClockLabel} that displays the current date and time.
	 * The label will update it's value automatically every second.
	 */
	public ClockLabel() {
		clock = new Timer(100, new ActionListener() {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			@Override
			public void actionPerformed(ActionEvent e) {
				String time = formatter.format(new Date());
				setText(time);
			}
		});
		clock.start();
	}

	/**
	 * Stops the execution of the clock.
	 */
	public void stop() {
		clock.stop();
	}

}
