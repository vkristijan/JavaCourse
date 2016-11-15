package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Implementation of {@link IColorProvider} that uses a {@link JColorChooser} to
 * pick a new color.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class JColorArea extends JComponent implements IColorProvider {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The change listeners. */
	private List<ColorChangeListener> changeListeners;

	/** The selected color. */
	private Color selectedColor;

	/** The default color. */
	private static Color DEFAULT_COLOR = Color.BLACK;

	/** The width. */
	private static int WIDTH = 15;

	/** The height. */
	private static int HEIGHT = 15;

	/**
	 * Instantiates a new color area to the default initial color.
	 */
	public JColorArea(){
		this(DEFAULT_COLOR);
	}
	
	/**
	 * Instantiates a new color area.
	 *
	 * @param initialColor
	 *            the initial color
	 */
	public JColorArea(Color initialColor) {
		changeListeners = new ArrayList<>();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.selectedColor = initialColor;
		setBackground(selectedColor);
		setOpaque(true);
		repaint();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color choosenColor = JColorChooser.showDialog(
					JColorArea.this.getParent(), 
					"Choose a color", 
					selectedColor
				);
				
				if (choosenColor != null){
					colorChanged(selectedColor, choosenColor);
				}
			}
		});
	}
	
	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		changeListeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		changeListeners.remove(l);
	}

	
	/**
	 * Color changed, notifies all the listeners about that event.
	 *
	 * @param oldColor
	 *            the old color
	 * @param newColor
	 *            the new color
	 */
	private void colorChanged(Color oldColor, Color newColor){
		selectedColor = newColor;
		setBackground(selectedColor);
		repaint();

		List<ColorChangeListener> listeners = new ArrayList<>(changeListeners);
		
		for (ColorChangeListener listener : listeners){
			listener.newColorSelected(this, oldColor, newColor);
		}
	}
	
	
	@Override
	public Color getCurrentColor(){
		return selectedColor;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
