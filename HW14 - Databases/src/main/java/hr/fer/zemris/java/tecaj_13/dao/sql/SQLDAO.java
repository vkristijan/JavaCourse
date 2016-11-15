package hr.fer.zemris.java.tecaj_13.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

/**
 * {@link DAO} implementation by using SQL. This implementation expects to get a
 * connection over {@link SQLConnectionProvider} class.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public class SQLDAO implements DAO {
	@Override
	public List<Poll> getPolls() {
		List<Poll> results = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			String command = "SELECT * FROM Polls";
			pst = con.prepareStatement(command);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						
						results.add(poll);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during reading polls.", ex);
		}
		return results;
	}

	@Override
	public List<PollOption> getPollOptions(long pollId) {
		List<PollOption> results = new ArrayList<>();
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			String command = 	"SELECT * FROM PollOptions WHERE pollID = ?" +
								"	ORDER BY votesCount DESC";
			pst = con.prepareStatement(command);
			pst.setLong(1, pollId);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setPollID(rs.getLong(4));
						pollOption.setVotesCount(rs.getLong(5));
						
						results.add(pollOption);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during reading poll options.", ex);
		}
		return results;
	}

	@Override
	public void addVote(final long pollOptionId) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			final String command = 	"UPDATE PollOptions" +
									"	SET votesCount = ?" +
									"	WHERE id = ?";
			pst = con.prepareStatement(command);
			pst.setLong(1, getVoteCount(pollOptionId) + 1);
			pst.setLong(2, pollOptionId);
			try {
				pst.execute();

			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during updating votes.", ex);
		}
	}

	@Override
	public long getVoteCount(long pollOptionId) {
		long voteCount = 0;
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			final String command = 	"SELECT votesCount FROM PollOptions WHERE id = ?";
			pst = con.prepareStatement(command);
			pst.setLong(1, pollOptionId);
			try {
				ResultSet rs = pst.executeQuery();
				
				try {
					while (rs != null && rs.next()) {
						voteCount = rs.getLong(1);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}

			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during reading votes.", ex);
		}
		
		return voteCount;
	}

	@Override
	public Poll getPoll(long pollId) {
		Poll poll = null;
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			final String command = 	"SELECT * FROM Polls WHERE id = ?";
			pst = con.prepareStatement(command);
			pst.setLong(1, pollId);
			try {
				ResultSet rs = pst.executeQuery();
				
				try {
					while (rs != null && rs.next()) {
						poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}

			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during reading id.", ex);
		}
		
		return poll;
	}

	@Override
	public long getPollId(long pollOptionId) {
		long pollId = 0;
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			final String command = 	"SELECT pollID FROM PollOptions WHERE id = ?";
			pst = con.prepareStatement(command);
			pst.setLong(1, pollOptionId);
			try {
				ResultSet rs = pst.executeQuery();
				
				try {
					while (rs != null && rs.next()) {
						pollId = rs.getLong(1);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}

			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during reading id.", ex);
		}
		
		return pollId;
	}

}