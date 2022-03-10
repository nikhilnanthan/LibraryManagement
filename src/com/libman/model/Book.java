package com.libman.model;
public class Book {
	private String bookTitle;
	private String category;
	private String authorName;
	private int editionNo;
	private long isbnNo;
	private int price;
	private int bookId;
	private int publishedYear;
	public int getPublishedYear() {
		return publishedYear;
	}
	public void setPublishedYear(int published_year) {
		this.publishedYear = published_year;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public int getEditionNo() {
		return editionNo;
	}
	public void setEditionNo(int editionNo) {
		this.editionNo = editionNo;
	}
	public long getIsbnNo() {
		return isbnNo;
	}
	public void setIsbnNo(long isbnNo) {
		this.isbnNo = isbnNo;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
