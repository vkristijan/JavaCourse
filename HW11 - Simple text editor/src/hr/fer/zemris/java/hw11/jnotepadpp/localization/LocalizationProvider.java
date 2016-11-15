package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * {@link ILocalizationProvider} for {@link JNotepadPP}. Implemented as
 * <a href="https://en.wikipedia.org/wiki/Singleton_pattern">Singleton</a>. The
 * only instance of this class can be accessed through static getter. <br>
 * Currently supported languages are:
 * <ul>
 * <li><strong>en</strong> - English</li>
 * <li><strong>de</strong> - German</li>
 * <li><strong>hr</strong> - Croatian</li>
 * </ul>
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * The instance of this {@link LocalizationProvider}.
	 */
	private static LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * The currently selected language. The language is defined by the language
	 * code (<strong>en</strong> English, <strong>de</strong> German and
	 * <strong>hr</strong> Croatian.
	 */
	private String language;
	/**
	 * The {@link Locale} of the current language.
	 */
	private Locale locale;
	/**
	 * {@link ResourceBundle} used for text localization.
	 */
	private ResourceBundle bundle;
	
	/**
	 * Creates a new instance of {@link LocalizationProvider}, and sets the
	 * language to English.
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	/**
	 * Returns the instance of the {@link LocalizationProvider}.
	 * 
	 * @return the instance of the {@link LocalizationProvider}.
	 */
	public static LocalizationProvider getInstance(){
		return instance;
	}
	
	/**
	 * Sets the language to the one given in the argument.
	 * 
	 * @param language
	 *            the language code of the language that should be used.
	 */
	public void setLanguage(String language){
		if (language.equals(this.language)){
			return;
		}
		this.language = language;
		
		locale = Locale.forLanguageTag(this.language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.localization.prijevodi", locale);
		fire();
	}
	
	/**
	 * Returns the {@link Locale} of the language currently in use.
	 * 
	 * @return the {@link Locale} of the language currently in use.
	 */
	public Locale getLocale(){
		return locale;
	}
	
	@Override
	public String getString(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return " - " + key + " - ";
		}
	}
}
