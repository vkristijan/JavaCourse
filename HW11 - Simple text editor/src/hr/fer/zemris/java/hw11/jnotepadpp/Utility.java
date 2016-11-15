package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizationProvider;

/**
 * Utility class with helper methods for {@link JNotepadPP}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Utility {
	/**
	 * Reads all bytes from the given {@link InputStream}.
	 * 
	 * @param is
	 *            the {@link InputStream} to be read.
	 * @return a byte array containing all the data from the {@link InputStream}.
	 * @throws NullPointerException
	 *             if the given {@link InputStream} is null.
	 */
	public static byte[] readAllBytes(InputStream is) {
	    Objects.requireNonNull(is, "The given stream does not exist!");
	    
	    ByteArrayOutputStream data = new ByteArrayOutputStream();
	    try {
			byte[] buffer = new byte[512];
			int readSize = 0;
			
			while ((readSize = is.read(buffer)) != -1){
				data.write(buffer, 0, readSize);
			}

		} catch (IOException e) {
			System.err.println("Unable to read from the given input stream!");
			System.exit(1);
		}
	    
	    return data.toByteArray();
	}
	
	/**
	 * Inverts the case of the given character. If the character was in lower
	 * case, the method will return the upper case of the character. If the
	 * character was in upper case, the method will return the lower case of the
	 * character. If the character was not an alphabet letter, it will stay
	 * unchanged.
	 * 
	 * @param c
	 *            the character which case should be inverted.
	 * @return the character from the argument in inverted case.
	 */
	public static char invertCase(char c){
		if (Character.isLowerCase(c)){
			c = Character.toUpperCase(c);
		} else if (Character.isUpperCase(c)){
			c = Character.toLowerCase(c);
		}
		
		return c;
	}
	
	/**
	 * Returns the {@link Collator} based on the current language.
	 * 
	 * @return the {@link Collator} based on the current language.
	 */
	private static Collator getCollator(){
		Locale locale = LocalizationProvider.getInstance().getLocale();
		Collator collator = Collator.getInstance(locale);
		
		return collator;
	}
	
	/**
	 * Sorts the array of strings given in the argument, by following the order
	 * of the given {@link Comparator}. The result will be stored into a
	 * {@link List} that is returned.
	 * 
	 * @param lines
	 *            the array of strings that should be sorted.
	 * @param comparator
	 *            the {@link Comparator} used for the sorting.
	 * @return a {@link Collection} of {@link String}s. When iterating through
	 *         the collection, the elements will be in sorted order.
	 */
	private static Collection<String> sort(String[] lines, Comparator<Object> comparator){
		
		List<String> data = Arrays.asList(lines);
		data.sort(comparator);
		
		return data;
	}

	/**
	 * Sorts the array of strings given in the argument in ascending order. The
	 * result will be stored into a {@link List} that is returned.
	 * 
	 * @param lines
	 *            the array of strings that should be sorted.
	 * @return a {@link Collection} of {@link String}s. When iterating through
	 *         the collection, the elements will be in sorted order.
	 */
	public static Collection<String> sortAscending(String[] lines){
		
		Comparator<Object> comparator = getCollator();
		return sort(lines, comparator);
	}
	

	/**
	 * Sorts the array of strings given in the argument in descending order. The
	 * result will be stored into a {@link List} that is returned.
	 * 
	 * @param lines
	 *            the array of strings that should be sorted.
	 * @return a {@link Collection} of {@link String}s. When iterating through
	 *         the collection, the elements will be in sorted order.
	 */
	public static Collection<String> sortDescending(String[] lines){
		
		Comparator<Object> comparator = getCollator().reversed();
		return sort(lines, comparator);
	}
	
	/**
	 * Removes all the duplicate strings from the given array of strings. The
	 * result will be stored into a {@link List} that is returned.
	 * 
	 * @param lines
	 *            the array of strings that should be used for the operation.
	 * @return a {@link Collection} of {@link String}s. When iterating through
	 *         the collection, the elements will be in original order, but every
	 *         duplicate element will be deleted.
	 */
	public static Collection<String> unique(String[] lines){
		
		Set<String> data = new LinkedHashSet<>(Arrays.asList(lines));
		return data;
	}
	
	/**
	 * Creates a new {@link JSeparator} that is used to separate labels in
	 * status bar.
	 * 
	 * @return a new {@link JSeparator} that is used to separate labels in
	 *         status bar.
	 */
	public static JComponent getStatusSeparator(){
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(3, 15));
        return separator;
	}
}
