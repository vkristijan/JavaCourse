package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * Listener for {@link ILocalizationProvider}. Every time the localization is
 * changed the listener will be notified.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface ILocalizationListener {
	
	/**
	 * The method called once the localization has changed.
	 */
	void localizationChanged();
}
