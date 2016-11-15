package hr.fer.zemris.voting;

/**
 * Class holding all the data about a voting entry. Stored values are id number,
 * name of band, url of a song and number of votes earned.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class VotingEntry {
	/**
	 * The identification number of the band.
	 */
	private final int id;
	/**
	 * The name of the band.
	 */
	private final String name;
	/**
	 * The url of a representative song for the band.
	 */
	private final String url;
	/**
	 * The number of votes the band earned.
	 */
	private int numberOfVotes;
	
	/**
	 * Creates a new {@link VotingEntry} for the band with the given id number.
	 * Sets the name and song url to the given values.
	 * 
	 * @param id
	 *            the id number of the band.
	 * @param name
	 *            the name of the band.
	 * @param url
	 *            the url of a representative song for the band.
	 */
	public VotingEntry(int id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	/**
	 * Returns the band id number.
	 * 
	 * @return the band id number.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the name of the band.
	 * 
	 * @return the name of the band.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the URL of a representative song for the band.
	 * 
	 * @return the URL of a representative song for the band.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Returns the number of votes the band earned.
	 * 
	 * @return the number of votes the band earned.
	 */
	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	/**
	 * Sets the number of votes the band got to the value given in the argument.
	 * 
	 * @param numberOfVotes
	 *            the new number of votes the band got.
	 */
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
}
