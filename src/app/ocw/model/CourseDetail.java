package app.ocw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for an OCW Course's Detail
 * 
 * @author Nick Ferraro
 *
 */
public class CourseDetail {
	private final int id;
	private final String linkhash;
	private final String title;
	private final String description;
	private final String tags;
	private final int provider;
	private final String providerName;
	private final String language;
	private final String datePublished;
	private final String linkUrl;
	private final String author;
	private final List<String> categories;
	
	public CourseDetail(int id, String linkhash, String title, String description, String tags, int provider, String providerName, String language, String datePublished, String linkUrl, String author) {
		this.id = id;
		this.linkhash = linkhash;
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.provider = provider;
		this.providerName = providerName;
		this.language = language;
		this.datePublished = datePublished;
		this.linkUrl = linkUrl;
		this.author = author;
		this.categories = new ArrayList<String>();
	}

	public int getId() {
		return id;
	}

	public String getLinkhash() {
		return linkhash;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getTags() {
		return tags;
	}

	public int getProvider() {
		return provider;
	}

	public String getProviderName() {
		return providerName;
	}

	public String getLanguage() {
		return language;
	}

	public String getDatePublished() {
		return datePublished;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public String getAuthor() {
		return author;
	}

	public List<String> getCategories() {
		return categories;
	}
}
