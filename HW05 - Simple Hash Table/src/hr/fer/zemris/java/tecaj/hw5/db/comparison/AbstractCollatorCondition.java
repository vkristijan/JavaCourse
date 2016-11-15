package hr.fer.zemris.java.tecaj.hw5.db.comparison;

import java.text.Collator;
import java.util.Locale;

/**
 * A comparison operator that compares the lexicographic order of two
 * {@code String}s, by following the order in the Croatian alphabet.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public abstract class AbstractCollatorCondition implements IComparisonOperator {
	/**
	 * The {@link Collator} that is used to compare two {@code String}s.
	 */
	protected Collator collator;

	/**
	 * Creates a new {@code AbstractCollatorCondition} with a {@code Collator}
	 * for the Croatian language.
	 */
	protected AbstractCollatorCondition() {
		super();
		Locale loc = new Locale("hr", "HR");
		collator = Collator.getInstance(loc);
	}

}
