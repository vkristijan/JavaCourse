package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Stores the data about a text editor. Contains the {@link JTextArea} used as
 * editor, the path of the file and flag if the file was changed or not.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class EditorData {
	/**
	 * The {@link JTextArea} used as editor to the value given in the argument.
	 */
	private JTextArea editor;
	/**
	 * The path of the document.
	 */
	private Path openedFilePath;
	/**
	 * A flag storing if the document has been changed or not.
	 */
	private boolean hasChanged;

	/**
	 * Creates a new {@link EditorData} that stores the editor, file path and
	 * changed flag.
	 * 
	 * @param editor
	 *            the {@link JTextArea} used as editor to the value given in the
	 *            argument.
	 * @param openedFilePath
	 *            the path of the document.
	 * @param hasChanged
	 *            a flag storing if the document has been changed or not.
	 */
	public EditorData(JTextArea editor, Path openedFilePath, boolean hasChanged) {
		editor.setLineWrap(true);
		editor.setWrapStyleWord(true);
		
		this.editor = editor;
		this.openedFilePath = openedFilePath;
		this.hasChanged = hasChanged;
	}

	/**
	 * Returns the {@link JTextArea} used as editor to the value given in the
	 * argument.
	 * 
	 * @return the {@link JTextArea} used as editor to the value given in the
	 *         argument.
	 */
	public JTextArea getEditor() {
		return editor;
	}

	/**
	 * Sets the {@link JTextArea} used as editor to the value given in the
	 * argument.
	 * 
	 * @param editor
	 *            the {@link JTextArea} used as editor to the value given in the
	 *            argument.
	 */
	public void setEditor(JTextArea editor) {
		this.editor = editor;
	}

	/**
	 * Returns the path of the opened file.
	 * 
	 * @return the path of the opened file.
	 */
	public Path getOpenedFilePath() {
		return openedFilePath;
	}

	/**
	 * Sets the path of the opened file.
	 * 
	 * @param openedFilePath
	 *            the path of the file.
	 */
	public void setOpenedFilePath(Path openedFilePath) {
		this.openedFilePath = openedFilePath;
	}

	/**
	 * Returns the has changed flag.
	 * 
	 * @return the hash changed flag.
	 */
	public boolean getHasChanged() {
		return hasChanged;
	}

	/**
	 * Sets the changed flag to the value given in the argument.
	 * 
	 * @param hasChanged
	 *            the new value for the has changed flag.
	 */
	public void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((editor == null) ? 0 : editor.hashCode());
		result = prime * result + (hasChanged ? 1231 : 1237);
		result = prime * result + ((openedFilePath == null) ? 0 : openedFilePath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EditorData other = (EditorData) obj;
		if (editor == null) {
			if (other.editor != null) return false;
		} else if (!editor.equals(other.editor)) return false;
		if (hasChanged != other.hasChanged) return false;
		if (openedFilePath == null) {
			if (other.openedFilePath != null) return false;
		} else if (!openedFilePath.equals(other.openedFilePath)) return false;
		return true;
	}

}
