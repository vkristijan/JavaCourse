package hr.fer.zemris.java.tecaj_13.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import hr.fer.zemris.java.tecaj_13.dao.jpa.DAOProvider;

/**
 * Defines the model of a blog user in the database. The user has it's first
 * name, last name, nick name, email and password.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@Entity
@Table(
	name = "blog_users",
	uniqueConstraints = @UniqueConstraint(columnNames = "nick")
)
public class BlogUser {
	/**
	 * The ID number of the user. Used as primary key in the database.
	 */
	@Id @GeneratedValue
	private long id;
	
	/**
	 * The first name of the user. It's not allowed to be null, maximal size is
	 * 25.
	 */
	@Column(nullable = false, length = 25)
	private String firstName;
	/**
	 * The last name of the user. It's not allowed to be null, maximal size is
	 * 25.
	 */
	@Column(nullable = false, length = 25)
	private String lastName;
	/**
	 * The user's nick name. It's not allowed to be null, maximal size is 25.
	 */
	@Column(nullable = false, length = 25)
	private String nick;
	/**
	 * The user's email address. It's not allowed to be null, maximal size is
	 * 25.
	 */
	@Column(nullable = false, length = 50)
	private String email;
	/**
	 * The hash value of the password for this user. It's not allowed to be
	 * null, maximal size is 25.
	 */
	@Column(nullable = false, length = 50)
	private String passwordHash;
	
	/**
	 * List of entries that this user has written. Not allowed to be null.
	 */
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("lastModifiedAt ASC")
	private List<BlogEntry> entries;
	
	/**
	 * Returns all the entries this user has written.
	 * 
	 * @return a {@link List} of {@link BlogEntry} that this user has written.
	 */
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Sets the list of entries that this user has written to the new value
	 * given in the argument.
	 * 
	 * @param entries
	 *            the list of entries that this user has written.
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	/**
	 * Returns the id of the user.
	 * 
	 * @return the id of the user.
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Sets the id of the user to the new value given in the argument.
	 * 
	 * @param id
	 *            the new value for the user id number.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the first name of the user.
	 * 
	 * @return the first name of the user.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the first name of the user to the new value given in the argument.
	 * 
	 * @param firstName
	 *            the new value for the first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Returns the last name of the user.
	 * 
	 * @return the last name of the user.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the last name of the user to the value given in the argument.
	 * 
	 * @param lastName
	 *            the new value for the user first name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Returns the user nick name.
	 * @return the user nick name.
	 */
	public String getNick() {
		return nick;
	}
	
	/**
	 * Sets the user nick name to the new value given in the argument.
	 * 
	 * @param nick
	 *            the new value for the user nick name.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Returns the user email address.
	 * 
	 * @return the user email address.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the user email address to the new value given in the argument.
	 * 
	 * @param email
	 *            the new value for the user email address.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns the value of the password hash.
	 * 
	 * @return the value of the password hash.
	 */
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Sets the hash value of the password to the value given in the argument.
	 * 
	 * @param passwordHash
	 *            the new value of the password hash.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BlogUser other = (BlogUser) obj;
		if (id != other.id) return false;
		return true;
	}
	
	/**
	 * Checks if this user satisfies all the criteria for the database. Checks
	 * if the username is unique or not, if all the data are given and are not
	 * greater then the maximal length.
	 * 
	 * @return a {@link Map} where the key defines the value where an error
	 *         occurred, and the value is a error message that should be
	 *         displayed to the user. If everything is right, the map will be
	 *         empty.
	 */
	public Map<String, String> isValid(){
		Map<String, String> errors = new HashMap<String, String>();
		
		if (DAOProvider.getDAO().nickExists(nick)){
			errors.put("username", "The username already exists.");
		}
		
		if (firstName == null || firstName.isEmpty()){
			errors.put("firstName", "The first name was not given!");
		} else if (firstName.length() > 25){
			errors.put("firstName", "The first name is to long! Maximal allowed size is 25.");
		}
		
		if (lastName == null || lastName.isEmpty()){
			errors.put("lastName", "The last name was not given!");
		} else if (lastName.length() > 25){
			errors.put("lastName", "The last name is to long! Maximal allowed size is 25.");
		}
		
		if (email == null || email.isEmpty()){
			errors.put("email", "The email address was not given!");
		} else if (!email.contains("@")){
			errors.put("email", "The given e-mail address is invalid.");
		}
		
		if (firstName == null || firstName.isEmpty()){
			errors.put("password", "The password was not given!");
		} 
		return errors;
	}
}
