package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Defines the model of a blog entry that is used in the database. A single blog
 * entry consists of a id number that is used as the primary key in the
 * database. A time stamp defining the time when the entry was created, a time
 * stamp defining the time of the last modification of the {@link BlogEntry}, a
 * string representation of the title and text.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 *
 */
@NamedQueries({
	@NamedQuery(
		name="BlogEntry.upit1",
		query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when"
	)
})
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * The unique ID number of the {@link BlogEntry}. Used as primary key in the
	 * database. Not allowed to be null.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * A {@link List} of {@link BlogComment}s that are written for this
	 * {@link BlogEntry}.
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	private List<BlogComment> comments = new ArrayList<>();

	/**
	 * The time of creation of this {@link BlogEntry}. Not allowed to be null.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdAt;

	/**
	 * The time of the last modification of this {@link BlogEntry}. Not allowed
	 * to be null.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date lastModifiedAt;

	/**
	 * The title of this {@link BlogEntry}. Not allowed to be null.
	 */
	@Column(nullable = false, length = 60)
	private String title;
	
	/**
	 * The text of this {@link BlogEntry}. Not allowed to be null.
	 */
	@Column(nullable = false, length = 4 * 1024)
	private String text;
	
	/**
	 * The user who has written this {@link BlogEntry}. Not allowed to be null.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogUser author;

	/**
	 * Returns the author of this {@link BlogEntry}.
	 * 
	 * @return the author of this {@link BlogEntry}, as a {@link BlogUser}.
	 */
	public BlogUser getAuthor() {
		return author;
	}

	/**
	 * Sets the value of the author to the new one given in the argument.
	 * 
	 * @param author
	 *            a {@link BlogUser} that is the author of this
	 *            {@link BlogEntry}.
	 */
	public void setAuthor(BlogUser author) {
		this.author = author;
	}

	/**
	 * Returns the value of the Id number.
	 * 
	 * @return the value of the id number.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the value of the ID number to the one from the argument.
	 * 
	 * @param id
	 *            the new value of the id number.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns a list of all the {@link BlogComment}s for this {@link BlogEntry}
	 * .
	 * 
	 * @return a list of all the {@link BlogComment}s for this {@link BlogEntry}
	 *         .
	 */
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets the list of comments to the value given in the argument.
	 * 
	 * @param comments
	 *            a {@link List} of {@link BlogComment} for this
	 *            {@link BlogEntry}.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Returns the time when this {@link BlogEntry} was created.
	 * 
	 * @return the time when this {@link BlogEntry} was created.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the time when this {@link BlogEntry} was created to the value from
	 * the argument.
	 * 
	 * @param createdAt
	 *            the new value for the time when this {@link BlogEntry} was
	 *            created.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Returns the time when this {@link BlogEntry} was last time modified.
	 * 
	 * @return the time when this {@link BlogEntry} was last time modified.
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the time of the last modification to the new value given in the
	 * argument.
	 * 
	 * @param lastModifiedAt
	 *            the new value for the time when this entry was modified.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Returns the current title of the {@link BlogEntry}.
	 * 
	 * @return the title of the {@link BlogEntry}.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of this blog entry to the new one given in the argument.
	 * 
	 * @param title
	 *            the new value for the {@link BlogEntry} title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the text of the {@link BlogEntry}.
	 * 
	 * @return the text of the {@link BlogEntry}.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of the blog entry to the new value given in the argument.
	 * 
	 * @param text
	 *            the new value for the text.
	 */
	public void setText(String text) {
		this.text = text;
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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}
}
