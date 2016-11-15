package hr.fer.zemris.java.gallery.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Defines all the information about a picture that is used in this photo
 * gallery. Every picture is defined by it's file name, has a description and a
 * list of categories.
 * 
 * @author Kristijan Vulinovic
 * @version 1.0
 */
public class Picture {
	
	/** The file name. */
	private String fileName;
	
	/** The description. */
	private String description;
	
	/** The categories. */
	private String[] categories;

	/**
	 * Creates a new {@link Picture} with the given file name and description.
	 *
	 * @param fileName
	 *            the file name
	 * @param description
	 *            the description
	 */
	private Picture(String fileName, String description) {
		this.fileName = fileName;
		this.description = description;
	}

	/**
	 * Creates a new {@link Picture} with the given file name, description and
	 * categories.
	 * 
	 * @param fileName
	 *            the file name
	 * @param description
	 *            the description
	 * @param categories
	 *            the categories.
	 */
	public Picture(String fileName, String description, String[] categories) {
		this(fileName, description);
		this.categories = categories;
	}

	/**
	 * Creates a new {@link Picture} with the given file name, description and
	 * categories.
	 * 
	 * @param fileName
	 *            the file name
	 * @param description
	 *            the description
	 * @param categories
	 *            a {@link String} containing all the category names. Every
	 *            category is separated with a ',' sign.
	 */
	public Picture(String fileName, String description, String categories) {
		this(fileName, description);
		
		Set<String> categorySet = new HashSet<>();
		String categoriesInList[] = categories.split(",");
		
		for (String category : categoriesInList){
			category = category.trim();
			if (category.isEmpty()) continue;
			categorySet.add(category);
		}
		
		String[] extractedCategories = new String[categorySet.size()];
		categorySet.toArray(extractedCategories);
		
		this.categories = extractedCategories;
	}

	/**
	 * Returns the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName
	 *            the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Returns the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the categories.
	 *
	 * @return the categories
	 */
	public String[] getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 *
	 * @param categories
	 *            the new categories
	 */
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
}
