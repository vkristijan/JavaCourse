package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * Interface defining the data access object for the database. Defines methods
 * to read and modify data.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface DAO {
	/**
	 * Returns a list of all the polls in the database.
	 * 
	 * @return a list of all the polls in the database.
	 */
	List<Poll> getPolls();
	
	/**
	 * Returns all the poll options for the poll with the given id. The poll
	 * options are returned in an sorted order, descending on the number of
	 * votes.
	 * 
	 * @param pollId
	 *            the id of the poll.
	 * @return all the poll options for the poll with the given id.
	 */
	List<PollOption> getPollOptions(long pollId);
	
	/**
	 * Adds a vote to the poll option with the given id.
	 * 
	 * @param pollOptionId
	 *            the id of the poll option where the vote should be added.
	 */
	void addVote(long pollOptionId);
	
	/**
	 * Returns the vote count for the poll option with the given id.
	 * 
	 * @param pollOptionId
	 *            the id of the poll option.
	 * @return the vote count for the poll option with the given id, or 0 if the
	 *         id was not found in the database.
	 */
	long getVoteCount(long pollOptionId); 
	
	/**
	 * Returns the poll that is defined with the id given in the argument.
	 * 
	 * @param pollId
	 *            the id of the wanted poll.
	 * @return a new poll object holding the data about the poll from the
	 *         database, or null if the poll doesn't exist.
	 */
	Poll getPoll(long pollId);
	
	/**
	 * Returns the id of the poll which option is given in the argument.
	 * 
	 * @param pollOptionId
	 *            the id of the poll option.
	 * @return the id of the poll.
	 */
	long getPollId(long pollOptionId);
}