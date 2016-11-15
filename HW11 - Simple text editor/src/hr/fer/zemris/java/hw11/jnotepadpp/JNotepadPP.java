package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.components.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.components.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.components.LJToolBar;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.components.LocalizableAction;

/**
 * A text editor capable of some special features. 
 * 
 * <br>
 * The program is available in three languages:
 * <ul>
 * <li>English</li>
 * <li>German</li>
 * <li>Croatian</li>
 * </ul>
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class JNotepadPP extends JFrame {
	/**
	 * Default serial version ID number used for serialization.
	 */
	private static final long serialVersionUID = -7979971628441460706L;
	/**
	 * A list of all currently open {@link EditorData}.
	 */
	private List<EditorData> editors;
	/**
	 * A list of all {@link Action}s that should be disabled if there is
	 * nothing currently selected.
	 */
	private List<Action> actionsToDisable;
	
	/**
	 * The currently open editor.
	 */
	private EditorData openEditor;
	/**
	 * The {@link JTabbedPane} used to display multiple documents in different
	 * tabs.
	 */
	private JTabbedPane tabbedPane;
	
	/**
	 * The {@link JLabel} used to display the text length in the status bar.
	 */
	private JLabel lengthLabel;
	
	/**
	 * The {@link JLabel} used to display the statistics in the status bar.
	 */
	private JLabel currentStatsLabel;
	
	/**
	 * The icon of saved document.
	 */
	private static Icon savedDocumentIcon;
	
	/**
	 * The icon of the edited document.
	 */
	private static Icon editedDocumentIcon;

	/**
	 * The {@link FormLocalizationProvider} used to localize all the elements on
	 * this {@link JFrame}.
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * Calculates the length of the document and updates the text in the label in
	 * the status bar.
	 */
	private void calculateLengthLabel() {
		int len = openEditor.getEditor().getDocument().getLength();
		lengthLabel.setText(String.valueOf(len));
	}

	/**
	 * Calculates the data for the statistic label that displays the info about
	 * the caret position, and updates the text in the label.
	 */
	private void calculateStatsLabel() {
		JTextArea editor = openEditor.getEditor();

		int caretPosition = editor.getCaret().getDot();
		int line = 0;
		int column = 0;
		try {
			line = editor.getLineOfOffset(caretPosition);
			column = caretPosition - editor.getLineStartOffset(line);
		} catch (BadLocationException e1) {
			System.err.println("Error during calculating statistics!");
			System.exit(1);
		}

		int caretMark = editor.getCaret().getMark();
		int len = Math.abs(caretMark - caretPosition);

		StringBuilder string = new StringBuilder();
		string.append("Ln: ");
		string.append(line);

		string.append("   Col: ");
		string.append(column);

		if (len > 0) {
			string.append("   Sel: ");
			string.append(len);
		}

		currentStatsLabel.setText(string.toString());
	}
	
	/**
	 * Enables or disables the actions from the {@link #actionsToDisable} list,
	 * depending on the selection. If a selection exists, the actions will be
	 * enabled, if no selection exists, the actions will be disabled.
	 */
	private void actionsDisable(){
		JTextArea editor = openEditor.getEditor();
		int len = editor.getSelectionEnd() - editor.getSelectionStart();
		
		for (Action action : actionsToDisable){
			action.setEnabled(len != 0);
		}
	}
	
	/**
	 * Creates a new instance of the {@link JNotepadPP} program.
	 */
	public JNotepadPP() {
		editors = new ArrayList<>();
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);;

		savedDocumentIcon = loadIcon("icons/saved.png");
		editedDocumentIcon = loadIcon("icons/edited.png");
		
		initGUI();
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
	}

	/**
	 * Loads the {@link Icon} from the given path.
	 * 
	 * @param relativePath
	 *            the relative path of the icon.
	 * @return the icon loaded.
	 */
	private Icon loadIcon(String relativePath){
		InputStream is = this.getClass().getResourceAsStream(relativePath); 
		byte[] bytes = Utility.readAllBytes(is);
		try {
			is.close();
		} catch (IOException e) {
			System.err.println(flp.getString("streamErr"));
			System.exit(1);
		}
		
		return new ImageIcon(bytes);
	}
	
	/**
	 * Initializes the graphical user interface and creates all the components
	 * in it.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		cp.add(centerPanel, BorderLayout.CENTER);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	int index = tabbedPane.getSelectedIndex();
	        	if (index < 0) return;
	        	openEditor = editors.get(index);
	        	calculateLengthLabel();
	        	calculateStatsLabel();
	        	actionsDisable();
	        	
	        	Path filePath = openEditor.getOpenedFilePath();
	        	String title = filePath == null ? "Untitled" : filePath.toAbsolutePath().toString();
	        	title += " - JNotepad++";
	        	setTitle(title);
	        }
	    });
		
		centerPanel.add(tabbedPane, BorderLayout.CENTER);
		centerPanel.add(createStatusBar(), BorderLayout.SOUTH);
		
		createActions();
		populateActionsToDisable();
		createMenus();
		createToolbars();
		
		createNewDocumentAction.actionPerformed(null);
		
	}
	
	/**
	 * Adds the actions that should be enabled and disabled to the list of
	 * disabled actions.
	 */
	private void populateActionsToDisable(){
		actionsToDisable = new LinkedList<>();
		
		actionsToDisable.add(cutAction);
		actionsToDisable.add(copyAction);
		actionsToDisable.add(uniqueAction);
		actionsToDisable.add(invertCaseAction);
		actionsToDisable.add(toUpperCaseAction);
		actionsToDisable.add(toLowerCaseAction);
		actionsToDisable.add(sortAscendingAction);
		actionsToDisable.add(sortDescendingAction);
	}
	
	/**
	 * Creates a new component that is used as a status bar. The status bar
	 * displays 3 sections of text. The length of the document, on the left
	 * side. The caret position in the center and the current time in the right
	 * side.
	 * 
	 * @return the new component used as status bar.
	 */
	private JComponent createStatusBar(){
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel lengthText = new LJLabel("length", flp);
		lengthLabel = new JLabel("0");
		
		JPanel lengthPanel = new JPanel();
		lengthPanel.add(lengthText);
		lengthPanel.add(lengthLabel);
		lengthPanel.add(Utility.getStatusSeparator());
		panel.add(lengthPanel, BorderLayout.LINE_START);
		
		JPanel centerPanel = new JPanel();
		currentStatsLabel = new JLabel("Ln: 0   Col: 0");
		
	    centerPanel.add(Utility.getStatusSeparator());
	    centerPanel.add(currentStatsLabel);
	    centerPanel.add(Utility.getStatusSeparator());
		
		panel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel clockPanel = new JPanel();
		ClockLabel clockLabel = new ClockLabel();
		clockPanel.add(Utility.getStatusSeparator());
		clockPanel.add(clockLabel);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				clockLabel.stop();
			}
		});
		
		panel.add(clockPanel, BorderLayout.LINE_END);
		return panel;
	}
	
	/**
	 * Sets the icon for the open tab.
	 */
	private void setIcon(){
		int index = tabbedPane.getSelectedIndex();
		EditorData data = editors.get(index);
		
		if (data.getHasChanged()){
			tabbedPane.setIconAt(index, editedDocumentIcon);
		} else {
			tabbedPane.setIconAt(index, savedDocumentIcon);
		}
	}

	/**
	 * Creates all the actions and defines their mnemonic keys and accelerator keys.
	 */
	private void createActions() {
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		saveDocumentAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveDocumentAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		createNewDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		closeTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		
		statisticAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		statisticAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		
		toLowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt L"));
		toLowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		
		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt U"));
		toUpperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		
		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt I"));
		invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		
		englishLanguage.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt shift E"));
		englishLanguage.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		germanLanguage.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt shift D"));
		germanLanguage.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		
		croatianLanguage.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt shift H"));
		croatianLanguage.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		
		sortAscendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt F1"));
		sortAscendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		sortDescendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt F2"));
		sortDescendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt F3"));
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
	}

	/**
	 * Creates the menu bar and adds all the items.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new LJMenu("LJMenu", flp);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(createNewDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeTabAction));
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new LJMenu("LJEdit", flp);
		editMenu.setMnemonic(KeyEvent.VK_E);
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(statisticAction));
		
		JMenuItem toolsMenu = new LJMenu("LJTools", flp);
		toolsMenu.setMnemonic(KeyEvent.VK_T);
		menuBar.add(toolsMenu);

		JMenuItem changeCaseMenu = new LJMenu("LJChange", flp);
		changeCaseMenu.setMnemonic(KeyEvent.VK_H);
		toolsMenu.add(changeCaseMenu);
		
		changeCaseMenu.add(new JMenuItem(toLowerCaseAction));
		changeCaseMenu.add(new JMenuItem(toUpperCaseAction));
		changeCaseMenu.add(new JMenuItem(invertCaseAction));
		
		JMenuItem sortingMenu = new LJMenu("LJSort", flp);
		sortingMenu.setMnemonic(KeyEvent.VK_S);
		toolsMenu.add(sortingMenu);
		
		sortingMenu.add(new JMenuItem(sortAscendingAction));
		sortingMenu.add(new JMenuItem(sortDescendingAction));
		
		toolsMenu.add(new JMenuItem(uniqueAction));
		
		JMenuItem languageMenu = new LJMenu("LJLanguage", flp);
		languageMenu.setMnemonic(KeyEvent.VK_L);
		menuBar.add(languageMenu);
		
		languageMenu.add(new JMenuItem(englishLanguage));
		languageMenu.add(new JMenuItem(germanLanguage));
		languageMenu.add(new JMenuItem(croatianLanguage));
		
		this.setJMenuBar(menuBar);
	}

	/**
	 * Creates the toolbars and adds toolbar items to it.
	 */
	private void createToolbars() {
		JToolBar toolBar = new LJToolBar("LJToolbar", flp);
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveDocumentAsAction));
		toolBar.add(new JButton(createNewDocumentAction));
		toolBar.add(new JButton(closeTabAction));
		toolBar.add(new JButton(exitAction));
		
		toolBar.addSeparator();
		
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));
		
		toolBar.addSeparator();
		
		toolBar.add(new JButton(statisticAction));
		
		toolBar.addSeparator();
		
		toolBar.add(new JButton(toLowerCaseAction));
		toolBar.add(new JButton(toUpperCaseAction));
		toolBar.add(new JButton(invertCaseAction));
		
		toolBar.addSeparator();
		
		toolBar.add(new JButton(sortAscendingAction));
		toolBar.add(new JButton(sortDescendingAction));
		toolBar.add(new JButton(uniqueAction));
		
		toolBar.addSeparator();
		
		toolBar.add(new JButton(englishLanguage));
		toolBar.add(new JButton(germanLanguage));
		toolBar.add(new JButton(croatianLanguage));
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            console arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(JNotepadPP::new);
	}
	
	
	/**
	 * Sorts the selection in ascending order.
	 */
	private Action sortAscendingAction = new LocalizableAction("sortAscending", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = 7899368972956034799L;

		@Override
		public void actionPerformed(ActionEvent e) {
			performSortingAction(Utility::sortAscending);
		}
	};
	
	/**
	 * Sorts the selection in descending order.
	 */
	private Action sortDescendingAction = new LocalizableAction("sortDescending", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = -7231204954176137123L;

		@Override
		public void actionPerformed(ActionEvent e) {
			performSortingAction(Utility::sortDescending);
		}
	};
	
	/**
	 * Removes duplicate values from the selection.
	 */
	private Action uniqueAction = new LocalizableAction("unique", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = 4212900833198118530L;

		@Override
		public void actionPerformed(ActionEvent e) {
			performSortingAction(Utility::unique);
		}
	};
	
	/**
	 * Sorts the currently selected text following the {@link Function} given in
	 * the argument.
	 * 
	 * @param function
	 *            the {@link Function} used to sort the values.
	 */
	private void performSortingAction(Function<String[], Collection<String>> function){
		JTextArea editor = openEditor.getEditor();
		try {
			
			int firstLine = editor.getSelectionStart();
			firstLine = editor.getLineOfOffset(firstLine);
			firstLine = editor.getLineStartOffset(firstLine);

			int lastLine = editor.getSelectionEnd();
			lastLine = editor.getLineOfOffset(lastLine);
			lastLine = editor.getLineEndOffset(lastLine);
			
			int length = lastLine - firstLine;
			String text = editor.getDocument().getText(firstLine, length);
			editor.getDocument().remove(firstLine, length);
			
			String[] data = text.split("[\\n\\r]");
			
			Collection<String> lines = function.apply(data);
			
			StringBuilder string = new StringBuilder();
			lines.forEach((x) -> string.append(x).append("\n"));
			
			editor.getDocument().insertString(firstLine, string.toString(), null);
		} catch (BadLocationException e1) {
			JOptionPane.showMessageDialog(
				JNotepadPP.this, 
				flp.getString("selectionErr"), 
				flp.getString("error"), 
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
	
	/**
	 * Opens a new document in a new tab.
	 */
	private Action openDocumentAction = new LocalizableAction("open", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = 6838445036541101249L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("openFile"));
			
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION){
				return;
			}
			
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)){
				JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					flp.getString("fileNotExistErr") + fileName.getAbsolutePath() + flp.getString("fileNotExistErr2"),
					flp.getString("error"),
					JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			byte[] data = null;
			try {
				data = Files.readAllBytes(filePath);
			} catch(IOException ex){
				JOptionPane.showMessageDialog(
					JNotepadPP.this,
					flp.getString("cantOpenErr") + fileName.getAbsolutePath() + flp.getString("cantOpenErr2"),
					flp.getString("error"),
					JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			String text = new String(data, StandardCharsets.UTF_8);
			
			if (!openEditor.getHasChanged() && openEditor.getEditor().getDocument().getLength() == 0){
				closeTabAction.actionPerformed(null);
			} 
			
			JTextArea textArea = new JTextArea(text);
			openEditor = new EditorData(textArea, filePath, false);
			createTab(openEditor);
		}
	};
	
	/**
	 * Saves the current open document. If the document was never saved before,
	 * the user will be asked to choose the save location and file name.
	 */
	private Action saveDocumentAction = new LocalizableAction("save", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = 242391791359264362L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (openEditor.getOpenedFilePath() == null){
				saveDocumentAsAction.actionPerformed(e);
				return;
			}
			
			byte[] data = openEditor.getEditor().getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openEditor.getOpenedFilePath(), data);
			} catch (IOException ex){
				JOptionPane.showMessageDialog(
					JNotepadPP.this,
					flp.getString("saveErr"),
					flp.getString("error"),
					JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			JOptionPane.showMessageDialog(
				JNotepadPP.this, 
				flp.getString("saveSuccess"), 
				flp.getString("saveFile"), 
				JOptionPane.INFORMATION_MESSAGE
			);
			openEditor.setHasChanged(false);
			setIcon();
		}
	};
	
	/**
	 * Saves the current open document on a new location. If the selected
	 * destination already exists, the user will be asked to confirm if he wants
	 * to overwrite the document or not.
	 */
	private Action saveDocumentAsAction = new LocalizableAction("saveAs", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = -4046949428633726107L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("saveFile"));
			
			if (fc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION){
				return;
			}
			
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			
			if (Files.exists(filePath)){
				int answer = JOptionPane.showConfirmDialog(
					JNotepadPP.this, 
					filePath.getFileName() + flp.getString("saveOverwrite"), 
					flp.getString("saveConfirm"), 
					JOptionPane.YES_NO_OPTION
				);
				
				if (answer == JOptionPane.NO_OPTION){
					return;
				}
			}
			
			openEditor.setOpenedFilePath(filePath);
			saveDocumentAction.actionPerformed(e);
			
			int index = tabbedPane.getSelectedIndex();
			String tabTitle = filePath == null ? "Untitled" : filePath.getFileName().toString();
        	tabbedPane.setTitleAt(index, tabTitle);
        	String toolTipText = fileName.getAbsolutePath().toString();
        	tabbedPane.setToolTipTextAt(index, toolTipText);
		}
	};
	
	/**
	 * Creates a new blank document and opens a new tab for it.
	 */
	private Action createNewDocumentAction = new LocalizableAction("new", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = 3264730594633560676L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = new JTextArea();
			EditorData data = new EditorData(textArea, null, false);
			
			createTab(data);
		}
	};
	
	/**
	 * Closes the current tab. If the tab contains a document that is not saved,
	 * the user will be asked if he wants to save it or not.
	 */
	private Action closeTabAction = new LocalizableAction("close", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = 4826369996330200642L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (openEditor.getHasChanged()){
				int answer = JOptionPane.showConfirmDialog(
					JNotepadPP.this, 
					flp.getString("closeSave"), 
					flp.getString("closeConfirm"), 
					JOptionPane.YES_NO_CANCEL_OPTION
				);
				
				if (answer == JOptionPane.YES_OPTION){
					saveDocumentAction.actionPerformed(null);
				} else if (answer == JOptionPane.CANCEL_OPTION){
					return;
				}
			}

			lengthLabel.setText("0");
			currentStatsLabel.setText(flp.getString("noDoc"));
			
			int index = tabbedPane.getSelectedIndex();
			editors.remove(index);
			tabbedPane.remove(index);
		}
	};
	
	/**
	 * Closes all the tabs in the application and exits the application. If
	 * there is an tab containing an unsaved document, the user will be asked if
	 * he wants to save it or not before closing.
	 */
	private Action exitAction = new LocalizableAction("exit", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = -4896246742740570823L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int tabCount = tabbedPane.getTabCount();
			
			for (int i = 0; i < tabCount; ++i){
				tabbedPane.setSelectedIndex(0);
				closeTabAction.actionPerformed(null);
				
				if (tabbedPane.getTabCount() > tabCount - i - 1){
					return;
				}
			}
			dispose();
		}
	};
	
	/**
	 * Cuts the selected text to the clipboard.
	 */
	private Action cutAction = new LocalizableAction("cut", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = -5775805310009759515L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Action privateAction = new DefaultEditorKit.CutAction();
			privateAction.actionPerformed(e);
		}
	};
	
	/**
	 * Copies the selected text to the clipboard.
	 */
	private Action copyAction = new LocalizableAction("copy", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = -3106915201463094864L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Action privateAction = new DefaultEditorKit.CopyAction();
			privateAction.actionPerformed(e);
		}
	};
	
	/**
	 * Pastes the text from the clipboard to the currently caret position.
	 */
	private Action pasteAction = new LocalizableAction("paste", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = -791757490282728941L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Action privateAction = new DefaultEditorKit.PasteAction();
			privateAction.actionPerformed(e);
		}
	};
	
	/**
	 * Calculates the statistics for the currently open document. The statistics
	 * include the number of characters in the document, number of non blank
	 * character and number of lines.
	 */
	private Action statisticAction = new LocalizableAction("statistics", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = 7986411386183308071L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = openEditor.getEditor();
			
			int numberOfCharacters = editor.getDocument().getLength();
			int numberNonBlankChars = 0;
			int numberOfLines = editor.getLineCount();
			
			char[] text = editor.getText().toCharArray();
			for (char currentChar : text){
				if (!Character.isWhitespace(currentChar)){
					numberNonBlankChars++;
				}
			}
			
			StringBuilder message = new StringBuilder();
			message.append(flp.getString("stats1") + numberOfCharacters + "\n");
			message.append(flp.getString("stats2") + numberNonBlankChars + "\n");
			message.append(flp.getString("stats3") + numberOfLines + "\n");
			
			JOptionPane.showMessageDialog(
				JNotepadPP.this, 
				message.toString(), 
				flp.getString("statsTitle"), 
				JOptionPane.INFORMATION_MESSAGE
			);
		}
	};
	
	/**
	 * Changes the case of the selected text in such a way that every upper case
	 * character will be replaced with a lower case character.
	 */
	private Action toLowerCaseAction = new ChangeCaseAction("lower", Character::toLowerCase);
	/**
	 * Changes the case of the selected text in such a way that every lower case
	 * character will be replaced with a upper case character.
	 */
	private Action toUpperCaseAction = new ChangeCaseAction("upper", Character::toUpperCase);
	/**
	 * Changes the case of the selected text in such a way that every upper case
	 * character will be changed with a lower case one, and every lower case
	 * character will be replaced with a upper case character.
	 */
	private Action invertCaseAction = new ChangeCaseAction("invert", Utility::invertCase);
	
	/**
	 * Implements methods that change the text case of the selected text.
	 * 
	 * @author Kristijan Vulinovic
	 * @version 1.0
	 */
	private class ChangeCaseAction extends LocalizableAction {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = 7456530306082029399L;
		/**
		 * The {@link Function} used to change the case of a single character.
		 */
		private Function<Character, Character> operation;

		/**
		 * Creates new {@link ChangeCaseAction} with the given localization key,
		 * and the {@link Function} used to change the case of a single
		 * character.
		 * 
		 * @param key
		 *            the key used in localization.
		 * @param operation
		 *            {@link Function} used to change the case of a single
		 *            letter.
		 */
		public ChangeCaseAction(String key, Function<Character, Character> operation){
			super(key, flp);
			
			this.operation = operation;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea editor = openEditor.getEditor();
			Document doc = editor.getDocument();
			
			int caretDot = editor.getCaret().getDot();
			int caretMark = editor.getCaret().getMark();
			int len = Math.abs(caretDot - caretMark);
			int offset = 0;
			
			if (len == 0){
				return;
			} else {
				offset = Math.min(caretDot, caretMark);
			}
			
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
				
				editor.setSelectionStart(offset);
				editor.setSelectionEnd(Math.max(caretMark, caretDot));
			} catch (BadLocationException ex){
				ex.printStackTrace();
			}
		}
		
		/**
		 * Changes the character casing of the given text, by using the
		 * operation specified in the constructor.
		 * 
		 * @param text
		 *            the text which casing should be changed.
		 * @return a new text with changed casing.
		 */
		protected String changeCase(String text) {
			
			char[] znakovi = text.toCharArray();
			for (int i = 0; i < znakovi.length; ++i) {
				char c = znakovi[i];
				znakovi[i] = operation.apply(c);
			}

			return new String(znakovi);
		}
	};
	
	/**
	 * Changes the currently selected language to English.
	 */
	private Action englishLanguage = new LocalizableAction("eng", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = -7444161833121784050L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("eng");
		}
	};

	/**
	 * Changes the currently selected language to German.
	 */
	private Action germanLanguage = new LocalizableAction("de", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = 3182008706473751060L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};

	/**
	 * Changes the currently selected language to Croatian.
	 */
	private Action croatianLanguage = new LocalizableAction("hr", flp) {
		/**
		 * Default serial version ID number used for serialization.
		 */
		private static final long serialVersionUID = 1659412356605637924L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	
	/**
	 * Creates a new tab that is added to the {@link JTabbedPane}. The tab will
	 * have the {@link EditorData} from the argument in it.
	 * 
	 * @param data
	 *            the {@link EditorData} that should be displayed in this tab.
	 */
	private void createTab(EditorData data) {
		editors.add(data);
		
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(data.getEditor());
		panel.add(scrollPane, BorderLayout.CENTER);
		
		Document doc = data.getEditor().getDocument();
		doc.addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				documentChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				documentChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				documentChanged();
			}

			private void documentChanged() {
				calculateLengthLabel();
				if (!openEditor.getHasChanged()) {
					openEditor.setHasChanged(true);
					setIcon();
				}
			}
		});
		
		data.getEditor().addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				calculateStatsLabel();
				actionsDisable();
			}
		});
		
		String title = "Untitled";
		String toolTipText = "Untitled";
		Path dataPath = data.getOpenedFilePath();
		if (dataPath != null){
			title = dataPath.getFileName().toString();
			toolTipText = dataPath.toAbsolutePath().toString();
		}
		
		tabbedPane.addTab(title, savedDocumentIcon, panel);
		int index = tabbedPane.getTabCount() - 1;

    	tabbedPane.setToolTipTextAt(index, toolTipText);
	}
}
