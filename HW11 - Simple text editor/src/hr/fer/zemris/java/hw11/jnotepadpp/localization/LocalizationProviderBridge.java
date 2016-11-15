package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * <a href="https://en.wikipedia.org/wiki/Decorator_pattern">Decorator</a> for
 * {@link ILocalizationProvider} that uses another {@link ILocalizationProvider}
 * to get the localized string and provides the functionality to connect itself
 * to the {@link ILocalizationProvider} and disconnect.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * A boolean flag, remembering the state of connection. Used to ensure that
	 * the listener is only connected once.
	 */
	private boolean connected;
	/**
	 * The {@link ILocalizationProvider} used to get localized string.
	 */
	private ILocalizationProvider parent;

	/**
	 * The listener used to register this component to the parent
	 * {@link ILocalizationProvider}.
	 */
	private ILocalizationListener listener;

	/**
	 * Creates a new {@link LocalizationProviderBridge} that used the given
	 * parent localization provider to get the localized strings.
	 * 
	 * @param parent
	 *            the {@link ILocalizationProvider} used to get localized
	 *            string.
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;

		this.listener = () -> fire();
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	/**
	 * Connects this localization provider to the parent.
	 */
	public void connect() {
		if (!connected) {
			connected = true;
			parent.addLocalizationListener(listener);
		}
	}

	/**
	 * Disconnects this localization provider from the parent, allowing the
	 * garbage collector to remove the object that requested localization if it
	 * is not in use anymore.
	 */
	public void discnnect() {
		if (connected) {
			connected = false;
			parent.removeLocalizationListener(listener);
		}
	}
}
