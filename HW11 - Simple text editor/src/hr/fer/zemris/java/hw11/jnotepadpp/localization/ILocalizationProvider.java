package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * Interface for a Localization provider that can be used for
 * internationalization of strings. Defines methods to add and remove listeners,
 * and to get the string for a given key.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface ILocalizationProvider {
	/**
	 * Adds the listener to this Localization provider.
	 * 
	 * @param listener
	 *            the listener to be added.
	 */
	void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Removes the listener from this Localization provider.
	 * 
	 * @param listener
	 *            the listener to be removed.
	 */
	void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Returns the {@link String} value defined for the key. If there is no
	 * defined localization for the given string in the current language, the
	 * key will be returned, surrounded with '-' characters.
	 * 
	 * @param key
	 *            the key to find the localization string.
	 * @return the {@link String} value defined for the key.
	 */
	String getString(String key);
}
