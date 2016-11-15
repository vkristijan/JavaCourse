package hr.fer.zemris.voting;

/**
 * Class holding all the data about a voting result. Stored values are id number
 * and number of votes.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class VotingResult {
	/**
	 * The identification number of the band.
	 */
	private final int id;
	/**
	 * The number of votes the bang earned.
	 */
	private int numberOfVotes;

	/**
	 * Creates a new {@link VotingResult} for the given id and with the given
	 * number of votes.
	 * 
	 * @param id
	 *            the identification number of the band.
	 * @param numberOfVotes
	 *            the number of votes the bang earned.
	 */
	public VotingResult(int id, int numberOfVotes) {
		this.id = id;
		this.numberOfVotes = numberOfVotes;
	}

	/**
	 * Increments the number of votes the band got.
	 */
	public void addVote(){
		numberOfVotes++;
	}
	
	/**
	 * Returns the band id number.
	 * 
	 * @return the band id number.
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Returns the number of votes for the band.
	 * 
	 * @return the number of votes for the band.
	 */
	public int getNumberOfVotes(){
		return numberOfVotes;
	}
	
	@Override
	public String toString() {
		return id + "\t" + numberOfVotes;
	}
}
