package org.nebobrod.mylibrary;

public class Book {
	private int id;
	private String name;
	private String author;
	private int pages;
	private String imgUrl;
	private String shortDesc;
	private String longDesc;
	private boolean isExpanded;

	public Book(int id, String name, String author, int pages, String imgUrl, String shortDesc, String longDesc) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.pages = pages;
		this.imgUrl = imgUrl;
		this.shortDesc = shortDesc;
		this.longDesc = longDesc;
		this.isExpanded = false;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean expanded) {
		isExpanded = expanded;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", name='" + name + '\'' +
				", author='" + author + '\'' +
				", pages=" + pages +
				", imgUrl='" + imgUrl + '\'' +
				", shortDesc='" + shortDesc + '\'' +
				", longDesc='" + longDesc + '\'' +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}
}
