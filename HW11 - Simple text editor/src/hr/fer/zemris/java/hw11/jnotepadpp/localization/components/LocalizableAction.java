package hr.fer.zemris.java.hw11.jnotepadpp.localization.components;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.ILocalizationProvider;

/**
 * Wrapper for {@link AbstractAction} that can be localized using
 * {@link ILocalizationProvider}.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public abstract class LocalizableAction extends AbstractAction {
	/**
	 * Default serial version ID number used for serialization.
	 */
	private static final long serialVersionUID = 4952812929911144093L;
	
	/**
	 * The {@link ILocalizationProvider} used to get the localizations.
	 */
	private ILocalizationListener listener;

	/**
	 * Creates new instance of {@link LocalizableAction} that uses the given key
	 * to get the localized name and description, from the given
	 * {@link ILocalizationProvider}.
	 * 
	 * @param key
	 *            the key used to access the localizations.
	 * @param lp
	 *            the {@link ILocalizationProvider} used to get the
	 *            localizations.
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		String descriptionKey = key + "Description";

		listener = new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(Action.NAME, lp.getString(key));
				putValue(Action.SHORT_DESCRIPTION, lp.getString(descriptionKey));
			}
		};

		listener.localizationChanged();
		lp.addLocalizationListener(listener);
	}
}
