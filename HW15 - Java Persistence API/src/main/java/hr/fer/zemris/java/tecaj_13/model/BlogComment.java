package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Defines the model of a blog comment. Every comment has it's own unique id
 * number, a reference to the {@link BlogEntry} where the comment was made, the
 * e-mail of the user who left the comment, comment message and date when the
 * comment was posted.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {
	/**
	 * The {@link BlogComment} unique ID number. Used as primary key in the data
	 * base.
	 */
	@Id @GeneratedValue
	private Long id;
	
	/**
	 * Reference to the {@link BlogEntry} where the comment was made. Not
	 * allowed to be null.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogEntry blogEntry;
	
	/**
	 * E-mail address of user who made the comment. Not allowed to be null!
	 */
	@Column(length=100, nullable=false)
	private String usersEMail;
	
	/**
	 * The text message of the comment. Not allowed to be null.
	 */
	@Column(length = 4 * 1024, nullable = false)
	private String message;
	
	/**
	 * Time stamp of the time when the comment was made.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date postedOn;
	
	/**
	 * Returns the ID number.
	 * 
	 * @return the ID number.
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the value of the id number to the new one from the argument.
	 * 
	 * @param id
	 *            the new value for the id number.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the {@link BlogEntry} where this comment was made.
	 * 
	 * @return the {@link BlogEntry} where this comment was made.
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets the {@link BlogEntry} to the new given value.
	 * 
	 * @param blogEntry
	 *            the new value for the {@link BlogEntry}.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Returns the e-mail address of the user who made the comment.
	 * 
	 * @return the e-mail address of the user who made the comment.
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the e-mail of the user who made this comment to the new value given
	 * in the argument.
	 * 
	 * @param usersEMail
	 *            the new value of the e-mail address.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Returns the message of the comment.
	 * 
	 * @return the message of the comment.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message to the value from the argument.
	 * 
	 * @param message
	 *            the new value for the message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the time when the comment was made.
	 * 
	 * @return the time when the comment was made.
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the time when the comment was made to the new one given in the
	 * argument.
	 * 
	 * @param postedOn
	 *            the new time when the comment was made.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
