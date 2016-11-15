package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction of {@link ILocalizationProvider} that has listeners registered to
 * it and methods to add new listeners, remove listeners and notify them that a
 * change has occurred.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/**
	 * List of all the listeners interested in the change of localization.
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * Creates a new {@link AbstractLocalizationProvider}.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notifies all listeners that the localization has changed.
	 */
	public void fire(){
		for (ILocalizationListener listener : listeners){
			listener.localizationChanged();
		}
	}

}
