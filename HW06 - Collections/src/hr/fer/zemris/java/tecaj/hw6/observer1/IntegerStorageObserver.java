package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * An observer for {@link IntegerStorage}. This can be used to inform an
 * {@link Object} that a change in {@link IntegerStorage} has occurred.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public interface IntegerStorageObserver {
	/**
	 * This method is called every time the observed {@link IntegerStorage} is
	 * changed.
	 * 
	 * @param istorage
	 *            the {@link IntegerStorage} that was changed.
	 */
	public void valueChanged(IntegerStorage istorage);
}
