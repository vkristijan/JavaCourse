package hr.fer.zemris.java.tecaj_13.model;

import java.util.Objects;

/**
 * Describes the poll option object from the database. A poll option has its id,
 * title, link, votes count and id of the poll in which the poll option exists.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class PollOption {
	/**
	 * The poll option id.
	 */
	private long id;
	/**
	 * The poll option title.
	 */
	private String optionTitle;
	/**
	 * The poll option link.
	 */
	private String optionLink;
	/**
	 * The id of the poll of this poll option.
	 */
	private long pollID;
	/**
	 * The votes count for the poll option.
	 */
	private long votesCount;

	/**
	 * Constructs a new poll option.
	 */
	public PollOption() {
		super();
	}

	/**
	 * Returns the poll option id number.
	 * 
	 * @return the poll option id number.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the poll option id number to the value given in the argument.
	 * 
	 * @param id
	 *            the new value for the poll id number.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the poll option title.
	 * 
	 * @return the poll option title.
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets the option title to the new value given in the argument. It i not
	 * allowed for the value to be null.
	 * 
	 * @param optionTitle
	 *            the new value for the option title.
	 * @throws NullPointerException
	 *             if the title is null.
	 */
	public void setOptionTitle(String optionTitle) {
		Objects.requireNonNull(optionTitle, "The poll option title must not be null!");
		this.optionTitle = optionTitle;
	}

	/**
	 * Returns the option link.
	 * 
	 * @return the option link.
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets the option link to the value given in the argument.
	 * 
	 * @param optionLink
	 *            the new option link.
	 */
	public void setOptionLink(String optionLink) {
		Objects.requireNonNull(optionLink, "The poll option link must not be null!");
		this.optionLink = optionLink;
	}

	/**
	 * Returns the poll id number.
	 * 
	 * @return the poll id numebr.
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets the poll id number to the value given in the argument.
	 * 
	 * @param pollID
	 *            the new poll id number.
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Returns the current votes count.
	 * 
	 * @return the current votes count.
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets the votes count to the value given in the argument.
	 * 
	 * @param votesCount
	 *            the new votes count.
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

}
