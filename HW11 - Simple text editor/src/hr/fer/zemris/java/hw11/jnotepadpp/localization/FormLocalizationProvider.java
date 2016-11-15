package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * {@link ILocalizationProvider} used to register a form and get localizations
 * for elements on the form.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	/**
	 * Creates a new {@link FormLocalizationProvider} that is attached to the
	 * given {@link JFrame}. Once the windows opens the localization provider
	 * will connect itself to the parent localization provider. When the windows
	 * closes,the localization provider will disconnect itself so that garbage
	 * collector can delete it.
	 * 
	 * @param parent
	 *            the {@link ILocalizationProvider} that should be used for
	 *            localization.
	 * @param frame
	 *            the {@link JFrame} that created the
	 *            {@link FormLocalizationProvider}.
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				discnnect();
			}
		});
	}

}
