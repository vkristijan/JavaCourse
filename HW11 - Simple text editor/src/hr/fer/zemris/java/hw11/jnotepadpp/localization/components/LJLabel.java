package hr.fer.zemris.java.hw11.jnotepadpp.localization.components;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Wrapper for {@link JLabel} that can be localized using
 * {@link ILocalizationProvider}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class LJLabel extends JLabel {
	/**
	 * Default serial version ID number used for serialization.
	 */
	private static final long serialVersionUID = -2802494827765282700L;
	
	/**
	 * The {@link ILocalizationProvider} used to get the localizations.
	 */
	private ILocalizationListener listener;
	
	/**
	 * Creates new instance of {@link LJLabel} that uses the given key
	 * to get the localized name and description, from the given
	 * {@link ILocalizationProvider}.
	 * 
	 * @param key
	 *            the key used to access the localizations.
	 * @param lp
	 *            the {@link ILocalizationProvider} used to get the
	 *            localizations.
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(lp.getString(key));
			}
		};
		
		listener.localizationChanged();
		lp.addLocalizationListener(listener);
	}
}
