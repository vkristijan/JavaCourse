package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementation of {@link DAO} that reads values from a database.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@SuppressWarnings("unchecked")
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	@Override
	public List<BlogUser> getUsers() {
		List<BlogUser> users = (List<BlogUser>) 
			JPAEMProvider.getEntityManager()
			.createQuery("SELECT u FROM BlogUser AS u")
			.getResultList();
		
		return users;
	}

	@Override
	public BlogUser getUser(String username, String password) {
		BlogUser user = null;
		try {
			user = (BlogUser) JPAEMProvider.getEntityManager() 
					.createQuery("SELECT u FROM BlogUser AS u "
								+"   WHERE u.nick=:nick "
								+"   AND u.passwordHash=:password") 
					.setParameter("nick", username)
					.setParameter("password", password)
					.getSingleResult(); 
		} catch (NoResultException e) {
			return null;
		}
		
		return user;
	}
	
	@Override
	public BlogUser getUser(long authorId) {
		BlogUser user = null;
		user = (BlogUser) JPAEMProvider.getEntityManager() 
				.createQuery("SELECT u FROM BlogUser AS u "
							+"   WHERE u.id=:id ")
				.setParameter("id", authorId)
				.getSingleResult(); 
		
		return user;
	}
	
	@Override
	public boolean nickExists(String nick) {
		List<BlogUser> users = (List<BlogUser>) 
				JPAEMProvider.getEntityManager()
				.createQuery("SELECT u FROM BlogUser AS u WHERE u.nick=:nick")
				.setParameter("nick", nick)
				.getResultList();
		return !users.isEmpty();
	}
	
	@Override
	public void addUser(BlogUser user) {
		JPAEMProvider.getEntityManager().persist(user);
	}
	
	@Override
	public void addEntry(BlogEntry blogEntry) {
		JPAEMProvider.getEntityManager().persist(blogEntry);
	}
	
	@Override
	public void addComment(BlogComment comment) {
		JPAEMProvider.getEntityManager().persist(comment);
	}
	
	@Override
	public List<BlogEntry> getBlogEntries(String author) {
		List<BlogEntry> entries = (List<BlogEntry>)
				JPAEMProvider.getEntityManager()
				.createQuery("SELECT e FROM BlogEntry AS e WHERE e.author.nick=:author")
				.setParameter("author", author)
				.getResultList();
		return entries;
	}
}