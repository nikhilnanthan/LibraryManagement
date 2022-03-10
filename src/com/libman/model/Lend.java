package com.libman.model;


public class Lend {
	private int lendId;
	private String issuedDate;
	private String returnDate;
	private boolean isReturned;
	private String bookTitle;
	private int paybill;
	private String userName;
	private int bookId;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPaybill() {
		return paybill;
	}
	public void setPaybill(int paybill) {
		this.paybill = paybill;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String book_title) {
		this.bookTitle = book_title;
	}
	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}
	public int getLendId() {
		return lendId;
	}
	public void setLendId(int lendId) {
		this.lendId = lendId;
	}
	public String getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public boolean getIsReturned() {
		return isReturned;
	}
	public void setIsReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
}
