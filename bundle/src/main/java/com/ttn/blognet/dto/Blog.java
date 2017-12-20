package com.ttn.blognet.dto;

import org.apache.sling.api.resource.ValueMap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by vishal on 14/12/17.
 */
public class Blog {

	private String title;
	private String author;
	private String publishOn;
	private String description;
	private String[] tags;
	private Long viewCount;
	private String blogPath;

	public Blog() {
		super();
	}

	public Blog(ValueMap valueMap) {
		super();
		this.title = (String) valueMap.get("title");
		this.author = (String) valueMap.get("author");
		setPublished((Calendar) valueMap.get("cq:lastModified"));
		this.description = (String) valueMap.get("description");
		this.viewCount = Long.parseLong((String) valueMap.get("viewCount"));
	}

	public String getTitle() {
		return title;
	}

	public String getBlogPath() {
		return blogPath;
	}

	public void setBlogPath(String blogPath) {
		this.blogPath = blogPath;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishOn() {
		return publishOn;
	}

	public Date getPublishedDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return (this.publishOn != null) ? df.parse(this.publishOn) : new Date();
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public void setPublished(Calendar published) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = format1.format(published.getTime());
		this.publishOn = date1;
	}

	public void setPublishOn(String publishOn) {
		this.publishOn = publishOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public Long getViewCount() {
		return (viewCount != null) ? viewCount : 0l;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	@Override public String toString() {
		return "{" + "title:'" + title + '\'' + ", author:'" + author + '\'' + ", publishOn:'" + publishOn + '\'' + ", description:'" + description + '\'' + ", tags:'" + tags
				+ '\'' + ", viewCount:'" + viewCount + '\'' + '}';
	}
}
