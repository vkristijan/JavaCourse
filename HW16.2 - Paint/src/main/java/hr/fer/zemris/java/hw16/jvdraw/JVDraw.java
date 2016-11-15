package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Point;

/**
 * An GUI application that acts like a simple paining program. It is capable of
 * drawing lines and circles. The object can be exported to a textual file or to
 * an image.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class JVDraw extends JFrame{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The default HEIGHT. */
	private static final int HEIGHT = 500;
	
	/** The default WIDTH. */
	private static final int WIDTH = 750;
	
	/** The default DEFAULT_FOREGROUND_COLOR. */
	private static final Color DEFAULT_FOREGROUND_COLOR = Color.RED;
	
	/** The default DEFAULT_BACKGROUND_COLOR. */
	private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLUE;
	
	/** The line button. */
	private JToggleButton lineBtn;
	
	/** The circle button. */
	private JToggleButton circleBtn;
	
	/** The filled circle button. */
	private JToggleButton filledCircleBtn;
	
	/** The foreground color. */
	private JColorArea foregroundCol;
	
	/** The background color. */
	private JColorArea backgroundCol;
	
	/** The drawing model. */
	private DrawingModel drawingModel;
	
	/** The canvas. */
	private JDrawingCanvas canvas;
	
	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(JVDraw::new);
	}
	
	/**
	 * Instantiates a new JV draw program.
	 */
	public JVDraw() {
		setTitle("JVDraw");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
		
		drawingModel = new DrawingModelImpl();
		initGui();
		
		setVisible(true);
	}
	
	/**
	 * Initializes the gui.
	 */
	private void initGui(){
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		createToolbar();
		createCanvas();
		createStatusBar();
		createList();
		createMenuBar();
	}

	/**
	 * Creates the menu bar.
	 */
	private void createMenuBar() {
		createActions();
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(openAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exportAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		this.setJMenuBar(menuBar);
	}

	/**
	 * Creates the actions, defines the action names, descriptions, mnemonic
	 * keys and accelerator keys.
	 */
	private void createActions() {
		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.SHORT_DESCRIPTION, "Opens document");
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		
		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.SHORT_DESCRIPTION, "Saves document");
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		
		saveAsAction.putValue(Action.NAME, "Save as");
		saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Saves document to new location");
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Closes the program");
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		
		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.SHORT_DESCRIPTION, "Exports the image.");
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
	}

	/** The open action. */
	private Action openAction = new AbstractAction() {
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			
			if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION){
				return;
			}
			
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)){
				JOptionPane.showMessageDialog(
					JVDraw.this, 
					"File doesn't exist.",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			try {
				List<String> lines = Files.readAllLines(filePath);
				
				drawingModel.clear();
				for (String line : lines){
					GeometricalObject object = fromLine(line);
					drawingModel.add(object);
				}
				drawingModel.setChanged(false);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
					JVDraw.this, 
					"Unable to read file.",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}

	};

	/** The save action. */
	private Action saveAction = new AbstractAction() {
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (drawingModel.getPath() == null){
				saveAsAction.actionPerformed(e);
			} else {
				drawingModel.save();
			}
		}
	};

	/** The save as action. */
	private Action saveAsAction = new AbstractAction() {
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save file");
			
			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION){
				return;
			}
			
			File fileName = new File(fc.getSelectedFile().toString() + ".jvd");
			Path filePath = fileName.toPath();
			
			if (Files.exists(filePath)){
				int answer = JOptionPane.showConfirmDialog(
					JVDraw.this, 
					"Overwrite", 
					"The selected file already exists, would you like to overwrite it?", 
					JOptionPane.YES_NO_OPTION
				);
				
				if (answer == JOptionPane.NO_OPTION){
					return;
				}
			}
			
			drawingModel.setPath(filePath);
			saveAction.actionPerformed(e);
		}
	};
	
	/** The exit action. */
	private Action exitAction = new AbstractAction() {
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (drawingModel.hasChanged()){
				int answer = JOptionPane.showConfirmDialog(
					JVDraw.this, 
					"The document has been changed, would you like to save it?", 
					"Would you like to save?", 
					JOptionPane.YES_NO_CANCEL_OPTION
				);
				
				if (answer == JOptionPane.YES_OPTION){
					saveAction.actionPerformed(e);
				} else if (answer == JOptionPane.CANCEL_OPTION){
					return;
				}
			} 
			dispose();
		}
	};
	
	/** The export action. */
	private Action exportAction = new AbstractAction() {
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Export");
			
			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION){
				return;
			}
			
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!fileName.toString().endsWith(".png") && 
					!fileName.toString().endsWith(".jpg") &&
					!fileName.toString().endsWith(".gif")){
				
				JOptionPane.showConfirmDialog(
					JVDraw.this, 
					"The selected file extension is not supported, please use png, jpg or gif.", 
					"Unsupported", 
					JOptionPane.CANCEL_OPTION
				);
				return;
			}
			
			if (Files.exists(filePath)){
				int answer = JOptionPane.showConfirmDialog(
					JVDraw.this, 
					"The selected file already exists, would you like to overwrite it?", 
					"Overwrite", 
					JOptionPane.YES_NO_OPTION
				);
				
				if (answer == JOptionPane.NO_OPTION){
					return;
				}
			}
			
			Rectangle dimension = drawingModel.getBoundingBox();
			
			Point start = new Point(dimension.x, dimension.y);
			int height = dimension.height - start.getY();
			int width = dimension.width - start.getX();
			
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR ); 
			Graphics2D g = image.createGraphics(); 
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			
			int to = drawingModel.getSize();
			for (int i = 0; i < to; ++i){
				GeometricalObject object = drawingModel.getObject(i);

				Point oFrom = object.getFrom();
				Point oTo = object.getTo();
				
				object.setFrom(new Point(oFrom.getX() - start.getX(), oFrom.getY() - start.getY()));
				object.setTo(new Point(oTo.getX() - start.getX(), oTo.getY() - start.getY()));
				object.draw(g);
				object.setFrom(oFrom);
				object.setTo(oTo);
			}

			String extension = filePath.toString().substring(filePath.toString().length() - 3);
			try {
				ImageIO.write(image, extension, fileName);
			} catch (IOException e1) {
				JOptionPane.showConfirmDialog(
					JVDraw.this, 
					"Unable to save the picture :(", 
					"Error", 
					JOptionPane.CANCEL_OPTION
				);
			} 
		}
	};
	
	/**
	 * Creates a list of {@link GeometricalObject}s that is displayed on the
	 * right sde of the screen.
	 */
	private void createList() {
		ListModel<GeometricalObject> dataModel = new DrawingObjectListModel(drawingModel);
		
		JList<GeometricalObject> list = new JList<>(dataModel);
		getContentPane().add(list, BorderLayout.EAST);
		
		KeyListener keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE && list.getSelectedValue() != null){
					drawingModel.remove(list.getSelectedValue());
				}
			}
		};
		list.addKeyListener(keyListener);
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) return;
				
				GeometricalObject object = list.getSelectedValue();
				if (object != null){
					int answer = JOptionPane.showConfirmDialog(
						null, 
						object.getSettingsPanel(),
						"Edit object",
						JOptionPane.OK_CANCEL_OPTION
					);
					
					if (answer == JOptionPane.OK_OPTION){
						object.updateSettings();
						changedObject(object);
					}
				}
			}
		});
	}

	/**
	 * Creates the status bar.
	 */
	private void createStatusBar() {
		StatusLabel status = new StatusLabel(foregroundCol, backgroundCol);
		getContentPane().add(status, BorderLayout.SOUTH);
	}

	/**
	 * Creates the canvas.
	 */
	private void createCanvas() {
		canvas = new JDrawingCanvas();
		drawingModel.addDrawingModelListener(canvas);
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		MouseAdapter listener = new DrawMouseListener(this);
		canvas.addMouseListener(listener);
		canvas.addMouseMotionListener(listener);
	}

	/**
	 * Creates the toolbar.
	 */
	private void createToolbar() {
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new BorderLayout());
		
		JPanel colors = new JPanel();
		foregroundCol = new JColorArea(DEFAULT_FOREGROUND_COLOR);
		backgroundCol = new JColorArea(DEFAULT_BACKGROUND_COLOR);
		
		colors.add(foregroundCol);
		colors.add(backgroundCol);
		toolbar.add(colors, BorderLayout.WEST);
		
		
		JPanel buttons = new JPanel();
		ButtonGroup btnGroup = new ButtonGroup();
		lineBtn = new JToggleButton("Line");
		circleBtn = new JToggleButton("Circle");
		filledCircleBtn = new JToggleButton("Filled circle");
		
		btnGroup.add(lineBtn);
		btnGroup.add(circleBtn);
		btnGroup.add(filledCircleBtn);
		
		buttons.add(lineBtn);
		buttons.add(circleBtn);
		buttons.add(filledCircleBtn);
		
		lineBtn.setSelected(true);
		toolbar.add(buttons, BorderLayout.CENTER);
		
		getContentPane().add(toolbar, BorderLayout.NORTH);
	}
	
	/**
	 * Adds the object.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @return the geometrical object
	 */
	public GeometricalObject addObject(Point from, Point to){
		GeometricalObject object = null;
		if (lineBtn.isSelected()){
			object = new Line(from, to, foregroundCol.getCurrentColor());
		} else if (circleBtn.isSelected()) {
			object = new Circle(from, to, foregroundCol.getCurrentColor());
		} else {
			object = new FilledCircle(from, to, foregroundCol.getCurrentColor(), backgroundCol.getCurrentColor());
		}
		
		drawingModel.add(object);
		return object;
	}
	
	/**
	 * Changes an object.
	 *
	 * @param object the object
	 */
	public void changedObject(GeometricalObject object){
		drawingModel.change(object);
	}
	
	/**
	 * Removes the object.
	 *
	 * @param object the object
	 */
	public void removeObject(GeometricalObject object){
		drawingModel.remove(object);
	}
	
	/**
	 * Creates a new {@link GeometricalObject} from the given string line.
	 *
	 * @param line
	 *            the line
	 * @return the geometrical object
	 */
	private GeometricalObject fromLine(String line) {
		GeometricalObject object = null;
		
		line = line.trim();
		if (line.startsWith("LINE")){
			line = line.substring(5);
			object = Line.fromString(line);
		} else if (line.startsWith("CIRCLE")){
			line = line.substring(7);
			object = Circle.fromString(line);
		} else if (line.startsWith("FCIRCLE")){
			line = line.substring(8);
			object = FilledCircle.fromString(line);
		}
		
		return object;
	}
}
