package hr.fer.zemris.java.hw11.jnotepadpp.localization.components;

import javax.swing.JToolBar;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Wrapper for {@link JToolBar} that can be localized using
 * {@link ILocalizationProvider}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class LJToolBar extends JToolBar {
	/**
	 * Default serial version ID number used for serialization.
	 */
	private static final long serialVersionUID = -3190747434135570016L;
	
	/**
	 * The {@link ILocalizationProvider} used to get the localizations.
	 */
	private ILocalizationListener listener;
	
	/**
	 * Creates new instance of {@link LJToolBar} that uses the given key
	 * to get the localized name and description, from the given
	 * {@link ILocalizationProvider}.
	 * 
	 * @param key
	 *            the key used to access the localizations.
	 * @param lp
	 *            the {@link ILocalizationProvider} used to get the
	 *            localizations.
	 */
	public LJToolBar(String key, ILocalizationProvider lp) {
		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setName(lp.getString(key));
			}
		};
		
		listener.localizationChanged();
		lp.addLocalizationListener(listener);
	}
	
}
