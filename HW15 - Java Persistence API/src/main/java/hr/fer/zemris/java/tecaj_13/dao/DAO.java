package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface that defines the data access methods.
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
public interface DAO {

	/**
	 * Returns the {@link BlogEntry} with the specified ID. If no such entry
	 * exists, null will be returned.
	 * 
	 * @param id
	 *            the entry key.
	 * @return the found {@link BlogEntry} or null if it doesn't exist.
	 * @throws DAOException
	 *             if an error occurs while accessing the data.
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Returns a list of all the users of this blog. The list may be empty if no
	 * user exists.
	 * 
	 * @return a list of all users.
	 */
	List<BlogUser> getUsers();

	/**
	 * Returns a user with the specified username and password. If no such user
	 * exists in the database, null will be returnd.
	 * 
	 * @param username
	 *            the username of the user.
	 * @param password
	 *            the password of the user.
	 * @return The user with the specified username and password.
	 */
	BlogUser getUser(String username, String password);

	/**
	 * Checks if the given nick name already exists in the database.
	 * 
	 * @param nick
	 *            the nick name to be checked.
	 * @return a boolean value, true if the nick name already exists in the
	 *         database, false otherwise.
	 */
	boolean nickExists(String nick);

	/**
	 * Returns a {@link List} of all the {@link BlogEntry} for the author whose
	 * nick is given as the argument.
	 * 
	 * @param author
	 *            the nick of the author.
	 * @return a {@link List} of all the {@link BlogEntry} for the specified
	 *         author.
	 */
	List<BlogEntry> getBlogEntries(String author);

	/**
	 * Returns the {@link BlogUser} from the database that has the ID number as
	 * specified in the argument. If no such user is found, null is returned.
	 * 
	 * @param authorId
	 *            the ID number of the user.
	 * @return the user from the database, or null if no such user exists.
	 */
	BlogUser getUser(long authorId);
	
	/**
	 * Adds the given {@link BlogUser} to the database.
	 * 
	 * @param user
	 *            the {@link BlogComment} that should be added to the database.
	 */
	void addUser(BlogUser user);

	/**
	 * Adds the given {@link BlogEntry} to the database.
	 * 
	 * @param blogEntry
	 *            the {@link BlogEntry} that should be added to the database.
	 */
	void addEntry(BlogEntry blogEntry);

	/**
	 * Adds the given {@link BlogComment} to the database.
	 * 
	 * @param comment
	 *            the {@link BlogComment} that should be added to the database.
	 */
	void addComment(BlogComment comment);
}