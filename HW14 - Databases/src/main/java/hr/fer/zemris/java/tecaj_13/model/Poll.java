package hr.fer.zemris.java.tecaj_13.model;

import java.util.Objects;

/**
 * Object describing a poll in the database. A poll has its id number, title and
 * message.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class Poll {
	/**
	 * The poll unique identification number. Used as the primary key.
	 */
	private long id;
	/**
	 * The title of the poll.
	 */
	private String title;
	/**
	 * The message of the poll.
	 */
	private String message;

	/**
	 * Creates a new empty poll.
	 */
	public Poll() {
	}

	/**
	 * Returns the poll id number.
	 * 
	 * @return the poll id number.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the poll id number to the value from the argument.
	 * 
	 * @param id
	 *            the new value for the poll id number.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the poll title.
	 * 
	 * @return the poll title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the poll title to the one given in the argument. The title is
	 * not allowed to be null.
	 * 
	 * @param title
	 *            the new poll title.
	 * @throws NullPointerException
	 *             if the given title is null.
	 */
	public void setTitle(String title) {
		Objects.requireNonNull(title, "The poll title must not be null!");
		this.title = title;
	}

	/**
	 * Returns the poll message.
	 * 
	 * @return the poll message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the poll message to the one given in the argument. The message is
	 * not allowed to be null.
	 * 
	 * @param message
	 *            the new poll message.
	 * @throws NullPointerException
	 *             if the given message is null.
	 */
	public void setMessage(String message) {
		Objects.requireNonNull(title, "The poll message must not be null!");
		this.message = message;
	}

}
